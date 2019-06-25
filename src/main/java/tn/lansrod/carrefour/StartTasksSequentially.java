package tn.lansrod.carrefour;

import tn.lansrod.carrefour.services.StartTask;

public class StartTasksSequentially {
	public static void main( String[] args ) {
    	if(args.length != 4) {
			// exemple 10/06/2019 | /home/xx/input /home/xx/output
			System.out.println("Please enter the date, input folder path, nb store and product by store.");
			return;
		}
    	else {
    		StartTask.taskOne(args);
    		StartTask.taskTwo(args);
    		StartTask.taskthree(args);
    		StartTask.taskFour(args);
    		StartTask.taskFive(args);
    		StartTask.taskSix(args);
    		StartTask.taskSeven(args);
    	}
    }
}
