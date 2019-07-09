package org.galatea.pocpnl.service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.galatea.pocpnl.domain.Position;
import org.galatea.pocpnl.service.valuation.IValuationService;
import org.galatea.pocpnl.service.valuation.ValuationInput;
import org.galatea.pocpnl.service.valuation.ValuationResponse;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PnLService {

  @NonNull
  private final IValuationService valuationService;

  public PnLService(IValuationService valuationService) {
    this.valuationService = valuationService;
  }

  public void start() {
    log.info("Calculating PnL");

    // Get books
    Set<String> books = getBooks();
    // For each book
    for (String book : books) {
      log.info("Calculating P&L for book: {}", book);
      // .. get positions
      Set<Position> positions = getPositionsForBook(book);

      // .. for each position
      for (Position position : positions) {
        log.info("Valuing position: {}", position);
        // .. .. value
        // Placeholder for the input
        ValuationInput valuationInput = null;

        ValuationResponse valuation = valuationService.valuate(valuationInput);
        log.info("Valuation for position {}: {}", position, valuation);
      }
    }

    log.info("PnL Calculation completed");
  }

  private Set<Position> getPositionsForBook(String book) {
    return Stream.of(new Position()).collect(Collectors.toSet());
  }

  private Set<String> getBooks() {
    return Stream.of("book1").collect(Collectors.toSet());
  }

}
