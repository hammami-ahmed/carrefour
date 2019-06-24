package tn.lansrod.carrefour.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class RandomUtils {
	public static int getRandomWithMinMax(int min, int max) {
		return (int) ((Math.random() * (max - min + 1))  + min);
	}
	
	public static Map<Integer, String> generateMagasinIDs(int nbStore) {
		Map<Integer, String> listIDs = new HashMap<Integer, String>();
		for(int i = 1; i <= nbStore; i++) {
			listIDs.put(i, UUID.randomUUID().toString());
		}
		return listIDs;
	}
	
	public static Double generateRandomDouble() {
		return (double) Math.round(((999.9d * new Random().nextDouble()) + 1) * 100) / 100;
	}
}
