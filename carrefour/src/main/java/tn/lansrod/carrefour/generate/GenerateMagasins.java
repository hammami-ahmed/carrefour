package tn.lansrod.carrefour.generate;

import java.io.File;

import com.opencsv.CSVWriter;

import tn.lansrod.carrefour.utils.FileUtils;
import tn.lansrod.carrefour.utils.RandomUtils;

public class GenerateMagasins {

	public static void generate(String magasinID, int productLines, String dateForName, String inputDirectoryName) {
		// preparer le nom de fichier
		String fileName = "reference_prod-" + magasinID + "_" + dateForName + ".csv";
		// preparer toute la path dans laquelle on va ecrire
		String fullPath = inputDirectoryName + "/" + fileName;
		File file = new File(fullPath);
		// creation de ficher
		FileUtils.createFile(file);
		// ouvrir le fichier pour commencer l'ecriture des donnees
		CSVWriter writer = FileUtils.openFile(fullPath);
		for(int j = 1; j <= productLines; j++) {
			Double price = RandomUtils.generateRandomDouble();
			String[] data = { j + "|" + price }; 
			// ecrire les donnees
	        writer.writeNext(data);
		}
		// fermeture de fichier 
		// les donnees sont enregistrer si seulement si le fichier est fermer 
		// ou bien avec la fonction push
		FileUtils.closeFile(writer);
	}

}
