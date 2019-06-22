package tn.lansrod.carrefour.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

import tn.lansrod.carrefour.utils.FileUtils;

public class TransactionWriter {
	public static void write(Map<String, Map<String, Integer>> map, String dateToProcess, String outputDirectory) {
		String outputDirectoryName = outputDirectory + "/" + dateToProcess;
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
			
			String fullPath = outputDirectoryName + "/top_100_ventes-" + magasinID + "-" + dateToProcess + ".csv";
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

	public static void writeCA(Map<String, Map<String, Double>> mapCA, String dateToProcess, String outputDirectory) {
		String outputDirectoryName = outputDirectory + "/" + dateToProcess;
	    FileUtils.createDirectory(outputDirectoryName);
	    for (Map.Entry<String, Map<String, Double>> entry : mapCA.entrySet()) {
			String magasinID = entry.getKey();
			Map<String, Double> product = entry.getValue();
			product = product.entrySet().stream()
		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		            .limit(100)
		            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
		                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			
			String fullPath = outputDirectoryName + "/top_100_ca_ventes-" + magasinID + "-" + dateToProcess + ".csv";
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

	public static List<String> writeTMP(Map<String, Map<String, Integer>> stream, String outputDirectory) {
		List<String> lst = new ArrayList<>();
		String outputDirectoryName = outputDirectory + "/tmp";
	    FileUtils.createDirectory(outputDirectoryName);
	    for (Map.Entry<String, Map<String, Integer>> entry : stream.entrySet()) {
			String magasinID = entry.getKey();
			Map<String, Integer> product = entry.getValue();
			String fullPath = outputDirectoryName + "/top_100_ventes-" + magasinID + ".csv";
			lst.add(fullPath);
			File file = new File(fullPath);
			if(FileUtils.createFile(file)) {
				CSVWriter writer = FileUtils.openFile(fullPath);
				createTopTen(writer, product);
				FileUtils.closeFile(writer);
			}
			else {
				appendUsingBufferedWriter(fullPath, product);
			}
		}
	    return lst;
	}
	
	private static void appendUsingBufferedWriter(String filePath, Map<String,Integer> productList) {
		File file = new File(filePath);
		FileWriter fr = null;
		BufferedWriter br = null;
		try {
			// to append to file, you need to initialize FileWriter using below constructor
			fr = new FileWriter(file, true);
			br = new BufferedWriter(fr);
			
			for (Map.Entry<String,Integer> entry : productList.entrySet()) {
				String data = entry.getKey() + "|" + Double.toString(entry.getValue()); 
				br.newLine();
				// you can use write or append method
				br.write(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void writeGlobal(Map<String, Integer> map, String dateToProcess, String outputDirectory) {
		String outputDirectoryName = outputDirectory + "/" + dateToProcess;
	    FileUtils.createDirectory(outputDirectoryName);
	    
	    String fullPath = outputDirectoryName + "/top_100_ventes_GLOBAL_" + dateToProcess + ".csv";
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
			String outputDirectory) {
		String outputDirectoryName = outputDirectory + "/" + dateToProcess;
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
