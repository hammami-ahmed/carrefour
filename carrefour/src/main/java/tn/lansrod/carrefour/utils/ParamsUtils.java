package tn.lansrod.carrefour.utils;

public class ParamsUtils {
	public static String date;
	public static String delimiter;
	public static String inputDirectory;
	public static String outputDirectory;
	public static String tranctionFilePath;
	public static String dateToProcess;
	
	public static void initParams(String[] args) {
		date = args[0];
		delimiter = args[1];
		inputDirectory = args[2];
		outputDirectory = args[3];
		dateToProcess = DateUtils.getStringDate(ParamsUtils.date);
		tranctionFilePath = inputDirectory + "/" + dateToProcess + "/transactions_" + dateToProcess + ".csv";
	}
}
