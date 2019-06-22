package tn.lansrod.carrefour.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	// convertion de date vers un format iso
	public static String convertDateToISOFormat(String stringDate, int hour, int min, int second) {
		Date date = convertDateSetTime(stringDate, hour, min, second, 0).getTime();
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		sdf.setTimeZone(TimeZone.getTimeZone("CET"));
		return sdf.format(date);
	}
	
	// seter l'heure les mins et les secondes pour une date
	public static Calendar convertDateSetTime(String date, int hour, int minute, int second, int millisecond) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	cal.set(Calendar.HOUR_OF_DAY,hour);
    	cal.set(Calendar.MINUTE,minute);
    	cal.set(Calendar.SECOND,second);
    	cal.set(Calendar.MILLISECOND,millisecond);
    	return cal;
	}
	
	public static String getStringDate(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	// convertion de date vers le format yyyyMMdd
	public static String getStringDate(String date) {
		Date dateToFormat = parseDateFromString(date);
		return new SimpleDateFormat("yyyyMMdd").format(dateToFormat);
	}
	
	
	// convertion de date de string vers date
	public static Date parseDateFromString(String date) {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String addDay(Date date, int day) {
		Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day); //same with c.add(Calendar.DAY_OF_MONTH, 1);
        // convert calendar to date
        Date currentDatePlusOne = c.getTime();
        return dateFormat.format(currentDatePlusOne);
	}
}
