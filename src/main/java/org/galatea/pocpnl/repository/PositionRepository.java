package org.galatea.pocpnl.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.galatea.pocpnl.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PositionRepository extends JpaRepository<Position, Long> {

  List<Position> findByBook(String book);

  List<Position> findByBookAndInstrumentAndDate(String book, String instrument, LocalDate eodDate);

  @Query("SELECT DISTINCT book FROM Position")
  Set<String> findDistinctBook();

  @Query("SELECT DISTINCT instrument FROM Position WHERE book=?1 and date=?2")
  Set<String> findDistinctInstrumentByBookAndDate(String book, LocalDate date);


}
