import com.toedter.calendar.JDateChooser;
import entities.*;
import localData.CacheData;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CandidateJobHistoriesForUnsavedCvs extends JFrame {

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

    public User user;

    public CandidateJobHistoriesForUnsavedCvs(User user){

        this.user = user;

        this.add(panelMain);
        this.setSize(720, 600);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        this.setResizable(false);

        this.setTitle("Job histories for unsaved cvs");

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
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            throw new NullPointerException("You didn't select any job history");
        else {
            CacheData.savedJobHistories.removeIf(jobHistory -> jobHistory.id == Integer.parseInt(textFieldJobHistoryId.getText()));
            assignTable();
            deleteTextFields();
        }

    }

    private void assignTable(){

        String[] columns = new String[] {
                "Id","Company Name", "Position Name", "Started Date" , "Finished Date" , "Is Finished"
        };

        //actual data for the table in a 2d array
        Object[][] data = new Object[CacheData.savedJobHistories.size()][6];

        for(var i = 0; i < CacheData.savedJobHistories.size(); i++){
            data[i][0] = CacheData.savedJobHistories.get(i).id;
            data[i][1] = CacheData.savedJobHistories.get(i).companyName;
            data[i][2] = CacheData.savedJobHistories.get(i).jobPosition.name;
            data[i][3] = CacheData.savedJobHistories.get(i).startedDate;
            data[i][4] = CacheData.savedJobHistories.get(i).finishedDate;
            if (CacheData.savedJobHistories.get(i).isFinished)
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
            throw new NullPointerException("Herhangi bir iş geçmişi işaretlemediniz");

        JobHistory foundJobHistory = new JobHistory();

        for (var jobHistory : CacheData.savedJobHistories){
            if (jobHistory.id == Integer.parseInt(textFieldJobHistoryId.getText()))
                foundJobHistory = jobHistory;
        }


        foundJobHistory.companyName = textFieldCompanyName.getText();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String strDate = dateFormat.format(chooserStartedDate.getDate());
        foundJobHistory.startedDate = strDate;

        if (chooserFinishedDate.getDate() != null && foundJobHistory.finishedDate == null){
            String finishedDate = dateFormat.format(chooserFinishedDate.getDate());
            foundJobHistory.finishedDate = finishedDate;
        }


        if (foundJobHistory.finishedDate != null)
            foundJobHistory.isFinished = true;
        else
            foundJobHistory.isFinished = false;

        assignTable();
        deleteTextFields();

    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private void addJobHistory(){
        JobHistory jobHistory = new JobHistory();
        jobHistory.companyName = textFieldCompanyName.getText();

        if (CacheData.savedJobHistories.size() == 0)
            jobHistory.id = 1;
        else
            jobHistory.id = CacheData.savedJobHistories.get(CacheData.savedJobHistories.size()-1).id + 1;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String strDate = dateFormat.format(chooserStartedDate.getDate());
        jobHistory.startedDate = strDate;

        if (chooserFinishedDate.getDate() != null){
            String finishedDate = dateFormat.format(chooserFinishedDate.getDate());
            jobHistory.finishedDate = finishedDate;
        }

        if (chooserFinishedDate.getDate() != null)
            jobHistory.isFinished = true;
        else
            jobHistory.isFinished = false;

        for (var jobPosition : jobPositions){
            if (jobPosition.name.equals(comboBoxPositions.getSelectedItem().toString()))
                jobHistory.jobPosition = jobPosition;
        }

        CacheData.savedJobHistories.add(jobHistory);
        assignTable();
        deleteTextFields();
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
            CandidateJobHistoriesForUnsavedCvs gui = new CandidateJobHistoriesForUnsavedCvs(user);
            gui.setVisible(true);
        });

    }


}
