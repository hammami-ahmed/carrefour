package tn.lansrod.carrefour.utils;

import java.util.ArrayList;
import java.util.List;

public class GenerateDataUtils {
	public static String date;
	public static String inputPath;
	public static int nbStore;
	public static int maxproduct;
	
	public static void initParams(String[] args) {
		date = args[0];
		inputPath = args[1];
		nbStore = Integer.parseInt(args[2]);
		maxproduct = Integer.parseInt(args[3]);
	}
	
	public static List<String> initDateList(int index) {
		List<String> list = new ArrayList<>();
		String date = GenerateDataUtils.date;
		for(int i = 1; i <= index; i++) {
			list.add(DateUtils.addDay(DateUtils.parseDateFromString(date), i));
		}
		return list;
	}
}
