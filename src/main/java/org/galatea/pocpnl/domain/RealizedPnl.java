
package org.galatea.pocpnl.domain;

import java.math.BigDecimal;
import lombok.Data;


@Data
public class RealizedPnl {

  BigDecimal proceeds = new BigDecimal(0);
  BigDecimal fees = new BigDecimal(0);
  BigDecimal commissions = new BigDecimal(0);

  public void addProceeds(double proceeds) {
    this.proceeds = this.proceeds.add(BigDecimal.valueOf(proceeds));
  }

  public void addFees(double fees) {
    this.fees = this.fees.add(BigDecimal.valueOf(fees));
  }

  public void addCommissions(double commissions) {
    this.commissions = this.commissions.add(BigDecimal.valueOf(commissions));
  }

}
