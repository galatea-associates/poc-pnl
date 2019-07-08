package org.galatea.pocpnl.service;

import org.galatea.pocpnl.domain.ValuationResponse;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PnLService {

	@NonNull
	private final IValuationService valuationService;

	public PnLService(IValuationService valuationService) {
		this.valuationService = valuationService;
	}

	public void start() {
		log.info("Calculating PnL");

		// Placeholder for the input
		Object input = null;
		
		ValuationResponse valuation = valuationService.valuate(input);
		log.info("Valuation {}", valuation);

		log.info("PnL Calculation completed");
	}
}
