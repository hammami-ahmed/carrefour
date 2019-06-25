package tn.lansrod.carrefour.services;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import tn.lansrod.carrefour.utils.ParamsJ7Utils;
import tn.lansrod.carrefour.utils.ParamsUtils;

public class StartTask {

	public static Map<String, Map<String, Double>> listJ7Double;
	public static Map<String, Integer> listJ7SimpleInteger;
	public static Map<String, Map<String, Integer>> listJ7;
	
	public static void taskOne(String[] args) {
		Date start = new Date();
    	System.out.println("Task N°1: start processing at: " + start);
    	// preparation des parametres
		ParamsUtils.initParams(args);
		// lire le fichier de transaction et faire u groupe par magasin et par produit
		Map<String, Map<String, Integer>> map = TransactionReader.read(ParamsUtils.tranctionFilePath, ParamsUtils.delimiter);
		// ecriture de resultat dans le dossier destination
		String endWith = "";
		String task = "Task_N°1";
		TransactionWriter.write(map, ParamsUtils.dateToProcess, ParamsUtils.outputDirectory, endWith, task);
		Date end = new Date();
    	System.out.println("End processing at: " + end);
	}
	
	public static void taskTwo(String[] args) {
		Date start = new Date();
    	System.out.println("Task N°2: start processing at: " + start);
    	// preparation des parametres
		ParamsUtils.initParams(args);
		// convertir la qte en chiffre d'affaire en le mult par le prix de produit dans le fichier reference magasin
		Map<String, Integer> map = TransactionReader.readGlobal(ParamsUtils.tranctionFilePath, ParamsUtils.delimiter);
		// ecriture de resultat dans le dossier destination
		String endWith = "";
		String task = "Task_N°2";
		TransactionWriter.writeGlobal(map, ParamsUtils.dateToProcess, ParamsUtils.outputDirectory, endWith, task);
		Date end = new Date();
    	System.out.println("End processing at: " + end);
	}
	
	public static void taskthree(String[] args) {
		Date start = new Date();
    	System.out.println("Task N°3: start processing at: " + start);
    	// preparation les parametres
		ParamsUtils.initParams(args);
		// lire le fichier de transaction et faire u groupe par magasin et par produit
		Map<String, Map<String, Integer>> map = TransactionReader.read(ParamsUtils.tranctionFilePath, ParamsUtils.delimiter);
		// convertir la qte en chiffre d'affaire en le mult par le prix de produit dans le fichier reference magasin
		Map<String, Map<String, Double>> mapCA = TransactionReader.convertReaderWithChiffreAffaire(map, ParamsUtils.inputDirectory, ParamsUtils.dateToProcess, ParamsUtils.delimiter);
		// ecriture de resultat dans le dossier destination
		String addedToEnd = "";
		String task = "Task_N°3";
		TransactionWriter.writeCA(mapCA, ParamsUtils.dateToProcess, ParamsUtils.outputDirectory, addedToEnd, task);
		Date end = new Date();
    	System.out.println("End processing at: " + end);
	}
	
	public static void taskFour(String[] args) {
		Date start = new Date();
    	System.out.println("Task N°4: start processing at: " + start);
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
		String task = "Task_N°4";
		TransactionWriter.writeCAGlobal(mergedProduct, ParamsUtils.dateToProcess, ParamsUtils.outputDirectory, task);
		Runtime.getRuntime().gc();
		Date end = new Date();
    	System.out.println("End processing at: " + end);
	}
	
	public static void taskFive(String[] args) {
		Date start = new Date();
    	System.out.println("Task N°5: start processing at: " + start);
    	// preparation des parametres
		ParamsJ7Utils.initParams(args);
		listJ7 = new HashMap<String, Map<String,Integer>>();
		for(int i = 1; i <= ParamsJ7Utils.treansactionDateInterval.size(); i++) {
			Map<String, Map<String, Integer>> map = TransactionReader.read(ParamsJ7Utils.initPath(i-1), ParamsJ7Utils.delimiter);
			if(listJ7.size() == 0) {
				listJ7 = map;
			}
			else {
				// merger la resultat de toute la semaine
				listJ7 = TransactionReader.merge(map, listJ7);
			}
		}
		String endWith = "-J7";
		String task = "Task_N°5";
		TransactionWriter.write(listJ7, ParamsJ7Utils.getDateToProcess(0), ParamsJ7Utils.outputDirectory, endWith, task);
		Runtime.getRuntime().gc();
		Date end = new Date();
    	System.out.println("End processing at: " + end);
	}
	
	public static void taskSix(String[] args) {
		Date start = new Date();
    	System.out.println("Task N°6: start processing at: " + start);
    	// preparation des parametres
		ParamsJ7Utils.initParams(args);
		listJ7SimpleInteger = new HashMap<String,Integer>();
		for(int i = 1; i <= ParamsJ7Utils.treansactionDateInterval.size(); i++) {
			Map<String, Integer> map = TransactionReader.readGlobal(ParamsJ7Utils.initPath(i-1), ParamsJ7Utils.delimiter);
			map = map.entrySet().stream()
		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		            .limit(100)
		            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
		                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			if(listJ7SimpleInteger.size() == 0) {
				listJ7SimpleInteger = map;
			}
			else {
				listJ7SimpleInteger = TransactionReader.mergeProducts(map, listJ7SimpleInteger);
			}
		}
		String endWith = "-J7";
		String task = "Task_N°6";
		TransactionWriter.writeGlobal(listJ7SimpleInteger, ParamsJ7Utils.getDateToProcess(0), ParamsJ7Utils.outputDirectory, endWith, task);
		Date end = new Date();
    	System.out.println("End processing at: " + end);
	}
	
	public static void taskSeven(String[] args) {
		Date start = new Date();
    	System.out.println("Task N°7: start processing at: " + start);
		ParamsJ7Utils.initParams(args);
		listJ7Double = new HashMap<String, Map<String,Double>>();
		for(int i = 1; i <= ParamsJ7Utils.treansactionDateInterval.size(); i++) {
			// preparer la date en question
			String date = ParamsJ7Utils.getDateToProcess(i-1);
			// lire le fichier de transaction et faire u groupe par magasin et par produit
			Map<String, Map<String, Integer>> stream = TransactionReader.read(ParamsJ7Utils.initPath(i-1), ParamsJ7Utils.delimiter);
			// convertir la qte en chiffre d'affaire en le mult par le prix de produit dans le fichier reference magasin
			Map<String, Map<String, Double>> mapCA = TransactionReader.convertReaderWithChiffreAffaire(stream, ParamsJ7Utils.inputDirectory, date, ParamsJ7Utils.delimiter);
			if(listJ7Double.size() == 0) {
				listJ7Double = mapCA;
			}
			else {
				// merger la resultat de toute la semaine
				listJ7Double = TransactionReader.mergeCa(mapCA, listJ7Double);
			}
		}
		String addedToEnd = "-J7";
		String task = "Task_N°7";
		// ecriture de resultat dans le dossier destination
		TransactionWriter.writeCA(listJ7Double, ParamsJ7Utils.getDateToProcess(0), ParamsJ7Utils.outputDirectory, addedToEnd, task);
		Date end = new Date();
    	System.out.println("End processing at: " + end);
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
