package org.galatea.pocpnl.domain;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ValuationResult {

  private BigDecimal instrumentCurrencyValuation;
  private BigDecimal bookCurrencyValuation;
  private BigDecimal accruedAmortization;
  private BigDecimal bookValue;
  private double fxRate;
  private BigDecimal interestAccrued;

}
