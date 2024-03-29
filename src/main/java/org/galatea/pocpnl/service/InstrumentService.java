package org.galatea.pocpnl.service;

import java.time.LocalDate;
import org.galatea.pocpnl.domain.InstrumentStaticData;
import org.galatea.pocpnl.repository.InstrumentStaticDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstrumentService {

  @Autowired
  private InstrumentStaticDataRepository instrumentStaticDataRepository;

  public String getInstrumentCurrency(String instrumentId) {
    InstrumentStaticData instrumentStaticData =
        instrumentStaticDataRepository.getByInstrumentId(instrumentId).get();
    return instrumentStaticData.getCurrency();
  }

  public String getInstrumentAssetType(String instrumentId) {
    InstrumentStaticData instrumentStaticData =
        instrumentStaticDataRepository.getByInstrumentId(instrumentId).get();
    return instrumentStaticData.getAssetType().name();
  }

  public LocalDate getInstrumentMaturityDate(String instrumentId) {
    InstrumentStaticData instrumentStaticData =
        instrumentStaticDataRepository.getByInstrumentId(instrumentId).get();
    return instrumentStaticData.getMaturityDate();
  }

  public double getInstrumentCouponRate(String instrumentId) {
    InstrumentStaticData instrumentStaticData =
        instrumentStaticDataRepository.getByInstrumentId(instrumentId).get();
    return instrumentStaticData.getCouponRate();
  }

}
