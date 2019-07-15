package org.galatea.pocpnl;


import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.galatea.pocpnl.domain.Book;
import org.galatea.pocpnl.domain.FxRate;
import org.galatea.pocpnl.domain.Instrument;
import org.galatea.pocpnl.domain.PnL;
import org.galatea.pocpnl.domain.Position;
import org.galatea.pocpnl.domain.Trade;
import org.galatea.pocpnl.domain.Valuation;
import org.galatea.pocpnl.repository.BookRepository;
import org.galatea.pocpnl.repository.FxRepository;
import org.galatea.pocpnl.repository.InstrumentRepository;
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
  private BookRepository bookRepository;

  @Autowired
  private InstrumentRepository instrumentRepository;

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private ValuationRepository valuationRepository;

  @Autowired
  private TradeRepository tradeRepository;

  @Autowired
  private FxRepository fxRepository;

  @Autowired
  private PnLRepository pnlRepository;

  @Autowired
  private PnLService pnlService;

  @Test
  public void test1() throws FileNotFoundException, IOException {
    runScenario(loadScenario("scenario1.json"));
  }

  @Test
  public void priceMoveTest() throws FileNotFoundException, IOException {
    runScenario(loadScenario("priceMove.json"));
  }

  private void runScenario(TestScenario scenario) {
    log.info("Testing {}", scenario.getName());
    loadBooks(scenario.getBooks());
    loadInstruments(scenario.getInstruments());
    loadPositions(scenario.getPositions());
    loadValuations(scenario.getValuations());
    loadTraders(scenario.getTrades());
    loadFxRates(scenario.getFxRates());
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

  private void loadBooks(List<Book> books) {
    bookRepository.saveAll(books);
    log.info("Loaded {} Books {}", books.size(), books);
  }

  private void loadInstruments(List<Instrument> instruments) {
    instrumentRepository.saveAll(instruments);
    log.info("Loaded {} Instruments {}", instruments.size(), instruments);
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

  private void loadFxRates(List<FxRate> fxRates) {
    fxRepository.saveAll(fxRates);
    log.info("Loaded {} Fx Rates {}", fxRates.size(), fxRates);
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
