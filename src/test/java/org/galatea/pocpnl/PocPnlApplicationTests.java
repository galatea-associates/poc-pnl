package org.galatea.pocpnl;


import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.galatea.pocpnl.domain.PnL;
import org.galatea.pocpnl.domain.Position;
import org.galatea.pocpnl.domain.Trade;
import org.galatea.pocpnl.domain.Valuation;
import org.galatea.pocpnl.repository.PnLRepository;
import org.galatea.pocpnl.repository.PositionRepository;
import org.galatea.pocpnl.repository.TradeRepository;
import org.galatea.pocpnl.repository.ValuationRepository;
import org.galatea.pocpnl.service.PnLService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PocPnlApplicationTests {

  Gson gson = new Gson();

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private ValuationRepository valuationRepository;

  @Autowired
  private TradeRepository tradeRepository;

  @Autowired
  private PnLRepository pnlRepository;

  @Autowired
  private PnLService pnlService;

  public void before() {
    log.info("Positions {}", positionRepository.count());
    log.info("Valuations {}", valuationRepository.count());
    log.info("P&Ls {}", pnlRepository.count());
  }

  @Test
  public void test1() throws FileNotFoundException, IOException {
    before();
    runScenario(loadScenario("scenario1.json"));
  }

  private void runScenario(TestScenario scenario) {
    log.info("Testing {}", scenario.getName());
    loadPositions(scenario.getPositions());
    loadValuations(scenario.getValuations());
    loadTraders(scenario.getTrades());
    pnlService.calculateEODPnL(scenario.getEod());

    assertResults(scenario.getExpectedPnl());
  }

  private void assertResults(List<PnL> expectedResults) {
    for (PnL expected : expectedResults) {
      PnL actual = pnlRepository.findByBookAndInstrumentAndDate(expected.getBook(),
          expected.getInstrument(), expected.getDate()).get();

      assertEquals(gson.toJson(expected), gson.toJson(actual));
    }
  }


  private void loadPositions(List<Position> positions) {
    positionRepository.saveAll(positions);
    log.info("Loaded {} Positions {}", positions.size(), positions);
  }

  private void loadValuations(List<Valuation> valuations) {
    valuationRepository.saveAll(valuations);
    log.info("Loaded {} Valuations {}", valuations.size(), valuations);
  }

  private void loadTraders(List<Trade> trades) {
    tradeRepository.saveAll(trades);
    log.info("Loaded {} Trades {}", trades.size(), trades);
  }

  private TestScenario loadScenario(String testFile) throws FileNotFoundException, IOException {
    TestScenario scenario = null;
    try (BufferedReader reader =
        new BufferedReader(new FileReader(new ClassPathResource(testFile).getFile()));) {
      scenario = gson.fromJson(reader, TestScenario.class);
      reader.close();
    }
    return scenario;
  }

}
