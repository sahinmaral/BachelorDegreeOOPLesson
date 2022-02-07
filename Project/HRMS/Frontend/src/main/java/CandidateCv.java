import entities.*;
import localData.CacheData;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CandidateCv extends JFrame{
    private JPanel panelMain;
    private JTable tableCv;
    private JButton buttonCreateCv;
    private JButton buttonEditCv;
    private JButton buttonDeleteCv;
    private JTextField textFieldCvId;
    private JTextField textFieldLinkedinAdress;
    private JTextField textFieldGithubAdress;
    private JTextField textFieldCvImageUrl;
    private JLabel labelCvId;
    private JLabel labelCvGithubAdress;
    private JLabel labelCvLinkedinAdress;
    private JLabel labelCvCoveringLetter;
    private JLabel labelCvImageURL;
    private JPanel panelButtons;
    private JPanel panelCvInformations;
    private JScrollPane scrollPaneTable;
    private JPanel panelTable;
    private JButton buttonBrowse;
    private JButton buttonDeselectFile;
    private JTextField textFieldCoveringLetter;
    private JPanel panelForeignLanguages;
    private JPanel panelProgrammingTechnologies;
    private JButton buttonDeleteTextFields;
    private JButton buttonSchoolForSavedCvs;
    private JButton buttonJobHistoryForSavedCvs;
    private JButton buttonSchoolForUnsavedCvs;
    private JButton buttonJobHistoryForUnsavedCvs;
    private JFileChooser fileChooser;

    public User user;

    private List<School> schools = new ArrayList<>();
    private List<Cv> cvs = new ArrayList<>();
    private List<String> foreignLanguagesString = new ArrayList<>();
    private List<ForeignLanguage> foreignLanguages = new ArrayList<>();
    private List<ForeignLanguage> selectedForeignLanguages = new ArrayList<>();

    private List<String> programmingTechnologiesString = new ArrayList<>();
    private List<ProgrammingTechnology> programmingTechnologies = new ArrayList<>();
    private List<ProgrammingTechnology> selectedProgrammingTechnologies = new ArrayList<>();


    public CandidateCv(User user){
        this.user = user;

        this.add(panelMain);
        this.setSize(1280,720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);

        fileChooser = new JFileChooser();
        fileChooser.setVisible(false);

        try {
            getForeignLanguagesFromApi();
            getProgrammingTechnologiesFromApi();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,e.getMessage());
        }

        assignTable();


        tableCv.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                try{
                    deleteTextFieldsAndAssignCheckbox();

                    DefaultTableModel model = (DefaultTableModel) tableCv.getModel();

                    int selectedRowIndex = tableCv.getSelectedRow();
                    textFieldCvId.setText(model.getValueAt(selectedRowIndex,0).toString());
                    textFieldGithubAdress.setText(model.getValueAt(selectedRowIndex,1).toString());
                    textFieldLinkedinAdress.setText(model.getValueAt(selectedRowIndex,2).toString());
                    textFieldCoveringLetter.setText(model.getValueAt(selectedRowIndex,3).toString());
                    //textFieldCvImageUrl.setText(model.getValueAt(selectedRowIndex,4).toString());

                    assignCheckboxStatusByCvId(Integer.parseInt(textFieldCvId.getText()));
                }
                catch (ArrayIndexOutOfBoundsException ex){

                }
            }
        });

        buttonSchoolForSavedCvs.addActionListener(e -> {
            getSchoolsForSavedCvsPage();
        });

        buttonJobHistoryForSavedCvs.addActionListener(e -> {
            refreshJobHistories();
        });

        buttonJobHistoryForUnsavedCvs.addActionListener(e -> getJobHistoriesForUnsavedCvsPage());

        buttonDeleteCv.addActionListener(e -> {
            try {
                deleteCv();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage());
            }
        });

        buttonCreateCv.addActionListener(e -> {
            try {
                setForeignLanguageToCv();
                setProgrammingTechnologiesToCv();


                getCheckboxStatusAndFillLists();
                int databaseAddedCvId = addCv();

                if (CacheData.savedSchools.size() != 0)
                    addSchoolsToCv(databaseAddedCvId);

                if (CacheData.savedJobHistories.size() != 0)
                    addJobHistoriesToCv(databaseAddedCvId);

                updateImageToCv(databaseAddedCvId);

                assignTable();
                deleteTextFieldsAndAssignCheckbox();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage());
            }
        });

        buttonBrowse.addActionListener(e -> {
            fileChooser.setVisible(true);
            int value = fileChooser.showSaveDialog(this);

            if (value == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                textFieldCvImageUrl.setText(selectedFile.getAbsolutePath());
            }
        });

        buttonDeselectFile.addActionListener(e -> {
            textFieldCvImageUrl.setText("");
        });

        buttonEditCv.addActionListener(e -> {
            try {
                getCheckboxStatusAndFillLists();

                int databaseAddedCvId = updateCv();
                updateAndDeleteImageToCv(databaseAddedCvId);
                deleteTextFieldsAndAssignCheckbox();
                assignTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage());
            }
        });

        buttonSchoolForUnsavedCvs.addActionListener(e -> getSchoolsForUnsavedCvsPage());

        buttonJobHistoryForSavedCvs.addActionListener(e -> getJobHistoriesForSavedCvsPage());

        buttonDeleteTextFields.addActionListener(e -> deleteTextFieldsAndAssignCheckbox());

    }

    private void addJobHistoriesToCv(int databaseAddedCvId) throws Exception{
        for (var jobHistory : CacheData.savedJobHistories) {
            JSONObject postJobHistory = new JSONObject();
            postJobHistory.put("companyName", jobHistory.companyName);

            postJobHistory.put("startedDate", jobHistory.startedDate);
            postJobHistory.put("finishedDate", jobHistory.finishedDate);
            postJobHistory.put("isFinished", jobHistory.isFinished);

            JSONObject cv = new JSONObject();
            cv.put("id",databaseAddedCvId);
            JSONObject addedCandidate = new JSONObject();
            addedCandidate.put("id",user.id);
            cv.put("candidate",addedCandidate);

            JSONObject position = new JSONObject();
            position.put("id",jobHistory.jobPosition.id);

            postJobHistory.put("jobPosition",position);
            postJobHistory.put("cv",cv);

            addJobHistoryToApi(postJobHistory);
        }
    }

    private void addJobHistoryToApi(JSONObject jobHistory) throws Exception {
        URL url = new URL("http://localhost:8080/api/jobHistories/addJobHistory");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");

        String data = jobHistory.toString();

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
            http.disconnect();
            deleteTextFieldsAndAssignCheckbox();
        }

        else{
            http.disconnect();
            JOptionPane.showMessageDialog(this,returnObject.getString("message"),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getJobHistoriesForUnsavedCvsPage() {
        CandidateJobHistoriesForUnsavedCvs form = new CandidateJobHistoriesForUnsavedCvs(user);
        form.setVisible(true);
    }

    private void getJobHistoriesForSavedCvsPage() {
        if (!textFieldCvId.getText().equals("")){
            CandidateJobHistoriesForSavedCvs form = new CandidateJobHistoriesForSavedCvs(user,textFieldCvId.getText());
            form.setVisible(true);
        }
        else
            JOptionPane.showMessageDialog(this,"You didn't select any cv","Error",JOptionPane.ERROR_MESSAGE);

    }

    private void addSchoolsToCv(int cvId) throws Exception {

        for (var school : CacheData.savedSchools) {
            JSONObject postSchool = new JSONObject();
            postSchool.put("name",school.name);
            postSchool.put("department",school.department);

            postSchool.put("startedDate",school.startedDate);
            postSchool.put("graduatedDate",school.graduatedDate);
            postSchool.put("graduated",school.isGraduated);

            JSONObject cv = new JSONObject();
            cv.put("id",cvId);
            JSONObject addedCandidate = new JSONObject();
            addedCandidate.put("id",user.id);
            cv.put("candidate",addedCandidate);

            postSchool.put("cv",cv);

            addSchoolsToApi(postSchool);
        }
    }

    private void addSchoolsToApi(JSONObject obj) throws Exception{
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
            http.disconnect();
            deleteTextFieldsAndAssignCheckbox();
        }

        else{
            http.disconnect();
            JOptionPane.showMessageDialog(this,returnObject.getString("message"),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getSchoolsForSavedCvsPage(){
        if (!textFieldCvId.getText().equals("")){
            CandidateSchoolsForSavedCvs form = new CandidateSchoolsForSavedCvs(user,textFieldCvId.getText());
            form.setVisible(true);
        }
        else
            JOptionPane.showMessageDialog(this,"You didn't select any cv","Error",JOptionPane.ERROR_MESSAGE);


    }

    private void getSchoolsForUnsavedCvsPage(){
        CandidateSchoolsForUnsavedCvs candidateSchoolsForUnsavedCvs = new CandidateSchoolsForUnsavedCvs(user);
        candidateSchoolsForUnsavedCvs.setVisible(true);
    }

    private void refreshJobHistories(){

        /*panelJobHistories.removeAll();

        panelJobHistories.setLayout(new GridLayout(LocalCache.getSavedJobHistories().size(),0));

        for (var jobHistory :  LocalCache.getSavedJobHistories()) {

            String label = jobHistory.companyName + " : " + jobHistory.jobPosition.name;

            JCheckBox checkbox = new JCheckBox(label, null, false);

            panelJobHistories.add(checkbox);
        }

        panelJobHistories.revalidate();
        panelJobHistories.repaint();*/
    }

    private void updateAndDeleteImageToCv(int cvId) throws Exception{


        if (!textFieldCvImageUrl.getText().startsWith("http") && !textFieldCvImageUrl.getText().equals("Boş") ){

            if (!textFieldCvImageUrl.getText().equals("")){
                deleteCvImageFromCv(cvId);
                uploadCvImage(textFieldCvImageUrl.getText(),cvId);
            }
        }
    }

    private void updateImageToCv(int cvId) throws Exception{

        if (!textFieldCvImageUrl.getText().startsWith("http") && !textFieldCvImageUrl.getText().equals("Boş") ){

            if (!textFieldCvImageUrl.getText().equals("")){
                uploadCvImage(textFieldCvImageUrl.getText(),cvId);
            }
        }
    }

    private int updateCv() throws Exception{
        if (textFieldCvId.getText().equals(""))
            throw new NullPointerException("Herhangi bir CV işaretlemediniz");

        int databaseAddedCvId = Integer.parseInt(textFieldCvId.getText());

        JSONObject obj = new JSONObject();
        obj.put("id", textFieldCvId.getText());
        obj.put("githubAdress", textFieldGithubAdress.getText());
        obj.put("coveringLetter", textFieldCoveringLetter.getText());
        obj.put("linkedinAdress", textFieldLinkedinAdress.getText());
        JSONObject candidate = new JSONObject();
        candidate.put("id", user.id);
        obj.put("candidate",candidate);

        JSONArray postForeignLanguages = new JSONArray();
        JSONArray postProgrammingTechnologies = new JSONArray();

        for (int i = 0 ; i < this.selectedForeignLanguages.size(); i++) {

            JSONObject postForeignLanguage = new JSONObject();
            postForeignLanguage.put("id",selectedForeignLanguages.get(i).id);
            postForeignLanguage.put("name",selectedForeignLanguages.get(i).name);

            JSONObject candidateForeignLanguages = new JSONObject();
            candidateForeignLanguages.put("foreignLanguage",postForeignLanguage);
            candidateForeignLanguages.put("level",2);

            postForeignLanguages.put(candidateForeignLanguages);
        }

        obj.put("candidateForeignLanguages",postForeignLanguages);


        for (int i = 0 ; i < this.selectedProgrammingTechnologies.size(); i++) {

            JSONObject postProgrammingTechnology = new JSONObject();
            postProgrammingTechnology.put("id",selectedProgrammingTechnologies.get(i).id);
            postProgrammingTechnology.put("name",selectedProgrammingTechnologies.get(i).name);

            JSONObject candidateProgrammingTechnologies = new JSONObject();
            candidateProgrammingTechnologies.put("programmingTechnology",postProgrammingTechnology);

            postProgrammingTechnologies.put(candidateProgrammingTechnologies);
        }

        obj.put("candidateKnowledges",postProgrammingTechnologies);

        URL url = new URL("http://localhost:8080/api/cvs/updateCv");
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
            programmingTechnologiesString.clear();
            foreignLanguagesString.clear();
            selectedForeignLanguages.clear();
            selectedProgrammingTechnologies.clear();
            return databaseAddedCvId;
        }

        else{
            http.disconnect();
            JOptionPane.showMessageDialog(this,returnObject.get("message"));
            return 0;
        }

    }

    private void deleteCvImageFromCv(int cvId) throws Exception{

        URL url = new URL("http://localhost:8080/api/cvs/deleteImageToCv?cvId=" + cvId);
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

        if (!returnObject.getBoolean("success")){
            JOptionPane.showMessageDialog(this,returnObject.getString("message"),"Error",JOptionPane.ERROR_MESSAGE);
        }

    }

    private void uploadCvImage(String path,int cvId) throws Exception{

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost("http://localhost:8080/api/cvs/uploadImageToCv");

            FileBody bin = new FileBody(new File(path));

            StringBody cvIdBody = new StringBody(String.valueOf(cvId), ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("multipartFile", bin)
                    .addPart("cvId", cvIdBody)
                    .build();


            httppost.setEntity(reqEntity);

            CloseableHttpResponse response = httpclient.execute(httppost);

            HttpEntity resEntity = response.getEntity();

            String responseString = EntityUtils.toString(resEntity, "UTF-8");

            JSONObject obj = new JSONObject(responseString);
            if (!obj.getBoolean("success")){
                JOptionPane.showMessageDialog(this,obj.getString("message"));
            }

            response.close();
            httpclient.close();

    }

    private void getCheckboxStatusAndFillLists(){

        programmingTechnologiesString.clear();
        foreignLanguagesString.clear();
        selectedForeignLanguages.clear();
        selectedProgrammingTechnologies.clear();

        var programmingTechnologiesComponents = panelProgrammingTechnologies.getComponents();

        for (Component component : programmingTechnologiesComponents){
            JCheckBox checkbox = (JCheckBox) component;

            if(checkbox.isSelected()){
                programmingTechnologiesString.add(checkbox.getText());
            }
        }


        var foreignLanguagesComponents = panelForeignLanguages.getComponents();

        for (Component component : foreignLanguagesComponents){
            JCheckBox checkbox = (JCheckBox) component;

            if(checkbox.isSelected()){
                foreignLanguagesString.add(checkbox.getText());
            }
        }


        for (String foreignLanguageString : foreignLanguagesString){
            for (ForeignLanguage foreignLanguage : foreignLanguages){
                if (foreignLanguage.name.equals(foreignLanguageString)){
                    selectedForeignLanguages.add(foreignLanguage);
                }
            }
        }

        for (String programmingTechnologyString : programmingTechnologiesString){
            for (ProgrammingTechnology programmingTechnology : programmingTechnologies){
                if (programmingTechnology.name.equals(programmingTechnologyString)){
                    selectedProgrammingTechnologies.add(programmingTechnology);
                }
            }
        }

    }

    private void assignCheckboxStatusByCvId(int cvId){
        var programmingTechnologiesComponents = panelProgrammingTechnologies.getComponents();
        var foundCv = new Cv();

        for (var cv : cvs){
            if (cv.id == cvId){
                foundCv = cv;
                break;
            }
        }

        for (Component component : programmingTechnologiesComponents){
            JCheckBox checkbox = (JCheckBox) component;

            for(var tech : foundCv.programmingTechnologies){
                if(tech.name.equals(checkbox.getText())){
                    checkbox.setSelected(true);
                }
            }
        }

        var foreignLanguagesComponents = panelForeignLanguages.getComponents();
        for (Component component : foreignLanguagesComponents){
            JCheckBox checkbox = (JCheckBox) component;

            for(var tech : foundCv.foreignLanguages){
                if(tech.name.equals(checkbox.getText())){
                    checkbox.setSelected(true);
                }
            }
        }
    }

    private void setProgrammingTechnologiesToCv() {

        for (int i = 0 ; i < programmingTechnologiesString.size(); i++){
            for (int j = 0 ; j < programmingTechnologies.size(); j++){
                if (programmingTechnologiesString.get(i) == programmingTechnologies.get(j).name){
                    selectedProgrammingTechnologies.add(programmingTechnologies.get(j));
                    break;
                }
            }
        }

    }

    private void setForeignLanguageToCv(){
        for (int i = 0 ; i < foreignLanguagesString.size(); i++){
            for (int j = 0 ; j < foreignLanguages.size(); j++){
                if (foreignLanguagesString.get(i) == foreignLanguages.get(j).name){
                    selectedForeignLanguages.add(foreignLanguages.get(j));
                    break;
                }
            }
        }
    }

    private void deleteCv() throws Exception{

        if (textFieldCvId.getText().equals(""))
            JOptionPane.showMessageDialog(this,"You didn't select any CV");
        else {

            URL url = new URL("http://localhost:8080/api/cvs/deleteCv?cvId=" + textFieldCvId.getText());
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
                deleteTextFieldsAndAssignCheckbox();
            } else
                JOptionPane.showMessageDialog(this, returnObject.getString("message"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getForeignLanguagesFromApi() throws Exception{

        URL getForeignLanguages = new URL("http://localhost:8080/api/foreignLanguages/getAll");
        HttpURLConnection conn = (HttpURLConnection) getForeignLanguages.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {

            String inline = "";
            Scanner scanner = new Scanner(getForeignLanguages.openStream());

            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            //Close the scanner
            scanner.close();

            String jsonString = inline;
            JSONObject obj = new JSONObject(jsonString);

            JSONArray arr = obj.getJSONArray("data");

            panelForeignLanguages.setLayout(new GridLayout(arr.length(),0));

            for (int i = 0; i < arr.length(); i++) {

                JSONObject foreignLanguage = arr.getJSONObject(i);

                ForeignLanguage f = new ForeignLanguage();
                f.id = foreignLanguage.getInt("id");
                f.name = foreignLanguage.getString("name");

                foreignLanguages.add(f);

                String label = foreignLanguage.getString("name");

                JCheckBox checkbox = new JCheckBox(label,null,false);

                checkbox.addActionListener(e -> {
                    if (checkbox.isSelected()){
                       foreignLanguagesString.add(checkbox.getText());
                    }else{
                        foreignLanguagesString.remove(checkbox.getText());
                    }
                });

                panelForeignLanguages.add(checkbox);
            }

        }

    }

    private void getProgrammingTechnologiesFromApi() throws Exception{

        URL getKnowledges = new URL("http://localhost:8080/api/programmingTechnologies/getAll");
        HttpURLConnection conn = (HttpURLConnection) getKnowledges.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {

            String inline = "";
            Scanner scanner = new Scanner(getKnowledges.openStream());

            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            //Close the scanner
            scanner.close();

            String jsonString = inline;
            JSONObject obj = new JSONObject(jsonString);

            JSONArray arr = obj.getJSONArray("data");

            panelProgrammingTechnologies.setLayout(new GridLayout(arr.length(), 1));

            for (int i = 0; i < arr.length(); i++) {

                JSONObject knowledge = arr.getJSONObject(i);

                ProgrammingTechnology f = new ProgrammingTechnology();
                f.id = knowledge.getInt("id");
                f.name = knowledge.getString("name");

                programmingTechnologies.add(f);

                String label = knowledge.getString("name");

                JCheckBox checkbox = new JCheckBox(label,null,false);

                checkbox.addActionListener(e -> {
                    if (checkbox.isSelected()){
                        programmingTechnologiesString.add(checkbox.getText());
                    }else{
                        programmingTechnologiesString.remove(checkbox.getText());
                    }
                });

                panelProgrammingTechnologies.add(checkbox);
            }



        }

    }

    private int addCv() throws Exception{

        JSONObject obj = new JSONObject();
        obj.put("githubAdress", textFieldGithubAdress.getText());
        obj.put("coveringLetter", textFieldCoveringLetter.getText());
        obj.put("linkedinAdress", textFieldLinkedinAdress.getText());
        JSONObject candidate = new JSONObject();
        candidate.put("id", user.id);
        obj.put("candidate",candidate);

        JSONArray postForeignLanguages = new JSONArray();
        JSONArray postProgrammingTechnologies = new JSONArray();

        for (int i = 0 ; i < this.selectedForeignLanguages.size(); i++) {

            JSONObject postForeignLanguage = new JSONObject();
            postForeignLanguage.put("id",selectedForeignLanguages.get(i).id);
            postForeignLanguage.put("name",selectedForeignLanguages.get(i).name);

            JSONObject candidateForeignLanguages = new JSONObject();
            candidateForeignLanguages.put("foreignLanguage",postForeignLanguage);
            candidateForeignLanguages.put("level",2);

            postForeignLanguages.put(candidateForeignLanguages);
        }

        obj.put("candidateForeignLanguages",postForeignLanguages);


        for (int i = 0 ; i < this.selectedProgrammingTechnologies.size(); i++) {

            JSONObject postProgrammingTechnology = new JSONObject();
            postProgrammingTechnology.put("id",selectedProgrammingTechnologies.get(i).id);
            postProgrammingTechnology.put("name",selectedProgrammingTechnologies.get(i).name);

            JSONObject candidateProgrammingTechnologies = new JSONObject();
            candidateProgrammingTechnologies.put("programmingTechnology",postProgrammingTechnology);

            postProgrammingTechnologies.put(candidateProgrammingTechnologies);
        }

        obj.put("candidateKnowledges",postProgrammingTechnologies);


        URL url = new URL("http://localhost:8080/api/cvs/addCv");
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
            return returnObject.getJSONObject("data").getInt("id");
        }

        else{
            http.disconnect();
            JOptionPane.showMessageDialog(this,returnObject.get("message"));
            return 0;
        }

    }



    public void deleteTextFieldsAndAssignCheckbox(){
        textFieldCvId.setText("");
        textFieldCoveringLetter.setText("");
        textFieldCvImageUrl.setText("");
        textFieldGithubAdress.setText("");
        textFieldLinkedinAdress.setText("");

        programmingTechnologiesString.clear();
        foreignLanguagesString.clear();
        selectedForeignLanguages.clear();
        selectedProgrammingTechnologies.clear();

        var programmingTechnologiesComponents = panelProgrammingTechnologies.getComponents();

        for (Component component : programmingTechnologiesComponents){
            JCheckBox checkbox = (JCheckBox) component;
            checkbox.setSelected(false);
        }

        var foreignLanguagesComponents = panelForeignLanguages.getComponents();

        for (Component component : foreignLanguagesComponents){
            JCheckBox checkbox = (JCheckBox) component;
            checkbox.setSelected(false);
        }
    }

    private void assignTable(){

        String[] columns = new String[] {
                "Id","Github Address", "LinkedIn Address", "Covering Letter"
        };

        try {
            cvs = getCvFromApi();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

        //actual data for the table in a 2d array
        Object[][] data = new Object[cvs.size()][5];

        for(var i = 0; i < cvs.size(); i++){
            data[i][0] = cvs.get(i).id;
            data[i][1] = cvs.get(i).githubAdress;
            data[i][2] = cvs.get(i).linkedinAdress;
            data[i][3] = cvs.get(i).coveringLetter;
            //data[i][4] = cvs.get(i).cvImage.url;
        }

        DefaultTableModel model = new DefaultTableModel(data,columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableCv.setModel(model);
    }

    private List<Cv> getCvFromApi() throws Exception{
        ArrayList<Cv> cvs = new ArrayList<>();

        URL getCvsByCandidateId = new URL("http://localhost:8080/api/cvs/getAllByCandidateId?candidateId="+user.id);
        HttpURLConnection conn = (HttpURLConnection) getCvsByCandidateId.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {

            String inline = "";
            Scanner scanner = new Scanner(getCvsByCandidateId.openStream());

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
                Cv cv = new Cv();
                cv.linkedinAdress = arr.getJSONObject(i).getString("linkedinAdress");
                cv.coveringLetter = arr.getJSONObject(i).getString("coveringLetter");
                cv.githubAdress = arr.getJSONObject(i).getString("githubAdress");

                if(arr.getJSONObject(i).has("image") && !arr.getJSONObject(i).isNull("image")){
                    cv.cvImage = new CvImage(
                            arr.getJSONObject(i).getJSONObject("image").getInt("id"),
                            arr.getJSONObject(i).getJSONObject("image").getString("url"),
                            arr.getJSONObject(i).getJSONObject("image").getString("publicImageId"),
                            arr.getJSONObject(i).getJSONObject("image").getString("createdDate"));
                }
                else{
                    cv.cvImage = new CvImage(0,"Boş","Boş","Boş");
                }


                var knowledges = arr.getJSONObject(i).getJSONArray("candidateKnowledges");
                var foreignLanguages = arr.getJSONObject(i).getJSONArray("candidateForeignLanguages");

                for (int x = 0; x < knowledges.length(); x++){
                    ProgrammingTechnology pt = new ProgrammingTechnology();
                    pt.name = knowledges.getJSONObject(x).getJSONObject("programmingTechnology").getString("name");
                    pt.id = knowledges.getJSONObject(x).getJSONObject("programmingTechnology").getInt("id");

                    cv.programmingTechnologies.add(pt);
                }

                for (int x = 0; x < foreignLanguages.length(); x++){
                    ForeignLanguage fl = new ForeignLanguage();
                    fl.name = foreignLanguages.getJSONObject(x).getJSONObject("foreignLanguage").getString("name");
                    fl.id = foreignLanguages.getJSONObject(x).getJSONObject("foreignLanguage").getInt("id");

                    cv.foreignLanguages.add(fl);
                }


                cv.id = arr.getJSONObject(i).getInt("id");

                cvs.add(cv);
            }

            return cvs;
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
            CandidateCv gui = new CandidateCv(user);
            gui.setVisible(true);
        });

    }

}
