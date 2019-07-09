package org.galatea.pocpnl.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "valuation")
@Entity
public class Valuation {

	@Id
	@GeneratedValue
	private long valuationId;
	
	private String book;
	private String instrument;
	private LocalDate date;
	private double valuation;
    private String inputJsonMap;
	
}
