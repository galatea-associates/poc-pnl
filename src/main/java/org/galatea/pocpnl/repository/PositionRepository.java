package org.galatea.pocpnl.repository;

import java.util.List;
import java.util.Set;
import org.galatea.pocpnl.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PositionRepository extends JpaRepository<Position, Long> {

  List<Position> findByBook(String book);

  @Query("SELECT DISTINCT book FROM Position")
  Set<String> findDistinctBook();

}
