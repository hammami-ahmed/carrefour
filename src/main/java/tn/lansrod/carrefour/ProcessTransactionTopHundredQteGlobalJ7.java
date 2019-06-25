package tn.lansrod.carrefour;

import tn.lansrod.carrefour.services.StartTask;

public class ProcessTransactionTopHundredQteGlobalJ7 {
	// fichier de transaction contient presque 1.4 million lignes
	// 6eme task
	
	public static void main( String[] args ) {
		// enter la date de transaction en question
		if(args.length != 4) {
			// exemple 10/06/2019 | /home/xx/input /home/xx/output
			System.out.println("Please enter the date, file delimiter, input folder path and output folder path.");
			return;
		}
		else {
			StartTask.taskSix(args);
		}
	}
}
