package org.galatea.pocpnl.repository;

import java.util.Optional;
import org.galatea.pocpnl.domain.FxRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FxRepository extends JpaRepository<FxRate, Long> {

  Optional<FxRate> getByFromCurrencyAndToCurrency(String from, String to);
}
