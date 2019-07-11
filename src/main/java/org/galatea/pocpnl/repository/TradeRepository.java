
package org.galatea.pocpnl.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.galatea.pocpnl.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TradeRepository extends JpaRepository<Trade, Long> {

  List<Trade> findByBookAndInstrumentAndTradeDateBetween(String book, String instrument, LocalDate startDate, LocalDate endDate);

  @Query("SELECT DISTINCT instrument FROM Trade WHERE book=?1 and trade_date between ?2 and ?3")
  Set<String> findDistinctInstrumentByBookAndTradeDateBetween(String book, LocalDate fromDate, LocalDate toDate);

}
