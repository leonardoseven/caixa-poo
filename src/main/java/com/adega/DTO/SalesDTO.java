package com.adega.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesDTO {

	private BigDecimal totalPrice;
	
	private BigDecimal totalGain;
	
	private LocalDateTime dtInitial;
	
	private LocalDateTime dtFinal;
}
