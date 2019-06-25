package tn.lansrod.carrefour.services;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tn.lansrod.carrefour.data.Magasin;
import tn.lansrod.carrefour.data.Transaction;

public class TransactionReader {
	public static Map<String, Map<String, Integer>> read(String tranctionFilePath, String delimiter) {
		// stream file
		try {
			// convert to Stream of Transaction then to Map<String, Map<String, Integer>>
			Stream<Transaction> tr =  Files.lines(Paths.get(tranctionFilePath), Charset.defaultCharset())
					.map(line -> {
					String[] arr = line.split("\\" + delimiter);
	                return new Transaction(arr[2], arr[3], Integer.parseInt(arr[4].replace("\"", ""))); });
			
			Map<String, Map<String, Integer>> list = tr.collect(
													   Collectors
													   .groupingBy
													     (
													    	Transaction::getMagasin,
													       Collectors.groupingBy
													       (
													    	Transaction::getProduit,
														       Collectors.reducing
														       (
														    		   0,
														    		   Transaction::getQte,
											                           Integer::sum
														       )
													       )
													   )
												);
			
			tr = null;
			return list;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}	

	// lire le fichier reference magasin jointure par produit et multiplier la qte par le
	// prix de chaque produit
	public static Map<String, Map<String, Double>> convertReaderWithChiffreAffaire(Map<String, Map<String, Integer>> list, String inputDirectory, String dateToProcess, String delimiter) {
		Map<String, Map<String, Double>> magasinPrice = new HashMap<String, Map<String, Double>>();
		for (Map.Entry<String, Map<String, Integer>> magasin : list.entrySet()) {
			String magasinID = magasin.getKey();
			String fileToRead = inputDirectory + "/" + dateToProcess + "/" + "reference_prod-" + magasinID + "_" + dateToProcess + ".csv";
			Map<String, Integer> product = magasin.getValue();
			try {
				Map<String, Magasin> fileRead = Files.lines(Paths.get(fileToRead), Charset.defaultCharset())
						    .map(line -> {
							String[] arr = line.split("\\" + delimiter);
			                return new Magasin(arr[0].replace("\"", ""), Double.parseDouble(arr[1].replace("\"", ""))); })
						    .collect( Collectors.toMap(Magasin::getProduct,
                                    Function.identity()) );
				for (Map.Entry<String, Integer> prod : product.entrySet()) {
					String productID = prod.getKey();
					int qte = prod.getValue();
					Double price = fileRead.get(productID).price;
					
					if(magasinPrice.get(magasinID) == null) {
						Map<String, Double> productPrice = new HashMap<String, Double>();
						productPrice.put(productID, ((qte * price) * 100) / 100);
						magasinPrice.put(magasinID, productPrice);
	        		}
	        		else {
	        			magasinPrice.get(magasinID).put(productID, (double) Math.round((qte * price) * 100) / 100);
	        		}
					
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return magasinPrice;
	}

	public static Map<String, Integer> readGlobal(String tranctionFilePath, String delimiter) {
		// stream file
				try {
					// convert to Stream of Transaction then to Map<String, Map<String, Integer>>
					Stream<Transaction> tr =  Files.lines(Paths.get(tranctionFilePath), Charset.defaultCharset())
							.map(line -> {
							String[] arr = line.split("\\" + delimiter);
			                return new Transaction(arr[3], Integer.parseInt(arr[4].replace("\"", ""))); });
					
					Map<String, Integer> list = tr.collect(
															   Collectors
															   .groupingBy
															     (
															    		 Transaction::getProduit,
																	       Collectors.reducing
																	       (
																	    		   0,
																	    		   Transaction::getQte,
														                           Integer::sum
																	       )
															   )
														);
					
					tr = null;
					// return avec sorted et prendre que les 100 premier lignes
					return list.entrySet().stream()
				            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				            .limit(100)
				            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
				                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
	}
}
