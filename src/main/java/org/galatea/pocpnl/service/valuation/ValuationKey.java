package org.galatea.pocpnl.service.valuation;

import java.time.LocalDate;
import lombok.Value;

@Value
public class ValuationKey {

  String instrument;
  String book;
  LocalDate date;
}
