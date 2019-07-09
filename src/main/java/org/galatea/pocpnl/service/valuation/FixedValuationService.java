package org.galatea.pocpnl.service.valuation;

import org.springframework.stereotype.Service;

@Service
public class FixedValuationService implements IValuationService {

  @Override
  public ValuationResponse value(ValuationInput input) {
    return new ValuationResponse(new ValuationResult(43.0, "USD"), input);
  }

}
