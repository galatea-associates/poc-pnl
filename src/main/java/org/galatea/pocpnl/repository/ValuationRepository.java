package org.galatea.pocpnl.repository;

import java.time.LocalDate;
import java.util.Optional;
import org.galatea.pocpnl.domain.Valuation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValuationRepository extends JpaRepository<Valuation, Long> {

  Optional<Valuation> findByBookAndInstrumentAndDate(String book, String instrument,
      LocalDate date);

}
