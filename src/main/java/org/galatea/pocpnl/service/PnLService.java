package org.galatea.pocpnl.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.galatea.pocpnl.domain.PnL;
import org.galatea.pocpnl.domain.Position;
import org.galatea.pocpnl.domain.RealizedPnl;
import org.galatea.pocpnl.domain.Trade;
import org.galatea.pocpnl.domain.UnRealizedPnL;
import org.galatea.pocpnl.domain.Valuation;
import org.galatea.pocpnl.repository.PnLRepository;
import org.galatea.pocpnl.repository.PositionRepository;
import org.galatea.pocpnl.repository.TradeRepository;
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
  private static final String BOOK_CURRENCY = "Book.Currency";
  private static final String INSTRUMENT_CURRENCY = "Instrument.Currency";
  private static final String FX_RATE = "FX.Rate";

  @Autowired
  private IValuationService valuationService;

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private ValuationRepository valuationRepository;

  @Autowired
  private PnLRepository pnlRepository;

  @Autowired
  private TradeRepository tradeRepository;

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

      Set<String> instruments = getInstrumentsForBook(book, referenceDate, eodDate);

      for (String instrument : instruments) {
        log.info("Calculating P&L for [{}, {}]", book, instrument);

        List<Position> positions = getPositionsForBookInstrument(book, instrument);
        Position position = positions.get(0);
        UnRealizedPnL unrealizedPnl = calculateUnrealizedPnL(eodDate, referenceDate, position);

        // TODO: now that we have the positional component of P&L, need the realized P&L
        // (from trades made
        // after the reference date up to and including today)
        List<Trade> trades = getTradesForBookInstrument(book, instrument, referenceDate, eodDate);
        RealizedPnl realizedPnl = calculateRealizedPnL(trades);
        log.info("Calculated realized P&L for [{}, {}]: {}", book, instrument, realizedPnl);

        PnL pnlResult = PnL.builder().book(book).instrument(instrument).date(eodDate)
            .unrealizedPnL(unrealizedPnl).realizedPnL(realizedPnl).build();
        persistPnL(pnlResult);
      }
    }

    log.info("PnL Calculation completed");
  }

  private UnRealizedPnL calculateUnrealizedPnL(LocalDate eodDate, LocalDate referenceDate,
      Position position) {
    log.info("Valuating position: {}", position);
    ValuationResponse valuationResponse = getValuation(position);
    log.info("Valuation for position {}: {}", position, valuationResponse);

    // now need to get reference valuation to calculate P&L
    // TODO: do we really need this Object?
    ValuationKey referenceValuationKey = getReferenceValuationKey(position, referenceDate);

    Valuation referenceValuation = getReferenceValuation(referenceValuationKey);
    Valuation currentValuation = Valuation.builder().book(position.getBook())
        .instrument(position.getInstrument()).date(eodDate)
        .instrumentCurrencyValuation(
            valuationResponse.getValuationResult().getInstrumentCurrencyValuation())
        .bookCurrencyValuation(
            valuationResponse.getValuationResult().getBookCurrencyValuation())
        .valuationInput(valuationResponse.getValuationInput())
        .fxRate(valuationResponse.getValuationResult().getFxRate()).build();

    UnRealizedPnL pnlResult =
        calculatePnl(currentValuation, referenceValuation, referenceValuationKey);
    log.info("P&L result: {}", pnlResult);
    return pnlResult;
  }

  private RealizedPnl calculateRealizedPnL(List<Trade> trades) {
    RealizedPnl realizedPnl = new RealizedPnl();
    for (Trade trade : trades) {
      log.info("Applying impact of trade {} to realized P&L", trade);
      // calculate Realized PnL
      realizedPnl.addProceeds(trade.getValue());
      realizedPnl.addFees(trade.getFee());
      realizedPnl.addCommissions(trade.getCommission());
      log.info("Applied impact of trade {} to realized P&L: {}", trade, realizedPnl);
    }
    return realizedPnl;
  }

  private ValuationResponse getValuation(Position position) {
    ValuationInput valuationInput = new ValuationInput();

    ValuationResponse valuationResponse = valuationService.value(valuationInput);
    while (valuationResponse.isMoreDataNeeded()) {
      log.info("More data needed for valuating position: {}, {}", position,
          valuationResponse);
      // get more data and revalue..
      valuationInput = augmentValuationInput(valuationResponse, position);

      // What should we do if we can't find inputs required for valuation?
      valuationResponse = valuationService.value(valuationInput);
    }
    return valuationResponse;
  }

  private void persistPnL(PnL pnlResult) {
    log.info("Persisting P&L: {}", pnlResult);
    pnlRepository.save(pnlResult);
  }

  private UnRealizedPnL calculatePnl(Valuation currentValuation, Valuation referenceValuation,
      ValuationKey valuationReferenceKey) {

    BigDecimal mtmPnL = currentValuation.getInstrumentCurrencyValuation()
        .subtract(referenceValuation.getInstrumentCurrencyValuation());

    BigDecimal mtmPnLFx = mtmPnL.multiply(BigDecimal.valueOf(currentValuation.getFxRate()));

    BigDecimal fxPnL = currentValuation.getBookCurrencyValuation()
        .subtract(referenceValuation.getBookCurrencyValuation());

    log.info("Calculated P&L {} from {}, {}", mtmPnL, currentValuation, referenceValuation);
    return UnRealizedPnL.builder()
        .referenceValuation(referenceValuation)
        .currentValuation(currentValuation)
        .mtmPnL(mtmPnL).mtmPnLFx(mtmPnLFx).fxPnL(fxPnL).build();
  }

  private Valuation getReferenceValuation(ValuationKey valuationReferenceKey) {
    // Fetch reference valuation
    log.info("Fetching reference valuation for {}", valuationReferenceKey);
    Valuation result =
        valuationRepository.findByBookAndInstrumentAndDate(valuationReferenceKey.getBook(),
            valuationReferenceKey.getInstrument(), valuationReferenceKey.getDate()).get();
    log.info("Fetched reference valuation for {}: {}", valuationReferenceKey, result);
    return result;
  }

  private ValuationKey getReferenceValuationKey(Position position, LocalDate referenceDate) {
    return new ValuationKey(position.getInstrument(), position.getBook(), referenceDate);
  }

  private ValuationInput augmentValuationInput(ValuationResponse valuationResponse,
      Position position) {
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
          double spotPrice = position.getCostBasis(); // (int) (position.getCostBasis() * (.9 +
                                                      // Math.random() / 5)
                                                      // * 100) / 100d;
          inputData.addInput(input, spotPrice);
          break;
        case QTY:
          inputData.addInput(input, position.getQty());
          break;
        case INSTRUMENT_CURRENCY:
          inputData.addInput(input, "HK");
          break;
        case BOOK_CURRENCY:
          inputData.addInput(input, "USD");
          break;
        case FX_RATE:
          inputData.addInput(input, 1.1);
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

  private Set<String> getBooks() {
    Set<String> books = positionRepository.findDistinctBook();
    log.info("Found books: {}", books);
    return books;
  }

  private LocalDate getReferenceDate(LocalDate eodDate) {
    return eodDate.minusDays(1);
  }

  // get all instruments this book has either a position in on the eodDate, or a
  // trade after the
  // referenceDate up to the eodDate
  private Set<String> getInstrumentsForBook(String book, LocalDate referenceDate,
      LocalDate eodDate) {
    // List<String> instrumentsFromTrades = tradeRepository.
    Set<String> instruments = positionRepository.findDistinctInstrumentByBookAndDate(book, eodDate);
    instruments.addAll(tradeRepository.findDistinctInstrumentByBookAndTradeDateBetween(book,
        referenceDate.plusDays(1), eodDate));
    log.info("Found {} instruments for book {}: {}", instruments.size(), book, instruments);
    return instruments;
  }

  private List<Trade> getTradesForBookInstrument(String book, String instrument, LocalDate fromDate,
      LocalDate toDate) {

    List<Trade> trades =
        tradeRepository.findByBookAndInstrumentAndTradeDateBetween(book, instrument,
            fromDate.plusDays(1), toDate);
    log.info("Found {} trades for book {}, instrument {} after {} through {}: {}", trades.size(),
        book, instrument,
        fromDate, toDate, trades);
    return trades;
  }

  private List<Position> getPositionsForBookInstrument(String book, String instrument) {
    List<Position> positions = positionRepository.findByBookAndInstrument(book, instrument);
    log.info("Found {} positions for [{}, {}]: {}", positions.size(), book, instrument, positions);
    return positions;
  }

}
