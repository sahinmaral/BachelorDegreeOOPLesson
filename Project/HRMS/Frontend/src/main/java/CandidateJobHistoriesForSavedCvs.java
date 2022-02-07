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
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CandidateJobHistoriesForSavedCvs extends JFrame {

    private JPanel panelMain;
    private JPanel panelTable;
    private JScrollPane scrollPaneTable;
    private JTable tableJobHistories;
    private JPanel panelButtons;
    private JButton buttonCreateJobHistories;
    private JButton buttonEditJobHistories;
    private JButton buttonDeleteSelectedJobHistories;
    private JButton buttonDeleteTextFields;
    private JPanel panelJobSchoolsInformations;
    private JTextField textFieldJobHistoryId;
    private JLabel labelCvId;
    private JTextField textFieldCompanyName;
    private JPanel panelStartedDate;
    private JPanel panelFinishedDate;
    private JComboBox comboBoxPositions;
    private JDateChooser chooserStartedDate;
    private JDateChooser chooserFinishedDate;

    private List<JobPosition> jobPositions = new ArrayList<>();
    private List<JobHistory> jobHistories = new ArrayList<>();

    public User user;
    public String cvId;

    public CandidateJobHistoriesForSavedCvs(User user,String cvId){

        this.user = user;
        this.cvId = cvId;

        this.add(panelMain);
        this.setSize(720, 600);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        this.setResizable(false);

        this.setTitle("Job histories for saved cvs");

        panelStartedDate.setLayout(new GridLayout(1,0));
        chooserStartedDate = new JDateChooser();
        chooserStartedDate.setDateFormatString("yyyy-MM-dd");
        chooserStartedDate.setName("chooserStartedDate");
        panelStartedDate.add(chooserStartedDate);

        panelFinishedDate.setLayout(new GridLayout(1,0));
        chooserFinishedDate = new JDateChooser();
        chooserFinishedDate.setDateFormatString("yyyy-MM-dd");
        chooserFinishedDate.setName("chooserFinishedDate");
        panelFinishedDate.add(chooserFinishedDate);

        buttonCreateJobHistories.addActionListener(e-> {
            try {
                addJobHistory();
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(this, exception.getMessage());
            }
        });

        buttonEditJobHistories.addActionListener(e-> {
            try {
                updateJobHistory();
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(this, exception.getMessage());
            }
        });

        buttonDeleteSelectedJobHistories.addActionListener(e-> {
            try {
                deleteJobHistory();
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(this, exception.getMessage());
            }
        });

        try {
            jobPositions = getJobPositionsFromApi();

            DefaultComboBoxModel model = new DefaultComboBoxModel();
            for (var jobPosition : jobPositions){
                model.addElement(new ComboKeyValue(jobPosition.name, String.valueOf(jobPosition.id)));
            }

            comboBoxPositions.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,e.getMessage());
        }

        tableJobHistories.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                deleteTextFields();

                DefaultTableModel model = (DefaultTableModel) tableJobHistories.getModel();

                int selectedRowIndex = tableJobHistories.getSelectedRow();
                textFieldJobHistoryId.setText(model.getValueAt(selectedRowIndex,0).toString());
                textFieldCompanyName.setText(model.getValueAt(selectedRowIndex,1).toString());


                for (var jobPosition : jobPositions){
                    if (jobPosition.name.equals(model.getValueAt(selectedRowIndex,2).toString())){
                        comboBoxPositions.setSelectedIndex(jobPosition.id-1);
                    }
                }

                Date startedDate= null;
                try {
                    startedDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) model.getValueAt(selectedRowIndex,3));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

                chooserStartedDate.setDate(startedDate);


                if (model.getValueAt(selectedRowIndex,4)!=null){
                    Date finishedDate= null;
                    try {
                        finishedDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) model.getValueAt(selectedRowIndex,4));
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    chooserFinishedDate.setDate(finishedDate);
                }


            }
        });

        buttonDeleteTextFields.addActionListener(e -> deleteTextFields());

        assignTable();
    }

    private void deleteJobHistory() throws Exception {
        if (textFieldJobHistoryId.getText().equals(""))
            JOptionPane.showMessageDialog(this, "You didn't select any job history");
        else {

            URL url = new URL("http://localhost:8080/api/jobHistories/deleteJobHistory?jobHistoryId=" + textFieldJobHistoryId.getText());
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

    private void assignTable(){

        String[] columns = new String[] {
                "Id","Company Name", "Position Name", "Started Date" , "Finished Date" , "Is Finished"
        };

        List<JobHistory> jobHistories = new ArrayList<>();

        try {
            jobHistories = getJobHistoriesFromApi();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,e.getMessage());
        }

        //actual data for the table in a 2d array
        Object[][] data = new Object[jobHistories.size()][6];

        for(var i = 0; i < jobHistories.size(); i++){
            data[i][0] = jobHistories.get(i).id;
            data[i][1] = jobHistories.get(i).companyName;
            data[i][2] = jobHistories.get(i).jobPosition.name;
            data[i][3] = jobHistories.get(i).startedDate;
            if (jobHistories.get(i).finishedDate!=null)
                data[i][4] = jobHistories.get(i).finishedDate;
            if (jobHistories.get(i).isFinished)
                data[i][5] = "İşten ayrılmış";
            else
                data[i][5] = "İşten ayrılmamış";
        }

        DefaultTableModel model = new DefaultTableModel(data,columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableJobHistories.setModel(model);
    }

    private void deleteTextFields() {
        textFieldCompanyName.setText("");
        textFieldJobHistoryId.setText("");


        ZoneId defaultZoneId = ZoneId.systemDefault();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Date.from(LocalDate.now().atStartOfDay(defaultZoneId).toInstant());
        formatter.format(date);

        chooserStartedDate.setDate(date);
        chooserFinishedDate.setDate(date);
    }

    private void updateJobHistory() throws Exception{
        if (textFieldJobHistoryId.getText().equals(""))
            throw new NullPointerException("Herhangi bir okul işaretlemediniz");

        JSONObject obj = new JSONObject();
        obj.put("id", textFieldJobHistoryId.getText());
        obj.put("companyName", textFieldCompanyName.getText());

        JSONObject cvJson = new JSONObject();
        cvJson.put("id",cvId);

        obj.put("cv",cvJson);

        for (var jobPosition : jobPositions){
            if (jobPosition.name == comboBoxPositions.getSelectedItem().toString()){
                JSONObject jobPositionJson = new JSONObject();
                jobPositionJson.put("id",jobPosition.id);
                obj.put("jobPosition",jobPositionJson);
            }
        }

        JSONObject candidate = new JSONObject();
        candidate.put("id", user.id);
        cvJson.put("candidate",candidate);

        var startedDate = convertToLocalDateTimeViaInstant(chooserStartedDate.getDate());
        obj.put("startedDate",startedDate);

        if (chooserFinishedDate.getDate()!=null){
            var finishedDate = convertToLocalDateTimeViaInstant(chooserFinishedDate.getDate());
            obj.put("finishedDate",finishedDate);
        }
        else{
            obj.put("finishedDate",JSONObject.NULL);
        }



        URL url = new URL("http://localhost:8080/api/jobHistories/updateJobHistory");
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

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private void addJobHistory() throws Exception{
        JSONObject obj = new JSONObject();
        obj.put("id", textFieldJobHistoryId.getText());
        obj.put("companyName", textFieldCompanyName.getText());

        JSONObject cvJson = new JSONObject();
        cvJson.put("id",cvId);

        obj.put("cv",cvJson);

        for (var jobPosition : jobPositions){
            if (jobPosition.name == comboBoxPositions.getSelectedItem().toString()){
                JSONObject jobPositionJson = new JSONObject();
                jobPositionJson.put("id",jobPosition.id);
                obj.put("jobPosition",jobPositionJson);
            }
        }

        JSONObject candidate = new JSONObject();
        candidate.put("id", user.id);
        cvJson.put("candidate",candidate);

        var startedDate = convertToLocalDateTimeViaInstant(chooserStartedDate.getDate());
        obj.put("startedDate",startedDate);

        if (chooserFinishedDate.getDate()!=null){
            var finishedDate = convertToLocalDateTimeViaInstant(chooserFinishedDate.getDate());
            obj.put("finishedDate",finishedDate);
        }

        obj.put("finishedDate",JSONObject.NULL);


        URL url = new URL("http://localhost:8080/api/jobHistories/addJobHistory");
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

    private List<JobHistory> getJobHistoriesFromApi() throws Exception{
        ArrayList<JobHistory> jobHistories = new ArrayList<>();

        URL getJobPositions = new URL("http://localhost:8080/api/jobHistories/getAllByCvId?cvId="+cvId);
        HttpURLConnection conn = (HttpURLConnection) getJobPositions.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {

            String inline = "";
            Scanner scanner = new Scanner(getJobPositions.openStream());

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
                JobHistory jobHistory = new JobHistory();
                jobHistory.companyName = arr.getJSONObject(i).getString("companyName");
                jobHistory.id = arr.getJSONObject(i).getInt("id");

                JobPosition jobPosition = new JobPosition();
                jobPosition.name = arr.getJSONObject(i).getJSONObject("jobPosition").getString("title");
                jobPosition.id = arr.getJSONObject(i).getJSONObject("jobPosition").getInt("id");
                jobHistory.jobPosition = jobPosition;

                jobHistory.startedDate = arr.getJSONObject(i).getString("startedDate");

                if (arr.getJSONObject(i).isNull("finishedDate"))
                    jobHistory.isFinished = false;

                else
                {
                    jobHistory.finishedDate = arr.getJSONObject(i).getString("finishedDate");
                    jobHistory.isFinished = true;
                }




                jobHistories.add(jobHistory);
            }

            return jobHistories;
        }
    }

    private List<JobPosition> getJobPositionsFromApi() throws Exception{
        ArrayList<JobPosition> jobPositions = new ArrayList<>();

        URL getJobPositions = new URL("http://localhost:8080/api/jobPositions/getAll");
        HttpURLConnection conn = (HttpURLConnection) getJobPositions.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {

            String inline = "";
            Scanner scanner = new Scanner(getJobPositions.openStream());

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
                JobPosition jobPosition = new JobPosition();
                jobPosition.name = arr.getJSONObject(i).getString("title");
                jobPosition.id = arr.getJSONObject(i).getInt("id");

                jobPositions.add(jobPosition);
            }

            return jobPositions;
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
            CandidateJobHistoriesForSavedCvs gui = new CandidateJobHistoriesForSavedCvs(user,cvId);
            gui.setVisible(true);
        });

    }


}
