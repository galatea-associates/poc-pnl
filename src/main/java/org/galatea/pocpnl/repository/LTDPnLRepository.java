package org.galatea.pocpnl.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.galatea.pocpnl.domain.LTDPnL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LTDPnLRepository extends JpaRepository<LTDPnL, Long> {

  Optional<LTDPnL> findByBookAndInstrumentAndDate(String book, String instrument,
      LocalDate date);


}
