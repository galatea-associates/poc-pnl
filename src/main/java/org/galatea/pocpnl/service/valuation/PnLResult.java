package org.galatea.pocpnl.service.valuation;

import lombok.Value;

@Value
public class PnLResult {
  double pnl;
  ValuationResult currentValuation;
  ValuationKey valuationReferenceKey;
}
