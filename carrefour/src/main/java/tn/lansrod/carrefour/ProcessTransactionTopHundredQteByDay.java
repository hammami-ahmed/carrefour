package tn.lansrod.carrefour;

import java.util.Date;
import java.util.Map;

import tn.lansrod.carrefour.services.TransactionReader;
import tn.lansrod.carrefour.services.TransactionWriter;
import tn.lansrod.carrefour.utils.ParamsUtils;;

public class ProcessTransactionTopHundredQteByDay {
	// fichier de transaction contient presque 3 million lignes
	// 1er pt
	// 9 sec
	
	// 1.199.669 lignes de transaction 5 sec
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
			// lire le fichier de transaction et faire u groupe par magasin et par produit
			Map<String, Map<String, Integer>> map = TransactionReader.read(ParamsUtils.tranctionFilePath, ParamsUtils.delimiter);
			// ecriture de resultat dans le dossier destination
			TransactionWriter.write(map, ParamsUtils.dateToProcess, ParamsUtils.outputDirectory);
			
			Date end = new Date();
	    	System.out.println("End processing at: " + end);
		}
	}
}
