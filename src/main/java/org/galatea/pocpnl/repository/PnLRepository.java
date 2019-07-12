package org.galatea.pocpnl.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.galatea.pocpnl.domain.PnL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PnLRepository extends JpaRepository<PnL, Long> {

  Optional<PnL> findByBookAndInstrumentAndDate(String book, String instrument,
      LocalDate date);


}
