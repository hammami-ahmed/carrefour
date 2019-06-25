package tn.lansrod.carrefour;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tn.lansrod.carrefour.services.TransactionReader;
import tn.lansrod.carrefour.services.TransactionWriter;
import tn.lansrod.carrefour.utils.ParamsJ7Utils;

public class ProcessTransactionTopHundredChiffreAffaireJ7 {
	
	public static Map<String, Map<String, Double>> listJ7;
	
public static void main( String[] args ) {
		// fichier de transaction contient presque 1.4 million lignes
		// 7eme Task
		// enter la date de transaction en question
		if(args.length != 4) {
			System.out.println("Please enter the start date, file delimiter, input folder path and output folder path.");
			return;
		}
		else {
			Date start = new Date();
	    	System.out.println("Start processing at: " + start);
			ParamsJ7Utils.initParams(args);
			listJ7 = new HashMap<String, Map<String,Double>>();
			for(int i = 1; i <= ParamsJ7Utils.treansactionDateInterval.size(); i++) {
				// preparer la date en question
				String date = ParamsJ7Utils.getDateToProcess(i-1);
				// lire le fichier de transaction et faire u groupe par magasin et par produit
				Map<String, Map<String, Integer>> stream = TransactionReader.read(ParamsJ7Utils.initPath(i-1), ParamsJ7Utils.delimiter);
				// convertir la qte en chiffre d'affaire en le mult par le prix de produit dans le fichier reference magasin
				Map<String, Map<String, Double>> mapCA = TransactionReader.convertReaderWithChiffreAffaire(stream, ParamsJ7Utils.inputDirectory, date, ParamsJ7Utils.delimiter);
				if(listJ7.size() == 0) {
					listJ7 = mapCA;
				}
				else {
					// merger la resultat de toute la semaine
					listJ7 = TransactionReader.mergeCa(mapCA, listJ7);
				}
			}
			String addedToEnd = "-J7";
			// ecriture de resultat dans le dossier destination
			TransactionWriter.writeCA(listJ7, ParamsJ7Utils.getDateToProcess(0), ParamsJ7Utils.outputDirectory, addedToEnd);
			Date end = new Date();
	    	System.out.println("End processing at: " + end);
		}
	}
}
