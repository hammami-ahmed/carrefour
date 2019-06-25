package tn.lansrod.carrefour;

import java.util.Date;
import java.util.Map;

import tn.lansrod.carrefour.services.TransactionReader;
import tn.lansrod.carrefour.services.TransactionWriter;
import tn.lansrod.carrefour.utils.ParamsUtils;

public class ProcessTransactionTopHundredChiffreAffaireByDay {
	// fichier de transaction contient presque 1.4 million lignes
	// 3eme task
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
			// convertir la qte en chiffre d'affaire en le mult par le prix de produit dans le fichier reference magasin
			Map<String, Map<String, Double>> mapCA = TransactionReader.convertReaderWithChiffreAffaire(map, ParamsUtils.inputDirectory, ParamsUtils.dateToProcess, ParamsUtils.delimiter);
			// ecriture de resultat dans le dossier destination
			String addedToEnd = "";
			TransactionWriter.writeCA(mapCA, ParamsUtils.dateToProcess, ParamsUtils.outputDirectory, addedToEnd);
			Date end = new Date();
	    	System.out.println("End processing at: " + end);
		}
	}
}
