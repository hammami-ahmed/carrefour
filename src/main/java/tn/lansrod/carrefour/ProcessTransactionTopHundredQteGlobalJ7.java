package tn.lansrod.carrefour;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import tn.lansrod.carrefour.services.TransactionReader;
import tn.lansrod.carrefour.services.TransactionWriter;
import tn.lansrod.carrefour.utils.ParamsJ7Utils;

public class ProcessTransactionTopHundredQteGlobalJ7 {
	// fichier de transaction contient presque 1.4 million lignes
	// 6eme task
	public static Map<String, Integer> listJ7;
	public static void main( String[] args ) {
		// enter la date de transaction en question
		if(args.length != 4) {
			// exemple 10/06/2019 | /home/xx/input /home/xx/output
			System.out.println("Please enter the date, file delimiter, input folder path and output folder path.");
			return;
		}
		else {
			Date start = new Date();
	    	System.out.println("Start processing at: " + start);
	    	// preparation des parametres
			ParamsJ7Utils.initParams(args);
			listJ7 = new HashMap<String,Integer>();
			for(int i = 1; i <= ParamsJ7Utils.treansactionDateInterval.size(); i++) {
				Map<String, Integer> map = TransactionReader.readGlobal(ParamsJ7Utils.initPath(i-1), ParamsJ7Utils.delimiter);
				map = map.entrySet().stream()
			            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			            .limit(100)
			            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
			                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
				if(listJ7.size() == 0) {
					listJ7 = map;
				}
				else {
					listJ7 = TransactionReader.mergeProducts(map, listJ7);
				}
			}
			String endWith = "-J7";
			TransactionWriter.writeGlobal(listJ7, ParamsJ7Utils.getDateToProcess(0), ParamsJ7Utils.outputDirectory, endWith);
			Date end = new Date();
	    	System.out.println("End processing at: " + end);
		}
	}
}
