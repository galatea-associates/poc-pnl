package org.galatea.pocpnl;

import java.time.LocalDate;

import org.galatea.pocpnl.domain.Position;
import org.galatea.pocpnl.repository.PositionRepository;
import org.galatea.pocpnl.service.PnLService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
    context.getBean(PnLService.class).calculateEODPnL(LocalDate.now());
	
    PositionRepository positionRepository = context.getBean(PositionRepository.class);
    positionRepository.save(new Position("book1", "inst1", 40.0, 6));
  }

}
