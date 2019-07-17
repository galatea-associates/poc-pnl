
package org.galatea.pocpnl.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "position")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(Position.PositionId.class)
public class Position {
  @Id
  @NotNull
  private String book;
  @Id
  @NotNull
  private String instrument;

  private int qty;

  private double costBasis;

  private LocalDate openDate;
  private double ytm;

  @Id
  @NotNull
  private LocalDate date;

  @Data
  static class PositionId implements Serializable {
    private static final long serialVersionUID = 1L;
    private String book;
    private String instrument;
    private LocalDate date;
  }
}
