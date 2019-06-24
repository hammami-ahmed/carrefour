package tn.lansrod.carrefour.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import tn.lansrod.carrefour.utils.DateUtils;

@RunWith(JUnit4.class)
public class DateUtilsTest {
	@Test
    public void shouldConvertDateToISOFormat() {
		String date = DateUtils.convertDateToISOFormat("10/06/2019", 0, 0, 0);
		assertEquals(date, "2019-06-10T00:00:00.000+02:00");
	}
	
	@Test
    public void shouldConvertDateToyyyyMMdd() {
		String date = DateUtils.getStringDate("10/06/2019");
		assertEquals(date, "20190610");
	}
	
	@Test
    public void shouldAddDay() {
		String date = DateUtils.addDay(DateUtils.parseDateFromString("10/06/2019"), 1);
		assertEquals(date, "11/06/2019");
	}
	
}
