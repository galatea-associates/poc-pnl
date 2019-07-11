package org.galatea.pocpnl.repository;

import org.galatea.pocpnl.domain.UnRealizedPnL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PnLRepository extends JpaRepository<UnRealizedPnL, Long> {


}
