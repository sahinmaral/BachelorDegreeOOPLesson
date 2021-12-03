import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DynamicButtonsGUI extends JFrame{
    private JTree TreeOptions;
    private JPanel PanelDynamicButtons;
    private JPanel PanelMain;
    private JTextField TextFieldButtonsCount;
    private JButton ButtonGenerateButtons;
    private JPanel PanelGenerateButtons;


    public DynamicButtonsGUI(){
        this.add(PanelMain);
        this.setSize(1000,1000);
        this.setTitle("Dynamic Buttons Homework");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        PanelDynamicButtons.setLayout(new GridLayout(10,10));

        PanelDynamicButtons.setVisible(false);
        PanelGenerateButtons.setVisible(false);

        DefaultMutableTreeNode settings=
                new DefaultMutableTreeNode("Ayarlar");
        DefaultMutableTreeNode closeApp=
                new DefaultMutableTreeNode("Kapat");

        DefaultMutableTreeNode generateButtons=
                new DefaultMutableTreeNode("Buton Oluşturma Paneli Aç");

        settings.add(closeApp);
        settings.add(generateButtons);

        DefaultTreeModel model=new DefaultTreeModel(settings);
        TreeOptions.setModel(model);

        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = TreeOptions.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = TreeOptions.getPathForLocation(e.getX(), e.getY());
                if(selRow != -1) {
                    if(e.getClickCount() == 2) {
                        String option = TreeDoubleClickStringReturn(selPath);
                        if (option.equals("Buton Oluşturma Paneli Aç"))
                            PanelGenerateButtons.setVisible(true);
                        else if(option.equals("Kapat"))
                            System.exit(1);
                    }
                }
            }
        };

        TreeOptions.addMouseListener(ml);

        ButtonGenerateButtons.addActionListener(e->{
            try{
                GenerateButtons(TextFieldButtonsCount.getText());
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this,exception.getMessage());
            }

            PanelDynamicButtons.setVisible(true);

        });

    }

    public void GenerateButtons(String countText) throws Exception{

        PanelDynamicButtons.removeAll();
        PanelDynamicButtons.revalidate();
        PanelDynamicButtons.repaint();

        if (countText.equals(""))
            throw new NullPointerException("Lütfen değer giriniz");
        else if (Integer.parseInt(countText) < 0)
            throw new IllegalArgumentException("Lütfen 0'dan büyük rakam giriniz");

        int count = Integer.parseInt(countText);

        for(int i = 0; i < count ; i++){
            JButton button = new JButton();
            button.setText("Button"+i);
            String message = "Hello from Button "+i;
            button.addActionListener(e->{
                JOptionPane.showMessageDialog(this,message);
            });
            button.setSize(100,100);
            PanelDynamicButtons.add(button);
        }
    }

    private String TreeDoubleClickStringReturn(TreePath selPath) {
        return selPath.getLastPathComponent().toString();
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
            DynamicButtonsGUI gui = new DynamicButtonsGUI();
            gui.setVisible(true);
        });
    }
}
