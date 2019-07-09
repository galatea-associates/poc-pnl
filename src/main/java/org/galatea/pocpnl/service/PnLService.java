package org.galatea.pocpnl.service;

import java.time.LocalDate;
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

  public void calculateEODPnL(LocalDate eodDate) {
    log.info("Calculating EOD P&L for: {}", eodDate);

    // get date for reference valuations for P&L calculations
    LocalDate referenceDate = getReferenceDate(eodDate);
    log.info("Retrieved reference date for P&L calculations: {}", referenceDate);

    // Get books
    Set<String> books = getBooks();

    for (String book : books) {
      log.info("Calculating P&L for book: {}", book);
      Set<Position> positions = getPositionsForBook(book);

      for (Position position : positions) {
        log.info("Valuing position: {}", position);
        ValuationInput valuationInput = null;

        ValuationResponse valuationResponse = valuationService.value(valuationInput);
        if (valuationResponse.isMoreDataNeeded()) {
          // get more data and revalue..
          valuationInput = augmentValuationInput(valuationResponse);
          valuationResponse = valuationService.value(valuationInput);
        }

        log.info("Valuation for position {}: {}", position, valuationResponse);

        // now need to get reference valuation to calculate P&L
      }
    }

    log.info("PnL Calculation completed");
  }

  private ValuationInput augmentValuationInput(ValuationResponse valuationResponse) {
    return valuationResponse.getValuationInput();
  }

  private Set<Position> getPositionsForBook(String book) {
    return Stream.of(new Position()).collect(Collectors.toSet());
  }

  private Set<String> getBooks() {
    return Stream.of("book1").collect(Collectors.toSet());
  }

  private LocalDate getReferenceDate(LocalDate eodDate) {
    return eodDate.minusDays(1);
  }

}
