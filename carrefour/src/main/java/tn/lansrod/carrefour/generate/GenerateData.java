package tn.lansrod.carrefour.generate;

import java.util.HashMap;
import java.util.Map;

import tn.lansrod.carrefour.utils.DateUtils;
import tn.lansrod.carrefour.utils.FileUtils;

public class GenerateData {
	public static void generate(String date, String inputPath, int nbStore, int maxProducedByStore, int minTransactionLine, Map<Integer, String> listMagasinIDs) {
		// la liste des IDs des magasins avec le nombre des produits dans chacune
		Map<Integer, Map<String, Integer>> storeList = new HashMap<Integer, Map<String, Integer>>();
		// changer la format de date en yyyyMMdd pour l'utiliser dans le nom
		String dateForName = DateUtils.getStringDate(date);
		// organiser les donnees a generer par date
		String inputDirectoryName = inputPath + dateForName;
		// create le folder 
	    FileUtils.createDirectory(inputDirectoryName);
		for(int i = 1; i <= nbStore; i++) {
			// generer un id pour chaque magasin
			String magasinID = listMagasinIDs.get(i);

			Map<String, Integer> value = new HashMap<String, Integer>();
			value.put(magasinID, maxProducedByStore);
			// remplir une liste qui va nous aider a generer le fichier des transactions
			storeList.put(i, value);
			
			// generer les fichiers reference produit de cette date
			GenerateMagasins.generate(magasinID, maxProducedByStore, dateForName, inputDirectoryName);
    	}
		// generer le fichier de transaction de cette date
		GenerateTransactions.generate(storeList, inputDirectoryName, date, dateForName, minTransactionLine);
	}
	
}
