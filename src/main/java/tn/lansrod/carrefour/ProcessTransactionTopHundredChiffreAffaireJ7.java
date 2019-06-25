package tn.lansrod.carrefour;

import java.util.Date;
import java.util.Map;

import tn.lansrod.carrefour.services.TransactionReader;
import tn.lansrod.carrefour.services.TransactionWriter;
import tn.lansrod.carrefour.utils.ParamsJ7Utils;

public class ProcessTransactionTopHundredChiffreAffaireJ7 {
public static void main( String[] args ) {
		
		// enter la date de transaction en question
		if(args.length != 4) {
			System.out.println("Please enter the start date, file delimiter, input folder path and output folder path.");
			return;
		}
		else {
			Date start = new Date();
	    	System.out.println("Start processing at: " + start);
			ParamsJ7Utils.initParams(args);

			for(int i = 1; i <= ParamsJ7Utils.treansactionDateInterval.size(); i++) {
				// preparer la date en question
				String date = ParamsJ7Utils.getDateToProcess(i-1);
				// lire le fichier de transaction et faire u groupe par magasin et par produit
				Map<String, Map<String, Integer>> stream = TransactionReader.read(ParamsJ7Utils.initPath(i-1), ParamsJ7Utils.delimiter);
				// convertir la qte en chiffre d'affaire en le mult par le prix de produit dans le fichier reference magasin
				Map<String, Map<String, Double>> mapCA = TransactionReader.convertReaderWithChiffreAffaire(stream, ParamsJ7Utils.inputDirectory, date, ParamsJ7Utils.delimiter);
				// ecriture de resultat dans le dossier destination
				TransactionWriter.writeCA(mapCA, date, ParamsJ7Utils.outputDirectory);
			}
			
			Date end = new Date();
	    	System.out.println("End processing at: " + end);
		}
	}
}
