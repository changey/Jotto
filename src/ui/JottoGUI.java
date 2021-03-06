package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import model.JottoModel;

/**
 * Deals with the parts to be shown through the JFrame.
 * Provides listeners for the interactive components of the GUI
 * and transmits requests to JottoModel
 */
@SuppressWarnings("serial")
public class JottoGUI extends JFrame {

    private JButton newPuzzleButton;
    private JTextField newPuzzleNumber;
    private JLabel puzzleNumber;
    private JLabel puzzlePrompt;
    private JTextField guess;
    private JTable guessTable;
    private DefaultTableModel table;
    private JottoModel model;
    GroupLayout layout;

    public JottoGUI() {
        model = new JottoModel(16952);
        table = new DefaultTableModel(0,3);
        newPuzzleButton = new JButton();
        newPuzzleButton.setName("newPuzzleButton");
        newPuzzleButton.setText("New Puzzle");
        newPuzzleNumber = new JTextField();
        newPuzzleNumber.setName("newPuzzleNumber");
        puzzleNumber = new JLabel();
        puzzleNumber.setName("puzzleNumber");
        puzzlePrompt = new JLabel();
        puzzlePrompt.setName("puzzlePrompt");
        puzzlePrompt.setText("Type a guess here:");
        guess = new JTextField();
        guess.setName("guess");
        guessTable = new JTable(table);
        guessTable.setName("guessTable");
        guessTable.setSize(200,50);
        puzzleNumber.setText("Puzzle Number #16952");

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout
                .createParallelGroup()
                .addGroup(
                        layout.createSequentialGroup()
                                .addComponent(puzzleNumber)
                                .addComponent(newPuzzleButton)
                                .addComponent(newPuzzleNumber))
                .addGroup(
                        layout.createSequentialGroup()
                                .addComponent(puzzlePrompt).addComponent(guess))
                .addComponent(guessTable));
        layout.setVerticalGroup(layout
                .createSequentialGroup()
                .addGroup(
                        layout.createParallelGroup().addComponent(puzzleNumber)
                                .addComponent(newPuzzleButton)
                                .addComponent(newPuzzleNumber))
                .addGroup(
                        layout.createParallelGroup().addComponent(puzzlePrompt)
                                .addComponent(guess)).addComponent(guessTable));

        newPuzzleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeNewPuzzle();
                repaint();
            }
        });
        
        guess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String word = guess.getText();
                table.addRow(new String[]{word, "pending...", ""});
                (new DealGuess(word, table.getRowCount()-1)).execute();
                guess.setText("");
                                
            }
        });
        
    }

    /**
     * Responds to the 'New Puzzle' button by making new JottoModel with the
     * contents of the text box or a random int.
     */
    public void makeNewPuzzle() {
            String puzNum = newPuzzleNumber.getText();
            try {
                model = new JottoModel(Integer.parseInt(puzNum));
            } catch (Exception e) {
            	//the catch condition specified
                model = new JottoModel(Math.abs(new Random().nextInt()));
            }
            puzzleNumber.setText("Puzzle # " + model.getPuzzleNumber());
            table.setNumRows(0);
            update();
    }
    
    /**
     * Create the GUI features
     */
    public void update() {
        guessTable.updateUI();
        repaint();
        this.pack();
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JottoGUI main = new JottoGUI();
                main.pack();
                main.setVisible(true);
            }
        });
    }
    /**
     * 
     * To process the guess result and create multi threads when needed
     * @author eric61213
     *
     */
    public class DealGuess extends SwingWorker<int[], Object> {
   	    private String theword;
        private int rowNumber;
       // private DefaultTableModel table;
        public DealGuess(String word, int rowNum) {
            this.theword = word;
            this.rowNumber = rowNum;
        }
   	
   	
        @Override
        public int[] doInBackground() {
            return JottoModel.makeGuess(theword);
        }
   	
   	
   	 @Override
        protected void done() {
            int[] result = new int[2];
            try {
                result = get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            //this is the method to parse the result
            if (result ==  null) {
                table.removeRow(rowNumber);
                table.insertRow(rowNumber, new String[]{theword, "Invalid guess.", ""});
                
            }
            else {
                if (result[0] == 5 && result[1] == 5) {
                    table.removeRow(rowNumber);
                    table.insertRow(rowNumber, new String[]{theword,"You win"
                            ,"you win"});
                } else {
                    table.removeRow(rowNumber);
                    table.insertRow(rowNumber, new String[]{theword, result[0]+"", result[1]+""});
                }
                
            }
            update();
   }
}
}
