package org.galatea.pocpnl.service.valuation;

import static org.galatea.pocpnl.domain.InputData.BOOK_CURRENCY;
import static org.galatea.pocpnl.domain.InputData.FX_RATE;
import static org.galatea.pocpnl.domain.InputData.INSTRUMENT_ASSET_TYPE;
import static org.galatea.pocpnl.domain.InputData.INSTRUMENT_CURRENCY;
import static org.galatea.pocpnl.domain.InputData.INSTRUMENT_MATURITY_DATE;
import static org.galatea.pocpnl.domain.InputData.INSTRUMENT_PRICE;
import static org.galatea.pocpnl.domain.InputData.POSITION_OPEN_COST;
import static org.galatea.pocpnl.domain.InputData.POSITION_OPEN_DATE;
import static org.galatea.pocpnl.domain.InputData.POSITION_QTY;
import static org.galatea.pocpnl.domain.InputData.POSITION_YTM;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.galatea.pocpnl.domain.AssetType;
import org.galatea.pocpnl.domain.InputData;
import org.galatea.pocpnl.domain.ValuationResult;
import org.galatea.pocpnl.domain.ValuationResult.ValuationResultBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class PQValuationService implements IValuationService {


  private static final Set<InputData> inputRequirements = new HashSet<>(
      Arrays.asList(BOOK_CURRENCY, INSTRUMENT_CURRENCY,
          INSTRUMENT_ASSET_TYPE, INSTRUMENT_PRICE, POSITION_QTY));

  @Override
  public ValuationResponse value(ValuationInput valuationInput) {

    ValuationResultBuilder resultBuilder = ValuationResult.builder();

    Set<InputData> missingInput = getMissingInput(valuationInput, inputRequirements);

    if (!missingInput.isEmpty()) {
      return ValuationResponse.builder().missingInput(missingInput).build();
    }


    double price = (double) valuationInput.get(INSTRUMENT_PRICE);
    int qty = (int) valuationInput.get(POSITION_QTY);

    AssetType assetType = AssetType.valueOf((String) valuationInput.get(INSTRUMENT_ASSET_TYPE));

    if (assetType.equals(AssetType.FIXED_INCOME)) {
      missingInput =
          getMissingInput(valuationInput,
              new HashSet<>(Arrays.asList(POSITION_OPEN_COST, POSITION_OPEN_DATE, POSITION_YTM,
                  INSTRUMENT_MATURITY_DATE)));
      if (!missingInput.isEmpty()) {
        return ValuationResponse.builder().valuationInput(valuationInput).missingInput(missingInput)
            .build();
      }

      double openCost = (double) valuationInput.get(POSITION_OPEN_COST);
      LocalDate openDate = LocalDate.ofEpochDay((long) valuationInput.get(POSITION_OPEN_DATE));
      LocalDate maturityDate =
          LocalDate.ofEpochDay((long) valuationInput.get(INSTRUMENT_MATURITY_DATE));

      long daysFromOpenDate =
          Duration.between(openDate.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
      long daysFromOpenToMaturity =
          Duration.between(openDate.atStartOfDay(), maturityDate.atStartOfDay()).toDays();

      // Accrued Amortization
      BigDecimal accruedAmortization = new BigDecimal(qty * (100 - openCost) * daysFromOpenDate)
          .divide(BigDecimal.valueOf(daysFromOpenToMaturity), 2, RoundingMode.HALF_UP);

      resultBuilder.accruedAmortization(accruedAmortization);

      BigDecimal bookValue = accruedAmortization.add(BigDecimal.valueOf(openCost * qty));
      resultBuilder.bookValue(bookValue);
    }



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

    resultBuilder
        .instrumentCurrencyValuation(instrumentCurrencyValuation)
        .bookCurrencyValuation(bookCurrencyValuation)
        .fxRate(fxRate).build();

    return ValuationResponse.builder().valuationInput(valuationInput)
        .valuationResult(resultBuilder.build()).build();
  }

  private Set<InputData> getMissingInput(ValuationInput valuationInput,
      Set<InputData> inputRequirements) {
    return inputRequirements.stream().filter(i -> !checkInput(valuationInput, i))
        .collect(Collectors.toSet());
  }

  private boolean checkInput(ValuationInput valuationInput, InputData inputRequirement) {
    return valuationInput.contains(inputRequirement);
  }

}
