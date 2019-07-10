package org.galatea.pocpnl.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "valuation")
@Entity
public class Valuation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long valuationId;
	
	private String book;
	private String instrument;
	private LocalDate date;
	private double valuation;
    private String inputJsonMap;
	
}
