package org.galatea.pocpnl.domain;

import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
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

  @Default
  private BigDecimal mtmPnL = new BigDecimal(0);;
  @Default
  private BigDecimal mtmPnLFx = new BigDecimal(0);;
  @Default
  private BigDecimal fxPnL = new BigDecimal(0);;


  public void addMtmPnL(BigDecimal mtmPnl) {
    this.mtmPnL = this.mtmPnL.add(mtmPnl);
  }

  public void addMtmPnLFx(BigDecimal mtmPnLFx) {
    this.mtmPnLFx = this.mtmPnLFx.add(mtmPnLFx);
  }

  public void addFxPnL(BigDecimal fxPnL) {
    this.fxPnL = this.fxPnL.add(fxPnL);
  }


}
