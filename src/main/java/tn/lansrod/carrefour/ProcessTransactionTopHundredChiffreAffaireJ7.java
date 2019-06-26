package tn.lansrod.carrefour;

import tn.lansrod.carrefour.services.StartTask;

public class ProcessTransactionTopHundredChiffreAffaireJ7 {
	
	public static void main( String[] args ) {
		// fichier de transaction contient presque 1.4 million lignes
		// 7eme Task
		// enter la date de transaction en question
		if(args.length != 4) {
			System.out.println("Please enter the start date, file delimiter, input folder path and output folder path.");
			return;
		}
		else {
			StartTask.taskSeven(args);
		}
	}

}
