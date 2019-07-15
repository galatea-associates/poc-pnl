
package org.galatea.pocpnl.domain;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trade")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Trade {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  String id;

  String book;
  String instrument;
  LocalDate tradeDate;
  LocalDate settlementDate;
  int quantity;
  double price;
  double fee;
  double commission;
  String currency;

  public double getValue() {
    return price * quantity;
  }

}
