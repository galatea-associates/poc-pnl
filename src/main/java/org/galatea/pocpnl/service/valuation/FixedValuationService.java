package org.galatea.pocpnl.service.valuation;

import java.math.BigDecimal;
import org.galatea.pocpnl.domain.ValuationResult;
import org.springframework.stereotype.Service;

@Service
public class FixedValuationService implements IValuationService {

  @Override
  public ValuationResponse value(ValuationInput input) {
    return ValuationResponse.builder().valuationResult(ValuationResult.builder().valuation(new BigDecimal(10)).build()).valuationInput(input).build();

  }

}
