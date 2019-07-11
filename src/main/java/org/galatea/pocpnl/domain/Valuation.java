package org.galatea.pocpnl.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.galatea.pocpnl.service.valuation.ValuationInput;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "valuation")
@Entity
public class Valuation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String book;
  private String instrument;
  private LocalDate date;
  private BigDecimal instrumentCurrencyValuation;
  private BigDecimal bookCurrencyValuation;
  @Default
  private double fxRate = 1.0;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "valuationInput", referencedColumnName = "id")
  private ValuationInput valuationInput;

}
