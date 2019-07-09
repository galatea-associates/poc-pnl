
package org.galatea.pocpnl.service.valuation;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ValuationInput {
	
	private Map<String, Object> inputData;
	
	public ValuationInput() {
		this.inputData = new HashMap<>();
	}
	
	public void addInput(String inputName, Object inputValue) {
		this.inputData.put(inputName, inputValue);
	}
	
	public Object get(String inputName) {
		return this.inputData.get(inputName);
	}
	
	public boolean contains(String inputName) {
		return this.inputData.containsKey(inputName);
	}
}
