package org.galatea.pocpnl;

import java.time.LocalDate;

import org.galatea.pocpnl.domain.PnL;
import org.galatea.pocpnl.domain.Position;
import org.galatea.pocpnl.domain.Valuation;
import org.galatea.pocpnl.repository.PnLRepository;
import org.galatea.pocpnl.repository.PositionRepository;
import org.galatea.pocpnl.repository.ValuationRepository;
import org.galatea.pocpnl.service.PnLService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(App.class, args);

    // load some Positions
    PositionRepository positionRepository = context.getBean(PositionRepository.class);
    positionRepository.save(new Position("EQTY1", "DNKN", 100, 79.22));
    positionRepository.save(new Position("EQTY1", "TSLA", 1000, 237.78));
    
    ValuationRepository valuationRepository = context.getBean(ValuationRepository.class);
    
    Valuation referenceValuation = new Valuation(1L, "EQTY1", "DNKN", LocalDate.now().minusDays(1), 75.6, "{}");
	valuationRepository.save(referenceValuation);
    Valuation currentValuation = new Valuation(2L, "EQTY1", "DNKN", LocalDate.now(), 70.0, "{}");
	valuationRepository.save(currentValuation);

    PnLRepository pnLRepository = context.getBean(PnLRepository.class);
    pnLRepository.save(new PnL("EQTY1", "DNKN", LocalDate.now(),referenceValuation, currentValuation, currentValuation.getValuation()-referenceValuation.getValuation()));
    // load some old PnL records

    context.getBean(PnLService.class).calculateEODPnL(LocalDate.now());

  }

}
