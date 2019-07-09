package org.galatea.pocpnl.service.valuation;

import java.util.HashSet;
import java.util.Set;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

@Builder
@Value
public class ValuationResponse {

  private ValuationResult valuationResult;
  
  @Default
  private ValuationInput valuationInput = new ValuationInput();
  
  @Default
  private Set<String> missingInput = new HashSet<String>();

  public boolean isMoreDataNeeded() {
	  return !missingInput.isEmpty();
  }
}
