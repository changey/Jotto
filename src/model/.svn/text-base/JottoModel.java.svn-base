package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * The data behind the scenes of the JottoGUI, variables and operations are stored here
 * and displayed by the GUI. Requests from the GUI also make their way to method of
 * this class.
 */
public class JottoModel {
    private static int puzzleNum;
    public JottoModel(int puzzleN) {
        puzzleNum = puzzleN;
    }
    
    /**
     * @return - returns this game's puzzle number
     */
    public int getPuzzleNumber() {
        return puzzleNum;
    }
	
	/**
	 * @guess - a String symbol of the client's guess
	 * 
	 * @return - null if there was an error, otherwise an int[2]
	 *                 where the first number is the number of letters
	 *                 that match and the second is correct positions.
	 */
	public static int[]  makeGuess(String guess) {
		try {
            URL server = new URL("http://6.005.scripts.mit.edu/jotto.py?puzzle="+
                    puzzleNum+"&guess="+guess);
            BufferedReader in = new BufferedReader(new InputStreamReader(server.openStream()));
            String response = in.readLine();
            String[] tokens = response.split(" ");
            if (tokens[0].equals("guess")) {
                return new int[] {new Integer(tokens[1]).intValue(), new Integer(tokens[2]).intValue()};
            }
            else {
                throw new Exception();
            }
        } catch (Exception e) {
            return null;
        }
	}

}