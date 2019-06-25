package tn.lansrod.carrefour;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tn.lansrod.carrefour.services.TransactionReader;
import tn.lansrod.carrefour.services.TransactionWriter;
import tn.lansrod.carrefour.utils.ParamsUtils;

public class ProcessTransactionTopHundredChiffreAffaireGlobalByDay {
		// fichier de transaction contient presque 1.4 million lignes
		// 4eme Task
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
				Map<String, Double> mergedProduct = null;
				// lire le fichier de transaction et faire u groupe par magasin et par produit
				Map<String, Map<String, Integer>> map = TransactionReader.read(ParamsUtils.tranctionFilePath, ParamsUtils.delimiter);
				// convertir la qte en chiffre d'affaire en le mult par le prix de produit dans le fichier reference magasin
				Map<String, Map<String, Double>> mapCA = TransactionReader.convertReaderWithChiffreAffaire(map, ParamsUtils.inputDirectory, ParamsUtils.dateToProcess, ParamsUtils.delimiter);
				// merger les resultats 
				mergedProduct = Merge(mapCA);
				// ecriture de resultat dans le dossier destination
				TransactionWriter.writeCAGlobal(mergedProduct, ParamsUtils.dateToProcess, ParamsUtils.outputDirectory);
				
				Runtime.getRuntime().gc();
				Date end = new Date();
		    	System.out.println("End processing at: " + end);
			}
		}

		public static Map<String, Double> Merge(Map<String, Map<String, Double>> mapCA) {
			Map<String, Double> list = new HashMap<String, Double>();
			
			for (Map.Entry<String, Map<String, Double>> magasin : mapCA.entrySet()) {
				Map<String, Double> products = magasin.getValue();
				for (Map.Entry<String, Double> prod : products.entrySet()) {
					String productID = prod.getKey();
					Double price = prod.getValue();
					// si le produit n'existe pas alors l'ajouter dans le map
					if(list.get(productID) == null) {
						list.put(productID, price);
	        		}
	        		else {
	        			// sinn summer les prix
	        			list.put(productID, list.get(productID) + price);
	        		}
				}
			}
			return list;
		}
}
