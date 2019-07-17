package org.galatea.pocpnl.entrypoint;

import java.util.Arrays;
import java.util.List;
import org.galatea.pocpnl.domain.PnL;
import org.galatea.pocpnl.domain.Position;
import org.galatea.pocpnl.domain.Valuation;
import org.galatea.pocpnl.repository.PnLRepository;
import org.galatea.pocpnl.repository.PositionRepository;
import org.galatea.pocpnl.repository.ValuationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PnLController {

  @Autowired
  PositionRepository positionRepository;
  @Autowired
  PnLRepository pnlRepository;
  @Autowired
  ValuationRepository valuationRepository;

  @GetMapping("/positions")
  public String getAllPositions(Model model) {
    Iterable<Position> positions = positionRepository.findAll();

    model.addAttribute("positions", positions);
    return "positions"; // view
  }

  @GetMapping("/vot")
  public String getValuationOverTime(Model model) {
    model.addAttribute("mydata", Arrays.asList(12, 19, 3, 5, 2, 3));

    List<Valuation> valuations = valuationRepository.findAll();
    model.addAttribute("valuations", valuations);

    String book = valuations.get(0).getBook();
    model.addAttribute("book", book);


    return "valuationOverTime"; // view
  }

  @GetMapping("/pnl")
  public String getAllPnL(Model model) {
    Iterable<PnL> results = pnlRepository.findAll();

    model.addAttribute("results", results);
    return "pnlResults"; // view
  }

}
