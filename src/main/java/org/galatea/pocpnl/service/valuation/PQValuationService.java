package org.galatea.pocpnl.service.valuation;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.galatea.pocpnl.domain.ValuationResult;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class PQValuationService implements IValuationService {

  private static final String QTY = "Position.Quantity";
  private static final String PRICE = "Instrument.Price";
  private static final String BOOK_CURRENCY = "Book.Currency";
  private static final String INSTRUMENT_CURRENCY = "Instrument.Currency";
  private static final String FX_RATE = "FX.Rate";

  private static final Set<String> inputRequirements = new HashSet<String>(
      Arrays.asList(BOOK_CURRENCY, INSTRUMENT_CURRENCY, PRICE, QTY));

  @Override
  public ValuationResponse value(ValuationInput valuationInput) {

    Set<String> missingInput = getMissingInput(valuationInput, inputRequirements);

    if (!missingInput.isEmpty()) {
      return ValuationResponse.builder().missingInput(missingInput).build();
    }

    double price = (double) valuationInput.get(PRICE);
    int qty = (int) valuationInput.get(QTY);

    String instrumentCurrency = (String) valuationInput.get(INSTRUMENT_CURRENCY);
    String bookCurrency = (String) valuationInput.get(BOOK_CURRENCY);

    BigDecimal instrumentCurrencyValuation = BigDecimal.valueOf(price * qty);
    BigDecimal bookCurrencyValuation = instrumentCurrencyValuation;
    double fxRate = 1.0;
    if (!bookCurrency.equals(instrumentCurrency)) {

      if (!checkInput(valuationInput, FX_RATE)) {
        return ValuationResponse.builder().valuationInput(valuationInput)
            .missingInput(Collections.singleton(FX_RATE)).build();
      }

      fxRate = (double) valuationInput.get(FX_RATE);
      bookCurrencyValuation = BigDecimal.valueOf(price * qty * fxRate);
    }

    ValuationResult valuationResult = ValuationResult.builder()
        .instrumentCurrencyValuation(instrumentCurrencyValuation)
        .bookCurrencyValuation(bookCurrencyValuation)
        .fxRate(fxRate).build();
    return ValuationResponse.builder().valuationInput(valuationInput)
        .valuationResult(valuationResult).build();
  }

  private Set<String> getMissingInput(ValuationInput valuationInput,
      Set<String> inputRequirements) {
    return inputRequirements.stream().filter(i -> !checkInput(valuationInput, i))
        .collect(Collectors.toSet());
  }

  private boolean checkInput(ValuationInput valuationInput, String inputRequirement) {
    return valuationInput.contains(inputRequirement);
  }

}
