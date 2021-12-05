import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUIWeek301GUI extends JFrame{
    private JButton ButtonFirst;
    private JButton ButtonSecond;
    private JPanel PanelMain;
    private Image ButtonImage;
    private boolean IsButtonFirstEnabled = true;
    private boolean IsButtonSecondEnabled = false;

    public GUIWeek301GUI(){
        this.add(PanelMain);
        this.setSize(500,200);
        this.setTitle("GUIWeek3_01 Homework");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            ButtonImage = ImageIO.read(getClass().getResource("resources/JavaIcon.png"));

        } catch (IOException e) {

        }

        ButtonFirst.setEnabled(true);
        ButtonFirst.setIcon(new ImageIcon(ButtonImage));

        ButtonSecond.setEnabled(false);
        ButtonSecond.setIcon(new ImageIcon(ButtonImage));


        ButtonFirst.addActionListener(e->{
            IsButtonSecondEnabled = !IsButtonSecondEnabled;
            ButtonSecond.setEnabled(IsButtonSecondEnabled);
        });

        ButtonSecond.addActionListener(e->{
            IsButtonFirstEnabled = !IsButtonFirstEnabled;
            ButtonFirst.setEnabled(IsButtonFirstEnabled);
        });

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
            GUIWeek301GUI gui = new GUIWeek301GUI();
            gui.setVisible(true);
        });

    }
}
