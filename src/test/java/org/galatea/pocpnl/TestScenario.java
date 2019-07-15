package org.galatea.pocpnl;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import org.galatea.pocpnl.domain.PnL;
import org.galatea.pocpnl.domain.Position;
import org.galatea.pocpnl.domain.Trade;
import org.galatea.pocpnl.domain.Valuation;

@Data
public class TestScenario {

  private String name;
  private List<Position> positions;
  private List<Valuation> valuations;
  private List<Trade> trades;
  private LocalDate eod;
  private List<PnL> expectedPnl;
}
