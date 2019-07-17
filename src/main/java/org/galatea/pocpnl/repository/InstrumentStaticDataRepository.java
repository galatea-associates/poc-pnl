package org.galatea.pocpnl.repository;

import java.util.Optional;
import org.galatea.pocpnl.domain.InstrumentStaticData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentStaticDataRepository extends JpaRepository<InstrumentStaticData, Long> {

  Optional<InstrumentStaticData> getByInstrumentId(String instrumentId);
}
