package org.galatea.pocpnl.service;

import org.galatea.pocpnl.domain.ValuationResponse;
import org.springframework.stereotype.Service;

@Service
public class FixedValuationService implements IValuationService {

	@Override
	public ValuationResponse valuate(Object input) {
		return new ValuationResponse(43.0);
	}

}
