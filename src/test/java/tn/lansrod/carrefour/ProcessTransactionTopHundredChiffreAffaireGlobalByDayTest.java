package tn.lansrod.carrefour;

import java.io.File;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import tn.lansrod.carrefour.services.TransactionReader;
import tn.lansrod.carrefour.services.TransactionWriter;

@RunWith(JUnit4.class)
public class ProcessTransactionTopHundredChiffreAffaireGlobalByDayTest {

	public static String inputPath;
	public static String outputPath;
	public static final String DELIMITER = "|";
	public static final String DATE = "20190610";
	
	@BeforeClass
    public static void setup() throws Exception {
		File resourcesDirectory = new File("src/test/resources");
		inputPath = resourcesDirectory.getAbsoluteFile().toString();
		outputPath = inputPath + "/output";
		String transactionFilePath = inputPath + "/20190610/transactions_20190610.csv";
		
		Map<String, Double> mergedProduct = null;
		Map<String, Map<String, Integer>> stream = TransactionReader.read(transactionFilePath, DELIMITER);
		Map<String, Map<String, Double>> streamCA = TransactionReader.convertReaderWithChiffreAffaire(stream, inputPath, DATE, DELIMITER);
		mergedProduct = ProcessTransactionTopHundredChiffreAffaireGlobalByDay.Merge(streamCA);
		TransactionWriter.writeCAGlobal(mergedProduct, DATE, outputPath);
    }
	
}
