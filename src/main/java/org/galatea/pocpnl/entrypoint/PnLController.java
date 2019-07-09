package org.galatea.pocpnl.entrypoint;

import org.galatea.pocpnl.domain.Position;
import org.galatea.pocpnl.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PnLController {

	@Autowired
	PositionRepository positionRepository;

	@GetMapping("/positions")
	public String getAllPositions(Model model) {
		Iterable<Position> positions = positionRepository.findAll();

		model.addAttribute("positions", positions);
		return "positions"; // view
	}

}
