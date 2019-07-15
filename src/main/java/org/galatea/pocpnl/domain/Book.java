package org.galatea.pocpnl.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "books")
@Data
public class Book {

  @Id
  @NotNull
  private String bookId;
  private String currency;

}
