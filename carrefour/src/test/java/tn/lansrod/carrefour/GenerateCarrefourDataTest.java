package tn.lansrod.carrefour;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import tn.lansrod.carrefour.generate.GenerateData;
import tn.lansrod.carrefour.utils.DateUtils;
import tn.lansrod.carrefour.utils.RandomUtils;

@RunWith(JUnit4.class)
public class GenerateCarrefourDataTest {
	public static final int MAXPRODUCEDBYSTORE = 10;
	public static final int MINTRANSACTIONLINE = 5;
	public static final int NB_STORE = 1;
	public static String inputPath;
	public static final String DATE = "10/06/2019";
	public static Map<Integer, String> listMagasinIDs;
	
	@BeforeClass
    public static void setup() throws Exception {
		ClassLoader classLoader = new GenerateCarrefourDataTest().getClass().getClassLoader();
		inputPath = classLoader.getResource("").getPath();
		listMagasinIDs = RandomUtils.generateMagasinIDs(NB_STORE);
		GenerateData.generate(DATE, inputPath, NB_STORE, MAXPRODUCEDBYSTORE, MINTRANSACTIONLINE, listMagasinIDs);
    }
	
	@Test
    public void shouldGenerateOneStoreOneTransactionFiles() {
		int count = new File(inputPath).list().length;
		assertEquals(count, 2);
	}
	
	@Test
    public void shouldContainMaxProduct() {
		String date = DateUtils.getStringDate(DATE);
		String fileName = "reference_prod-" + listMagasinIDs.get(1) + "_" + date + ".csv";
		long countReferenceProdLines = 0;
		try {
			countReferenceProdLines = Files.lines(Paths.get(inputPath + "/" + date + "/" + fileName)).count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertEquals(countReferenceProdLines, MAXPRODUCEDBYSTORE);
	}
	
	@Test
    public void shouldContainMinTransaction() {
		String date = DateUtils.getStringDate(DATE);
		String fileName = "transactions_" + DateUtils.getStringDate(DATE) + ".csv";
		long countTransactionLines = 0;
		try {
			countTransactionLines = Files.lines(Paths.get(inputPath + "/" + date + "/" + fileName), 
													  Charset.defaultCharset()).count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue("Error, transaction file lines is too low",  MINTRANSACTIONLINE  <= countTransactionLines);
	}
	
}
