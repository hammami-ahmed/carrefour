package tn.lansrod.carrefour;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tn.lansrod.carrefour.generate.GenerateData;
import tn.lansrod.carrefour.utils.RandomUtils;

public class GenerateCarrefourData {
	public static final int MAXPRODUCEDBYSTORE = 20000;
	public static final int MINTRANSACTIONLINE = 100000;
	public static final int NB_STORE = 1200;
	public static final String INPUTPATH = "/home/lansrod/carrefour/input/";
	public static final List<String> INTERVALTRANSACTIONDATE = Arrays.asList("10/06/2019", "11/06/2019", "12/06/2019", "13/06/2019", "14/06/2019", "15/06/2019", "16/06/2019");
	
	// génération des données de 1200 magasin carrefour
	// chaque magasin contient 20000 produits
	// et un fichier de transaction contenant 3 million lignes par jour
	// taille de fichier de transaction 250Mo
    public static void main( String[] args ) {
    	Date start = new Date();
    	System.out.println("Start processing at: " + start);
    	// generer 1200 cle aléatoirement pour les magasins
	    Map<Integer, String> listMagasinIDs = RandomUtils.generateMagasinIDs(NB_STORE);
    	// generer les données d'une semaines.
    	for (String date : INTERVALTRANSACTIONDATE) {
			GenerateData.generate(date, INPUTPATH, NB_STORE, MAXPRODUCEDBYSTORE, MINTRANSACTIONLINE, listMagasinIDs);
    	}
    	
    	Date end = new Date();
    	System.out.println("End processing at: " + end);
    }
}
