package org.galatea.pocpnl.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.galatea.pocpnl.domain.Instrument;
import org.galatea.pocpnl.domain.Position;
import org.galatea.pocpnl.repository.InstrumentRepository;
import org.galatea.pocpnl.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimulatorService {

	@Autowired
	private InstrumentRepository instrumentRepository;
	
	@Autowired PositionRepository positionRepository;
	
	@Autowired
	private PnLService pnlService;
	
	public void simulateNextEOD() {
		
	    LocalDate mostRecentDate = positionRepository.findMostRecentDate();

		List<Position> positions = positionRepository.findByDate(mostRecentDate);
		
		List<Position> newPositions = new ArrayList<>();
		for (Position position: positions) {
			Position newPosition = Position.builder()
					.book(position.getBook())
					.instrument(position.getInstrument())
					.date(mostRecentDate.plusDays(1))
					.qty(position.getQty()+10)
					.costBasis(position.getCostBasis())
					.openDate(position.getOpenDate())
					.ytm(position.getYtm())
					.build();
			newPositions.add(newPosition);
		}
		
		positionRepository.saveAll(newPositions);
		
		List<Instrument> instruments = instrumentRepository.findAll();
		for (Instrument instrument: instruments) {
			double spotPrice = (int) (instrument.getSpotPrice() * (.9 + Math.random() / 5) * 100) /100d;
			instrument.setSpotPrice(spotPrice);
		}
		instrumentRepository.saveAll(instruments);
		
		pnlService.calculateEODPnL(mostRecentDate.plusDays(1));

	}
}
