package org.galatea.pocpnl.service.valuation;

import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;
import org.galatea.pocpnl.domain.ValuationResult;

@Builder
@Value
public class ValuationResponse {

  private ValuationResult valuationResult;

  @Default
  private ValuationInput valuationInput = new ValuationInput();

  @Default
  private Set<String> missingInput = new HashSet<>();

  public boolean isMoreDataNeeded() {
    return !missingInput.isEmpty();
  }
}
