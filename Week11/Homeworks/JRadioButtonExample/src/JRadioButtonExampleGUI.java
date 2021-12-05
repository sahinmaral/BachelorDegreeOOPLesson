import javax.swing.*;

public class JRadioButtonExampleGUI extends JFrame{
    private JPanel PanelMain;
    private JRadioButton RadioButtonCSharp;
    private JRadioButton RadioButtonJava;
    private JRadioButton RadioButtonNodeJs;
    private JRadioButton RadioButtonPython;
    private JLabel LabelPicture;

    public JRadioButtonExampleGUI(){
        this.add(PanelMain);
        this.setSize(500,300);
        this.setTitle("Radio Button Example");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        RadioButtonCSharp.addActionListener(e -> {
            ChangeLabelIcon("CSharp.png");
        });

        RadioButtonJava.addActionListener(e -> {
            ChangeLabelIcon("Java.png");
        });

        RadioButtonNodeJs.addActionListener(e -> {
            ChangeLabelIcon("NodeJs.png");
        });

        RadioButtonPython.addActionListener(e -> {
            ChangeLabelIcon("Python.png");
        });

        ButtonGroup group = new ButtonGroup();
        group.add(RadioButtonCSharp);
        group.add(RadioButtonJava);
        group.add(RadioButtonPython);
        group.add(RadioButtonNodeJs);
    }

    public void ChangeLabelIcon(String path){
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        icon.getImage().flush();
        LabelPicture.setIcon(icon);
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
            JRadioButtonExampleGUI gui = new JRadioButtonExampleGUI();
            gui.setVisible(true);
        });

    }
}
