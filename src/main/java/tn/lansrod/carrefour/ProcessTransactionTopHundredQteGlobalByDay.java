package tn.lansrod.carrefour;

import java.util.Date;
import java.util.Map;

import tn.lansrod.carrefour.services.TransactionReader;
import tn.lansrod.carrefour.services.TransactionWriter;
import tn.lansrod.carrefour.utils.ParamsUtils;

public class ProcessTransactionTopHundredQteGlobalByDay {
		// fichier de transaction contient presque 3 million lignes
		// 2eme pt
		// 3 sec
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
				ParamsUtils.initParams(args);
				// convertir la qte en chiffre d'affaire en le mult par le prix de produit dans le fichier reference magasin
				Map<String, Integer> map = TransactionReader.readGlobal(ParamsUtils.tranctionFilePath, ParamsUtils.delimiter);
				// ecriture de resultat dans le dossier destination
				TransactionWriter.writeGlobal(map, ParamsUtils.dateToProcess, ParamsUtils.outputDirectory);
				
				Date end = new Date();
		    	System.out.println("End processing at: " + end);
			}
		}
}