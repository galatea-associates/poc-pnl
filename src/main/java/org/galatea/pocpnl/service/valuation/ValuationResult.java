package org.galatea.pocpnl.service.valuation;

import lombok.Value;

@Value
public class ValuationResult {
  private final double valuation;
  private final String currency;

}
