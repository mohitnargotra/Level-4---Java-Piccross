package piccross;
import java.util.ArrayList;
import static piccross.Game.DIM;

/**
 * @author mohit
 * This class is the brain of this game
 * It contains the logic to of the game from generating board to creating labels
 *
 */
public class GameModel {
	
	private static ArrayList<ArrayList<Integer>> colClue;
    private static ArrayList<ArrayList<Integer>> rowClue;		
	private String configString; 
	private static boolean [][] dimGrid; 
	

//--------------------------  CONSTRUCTOR  ---------------------------//
	
/**
 * Constructor for the class
 * @param configString
 * 
 * This constructor takes configString as parameter so that I can set the default string to match assigment instructions
 */
public GameModel(String configString) {
this.configString = configString;
dimGrid = randStrWr(configString);
colClue = makeColClue(dimGrid);
rowClue = makeRowClue(dimGrid);
	
}

//-------------------------- FUNCTION FOR GENERATIN RANDOM STRING   ---------------------------//


/**
 * This method creates random string based on dimensions. 
 * This function is used so that our board is random every time we run the game 
 * @return
 * It returns the random string response that we pass to constructor
 */
public static String randStr () {	
		   String randString = "";
		   for(int i = 0; i < DIM*DIM; i++){ 
		      if(Math.random()>=0.5){
		    	  randString +="1";
		      } else {
		    	  randString += "0";
		      }
		   }
			   return randString;		
}

//--------------------------  Function for writing bits from string into dimGrid  ---------------------------//

/**
 * This method takes the random string generated in randStr method 
 * 
 * @param str
 * It takes the string generated in randStr method and uses it as an argument to create a 2D boolean board
 * 
 * @return
 * This method returns a board of 2D array 
 */
public boolean[][] randStrWr(String str) {
	
	String subst[];
    boolean board[][] = new boolean[DIM][DIM];

    for(int i = 0; i < DIM; i++) {
        subst = configString.substring(i*DIM, i*DIM + DIM).split("");
        for(int j = 0; j < DIM; j++) {
        String digit = subst[j];
            if (digit.equals("1")){
            	board[i][j] = true;
            }            
            else board[i][j] = false;          		
    
        }}
 return board;    
    
}

//-------------------------- THIS METHOD IS GENERATING CLUES FOR THE LEFT COLUMN OF THE PICCROSS BOARD ---------------------------//

/**
 * This method takes the 2D arraylist board created in the previous function and then generates column labels
 * 
 * @param board
 * Takes 2D array list of boolean board 
 * 
 * @return
 * it returns column label array list that is then called in GameView class
 */
private ArrayList<ArrayList<Integer>> makeColClue(boolean[][] board) {

    int count = 0;
    ArrayList <ArrayList<Integer>> colClue = new ArrayList<>(DIM);

    for(int y = 0; y < DIM; y++) {
        ArrayList<Integer> hintCell = new ArrayList<>();
        for(int x = 0; x < DIM; x++) {
            if(board[y][x]) {
                count++;
            }
            else if(count > 0) {
                hintCell.add(count);
                count = 0;
            }
            else ;
        }
        //Need one last check if count > 0 since for loop exits too early
        if(count > 0)
            hintCell.add(count);

        colClue.add(hintCell);
        count = 0;
    }

    return colClue;
}

//-------------------------- THIS METHOD IS GENERATING CLUES FOR THE TOP ROW OF THE PICCROSS BOARD ---------------------------//

/**
 * This method takes the 2D array list board created in the previous function and then generates row labels
 * 
 * @param board
 * Takes 2D array list of boolean board 
 * 
 * @return
 * it returns row label array list that is then called in GameView class
 */
private ArrayList<ArrayList<Integer>> makeRowClue(boolean[][] board) {

    int count = 0;
    ArrayList<ArrayList<Integer>> rowClue = new ArrayList<>(DIM);

    for(int x = 0; x < DIM; x++) {
        ArrayList<Integer> hintCell = new ArrayList<>();
        for(int y = 0; y < DIM; y++) {
            if(board[y][x]) {
                count++;
            }
            else if(count > 0) {
                hintCell.add(count);
                count = 0;
            }
            else ;
        }
        //Need one last iteration since for loop exits too early
        if(count > 0)
            hintCell.add(count);

        rowClue.add(hintCell);
        count = 0;
    }

    return rowClue;
}
	

//------------- Getters -----------------

public static boolean[][] getDimGrid() {
	return dimGrid;	
	}
	
public static ArrayList<ArrayList<Integer>> getColClue() {

	return colClue;
}

public static ArrayList<ArrayList<Integer>> getRowClue() {
    
    return rowClue;	
	
}
}
//End of class
