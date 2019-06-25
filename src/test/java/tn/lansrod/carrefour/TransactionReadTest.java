package tn.lansrod.carrefour;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import tn.lansrod.carrefour.services.TransactionReader;

@RunWith(JUnit4.class)
public class TransactionReadTest {

	public static String inputPath;
	public static String outputPath;
	public static String transactionFilePath;
	public static final String DELIMITER = "|";
	public static final String DATE = "20190610";
	
	@BeforeClass
    public static void setup() throws Exception {
		File resourcesDirectory = new File("src/test/resources");
		inputPath = resourcesDirectory.getAbsoluteFile().toString();
		outputPath = inputPath + "/output";
		transactionFilePath = inputPath + "/20190610/transactions_20190610.csv";
    }
	
	@Test
    public void shouldReadTransactionFile() {
		Map<String, Map<String, Integer>> stream = TransactionReader.read(transactionFilePath, DELIMITER);
		assertEquals(stream.size(), 1);
		for (Map.Entry<String, Map<String, Integer>> entry : stream.entrySet()) {
			Map<String, Integer> product = entry.getValue();
			assertEquals(product.size(), 5);
		}
	}
	
	@Test
    public void shouldConvertReaderWithChiffreAffaire() {
		Map<String, Map<String, Integer>> stream = TransactionReader.read(transactionFilePath, DELIMITER);
		Map<String, Map<String, Double>> streamConverted = TransactionReader.convertReaderWithChiffreAffaire(stream, inputPath, DATE, DELIMITER);
		assertEquals(streamConverted.size(), 1);
		for (Map.Entry<String, Map<String, Double>> magasin : streamConverted.entrySet()) {
			Map<String, Double> product = magasin.getValue();
			product = product.entrySet().stream()
		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		            .limit(100)
		            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
		                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
			Double d = 50.5d;
			for (Map.Entry<String, Double> prod : product.entrySet()) {
				Double price = d * 4;
				assertEquals(prod.getValue(), price);
				d -= 10;
			}
		}
	}
	
	@Test
    public void shouldReadGlobal() {
		Map<String, Integer> products = TransactionReader.readGlobal(transactionFilePath, DELIMITER);
		assertEquals(products.size(), 5);
		int size = 4;
		for (Map.Entry<String, Integer> prod : products.entrySet()) {
			assertEquals((int)new Integer(size), (int)new Integer(prod.getValue()));
		}
	}
}
