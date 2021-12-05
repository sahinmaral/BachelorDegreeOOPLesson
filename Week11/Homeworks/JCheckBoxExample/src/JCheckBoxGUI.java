import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Map;

public class JCheckBoxGUI extends JFrame{
    private JTextField TextFieldSample;
    private JButton ButtonShowResult;
    private JCheckBox CheckBoxBold;
    private JCheckBox CheckBoxUnderline;
    private JCheckBox CheckBoxItalic;
    private JCheckBox CheckBoxStrikeThrough;
    private JLabel LabelResult;
    private JPanel PanelMain;

    public JCheckBoxGUI(){
        this.add(PanelMain);
        this.setSize(500,200);
        this.setTitle("Text Decoration Changer");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        ButtonShowResult.addActionListener(e->{
            try{
                ChangeTextDecoration();
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(this,exception.getMessage());
            }
        });
    }

    public void ChangeTextDecoration(){
        if (TextFieldSample.getText().equals(""))
            throw new NullPointerException("You didn't enter any text");

        String text = TextFieldSample.getText();
        LabelResult.setText(text);

        Font f = LabelResult.getFont();
        Map attributes = f.getAttributes();

        LabelFontChangeBold(attributes,f);
        LabelFontChangeItalic(attributes,f);
        LabelFontChangeUnderline(attributes,f);
        LabelFontChangeStrikeThrough(attributes,f);

        LabelResult.setText(text);

    }

    private void LabelFontChangeStrikeThrough(Map attributes,Font f) {
        if (CheckBoxStrikeThrough.isSelected()){
            attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        }
        else{
            attributes.put(TextAttribute.STRIKETHROUGH, -1);
        }

        LabelResult.setFont(f.deriveFont(attributes));


    }

    private void LabelFontChangeUnderline(Map attributes,Font f) {
        if (CheckBoxUnderline.isSelected()){
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        }
        else{
            attributes.put(TextAttribute.UNDERLINE, -1);
        }
        LabelResult.setFont(f.deriveFont(attributes));
    }

    private void LabelFontChangeItalic(Map attributes,Font f) {
        if (CheckBoxItalic.isSelected()){
            attributes.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
        }
        else{
            attributes.put(TextAttribute.POSTURE, TextAttribute.POSTURE_REGULAR);
        }

        LabelResult.setFont(f.deriveFont(attributes));
    }

    public void LabelFontChangeBold(Map attributes,Font f){
        if (CheckBoxBold.isSelected()){
            attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        }
        else{
            attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR);
        }

        LabelResult.setFont(f.deriveFont(attributes));
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
            JCheckBoxGUI gui = new JCheckBoxGUI();
            gui.setVisible(true);
        });
    }

}
