package tn.lansrod.carrefour.services;

import java.io.File;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

import tn.lansrod.carrefour.utils.FileUtils;

public class TransactionWriter {
	
	public static void write(Map<String, Map<String, Integer>> map, String dateToProcess, String outputDirectory, String endWith, String task) {
		String outputDirectoryName = outputDirectory + "/" + task + "/" + dateToProcess;
		FileUtils.createDirectory(outputDirectory + "/" + task);
		FileUtils.createDirectory(outputDirectoryName);
	    for (Map.Entry<String, Map<String, Integer>> entry : map.entrySet()) {
			String magasinID = entry.getKey();
			Map<String, Integer> product = entry.getValue();
			// sort product with reverseOrder and take only top 100 product
			product = product.entrySet().stream()
		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		            .limit(100)
		            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
		                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			
			String fullPath = outputDirectoryName + "/top_100_ventes-" + magasinID + "-" + dateToProcess + endWith + ".csv";
			File file = new File(fullPath);
			FileUtils.createFile(file);
			CSVWriter writer = FileUtils.openFile(fullPath);
			createTopTen(writer, product);
			FileUtils.closeFile(writer);
		}
	}
	
	private static void createTopTen(CSVWriter writer, Map<String, Integer> productList) {
		for (Map.Entry<String,Integer> entry : productList.entrySet()) {
			String[] data = { entry.getKey() + "|" + Integer.toString(entry.getValue())};
	        writer.writeNext(data);
		}
	}

	public static void writeCA(Map<String, Map<String, Double>> mapCA, String dateToProcess, String outputDirectory, String addedToEnd, String task) {
		String outputDirectoryName = outputDirectory + "/" + task + "/" + dateToProcess;
		FileUtils.createDirectory(outputDirectory + "/" + task);
	    FileUtils.createDirectory(outputDirectoryName);
	    for (Map.Entry<String, Map<String, Double>> entry : mapCA.entrySet()) {
			String magasinID = entry.getKey();
			Map<String, Double> product = entry.getValue();
			product = product.entrySet().stream()
		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		            .limit(100)
		            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
		                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			
			String fullPath = outputDirectoryName + "/top_100_ca_ventes-" + magasinID + "-" + dateToProcess + addedToEnd + ".csv";
			File file = new File(fullPath);
			FileUtils.createFile(file);
			CSVWriter writer = FileUtils.openFile(fullPath);
			createTopTenCA(writer, product);
			FileUtils.closeFile(writer);
		}
	}
	
	private static void createTopTenCA(CSVWriter writer, Map<String, Double> productList) {
		for (Map.Entry<String,Double> entry : productList.entrySet()) {
			String[] data = { entry.getKey() + "|" + Double.toString(entry.getValue())}; 
	        writer.writeNext(data);
		}
	}

	public static void writeGlobal(Map<String, Integer> map, String dateToProcess, String outputDirectory, String endWith, String task) {
		String outputDirectoryName = outputDirectory + "/" + task + "/" + dateToProcess;
		FileUtils.createDirectory(outputDirectory + "/" + task);
	    FileUtils.createDirectory(outputDirectoryName);
	    
	    String fullPath = outputDirectoryName + "/top_100_ventes_GLOBAL_" + dateToProcess + endWith + ".csv";
		File file = new File(fullPath);
		FileUtils.createFile(file);
		CSVWriter writer = FileUtils.openFile(fullPath);
	    
	    for (Map.Entry<String, Integer> entry : map.entrySet()) {
	    	String[] data = { entry.getKey() + "|" + Integer.toString(entry.getValue())}; 
	        writer.writeNext(data);
		}
		FileUtils.closeFile(writer);
	}

	public static void writeCAGlobal(Map<String, Double> products, String dateToProcess,
			String outputDirectory, String task) {
		String outputDirectoryName = outputDirectory + "/" + task + "/" + dateToProcess;
		FileUtils.createDirectory(outputDirectory + "/" + task);
	    FileUtils.createDirectory(outputDirectoryName);
	    
	    String fullPath = outputDirectoryName + "/top_100_ca_GLOBAL_" + dateToProcess + ".csv";
		File file = new File(fullPath);
		FileUtils.createFile(file);
		CSVWriter writer = FileUtils.openFile(fullPath);
	    
	    for (Map.Entry<String, Double> entry : products.entrySet()) {
	    	String[] data = { entry.getKey() + "|" + Double.toString(entry.getValue())}; 
	        writer.writeNext(data);
		}
		FileUtils.closeFile(writer);
	}

}
