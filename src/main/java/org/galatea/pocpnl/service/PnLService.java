package org.galatea.pocpnl.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.galatea.pocpnl.domain.PnL;
import org.galatea.pocpnl.domain.Position;
import org.galatea.pocpnl.domain.Valuation;
import org.galatea.pocpnl.repository.PnLRepository;
import org.galatea.pocpnl.repository.PositionRepository;
import org.galatea.pocpnl.repository.ValuationRepository;
import org.galatea.pocpnl.service.valuation.IValuationService;
import org.galatea.pocpnl.service.valuation.ValuationInput;
import org.galatea.pocpnl.service.valuation.ValuationKey;
import org.galatea.pocpnl.service.valuation.ValuationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PnLService {

	private static final String BOOK = "Position.Book";
	private static final String INSTRUMENT = "Position.Instrument";
	private static final String QTY = "Position.Quantity";
	private static final String PRICE = "Instrument.Price";
	private static final String CURRENCY = "Instrument.Currency";

	@Autowired
	private IValuationService valuationService;

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private ValuationRepository valuationRepository;

	@Autowired
	private PnLRepository pnlRepository;

	@Transactional
	public void calculateEODPnL(LocalDate eodDate) {
		log.info("Calculating EOD P&L for: {}", eodDate);

		// get date for reference valuations for P&L calculations
		LocalDate referenceDate = getReferenceDate(eodDate);
		log.info("Retrieved reference date for P&L calculations: {}", referenceDate);

		// Get books
		Set<String> books = getBooks();

		for (String book : books) {
			log.info("Calculating P&L for book: {}", book);
			List<Position> positions = getPositionsForBook(book);

			for (Position position : positions) {
				log.info("Valuating position: {}", position);
				ValuationInput valuationInput = new ValuationInput();

				ValuationResponse valuationResponse = valuationService.value(valuationInput);
				if (valuationResponse.isMoreDataNeeded()) {
					log.info("More data needed for valuating position: {}, {}", position, valuationResponse);
					// get more data and revalue..
					valuationInput = augmentValuationInput(valuationResponse, position);

					// What should we do if we can't find inputs required for valuation?
					valuationResponse = valuationService.value(valuationInput);
				}

				log.info("Valuation for position {}: {}", position, valuationResponse);

				// now need to get reference valuation to calculate P&L
				ValuationKey referenceValuationKey = getReferenceValuationKey(position, referenceDate);

				Valuation referenceValuation = getReferenceValuation(referenceValuationKey);
				Valuation currentValuation = Valuation.builder().book(position.getBook())
						.instrument(position.getInstrument()).date(eodDate)
						.valuation(valuationResponse.getValuationResult().getValuation()).valuationInput(valuationResponse.getValuationInput()).build();

				PnL pnlResult = calculatePnl(currentValuation, referenceValuation, referenceValuationKey);
				log.info("P&L result: {}", pnlResult);
				persistPnL(pnlResult);
			}
		}

		log.info("PnL Calculation completed");
	}

	private void persistPnL(PnL pnlResult) {
		log.info("Persisting P&L: {}", pnlResult);
		pnlRepository.save(pnlResult);
	}

	private PnL calculatePnl(Valuation currentValuation, Valuation referenceValuation,
			ValuationKey valuationReferenceKey) {
		BigDecimal pnl = currentValuation.getValuation().subtract(referenceValuation.getValuation());
		log.info("Calculated P&L {} from {}, {}", pnl, currentValuation, referenceValuation);
		return PnL.builder().book(valuationReferenceKey.getBook()).instrument(valuationReferenceKey.getInstrument())
				.date(LocalDate.now()).referenceValuation(referenceValuation).currentValuation(currentValuation)
				.pnl(pnl).build();
	}

	private Valuation getReferenceValuation(ValuationKey valuationReferenceKey) {
		// Fetch reference valuation
		log.info("Fetching reference valuation for {}", valuationReferenceKey);
		List<Valuation> results = valuationRepository.findByBookAndInstrumentAndDate(valuationReferenceKey.getBook(),
				valuationReferenceKey.getInstrument(), valuationReferenceKey.getDate());
		Valuation result = results.get(0);
		log.info("Fetched reference valuation for {}: {}", valuationReferenceKey, result);
		return result;
	}

	private ValuationKey getReferenceValuationKey(Position position, LocalDate referenceDate) {
		return new ValuationKey(position.getInstrument(), position.getBook(), referenceDate);
	}

	private ValuationInput augmentValuationInput(ValuationResponse valuationResponse, Position position) {
		ValuationInput inputData = valuationResponse.getValuationInput();
		Set<String> missingInput = valuationResponse.getMissingInput();

		for (String input : missingInput) {
			log.debug("Including position {} to ValuationInput data {}", input, inputData);
			switch (input) {
			case BOOK:
				inputData.addInput(input, position.getBook());
				break;
			case INSTRUMENT:
				inputData.addInput(input, position.getInstrument());
				break;
			case PRICE:
				// TODO: lookup price for this instrument
				// for now, move the price somewhere within +/- 10% of the cost basis for the
				// position
				double spotPrice = (int) (position.getCostBasis() * (.9 + Math.random() / 5) * 100) / 100d;
				inputData.addInput(input, spotPrice);
				break;
			case QTY:
				inputData.addInput(input, position.getQty());
				break;
			case CURRENCY:
				// TODO: lookup currency for this instrument
				inputData.addInput(input, "USD");
				break;
			default:
				// TODO: handle this error.
				log.error("Don't know how to get {} from position", input);
				break;
			}
			log.debug("Included position {} to ValuationInput data {}", input, inputData);
		}

		log.info("Augmented ValuationInput: {}", inputData);
		return inputData;
	}

	private List<Position> getPositionsForBook(String book) {
		List<Position> positions = positionRepository.findByBook(book);
		log.info("Found {} positions for book {}: {}", positions.size(), book, positions);
		return positions;
	}

	private Set<String> getBooks() {
		Set<String> books = positionRepository.findDistinctBook();
		log.info("Found books: {}", books);
		return books;
	}

	private LocalDate getReferenceDate(LocalDate eodDate) {
		return eodDate.minusDays(1);
	}

}
