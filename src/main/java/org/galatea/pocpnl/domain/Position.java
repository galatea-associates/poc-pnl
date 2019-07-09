
package org.galatea.pocpnl.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(Position.PositionId.class)
public class Position {
	@Id
	@NotNull
	private String book;
	@Id
	@NotNull
	private String instrument;

	private double price;

	private int qty;

	@Data
	static class PositionId implements Serializable {
		private static final long serialVersionUID = 1L;
		private String book;
		private String instrument;
	}
}
