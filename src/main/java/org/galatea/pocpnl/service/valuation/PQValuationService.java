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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import org.galatea.pocpnl.domain.AssetType;
import org.galatea.pocpnl.domain.InputData;
import org.galatea.pocpnl.domain.ValuationResult;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class PQValuationService implements IValuationService {

  @Override
  public ValuationResponse value(ValuationInput valuationInput) {

    try {
      AssetType assetType =
          AssetType.valueOf((String) getInputData(valuationInput, INSTRUMENT_ASSET_TYPE));

      switch (assetType) {
        case EQUITY:
          return valueEquity(valuationInput);
        case FIXED_INCOME:
          return valueFixedIncome(valuationInput);
      }

    } catch (InputDataException e) {
      return ValuationResponse.builder().valuationInput(valuationInput)
          .missingInput(e.getMissingInput()).build();
    }
    return null;
  }



  private ValuationResponse valueFixedIncome(ValuationInput valuationInput) {

    BigDecimal instrumentCurrencyValuation = getInstrumentValuation(valuationInput);
    double fxRate = getFxRate(valuationInput);
    BigDecimal bookCurrencyValuation =
        getBookValuation(valuationInput, instrumentCurrencyValuation, fxRate);

    BigDecimal accruedAmortization = getAccruedAmortization(valuationInput);
    BigDecimal bookValue = getBookValue(valuationInput, accruedAmortization);

    ValuationResult result = ValuationResult.builder()
        .instrumentCurrencyValuation(instrumentCurrencyValuation)
        .bookCurrencyValuation(bookCurrencyValuation)
        .accruedAmortization(accruedAmortization)
        .bookValue(bookValue)
        .fxRate(fxRate)
        .build();

    return ValuationResponse.builder().valuationInput(valuationInput)
        .valuationResult(result).build();
  }

  private double getFxRate(ValuationInput valuationInput) {
    String bookCurrency = (String) getInputData(valuationInput, BOOK_CURRENCY);
    String instrumentCurrency = (String) getInputData(valuationInput, INSTRUMENT_CURRENCY);

    double fxRate = 1.0;
    if (!bookCurrency.equals(instrumentCurrency)) {
      fxRate = (double) getInputData(valuationInput, FX_RATE);
    }
    return fxRate;
  }



  private ValuationResponse valueEquity(ValuationInput valuationInput) {
    BigDecimal instrumentCurrencyValuation = getInstrumentValuation(valuationInput);
    double fxRate = getFxRate(valuationInput);
    BigDecimal bookCurrencyValuation =
        getBookValuation(valuationInput, instrumentCurrencyValuation, fxRate);


    ValuationResult result = ValuationResult.builder()
        .instrumentCurrencyValuation(instrumentCurrencyValuation)
        .bookCurrencyValuation(bookCurrencyValuation)
        .fxRate(fxRate)
        .build();

    return ValuationResponse.builder().valuationInput(valuationInput)
        .valuationResult(result).build();

  }


  private BigDecimal getInstrumentValuation(ValuationInput valuationInput) {
    double price = (double) getInputData(valuationInput, INSTRUMENT_PRICE);
    long qty = (int) getInputData(valuationInput, POSITION_QTY);
    return BigDecimal.valueOf(price * qty);
  }

  private BigDecimal getBookValuation(ValuationInput valuationInput,
      BigDecimal instrumentCurrencyValuation, double fxRate) {
    return instrumentCurrencyValuation.multiply(BigDecimal.valueOf(fxRate));
  }

  private BigDecimal getAccruedAmortization(ValuationInput valuationInput) {
    double openCost = (double) getInputData(valuationInput, POSITION_OPEN_COST);
    LocalDate openDate =
        LocalDate.ofEpochDay((long) getInputData(valuationInput, POSITION_OPEN_DATE));
    LocalDate maturityDate =
        LocalDate.ofEpochDay((long) getInputData(valuationInput, INSTRUMENT_MATURITY_DATE));

    long daysFromOpenDate =
        Duration.between(openDate.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
    long daysFromOpenToMaturity =
        Duration.between(openDate.atStartOfDay(), maturityDate.atStartOfDay()).toDays();

    double qty = (int) getInputData(valuationInput, POSITION_QTY);

    // Accrued Amortization
    BigDecimal accruedAmortization = new BigDecimal(qty * (100 - openCost) * daysFromOpenDate)
        .divide(BigDecimal.valueOf(daysFromOpenToMaturity), 2, RoundingMode.HALF_UP);

    return accruedAmortization;
  }

  private BigDecimal getBookValue(ValuationInput valuationInput, BigDecimal accruedAmortization) {
    double openCost = (double) getInputData(valuationInput, POSITION_OPEN_COST);
    long qty = (int) getInputData(valuationInput, POSITION_QTY);
    BigDecimal bookValue = accruedAmortization.add(BigDecimal.valueOf(openCost * qty));
    return bookValue;
  }


  private Object getInputData(ValuationInput valuationInput, InputData inputRequirement)
      throws InputDataException {
    if (!valuationInput.contains(inputRequirement)) {
      throw new InputDataException(Collections.singleton(inputRequirement));
    }

    return valuationInput.get(inputRequirement);
  }


}
