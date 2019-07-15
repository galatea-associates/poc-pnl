package org.galatea.pocpnl.service;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.galatea.pocpnl.domain.Book;
import org.galatea.pocpnl.domain.FxRate;
import org.galatea.pocpnl.domain.Instrument;
import org.galatea.pocpnl.domain.Position;
import org.galatea.pocpnl.domain.Trade;
import org.galatea.pocpnl.domain.Valuation;
import org.galatea.pocpnl.repository.BookRepository;
import org.galatea.pocpnl.repository.FxRepository;
import org.galatea.pocpnl.repository.InstrumentRepository;
import org.galatea.pocpnl.repository.PositionRepository;
import org.galatea.pocpnl.repository.TradeRepository;
import org.galatea.pocpnl.repository.ValuationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataImportService {

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private ValuationRepository valuationRepository;

  @Autowired
  private TradeRepository tradeRepository;

  @Autowired
  private FxRepository fxRepository;

  @Autowired
  private InstrumentRepository instrumentRepository;

  @Autowired
  private BookRepository bookRepository;

  private String positionsFile = "data/positions.txt";
  private String valuationsFile = "data/valuations.txt";
  private String tradesFile = "data/trades.txt";
  private String instrumentsFile = "data/instruments.txt";
  private String booksFile = "data/books.txt";
  private String ratesFile = "data/rates.txt";

  private Gson g = new Gson();



  public void importData() {
    log.info("Importing data");

    importData(bookRepository, getLines(booksFile), Book.class);
    importData(instrumentRepository, getLines(instrumentsFile), Instrument.class);
    importData(positionRepository, getLines(positionsFile), Position.class);
    importData(valuationRepository, getLines(valuationsFile), Valuation.class);
    importData(tradeRepository, getLines(tradesFile), Trade.class);
    importData(fxRepository, getLines(ratesFile), FxRate.class);


  }

  private List<String> getLines(String path) {
    List<String> lines = new ArrayList<String>();
    BufferedReader reader;
    try {
      Resource resource = new ClassPathResource(path);
      File file = resource.getFile();
      reader = new BufferedReader(new FileReader(file));
      String line = reader.readLine();
      while (line != null) {
        lines.add(line);
        line = reader.readLine();
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines;
  }

  private <T> void importData(JpaRepository<T, ?> repository, List<String> lines,
      Class<T> objClass) {
    for (String json : lines) {
      repository.save(g.fromJson(json, objClass));
    }
  }
}
