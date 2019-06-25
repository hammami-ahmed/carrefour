package tn.lansrod.carrefour;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import tn.lansrod.carrefour.services.TransactionReader;
import tn.lansrod.carrefour.services.TransactionWriter;

@RunWith(JUnit4.class)
public class TransactionWriterTest {
	
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
	
	@AfterClass
    public static void after() throws Exception {
		String path = new File("src/test/resources/output").getAbsolutePath();
    	String[] files = new File("src/test/resources/output").list();
    	for(int i = 0; i < files.length; i++) {
    		try {
				deleteDirectoryRecursion(Paths.get(path + "/" + files[i]));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void deleteDirectoryRecursion(Path path) throws IOException {
		if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
		    try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
		      for (Path entry : entries) {
		        deleteDirectoryRecursion(entry);
		      }
		    }
		  }
		  Files.delete(path);
	}
	
	@Test
    public void shouldWriteOutputFile() {
		Map<String, Map<String, Integer>> stream = TransactionReader.read(transactionFilePath, DELIMITER);
		TransactionWriter.write(stream, DATE, outputPath, "");
		int count = new File(outputPath + "/" + DATE).list().length;
		assertEquals(count, 1);
	}
}
