package org.galatea.pocpnl.service.valuation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class PQValuationService implements IValuationService {

	private static final String PRICE = "price";
	private static final String QTY = "qty";
	private static final String CURRENCY = "currency";
	private static final Set<String> inputRequirements = new HashSet<String>(Arrays.asList(PRICE, QTY, CURRENCY));

	
	
	@Override
	public ValuationResponse value(ValuationInput valuationInput) {
		
		Set<String> missingInput = getMissingInput(valuationInput, inputRequirements);
		
		if (!missingInput.isEmpty()) {
			return ValuationResponse.builder().missingInput(missingInput).build();
		}
		
		double price = (double) valuationInput.get(PRICE);
		int qty = (int) valuationInput.get(QTY);
		String currency = (String) valuationInput.get(CURRENCY);
		
		ValuationResult valuationResult = new ValuationResult(price*qty, currency);
		return ValuationResponse.builder().valuationInput(valuationInput).valuationResult(valuationResult).build();
	}
	
	private Set<String> getMissingInput(ValuationInput valuationInput, Set<String> inputRequirements) {
		return inputRequirements.stream().filter(i -> !valuationInput.contains(i)).collect(Collectors.toSet());
	}

}
