package org.galatea.pocpnl.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ltdpnl")
@IdClass(LTDPnL.PnLId.class)
public class LTDPnL {

  @Id
  private String book;
  @Id
  private String instrument;
  @Id
  private LocalDate date;

  @Embedded
  private UnRealizedPnL ltdUnrealizedPnL;
  @Embedded
  private RealizedPnl ltdRealizedPnL;

  @Data
  static class PnLId implements Serializable {
    private static final long serialVersionUID = 1L;
    private String book;
    private String instrument;
    private LocalDate date;
  }
}
