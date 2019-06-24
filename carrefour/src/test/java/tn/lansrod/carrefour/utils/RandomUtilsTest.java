package tn.lansrod.carrefour.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RandomUtilsTest {
	@Test
    public void shouldGetRandomWithMinMax() {
		int num = RandomUtils.getRandomWithMinMax(1, 5);
		assertTrue("Error, random with Min Max is too high", 5 >= num);
		assertTrue("Error, random with Min Max is too low",  1 <= num);
	}
	
	@Test
    public void shouldGenerateRandomDouble() {
		Double d = RandomUtils.generateRandomDouble();
		String[] aux = d.toString().split("\\.");
		int count = aux[1].length();
		assertEquals(count, 2);
	}
}
