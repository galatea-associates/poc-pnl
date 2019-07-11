package org.galatea.pocpnl.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "pnl")
@IdClass(PnL.PnLId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PnL {

  @Id
  private String book;
  @Id
  private String instrument;
  @Id
  private LocalDate date;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "referenceValuationId", referencedColumnName = "id")
  private Valuation referenceValuation;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "currentValuationId", referencedColumnName = "id")
  private Valuation currentValuation;

  private BigDecimal mtmPnL;
  private BigDecimal mtmPnLFx;
  private BigDecimal fxPnL;

  @Data
  static class PnLId implements Serializable {
    private static final long serialVersionUID = 1L;
    private String book;
    private String instrument;
    private LocalDate date;
  }
}
