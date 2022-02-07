import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RegisterCandidate extends JFrame{
    private JTextField textFieldEmail;
    private JButton buttonRegister;
    private JButton buttonGoBack;
    private JTextField textFieldFirstName;
    private JTextField textFieldLastName;
    private JTextField textFieldIdentityId;
    private JTextField textFieldBirthYear;
    private JPanel panelMain;
    private JLabel LabelEmailValidation;
    private JLabel LabelFirstNameValidation;
    private JLabel LabelLastNameValidation;
    private JLabel LabelIdentityIdValidation;
    private JLabel LabelBirthYearValidation;
    private JLabel LabelPasswordValidation;
    private JLabel textFieldTittle;
    private JTextArea TextAreaPasswordValidation;
    private JPasswordField passwordFieldPassword;

    public RegisterCandidate(){
        this.add(panelMain);
        this.setSize(500,350);
        this.setTitle("Candidate Register");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        File currentDirFile = new File("");

        String helper = "";

        try {
            helper = currentDirFile.getCanonicalPath() + "\\src\\main\\java\\images\\register.png";
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageIcon icon = new ImageIcon(helper);
        textFieldTittle.setIcon(icon);

        buttonGoBack.addActionListener(e -> {
            this.setVisible(false);
            new Login().setVisible(true);
        });

        buttonRegister.addActionListener(e -> {
            try {
                Register();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage());
            }
        });
        
        //TestMethod();

    }

    private void TestMethod() {
        textFieldEmail.setText("sahin.maral@hotmail.com");
        textFieldFirstName.setText("Şahin");
        textFieldLastName.setText("Maral");
        textFieldIdentityId.setText("31241146608");
        textFieldBirthYear.setText("2000");
    }


    private void Register() throws Exception{

        JSONObject obj = new JSONObject();
        obj.put("birthYear", textFieldBirthYear.getText());
        obj.put("firstName", textFieldFirstName.getText());
        obj.put("lastName", textFieldLastName.getText());
        obj.put("identityId", textFieldIdentityId.getText());
        obj.put("password", String.valueOf(passwordFieldPassword.getPassword()));
        obj.put("email", textFieldEmail.getText());
        obj.put("passwordRepeat",String.valueOf(passwordFieldPassword.getPassword()));

        URL url = new URL("http://localhost:8080/api/candidates/register");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");


        String data = obj.toString();

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        InputStream inputStream;

        if (http.getResponseCode() != 200)
            inputStream = http.getErrorStream();
        else
            inputStream = http.getInputStream();

        String response = "";
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        while ((line = br.readLine()) != null) {
            response += line;
        }

        JSONObject returnObject = new JSONObject(response);

        if (returnObject.get("success").equals(true)){
            JOptionPane.showMessageDialog(this,returnObject.get("message"));
            http.disconnect();
            this.setVisible(false);
            new Login().setVisible(true);
        }

        else if(!response.contains("data")){
            JOptionPane.showMessageDialog(this,returnObject.get("message"));
        }

        else {

            clearValidations();

            JSONObject validationErrors = returnObject.getJSONObject("data");
            Dimension dimension = this.getSize();

            String content = validationErrors.toString();
            if (content.contains("birthYear")){
                if (LabelBirthYearValidation.getText().length() == 0)
                dimension.height += 50;
                LabelBirthYearValidation.setText(validationErrors.get("birthYear").toString());
            }

            if (content.contains("password")) {

                if (TextAreaPasswordValidation.getText().equals(""))
                    dimension.height += 50;
                TextAreaPasswordValidation.setVisible(true);
                TextAreaPasswordValidation.setText(validationErrors.get("password").toString());
            }
            if (content.contains("lastName")){
                if (LabelLastNameValidation.getText().equals(""))
                    dimension.height += 50;
                LabelLastNameValidation.setText(validationErrors.get("lastName").toString());
            }

            if (content.contains("identityId")){
                if (LabelIdentityIdValidation.getText().equals(""))
                    dimension.height += 50;
                LabelIdentityIdValidation.setText(validationErrors.get("identityId").toString());
            }

            if (content.contains("firstName")){
                if (LabelFirstNameValidation.getText().equals(""))
                    dimension.height += 50;
                LabelFirstNameValidation.setText(validationErrors.get("firstName").toString());
            }

            if (content.contains("email")){
                if (LabelEmailValidation.getText().equals(""))
                    dimension.height += 50;
                LabelEmailValidation.setText(validationErrors.get("email").toString());
            }

            this.setSize(dimension);

        }



    }

    private void clearValidations() {
        TextAreaPasswordValidation.setText("");
        LabelEmailValidation.setText("");
        LabelBirthYearValidation.setText("");
        LabelFirstNameValidation.setText("");
        LabelLastNameValidation.setText("");
        LabelIdentityIdValidation.setText("");
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
            RegisterCandidate gui = new RegisterCandidate();
            gui.setVisible(true);
        });
    }
}
