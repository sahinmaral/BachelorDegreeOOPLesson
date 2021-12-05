import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class JComboBoxExampleGUI extends JFrame{
    public JPanel PanelMain;
    public JComboBox ComboBoxCarModel;
    public JComboBox ComboBoxBrand;
    public JComboBox ComboBoxCarType;
    private JButton ButtonCalculateCar;
    private JToggleButton ToggleButtonDarkLightMode;
    private JLabel LabelCarType;
    private JLabel LabelCarBrand;
    private JLabel LabelCarModel;

    public JComboBoxExampleGUI(){
        this.add(PanelMain);
        this.setSize(500,200);
        this.setTitle("Rent A Car JComboBox Example");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Color whiteColour = new Color(255,255,255);
        Color blackColour = new Color(0,0,0);

        List<String> carTypes = new ArrayList<>();
        carTypes.add("SUV");
        carTypes.add("SEDAN");
        carTypes.add("SPORTS CAR");

        ComboBoxModel comboBoxCarTypeModel = new DefaultComboBoxModel(carTypes.toArray());
        ComboBoxCarType.setModel(comboBoxCarTypeModel);


        ComboBoxCarType.addActionListener(e->{
            ComboBoxCarModel.setModel(new DefaultComboBoxModel());
            ComboBoxBrand.setModel(SetComboBoxBrand((String) ComboBoxCarType.getSelectedItem()));
            ComboBoxBrand.setEnabled(true);
        });

        ComboBoxBrand.addActionListener(e -> {
            ComboBoxCarModel.setModel(SetComboCarModel((String) ComboBoxBrand.getSelectedItem() , (String) ComboBoxCarType.getSelectedItem()));
            ComboBoxCarModel.setEnabled(true);
        });

        ToggleButtonDarkLightMode.addActionListener(e -> {
            if (ToggleButtonDarkLightMode.isSelected()){
                PanelMain.setBackground(blackColour);
                LabelCarBrand.setForeground(whiteColour);
                LabelCarModel.setForeground(whiteColour);
                LabelCarType.setForeground(whiteColour);
            }

            else{
                PanelMain.setBackground(whiteColour);
                LabelCarBrand.setForeground(blackColour);
                LabelCarModel.setForeground(blackColour);
                LabelCarType.setForeground(blackColour);
            }

        });

        ButtonCalculateCar.addActionListener(e -> {
            try {
                CalculateCar((String) ComboBoxCarModel.getSelectedItem());
            }catch (Exception exception){
                JOptionPane.showMessageDialog(this,exception.getMessage());
            }

        });
    }

    private ComboBoxModel SetComboCarModel(String carBrand,String carType) {
        List<String> carModels = new ArrayList<>();

        switch (carType) {
            case "SUV":
                switch (carBrand){
                    case "Mazda":
                        carModels.add("Mazda CX-5");
                        break;
                    case "Hyundai":
                        carModels.add("Hyundai 1.4 MPI Style");
                }
                break;
            case "SEDAN":
                switch (carBrand){
                    case "Volkswagen":
                        carModels.add("Volkswagen Passat 1.6 TDI BMT ComfortLine");
                        break;
                    case "Volvo":
                        carModels.add("Volvo 2.0 D D5 Inscription");
                }
                break;
            case "SPORTS CAR":
                switch (carBrand){
                    case "BMW":
                        carModels.add("BMW 3 Serisi 5.20i");
                        break;
                    case "Audi":
                        carModels.add("Audio A4 Sedan 2.0");
                }
                break;

        }

        ComboBoxModel convertTypeModel = new DefaultComboBoxModel(carModels.toArray());
        return convertTypeModel;
    }

    public void CalculateCar(String carModel){

        if (carModel == null)
            throw new NullPointerException("Araba modeli seçiniz");

        Dictionary<String,Integer> modelPriceDictionary = new Hashtable<>();
        modelPriceDictionary.put("BMW 3 Serisi 5.20i",400000);
        modelPriceDictionary.put("Audio A4 Sedan 2.0",400000);

        modelPriceDictionary.put("Mazda CX-5",400000);
        modelPriceDictionary.put("Hyundai 1.4 MPI Style",400000);

        modelPriceDictionary.put("Volkswagen Passat 1.6 TDI BMT ComfortLine",400000);
        modelPriceDictionary.put("Volvo 2.0 D D5 Inscription",400000);

        String carType = (String) ComboBoxCarType.getSelectedItem();
        String carBrand = (String) ComboBoxBrand.getSelectedItem();

        String message = "You choosed " + carType +
                " type " + carBrand +
                " brand " + carModel +
                " model \n\n" + "Price is " +
                modelPriceDictionary.get(carModel);

        JOptionPane.showMessageDialog(this,message);
    }

    public static ComboBoxModel SetComboBoxBrand(String carType){

        List<String> brands = new ArrayList<>();
        
        switch (carType){
            case "SUV" :
                brands.add("Mazda");
                brands.add("Hyundai");
            break;
            case "SEDAN":
                brands.add("Volkswagen");
                brands.add("Volvo");
                break;
            case "SPORTS CAR":
                brands.add("BMW");
                brands.add("Audi");
                break;
        }

        ComboBoxModel convertTypeModel = new DefaultComboBoxModel(brands.toArray());
        return convertTypeModel;
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
            JComboBoxExampleGUI gui = new JComboBoxExampleGUI();
            gui.setVisible(true);
        });
    }
}
