package utils;

import java.util.Random;

public class utils {
	
	// BORED OF TYPING System.out.println(str); NOW INTRODUCING print
    public static void print(String str) {

        System.out.println(str);
    }
    
    
    public static String random_len_x_digit_number(int x) {
		
    	String str=""; 
    	int random_number = 0;
    	Random rand = new Random();
    	
    	while(str.length()<x){
    		
    		random_number = rand.nextInt(10);
            str = str + String.valueOf(random_number); 
    		
    	}
    	
    	return str;
    	   
    }
    
    
}

