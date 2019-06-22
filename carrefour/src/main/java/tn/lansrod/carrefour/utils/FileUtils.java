package tn.lansrod.carrefour.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public class FileUtils {
	
	// ouverture d'un fichier csv
	public static CSVWriter openFile(String fullPath) {
		try {
			return new CSVWriter(new FileWriter(fullPath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// fermeture d'un fichier csv
	public static void closeFile(CSVWriter writer) {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// creation d'un fichier
	public static Boolean createFile(File file) {
		try {
			if(file.createNewFile()) {
				return true;
			}
			else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// creation d'un folder
	public static void createDirectory(String directoryName) {
		File directory = new File(directoryName);
		if (! directory.exists()){
	        directory.mkdir();
	    }
	}
	
//	public static File getFileStartWith(String dirPath, String startWith) {
//		File result = null;
//		File dir = new File(dirPath);
//        if(!dir.isDirectory()) throw new IllegalStateException("there is not a directory !!");
//        for(File file : dir.listFiles()) {
//            if(file.getName().startsWith(startWith))
//            	result =  file;
//            break;
//        }
//        return result;
//	}
	
}
