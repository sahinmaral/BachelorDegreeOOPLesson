import com.toedter.calendar.JDateChooser;
import entities.*;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class CandidateSchoolsForSavedCvs extends JFrame {
    private JPanel panelMain;
    private JPanel panelTable;
    private JScrollPane scrollPaneTable;
    private JTable tableSchool;
    private JPanel panelButtons;
    private JButton buttonCreateSchool;
    private JButton buttonEditSchool;
    private JButton buttonDeleteTextFields;
    private JPanel panelSchoolIsnformations;
    private JTextField textFieldSchoolId;
    private JLabel labelCvId;
    private JTextField textFieldName;
    private JTextField textFieldDepartment;
    private JLabel labelCvLinkedinAdress;
    private JLabel labelCvGithubAdress;
    private JLabel labelCvCoveringLetter;
    private JPanel panelStartedDate;
    private JPanel panelGraduatedDate;
    private JButton buttonDeleteSchool;
    private JDateChooser chooserGraduatedDate;
    private JDateChooser chooserStartedDate;

    public User user;
    public String cvId;
    List<School> schools = new ArrayList<>();

    public CandidateSchoolsForSavedCvs(User user, String cvId) {
        this.user = user;
        this.cvId = cvId;

        this.add(panelMain);
        this.setSize(720, 600);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        this.setResizable(false);
        this.setTitle("Schools for saved cvs");

        chooserStartedDate = new JDateChooser();
        panelStartedDate.setLayout(new GridLayout(1,0));
        chooserStartedDate.setDateFormatString("yyyy-MM-dd");
        panelStartedDate.add(chooserStartedDate);

        chooserGraduatedDate = new JDateChooser();
        panelGraduatedDate.setLayout(new GridLayout(1,0));
        chooserGraduatedDate.setDateFormatString("yyyy-MM-dd");

        panelGraduatedDate.add(chooserGraduatedDate);

        tableSchool.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                deleteTextFields();

                DefaultTableModel model = (DefaultTableModel) tableSchool.getModel();

                int selectedRowIndex = tableSchool.getSelectedRow();
                textFieldSchoolId.setText(model.getValueAt(selectedRowIndex,0).toString());
                textFieldName.setText(model.getValueAt(selectedRowIndex,1).toString());
                textFieldDepartment.setText(model.getValueAt(selectedRowIndex,2).toString());

                Date startedDate= null;
                try {
                    startedDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) model.getValueAt(selectedRowIndex,3));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

                chooserStartedDate.setDate(startedDate);


                if (model.getValueAt(selectedRowIndex,4)!=null){
                    Date graduatedDate= null;
                    try {
                        graduatedDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) model.getValueAt(selectedRowIndex,4));
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    chooserGraduatedDate.setDate(graduatedDate);
                }


            }
        });

        if (!cvId.equals("")){
            try {
                getSchoolsFromApi();
                assignTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,e.getMessage());
            }
        }

        buttonCreateSchool.addActionListener(e -> {
            try {
                addSchool();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonDeleteSchool.addActionListener(e -> {
            try {
                deleteSchool();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonEditSchool.addActionListener(e -> {
            try {
                updateSchool();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonDeleteTextFields.addActionListener(e -> deleteTextFields());
    }

    private void addSchool() throws Exception{
        JSONObject obj = new JSONObject();
        obj.put("id", textFieldSchoolId.getText());
        obj.put("department", textFieldDepartment.getText());
        obj.put("name", textFieldName.getText());

        JSONObject cvJson = new JSONObject();
        cvJson.put("id",cvId);

        obj.put("cv",cvJson);

        JSONObject candidate = new JSONObject();
        candidate.put("id", user.id);
        cvJson.put("candidate",candidate);

        var startedDate = convertToLocalDateTimeViaInstant(chooserStartedDate.getDate());
        obj.put("startedDate",startedDate);

        if (chooserGraduatedDate.getDate()!=null){
            var graduatedDate = convertToLocalDateTimeViaInstant(chooserGraduatedDate.getDate());
            obj.put("graduatedDate",graduatedDate);
        }
        else{
            obj.put("graduatedDate",JSONObject.NULL);
        }



        URL url = new URL("http://localhost:8080/api/schools/addSchool");
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
            assignTable();
            deleteTextFields();
        }

        else{
            http.disconnect();
            JOptionPane.showMessageDialog(this,returnObject.get("message"));
        }

    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private void updateSchool() throws Exception {
        if (textFieldSchoolId.getText().equals(""))
            throw new NullPointerException("Herhangi bir okul işaretlemediniz");

        JSONObject obj = new JSONObject();
        obj.put("id", textFieldSchoolId.getText());
        obj.put("department", textFieldDepartment.getText());
        obj.put("name", textFieldName.getText());

        JSONObject cvJson = new JSONObject();
        cvJson.put("id",cvId);

        obj.put("cv",cvJson);

        JSONObject candidate = new JSONObject();
        candidate.put("id", user.id);
        cvJson.put("candidate",candidate);

        var startedDate = convertToLocalDateTimeViaInstant(chooserStartedDate.getDate());
        obj.put("startedDate",startedDate);

        if (chooserGraduatedDate.getDate()!=null){
            var graduatedDate = convertToLocalDateTimeViaInstant(chooserGraduatedDate.getDate());
            obj.put("graduatedDate",graduatedDate);
        }
        else{
            obj.put("graduatedDate",JSONObject.NULL);
        }



        URL url = new URL("http://localhost:8080/api/schools/updateSchool");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("PUT");
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
            assignTable();
        }

        else{
            http.disconnect();
            JOptionPane.showMessageDialog(this,returnObject.get("message"));
        }

    }

    private void assignTable(){

        String[] columns = new String[] {
                "Id","Name", "Department", "Started Date" , "Graduated Date" , "Is Graduated"
        };

        try {
            schools = getSchoolsFromApi();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,e.getMessage());
        }

        //actual data for the table in a 2d array
        Object[][] data = new Object[schools.size()][6];

        for(var i = 0; i < schools.size(); i++){
            data[i][0] = schools.get(i).id;
            data[i][1] = schools.get(i).name;
            data[i][2] = schools.get(i).department;
            data[i][3] = schools.get(i).startedDate;
            if (schools.get(i).graduatedDate!=null){
                data[i][4] = schools.get(i).graduatedDate;
                data[i][5] = "Mezun olmuş";
            }
            else{
                data[i][5] = "Mezun olmamış";
            }

        }

        DefaultTableModel model = new DefaultTableModel(data,columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableSchool.setModel(model);
    }

    private void deleteSchool() throws Exception {

        if (textFieldSchoolId.getText().equals(""))
            JOptionPane.showMessageDialog(this, "You didn't select any school");
        else {

            URL url = new URL("http://localhost:8080/api/schools/deleteSchool?schoolId=" + textFieldSchoolId.getText());
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("DELETE");
            httpCon.connect();

            InputStream inputStream;

            if (httpCon.getResponseCode() != 200)
                inputStream = httpCon.getErrorStream();
            else
                inputStream = httpCon.getInputStream();

            String response = "";
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = br.readLine()) != null) {
                response += line;
            }

            JSONObject returnObject = new JSONObject(response);

            if (returnObject.getBoolean("success")) {
                JOptionPane.showMessageDialog(this, returnObject.getString("message"), "Information", JOptionPane.INFORMATION_MESSAGE);
                assignTable();
                deleteTextFields();
            } else
                JOptionPane.showMessageDialog(this, returnObject.getString("message"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteTextFields(){
        textFieldSchoolId.setText("");
        textFieldName.setText("");
        textFieldDepartment.setText("");

        Date dateNow = null;
        String currentDate = new SimpleDateFormat("yyyy-mm-dd").format(Calendar.getInstance().getTime());
        try {
            dateNow=new SimpleDateFormat("yyyy-mm-dd").parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        chooserStartedDate.setDate(dateNow);
        chooserGraduatedDate.setDate(dateNow);

    }

    private List<School> getSchoolsFromApi() throws Exception{

        List<School> schools = new ArrayList<>();

        URL getSchoolsByCandidateId = new URL("http://localhost:8080/api/schools/getAllCvId?cvId="+Integer.parseInt(cvId));
        HttpURLConnection conn = (HttpURLConnection) getSchoolsByCandidateId.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {

            String inline = "";
            Scanner scanner = new Scanner(getSchoolsByCandidateId.openStream());

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
                School school = new School();
                school.id = arr.getJSONObject(i).getInt("id");
                school.department = arr.getJSONObject(i).getString("department");
                school.name = arr.getJSONObject(i).getString("name");
                school.startedDate = arr.getJSONObject(i).getString("startedDate");
                if (!arr.getJSONObject(i).isNull("graduatedDate"))
                    school.graduatedDate = arr.getJSONObject(i).getString("graduatedDate");
                JSONObject cvJson = arr.getJSONObject(i).getJSONObject("cv");

                Cv cv = new Cv();
                cv.id = cvJson.getInt("id");

                Candidate candidate = new Candidate();
                candidate.id = cvJson.getJSONObject("candidate").getInt("id");
                cv.candidate = candidate;

                schools.add(school);

            }

            return schools;

        }
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
            CandidateSchoolsForSavedCvs gui = new CandidateSchoolsForSavedCvs(user,cvId);
            gui.setVisible(true);
        });

    }


}
