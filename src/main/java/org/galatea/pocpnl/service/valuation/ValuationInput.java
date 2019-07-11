
package org.galatea.pocpnl.service.valuation;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.Data;

@Table(name = "valuationInput")
@Entity
@Data
public class ValuationInput {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ElementCollection
  @Column(name = "value", length = 1000)
  @CollectionTable(name = "numeric_input_data",
      joinColumns = @JoinColumn(name = "valuation_input_id"))
  private Map<String, Number> numericInputData;

  @ElementCollection
  @Column(name = "value", length = 1000)
  @CollectionTable(name = "text_input_data", joinColumns = @JoinColumn(name = "valuation_input_id"))
  private Map<String, String> textInputData;


  public ValuationInput() {
    this.numericInputData = new HashMap<>();
    this.textInputData = new HashMap<>();
  }

  public void addInput(String inputName, Object inputValue) {

    if (inputValue instanceof Number) {
      this.numericInputData.put(inputName, (Number) inputValue);
    } else {
      this.textInputData.put(inputName, (String) inputValue);
    }

  }

  public Object get(String inputName) {
    return this.numericInputData.containsKey(inputName) ? this.numericInputData.get(inputName)
        : this.textInputData.get(inputName);
  }

  public boolean contains(String inputName) {
    return this.numericInputData.containsKey(inputName)
        || this.textInputData.containsKey(inputName);
  }



}
