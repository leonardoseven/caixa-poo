package com.adega.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

@Component("convertMoney")
public class ConvertMoney {

	
	public static String BigDecimalToString(BigDecimal bigDecimal) {
		DecimalFormat f = new DecimalFormat();
		f.setMaximumFractionDigits(3);
		f.setMinimumFractionDigits(2);
		f.setGroupingUsed(true);
		f.setDecimalSeparatorAlwaysShown(false);
		
		return f.format(bigDecimal);
	}

}
