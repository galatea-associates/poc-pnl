package org.galatea.pocpnl.repository;

import java.time.LocalDate;
import java.util.List;

import org.galatea.pocpnl.domain.Valuation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValuationRepository extends JpaRepository<Valuation, Long> {

	List<Valuation> findByBookAndInstrumentAndDate(String book, String instrument, LocalDate date);

}
