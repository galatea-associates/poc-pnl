package org.galatea.pocpnl.service;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.galatea.pocpnl.domain.Position;
import org.galatea.pocpnl.domain.Valuation;
import org.galatea.pocpnl.repository.PositionRepository;
import org.galatea.pocpnl.repository.ValuationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


@Service
public class DataImportService {

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private ValuationRepository valuationRepository;

  private String positionsFile = "data/positions.txt";
  private String valuationsFile = "data/valuations.txt";

  private Gson g = new Gson();

  public void importData() {

    importPositions(getLines(positionsFile));
    importValuations(getLines(valuationsFile));

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

  private void importPositions(List<String> lines) {
    for (String json : lines) {
      Position position = g.fromJson(json, Position.class);
      positionRepository.save(position);
    }
  }

  private void importValuations(List<String> lines) {
    for (String json : lines) {
      Valuation valuation = g.fromJson(json, Valuation.class);
      valuationRepository.save(valuation);
    }
  }

}
