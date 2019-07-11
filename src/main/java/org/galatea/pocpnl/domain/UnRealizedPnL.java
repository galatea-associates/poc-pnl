package org.galatea.pocpnl.domain;

import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UnRealizedPnL {

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "referenceValuationId", referencedColumnName = "id")
  private Valuation referenceValuation;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "currentValuationId", referencedColumnName = "id")
  private Valuation currentValuation;

  private BigDecimal mtmPnL;
  private BigDecimal mtmPnLFx;
  private BigDecimal fxPnL;


}
