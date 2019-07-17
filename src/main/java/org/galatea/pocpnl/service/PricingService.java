package org.galatea.pocpnl.service;

import org.galatea.pocpnl.domain.Instrument;
import org.galatea.pocpnl.repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PricingService {

  @Autowired
  private InstrumentRepository instrumentRepository;

  public double getInstrumentPrice(String instrumentId) {
    Instrument instrument = instrumentRepository.getByInstrumentId(instrumentId).get();
    return instrument.getSpotPrice();
  }

}
