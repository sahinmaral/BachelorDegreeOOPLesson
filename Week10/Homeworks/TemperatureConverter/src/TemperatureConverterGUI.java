import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemperatureConverterGUI extends JFrame{
    private JPanel panelMain;
    private JButton ButtonConvertTemperature;
    private JComboBox ComboBoxTemperatureConvertType;
    private JTextField TextFieldTemperature;

    public TemperatureConverterGUI() {
        this.add(panelMain);
        this.setSize(500,500);
        this.setTitle("Temperature Converter Homework");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        ButtonConvertTemperature.addActionListener(e -> {
            ConvertTemperature();
        });

        String[] convertTypes = {"C -> F" , "F -> C"};

        ComboBoxModel convertTypeModel = new DefaultComboBoxModel(convertTypes);
        ComboBoxTemperatureConvertType.setModel(convertTypeModel);
    }

    public void ConvertTemperature(){
        double temperature = Double.parseDouble(TextFieldTemperature.getText());
        String convertType = ComboBoxTemperatureConvertType.getSelectedItem().toString();

        switch (convertType){
            case("C -> F"):
                temperature = temperature * (1.8) + 32;
                break;
            case("F -> C"):
                temperature = ((temperature - 32) * 5) / 9;
                break;
        }

        String result = "Converted Type : " + convertType + "\n" + "Result : " + temperature;

        JOptionPane.showMessageDialog(panelMain,result);
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
            TemperatureConverterGUI gui = new TemperatureConverterGUI();
            gui.setVisible(true);
        });

    }
}

