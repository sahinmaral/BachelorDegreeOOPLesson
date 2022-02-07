import javax.swing.*;
import javax.swing.table.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ShowOnList extends JFrame{
    private JTable tableList;
    private JPanel panelMain;
    private JButton buttonGetBack;
    public List<Information> informationList = new ArrayList<>();

    public ShowOnList(){

        this.add(panelMain);
        this.setSize(500,200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);



        buttonGetBack.addActionListener(e -> {
            this.setVisible(false);
            Homepage form = new Homepage();
            form.setVisible(true);
        });

        GetDatas();
        FillUpTable();

    }

    public void FillUpTable(){
        String[] columns = new String[] {
                "Id","Name","Surname"
        };

        Object[][] data = new Object[informationList.size()][3];

        for(var i = 0; i < informationList.size(); i++){
            data[i][0] = informationList.get(i).id;
            data[i][1] = informationList.get(i).name;
            data[i][2] = informationList.get(i).surname;
        }

        DefaultTableModel model = new DefaultTableModel(data,columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableList.setModel(model);
    }

    public void GetDatas(){
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/project_knowledge_db",
                            "postgres", "319528");

            String sqlQuery = "SELECT * FROM informations";

            pstmt = connection.prepareStatement(sqlQuery);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Information inf = new Information();
                inf.id = rs.getString("id");
                inf.name = rs.getString("name");
                inf.surname = rs.getString("surname");
                informationList.add(inf);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

    }
}
