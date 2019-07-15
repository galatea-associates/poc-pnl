package org.galatea.pocpnl.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
@Table(name = "fxrates")
@IdClass(FxRate.FxRateId.class)
public class FxRate {

  @Id
  @NotNull
  private String fromCurrency;
  @Id
  @NotNull
  private String toCurrency;
  @NotNull
  private double rate;

  public double getInverted() {
    return rate / rate;
  }

  @Data
  static class FxRateId implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fromCurrency;
    private String toCurrency;
  }

}
