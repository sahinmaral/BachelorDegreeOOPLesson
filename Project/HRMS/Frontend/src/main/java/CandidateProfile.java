import entities.User;

import javax.swing.*;
import java.awt.*;

public class CandidateProfile extends JFrame {

    public User user;
    private JPanel panelMain;
    private JButton buttonCv;
    private JPanel panelOptions;
    private JButton buttonJobAdvert;

    public CandidateProfile(User user){
        this.user = user;
        this.add(panelMain);
        this.setSize(1280,720);

        buttonCv.addActionListener(e -> {
            panelOptions.removeAll();
            panelOptions.revalidate();
            panelOptions.repaint();
            panelOptions.setLayout(new GridLayout(1,0));
            panelOptions.add(new CandidateCv(user).getContentPane());
            panelOptions.repaint();
            panelOptions.revalidate();
        });

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    public void main(String[] args){
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
            CandidateProfile gui = new CandidateProfile(user);
            gui.setVisible(true);
        });

    }
}
