package org.galatea.pocpnl.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "instrumentStaticData")
@Data
public class InstrumentStaticData {

  @Id
  private String instrumentId;
  private String currency;
  private AssetType assetType;

}
