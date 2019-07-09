package org.galatea.pocpnl.service.valuation;

import lombok.Data;

@Data
public class PnLResult {
	private final double pnl;
	private final ValuationResult currentValuation;
	private final ValuationResult referenceValuation;
	
	// Other relevant information to track
}
