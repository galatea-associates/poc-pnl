
package org.galatea.pocpnl.service.valuation;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.Data;
import org.galatea.pocpnl.domain.InputData;

@Table(name = "valuationInput")
@Entity
@Data
public class ValuationInput {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ElementCollection(fetch = FetchType.EAGER)
  @Column(name = "value", length = 1000)
  @CollectionTable(name = "numeric_input_data",
      joinColumns = @JoinColumn(name = "valuation_input_id"))
  private Map<String, Number> numericInputData;

  @ElementCollection(fetch = FetchType.EAGER)
  @Column(name = "value", length = 1000)
  @CollectionTable(name = "text_input_data", joinColumns = @JoinColumn(name = "valuation_input_id"))
  private Map<String, String> textInputData;


  public ValuationInput() {
    this.numericInputData = new HashMap<>();
    this.textInputData = new HashMap<>();
  }

  public void addInput(InputData inputName, Object inputValue) {

    if (inputValue instanceof Number) {
      this.numericInputData.put(inputName.getName(), (Number) inputValue);
    } else {
      this.textInputData.put(inputName.getName(), (String) inputValue);
    }

  }

  public Object get(InputData inputName) {
    return this.numericInputData.containsKey(inputName.getName())
        ? this.numericInputData.get(inputName.getName())
        : this.textInputData.get(inputName.getName());
  }

  public boolean contains(InputData inputName) {
    return this.numericInputData.containsKey(inputName.getName())
        || this.textInputData.containsKey(inputName.getName());
  }



}
