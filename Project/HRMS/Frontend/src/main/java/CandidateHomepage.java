import entities.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CandidateHomepage extends JFrame{
    private JPanel panelMain;
    private JButton buttonProfile;
    private JPanel panelSidebar;
    private JButton buttonSignOff;
    private JPanel panelAnotherForms;

    public static User user;

    public CandidateHomepage(User user){
        this.user = user;
        this.add(panelMain);
        this.setSize(1280,720);
        this.setTitle("Candidate Homepage");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);

        File currentDirFile = new File("");

        String customerPicture = "";
        String signOffPicture = "";

        try {
            customerPicture = currentDirFile.getCanonicalPath() + "\\src\\main\\java\\images\\customer.png";
            signOffPicture = currentDirFile.getCanonicalPath() + "\\src\\main\\java\\images\\logout.png";
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageIcon customerIcon = new ImageIcon(customerPicture);
        ImageIcon signOffIcon = new ImageIcon(signOffPicture);
        buttonProfile.setIcon(customerIcon);
        buttonSignOff.setIcon(signOffIcon);

        buttonSignOff.addActionListener(e -> {
            this.setVisible(false);
            Login form = new Login();
            form.setVisible(true);
        });

        buttonProfile.addActionListener(e -> {
            panelAnotherForms.removeAll();
            panelAnotherForms.revalidate();
            panelAnotherForms.repaint();
            panelAnotherForms.setLayout(new GridLayout(1,0));
            panelAnotherForms.add(new CandidateProfile(user).getContentPane());
            panelAnotherForms.repaint();
            panelAnotherForms.revalidate();
        });


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
            CandidateHomepage gui = new CandidateHomepage(user);
            gui.setVisible(true);
        });
    }
}
