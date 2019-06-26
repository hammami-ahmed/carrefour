package tn.lansrod.carrefour;

import java.util.Date;
import java.util.List;
import java.util.Map;

import tn.lansrod.carrefour.generate.GenerateData;
import tn.lansrod.carrefour.utils.GenerateDataUtils;
import tn.lansrod.carrefour.utils.RandomUtils;

public class GenerateCarrefourData {
	public static final int MINTRANSACTIONLINE = 250000;
	// génération des données de 1200 magasin carrefour
	// chaque magasin contient 20000 produits
	// et un fichier de transaction contenant environ 1.4 million lignes par jour
    public static void main( String[] args ) {
    	if(args.length != 4) {
			// exemple 10/06/2019 /home/xx/input 1200 20000
			System.out.println("Please enter the date, input folder path, nb store and product by store.");
			return;
		}
    	else {
    		Date start = new Date();
        	System.out.println("Start processing at: " + start);
        	GenerateDataUtils.initParams(args);
        	List<String> intervalList = GenerateDataUtils.initDateList(7);
        	// generer 1200 cle aléatoirement pour les magasins
    	    Map<Integer, String> listMagasinIDs = RandomUtils.generateMagasinIDs(GenerateDataUtils.nbStore);
        	// generer les données d'une semaines.
        	for (String date : intervalList) {
    			GenerateData.generate(date, GenerateDataUtils.inputPath, GenerateDataUtils.nbStore, GenerateDataUtils.maxproduct, MINTRANSACTIONLINE, listMagasinIDs);
        	}
        	Date end = new Date();
        	System.out.println("End processing at: " + end);
    	}
    }
}
