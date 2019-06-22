package tn.lansrod.carrefour.utils;

import java.text.DecimalFormat;

public class FormatUtils {

	// prendre deux chiffres seulement apres la vergule
	public static DecimalFormat initDecimalFormat() {
		DecimalFormat df = new DecimalFormat() ;
    	df.setMaximumFractionDigits(2);
    	df.setMinimumFractionDigits(2);
    	df.setDecimalSeparatorAlwaysShown(true); 
    	return df;
	}
}
