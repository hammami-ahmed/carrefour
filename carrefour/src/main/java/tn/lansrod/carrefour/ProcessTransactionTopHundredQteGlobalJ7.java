package tn.lansrod.carrefour;

import java.util.Date;
import java.util.Map;

import tn.lansrod.carrefour.services.TransactionReader;
import tn.lansrod.carrefour.services.TransactionWriter;
import tn.lansrod.carrefour.utils.ParamsJ7Utils;

public class ProcessTransactionTopHundredQteGlobalJ7 {
	// fichier de transaction contient presque 3 million lignes
			// 6eme pt
			// 19 sec
	
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
					
					for(int i = 1; i <= ParamsJ7Utils.treansactionDateInterval.size(); i++) {
						Map<String, Integer> map = TransactionReader.readGlobal(ParamsJ7Utils.initPath(i-1), ParamsJ7Utils.delimiter);
						TransactionWriter.writeGlobal(map, ParamsJ7Utils.getDateToProcess(i-1), ParamsJ7Utils.outputDirectory);
					}

					Date end = new Date();
			    	System.out.println("End processing at: " + end);
				}
			}
}


//public static Map<String, Integer> global;
//if(global == null) {
//	global = map;
//}
//else {
//	for (Map.Entry<String, Integer> entry : map.entrySet()) {
//		String productID = entry.getKey();
//		Integer qte = entry.getValue();
//		if(global.get(productID) == null) {
//			global.put(productID, qte);
//		}
//		else {
//			global.put(productID, global.get(productID) + qte);
//		}
//	}
//}
