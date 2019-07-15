package org.galatea.pocpnl.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "instruments")
@Data
public class Instrument {

  @Id
  private String instrumentId;
  private double spotPrice;
  private String currency;

}
