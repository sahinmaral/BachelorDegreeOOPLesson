
import javax.swing.*;
import java.awt.*;

public class ColorChangerGUI extends JFrame{
    private JSlider SliderRed;
    private JSlider SliderGreen;
    private JSlider SliderBlue;
    private JPanel PanelMain;
    private JPanel PanelColorChanged;

    public ColorChangerGUI(){
        this.add(PanelMain);
        this.setSize(500,500);
        this.setTitle("Color Changer Homework");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        SliderRed.addChangeListener(e->{
            ChangePanelColorChange();
        });
        SliderGreen.addChangeListener(e->{
            ChangePanelColorChange();
        });
        SliderBlue.addChangeListener(e->{
            ChangePanelColorChange();
        });

    }


    public void ChangePanelColorChange(){
        int redValue = SliderRed.getValue();
        int greenValue = SliderGreen.getValue();
        int blueValue = SliderBlue.getValue();


        PanelColorChanged.setBackground(new Color(redValue,greenValue,blueValue));
    }

    public static void main(String[] args){

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
            ColorChangerGUI gui = new ColorChangerGUI();
            gui.setVisible(true);
        });
    }


}
