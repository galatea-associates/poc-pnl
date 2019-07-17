package org.galatea.pocpnl.service.valuation;

import java.util.Set;
import lombok.Getter;
import org.galatea.pocpnl.domain.InputData;

public class InputDataException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  @Getter
  private final Set<InputData> missingInput;

  public InputDataException(Set<InputData> missingInput) {
    super();
    this.missingInput = missingInput;
  }
}
