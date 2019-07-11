package org.galatea.pocpnl;

import java.time.LocalDate;
import org.galatea.pocpnl.service.DataImportService;
import org.galatea.pocpnl.service.PnLService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
    context.getBean(DataImportService.class).importData();
    context.getBean(PnLService.class).calculateEODPnL(LocalDate.parse("2019-07-10"));
  }

}
