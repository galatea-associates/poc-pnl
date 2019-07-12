
package org.galatea.pocpnl.domain;

import java.math.BigDecimal;
import javax.persistence.Embeddable;
import lombok.Data;


@Embeddable
@Data
public class RealizedPnl {

  BigDecimal proceeds = new BigDecimal(0);
  BigDecimal fees = new BigDecimal(0);
  BigDecimal commissions = new BigDecimal(0);

  BigDecimal ltdProceeds = new BigDecimal(0);
  BigDecimal ltdFees = new BigDecimal(0);
  BigDecimal ltdCommissions = new BigDecimal(0);

  public void addProceeds(double proceeds) {
    this.proceeds = this.proceeds.add(BigDecimal.valueOf(proceeds));
  }

  public void addLtdProceeds(BigDecimal proceeds) {
    this.ltdProceeds = this.ltdProceeds.add(proceeds);
  }

  public void addFees(double fees) {
    this.fees = this.fees.add(BigDecimal.valueOf(fees));
  }

  public void addLtdFees(BigDecimal fees) {
    this.ltdFees = this.ltdFees.add(fees);
  }

  public void addCommissions(double commissions) {
    this.commissions = this.commissions.add(BigDecimal.valueOf(commissions));
  }

  public void addLtdCommissions(BigDecimal commissions) {
    this.ltdCommissions = this.ltdCommissions.add(commissions);
  }

}
