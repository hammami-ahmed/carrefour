package tn.lansrod.carrefour.generate;

import java.io.File;
import java.util.Map;

import com.opencsv.CSVWriter;

import tn.lansrod.carrefour.utils.DateUtils;
import tn.lansrod.carrefour.utils.FileUtils;
import tn.lansrod.carrefour.utils.RandomUtils;

public class GenerateTransactions {

	public static void generate(Map<Integer, Map<String, Integer>> listMagasins, String outputDirectoryName, String date, String dateForName, int minTransactionLine) {
		// initialisation de temps
		int hour = 6;
		int min = 0;
		int second = 0;
		// preparation de nom de fichier
		String fileName = "transactions_" + dateForName + ".csv";
		String fullPath = outputDirectoryName + "/" + fileName;
		File file = new File(fullPath);
		// creation de fichier
		FileUtils.createFile(file);
		// ouverture de fichier pour commencer l'ecriture
		CSVWriter writer = FileUtils.openFile(fullPath);
		
		for(int k = 1; k <= minTransactionLine; k++) {
			// repeter une transaction au maximum 5 fois minimum une seul fois
			int nbTransaction = RandomUtils.getRandomWithMinMax(1, 5);
			for(int l = 1; l <= nbTransaction; l++) {
				// prendre une magasin aleatiorement
				int rdmMagasin = RandomUtils.getRandomWithMinMax(1, listMagasins.size());
				Map<String, Integer> mg = listMagasins.get(rdmMagasin);
				
				// preparation des donnees
				String transactionDate = DateUtils.convertDateToISOFormat(date, hour, min, second);
				Map.Entry<String,Integer> entry = mg.entrySet().iterator().next();
				String mgID = entry.getKey();
				int productId = RandomUtils.getRandomWithMinMax(1, entry.getValue());
				int qte = RandomUtils.getRandomWithMinMax(1, 20);
				
				// ecriture dans le fichier
				String[] data = { k + "|" + transactionDate + "|" + mgID + "|" + productId + "|" + qte}; 
		        writer.writeNext(data);
		        
		        // ajouter 1,2 ou bien 3 second au maximum entre deux enregistrements
				second += RandomUtils.getRandomWithMinMax(1, 3);
				// si les second depasse 59 alors ajouter une min et enlever 60 des second
				if(second >= 60) {
					min += 1;
					second -= 60;
				}
				// si les min depasse 59 alors ajouter une heure et enlever 60 des min
				if(min >= 60) {
					hour += 1;
					min-= 60;
				}
			}
		}
		// fermeture de fichier
		FileUtils.closeFile(writer);
	}

}
