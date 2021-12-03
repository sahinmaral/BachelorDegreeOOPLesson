import javax.swing.*;
import java.util.Random;

public class GuessTheNumberV2 extends JFrame{
    private JPanel PanelMain;
    private JTextField TextFieldGuessingNumber;
    private JButton ButtonGuessComputer;
    private JButton ButtonDecreaseEnteredNumber;
    private JButton ButtonIncreaseEnteredNumber;

    public GuessTheNumberV2(){
        this.add(PanelMain);
        this.setSize(800,200);
        this.setTitle("Guess The Number V2 Homework");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        ButtonIncreaseEnteredNumber.setEnabled(false);
        ButtonDecreaseEnteredNumber.setEnabled(false);

        ButtonGuessComputer.addActionListener(e->{
            if (TextFieldGuessingNumber.getText().equals(""))
                JOptionPane.showMessageDialog(this,"Boş değer girdiniz , 0-100 arasında değer giriniz.");

            else if(Integer.parseInt(TextFieldGuessingNumber.getText()) < 0 || Integer.parseInt(TextFieldGuessingNumber.getText()) > 100)
                JOptionPane.showMessageDialog(this,"Değer girdiniz fakat 0-100 arasında değer girmeniz gerekir.");

            else{
                exactNumber = Integer.parseInt(TextFieldGuessingNumber.getText());
                TextFieldGuessingNumber.setEnabled(false);
                ButtonIncreaseEnteredNumber.setEnabled(true);
                ButtonDecreaseEnteredNumber.setEnabled(true);
                LetTheComputerGuess();
            }
        });

        ButtonIncreaseEnteredNumber.addActionListener(e->{
            IncreaseCheatingCount();
            exactNumber++;
            TextFieldGuessingNumber.setText(String.valueOf(exactNumber));
        });

        ButtonDecreaseEnteredNumber.addActionListener(e->{
            IncreaseCheatingCount();
            exactNumber--;
            TextFieldGuessingNumber.setText(String.valueOf(exactNumber));
        });
    }

    private void IncreaseCheatingCount() {
        cheatingCount++;
    }

    private void LetTheComputerGuess(){
        Random rand = new Random();
        int computerGuessNumber = rand.nextInt(0,3);

        if (computerGuessNumber != exactNumber && cheatingCount == 0)
            JOptionPane.showMessageDialog(this,"I didn't found it . Can you let me to try again ?");

        else if (computerGuessNumber != exactNumber && cheatingCount > 0) {
            String message = "I didn't found it , because you are cheating " + cheatingCount + " times . Can you let me to try again ?";
            JOptionPane.showMessageDialog(this, message);
        }

        else if (computerGuessNumber == exactNumber && cheatingCount > 0){
            String message = "Hurray , I won even you are cheating " + cheatingCount + " times";
            TextFieldGuessingNumber.setEnabled(true);
            ButtonIncreaseEnteredNumber.setEnabled(false);
            ButtonDecreaseEnteredNumber.setEnabled(false);
            JOptionPane.showMessageDialog(this, message);

        }

        else{
            JOptionPane.showMessageDialog(this,"Hurray , I won !!");
            TextFieldGuessingNumber.setEnabled(true);
            ButtonIncreaseEnteredNumber.setEnabled(false);
            ButtonDecreaseEnteredNumber.setEnabled(false);
        }
    }

    int exactNumber = 0;
    int cheatingCount = 0;

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
            GuessTheNumberV2 gui = new GuessTheNumberV2();
            gui.setVisible(true);
        });
    }


}
