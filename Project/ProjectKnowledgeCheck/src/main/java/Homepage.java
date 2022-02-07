import javax.swing.*;
import java.sql.*;

public class Homepage extends JFrame{
    private JTextField textFieldName;
    private JTextField textFieldSurname;
    private JButton buttonSubmit;
    private JButton buttonShowOnList;
    private JPanel panelMain;

    public Homepage(){

        this.add(panelMain);
        this.setSize(500,200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);


        buttonSubmit.addActionListener(e -> {
            SubmitInformationToDatabase();
        });

        buttonShowOnList.addActionListener(e -> {
            this.setVisible(false);

            ShowOnList form = new ShowOnList();
            form.setVisible(true);
        });

    }

    Connection connection = null;

    private void SubmitInformationToDatabase() {

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/project_knowledge_db",
                            "postgres", "319528");

            String sqlQuery = "INSERT INTO informations (name, surname) VALUES (?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, textFieldName.getText());
            preparedStatement.setString(2, textFieldSurname.getText());

            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this,"Saved on database");

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            Homepage gui = new Homepage();
            gui.setVisible(true);
        });
    }


}

