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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class CandidateSchoolsForUnsavedCvs extends JFrame {
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
    public List<School> savedSchools = new ArrayList<>();

    public CandidateSchoolsForUnsavedCvs(User user) {
        this.user = user;

        this.add(panelMain);
        this.setSize(720, 600);
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        this.setResizable(false);
        this.setTitle("Schools for unsaved cvs");

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

        buttonCreateSchool.addActionListener(e -> {
                addSchool();
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

    private void addSchool(){
        School school = new School();

        if (savedSchools.size() == 0)
            school.id = 1;
        else
            school.id = savedSchools.get(savedSchools.size()-1).id + 1;

        school.name = textFieldName.getText();
        school.department = textFieldDepartment.getText();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String strDate = dateFormat.format(chooserStartedDate.getDate());
        school.startedDate = strDate;

        if (chooserGraduatedDate.getDate() != null){
            String graduatedDate = dateFormat.format(chooserGraduatedDate.getDate());
            school.graduatedDate = graduatedDate;
        }


        if (chooserGraduatedDate.getDate() != null)
            school.isGraduated = true;
        else
            school.isGraduated = false;

        savedSchools.add(school);

        assignTable();
        deleteTextFields();
    }


    private void updateSchool(){
        if (textFieldSchoolId.getText().equals(""))
            JOptionPane.showMessageDialog(this,"You didn't select any school","Error",JOptionPane.ERROR_MESSAGE);
        else{

            School foundSchool = new School();

            for (var school : savedSchools){
                if (school.id == Integer.parseInt(textFieldSchoolId.getText()))
                    foundSchool = school;
            }


            foundSchool.name = textFieldName.getText();
            foundSchool.department = textFieldDepartment.getText();

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String strDate = dateFormat.format(chooserStartedDate.getDate());
            foundSchool.startedDate = strDate;

            if (chooserGraduatedDate.getDate() != null && foundSchool.graduatedDate == null){
                String graduatedDate = dateFormat.format(chooserGraduatedDate.getDate());
                foundSchool.graduatedDate = graduatedDate;
            }


            if (foundSchool.graduatedDate != null)
                foundSchool.isGraduated = true;
            else
                foundSchool.isGraduated = false;

            assignTable();
            deleteTextFields();
        }
    }

    private void assignTable(){

        String[] columns = new String[] {
                "Id","Name", "Department", "Started Date" , "Graduated Date" , "Is Graduated"
        };

        Object[][] data = new Object[savedSchools.size()][6];

        for(var i = 0; i < savedSchools.size(); i++){
            data[i][0] = savedSchools.get(i).id;
            data[i][1] = savedSchools.get(i).name;
            data[i][2] = savedSchools.get(i).department;
            data[i][3] = savedSchools.get(i).startedDate;
            if (savedSchools.get(i).isGraduated){
                data[i][4] = savedSchools.get(i).graduatedDate;
                data[i][5] = "Mezun olmuş";
            }
            else
            data[i][5] = "Mezun olmamış";
        }

        DefaultTableModel model = new DefaultTableModel(data,columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableSchool.setModel(model);
    }

    private void deleteSchool() {
        if (textFieldSchoolId.getText().equals(""))
            JOptionPane.showMessageDialog(this,"You didn't select any school","Error",JOptionPane.ERROR_MESSAGE);
        else{
            savedSchools.removeIf(school -> school.id == Integer.parseInt(textFieldSchoolId.getText()));
            assignTable();
            deleteTextFields();
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
            CandidateSchoolsForUnsavedCvs gui = new CandidateSchoolsForUnsavedCvs(user);
            gui.setVisible(true);
        });

    }


}
