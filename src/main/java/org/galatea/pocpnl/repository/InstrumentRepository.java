package org.galatea.pocpnl.repository;

import java.util.Optional;
import org.galatea.pocpnl.domain.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

  Optional<Instrument> getByInstrumentId(String instrumentId);
}
