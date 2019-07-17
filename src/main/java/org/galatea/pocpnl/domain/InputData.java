package org.galatea.pocpnl.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum InputData {
  INSTRUMENT_ASSET_TYPE("Instrument.AssetType"),
  POSITION_QTY("Position.Quantity"),
  POSITION_OPEN_COST("Position.OpenCost"),
  POSITION_OPEN_DATE("Position.OpenDate"),
  POSITION_YTM("Position.YTM"),
  INSTRUMENT_PRICE("Instrument.Price"),
  INSTRUMENT_MATURITY_DATE("Instrument.MaturityDate"),
  INSTRUMENT_COUPON_RATE("Instrument.CouponRate"),
  BOOK_CURRENCY("Book.Currency"),
  INSTRUMENT_CURRENCY("Instrument.Currency"),
  FX_RATE("FX.Rate");


  private final String name;

  @Override
  public String toString() {
    return this.getName();
  }

}
