package org.galatea.pocpnl.service.valuation;

import org.galatea.pocpnl.domain.Valuation;
import org.springframework.stereotype.Service;

@Service
public class FixedValuationService implements IValuationService {

  @Override
  public ValuationResponse value(ValuationInput input) {
	  return ValuationResponse.builder().valuationResult(Valuation.builder().valuation(10.0).build()).valuationInput(input).build();
  }

}
