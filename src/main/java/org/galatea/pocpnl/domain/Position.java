
package org.galatea.pocpnl.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;


import lombok.Builder;
import lombok.Getter;

@Builder
@Entity
@Getter
public class Position {
	@Id
    @GeneratedValue	
    private long id;
     
    @NotBlank
    private String book;
     
    @NotBlank
    private String instrument;
    
    private double price;
    
    private int qty;

}
