package tn.lansrod.carrefour.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParamsJ7Utils {
	public static String date;
	public static String delimiter;
	public static String inputDirectory;
	public static String outputDirectory;
	public static String tranctionFilePath;
	public static String dateToProcess;
	public static Date start;
	public static List<String> treansactionDateInterval;
	
	public static void initParams(String[] args) {
		date = args[0];
		delimiter = args[1];
		inputDirectory = args[2];
		outputDirectory = args[3];
		start = DateUtils.parseDateFromString(date);
		treansactionDateInterval = new ArrayList<>();
		treansactionDateInterval.add(date);
		for(int i = 1; i <= 6; i++) {
			treansactionDateInterval.add(DateUtils.addDay(start, i));	
		}
	}
	
	public static String getDateToProcess(int index) {
		String date = ParamsJ7Utils.treansactionDateInterval.get(index);
		return DateUtils.getStringDate(date);
	}
	
	public static String initPath(int index) {
		String date = ParamsJ7Utils.treansactionDateInterval.get(index);
		dateToProcess = DateUtils.getStringDate(date);
		return inputDirectory + "/" + dateToProcess + "/transactions_" + dateToProcess + ".csv";
	}
	
}
