package org.galatea.pocpnl.service;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.galatea.pocpnl.domain.FxRate;
import org.galatea.pocpnl.repository.FxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FxService {

  @Autowired
  private FxRepository fxRepository;

  public double getRate(String from, String to) {
    log.info("Retrieving FX Rate from {} to {}", from, to);
    Optional<FxRate> fxRate = fxRepository.getByFromCurrencyAndToCurrency(from, to);

    return fxRate.get().getRate();
  }
}
