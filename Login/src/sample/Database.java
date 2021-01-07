package sample;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private static final String Driver= "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String Url="jdbc:derby:database;create=true";
    Connection conn;
    public Database() throws SQLException
    {
        this.conn = DriverManager.getConnection(Url);
        if(conn==null)
            JOptionPane.showMessageDialog(null, "sorry , database not connected !!");
    }
    public void createTableAdmin()
    {
        String create="create table Admin(userName varchar(60),password varchar(60))";
        try {
            conn.createStatement().execute(create);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void createTableStudent()
    {
        String create="create table Student(userName varchar(60),password varchar(60),studentId varchar(60))";
        try {
            conn.createStatement().execute(create);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static boolean tableExist(Connection conn, String tableName) throws SQLException {
        boolean tExists = false;
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }
        }
        return tExists;
    }
    public void addData(String User, String password) throws SQLException
    {
       // String tableName1="Admin";
        //String tableName2="Student"


        //conn.createStatement().execute(sql1);
    }
    public boolean getDeta(String user, String pass) throws SQLException
    {
        String quary="Select *from test2 where userName='"+user+"'and password='"+pass+"'";
        Statement st= this.conn.createStatement();
        ResultSet rs= st.executeQuery(quary);
        int count=0;
        System.out.println("name : "+user+"  pass : "+pass);
        return rs.next();
    }
}
