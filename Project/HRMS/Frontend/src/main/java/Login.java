import entities.Candidate;
import entities.Employee;
import entities.Employer;
import entities.User;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Login extends JFrame{
    private JTextField textFieldEmail;
    private JButton buttonLogin;
    //private JTextField textFieldPassword;
    private JPanel panelMain;
    private JButton buttonRegisterEmployer;
    private JButton buttonRegisterCandidate;
    private JLabel textFieldTittle;
    private JPasswordField passwordFieldPassword;
    private User user;

    public Login(){
        this.add(panelMain);
        this.setSize(550,250);
        this.setTitle("Login");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        File currentDirFile = new File("");

        String helper = "";

        try {
            helper = currentDirFile.getCanonicalPath() + "\\src\\main\\java\\images\\login.png";
        } catch (IOException e) {
            e.printStackTrace();
        }


        ImageIcon icon = new ImageIcon(helper);
        textFieldTittle.setIcon(icon);

        buttonLogin.addActionListener(e->{
            checkUser(textFieldEmail.getText(), String.valueOf(passwordFieldPassword.getPassword()));
        });

        buttonRegisterCandidate.addActionListener(e -> {
            this.setVisible(false);
            new RegisterCandidate().setVisible(true);
        });

    }

    public void checkUser(String email,String password) {

        try {
            if (checkIfUserIsCandidate(email,password)){
                this.setVisible(false);

                CandidateHomepage form = new CandidateHomepage(user);
                form.setVisible(true);
            }

            else if (checkIfUserIsEmployer(email,password))
                JOptionPane.showMessageDialog(this,"You are logging as employer");
            else if (checkIfUserIsEmployee(email,password))
                JOptionPane.showMessageDialog(this,"You are logging as employee");
            else
                JOptionPane.showMessageDialog(this,"Your email or password is incorrect");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }

    public boolean checkIfUserIsCandidate(String email,String password) throws Exception{
        ArrayList<Candidate> candidates = new ArrayList<Candidate>();

        URL candidateGetAll = new URL("http://localhost:8080/api/candidates/getAll");
        HttpURLConnection conn = (HttpURLConnection) candidateGetAll.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {

            String inline = "";
            Scanner scanner = new Scanner(candidateGetAll.openStream());

            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            //Close the scanner
            scanner.close();

            String jsonString = inline ;
            JSONObject obj = new JSONObject(jsonString);

            JSONArray arr = obj.getJSONArray("data");
            for (int i = 0; i < arr.length(); i++)
            {
                Candidate candidate = new Candidate();
                candidate.id = arr.getJSONObject(i).getInt("id");
                candidate.firstName = arr.getJSONObject(i).getString("firstName");
                candidate.lastName = arr.getJSONObject(i).getString("lastName");
                candidate.identityId = arr.getJSONObject(i).getString("identityId");
                candidate.birthYear = arr.getJSONObject(i).getInt("birthYear");
                candidate.password = arr.getJSONObject(i).getString("password");
                candidate.email = arr.getJSONObject(i).getString("email");
                candidates.add(candidate);
            }

            for(int i = 0 ; i < candidates.size(); i++){

                if (candidates.get(i).email.equals(email) && candidates.get(i).password.equals(password)){
                    user = candidates.get(i);
                    return true;
                }

            }

            return false;
        }
    }

    public boolean checkIfUserIsEmployee(String email,String password) throws Exception{
        ArrayList<Employee> employees = new ArrayList<Employee>();

        URL employeeGetAll = new URL("http://localhost:8080/api/employees/getAll");
        HttpURLConnection conn = (HttpURLConnection) employeeGetAll.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {

            String inline = "";
            Scanner scanner = new Scanner(employeeGetAll.openStream());

            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            //Close the scanner
            scanner.close();

            String jsonString = inline ;
            JSONObject obj = new JSONObject(jsonString);

            JSONArray arr = obj.getJSONArray("data");
            for (int i = 0; i < arr.length(); i++)
            {
                Employee employee = new Employee();
                employee.firstName = arr.getJSONObject(i).getString("firstName");
                employee.lastName = arr.getJSONObject(i).getString("lastName");
                employee.password = arr.getJSONObject(i).getString("password");
                employee.email = arr.getJSONObject(i).getString("email");
                employees.add(employee);
            }

            for(int i = 0 ; i < employees.size(); i++){

                if (employees.get(i).email.equals(email) && employees.get(i).password.equals(password))
                    return true;
            }

            return false;
        }
    }

    public boolean checkIfUserIsEmployer(String email,String password) throws Exception{
        ArrayList<Employer> employers = new ArrayList<Employer>();

        URL employerGetAll = new URL("http://localhost:8080/api/employers/getAll");
        HttpURLConnection conn = (HttpURLConnection) employerGetAll.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {

            String inline = "";
            Scanner scanner = new Scanner(employerGetAll.openStream());

            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            //Close the scanner
            scanner.close();

            String jsonString = inline ;
            JSONObject obj = new JSONObject(jsonString);

            JSONArray arr = obj.getJSONArray("data");
            for (int i = 0; i < arr.length(); i++)
            {
                Employer employer = new Employer();
                employer.companyName = arr.getJSONObject(i).getString("companyName");
                employer.webSite = arr.getJSONObject(i).getString("webSite");
                employer.password = arr.getJSONObject(i).getString("password");
                employer.email = arr.getJSONObject(i).getString("email");
                employers.add(employer);
            }

            for(int i = 0 ; i < employers.size(); i++){

                if (employers.get(i).email.equals(email) && employers.get(i).password.equals(password))
                    return true;
            }

            return false;
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
            Login gui = new Login();
            gui.setVisible(true);
        });
    }

}


