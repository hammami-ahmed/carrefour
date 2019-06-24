package tn.lansrod.carrefour;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import tn.lansrod.carrefour.services.TransactionReader;
import tn.lansrod.carrefour.services.TransactionWriter;

@RunWith(JUnit4.class)
public class ProcessTransactionTopHundredChiffreAffaireByDayTest {

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
		Map<String, Map<String, Integer>> stream = TransactionReader.read(transactionFilePath, DELIMITER);
		Map<String, Map<String, Double>> streamCA = TransactionReader.convertReaderWithChiffreAffaire(stream, inputPath, DATE, DELIMITER);
		TransactionWriter.writeCA(streamCA, DATE, outputPath);
    }
	
	@Test
    public void shouldContainOneFolder() {
		File file = new File(outputPath);
		File[] files = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		int count = files.length;
		assertEquals(count, 1);
	}
	
	@Test
    public void shouldGenerateOneOutputFiles() {
		int count = new File(outputPath + "/20190610/").list().length;
		assertEquals(count, 1);
	}
	
	@Test
    public void shouldContainStoreLines() {
		String filePath =  outputPath + "/20190610/top_100_ca_ventes-10f2f3e6-f728-41f3-b079-43b0aa758292-20190610.csv";
		long counttopventeLines = 0;
		try {
			counttopventeLines = Files.lines(Paths.get(filePath)).count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(counttopventeLines, 5);
	}
	
	@Test
    public void shouldLinesCAEqualsResult() {
		String filePath =  outputPath + "/20190610/top_100_ca_ventes-10f2f3e6-f728-41f3-b079-43b0aa758292-20190610.csv";
		try {
			List<Double> list =  Files.lines(Paths.get(filePath), Charset.defaultCharset())
								.map(line -> {
									String[] arr = line.split("\\|");
							        return new Double(Double.valueOf(arr[1].replace("\"", "")));
						        })
								.collect( Collectors.toList());
			Double line1 = list.get(0);
			Double line2 = list.get(1);
			Double line3 = list.get(2);
			Double line4 = list.get(3);
			Double line5 = list.get(4);
			Double val1 = 202.0d;
			Double val2 = 162.0d;
			Double val3 = 122.0d;
			Double val4 = 82.0d;
			Double val5 = 42.0d;
			assertEquals(line1, val1);
			assertEquals(line2, val2);
			assertEquals(line3, val3);
			assertEquals(line4, val4);
			assertEquals(line5, val5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
