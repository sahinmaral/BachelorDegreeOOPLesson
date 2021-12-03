import javax.swing.*;
import java.util.Random;

public class GuessTheNumberGUI extends JFrame{
    private JPanel PanelMain;
    private JTextField TextFieldGuessedNumber;
    private JButton ButtonGuessNumber;
    private JLabel LabelGuessingResult;

    public GuessTheNumberGUI(){
        this.add(PanelMain);
        this.setSize(500,500);
        this.setTitle("Guess The Number Homework V1");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Random rand = new Random();
        number = rand.nextInt(0,100);


            ButtonGuessNumber.addActionListener(e->{
                try{
                    GuessTheNumber();
                }
                catch (Exception exception){
                    LabelGuessingResult.setText(exception.getMessage());
                    //JOptionPane.showMessageDialog(this,exception.getMessage());
                }
            });




    }

    public static int number;

    public void GuessTheNumber() throws Exception{

        if (TextFieldGuessedNumber.getText().equals(""))
            throw new NullPointerException("You entered nothing on TextField , enter a value");

        int guessedNumber = Integer.parseInt(TextFieldGuessedNumber.getText());

        if (guessedNumber < number)
            LabelGuessingResult.setText("Your guessed number is less than exact number");
            //JOptionPane.showMessageDialog(this,"Your guessed number is less than exact number");
        else if (guessedNumber > number)
            LabelGuessingResult.setText("Your guessed number is greater than exact number");
            //JOptionPane.showMessageDialog(this,"Your guessed number is greater than exact number");
        else
            LabelGuessingResult.setText("YOU FOUND IT");
            //JOptionPane.showMessageDialog(this,"YOU FOUND IT");
    }

    public static void main(String[] args){

        //Set Windows's Component To Your Project
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        //Set UI Thread to another thread
        SwingUtilities.invokeLater(() -> {
            GuessTheNumberGUI gui = new GuessTheNumberGUI();
            gui.setVisible(true);
        });

    }
}
