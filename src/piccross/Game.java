package piccross;

/**
 * @author mohit
 * This is the main class of my program
 * This class calls other classes and contains the main method which runs my program
 *
 */
public class Game{  	

	// The default dimension of my piccross game is 5 X 5 
	public static int DIM = 5;
	
	//This default string is used to generate the default board of my game as mentioned in the assignment instructions 
	static String defaultStr = "0010000100111110111001010";		

       public static void main(String args[])   {      	  
    	  GameModel gameModel = new GameModel(defaultStr); 
    	  GameView gameView = new GameView();
    	  new GameController(gameView, gameModel);  
    	 
    	     	   	       	         
       }              
}
//End of class
