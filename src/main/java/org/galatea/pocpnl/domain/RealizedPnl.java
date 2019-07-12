
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

  public void addProceeds(double proceeds) {
    this.proceeds = this.proceeds.add(BigDecimal.valueOf(proceeds));
  }

  public void addProceeds(BigDecimal proceeds) {
    this.proceeds = this.proceeds.add(proceeds);
  }

  public void addFees(double fees) {
    this.fees = this.fees.add(BigDecimal.valueOf(fees));
  }

  public void addFees(BigDecimal fees) {
    this.fees = this.fees.add(fees);
  }

  public void addCommissions(double commissions) {
    this.commissions = this.commissions.add(BigDecimal.valueOf(commissions));
  }

  public void addCommissions(BigDecimal commissions) {
    this.commissions = this.commissions.add(commissions);
  }

}
