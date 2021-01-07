package sample;
import javafx.scene.control.Alert;
import org.apache.derby.iapi.error.DerbySQLException;
import org.apache.derby.iapi.services.io.DerbyIOException;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    public String id;
    private static final String Driver= "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String Url="jdbc:derby:databas;create=true";
    Connection conn;
    public Database()
    {
        try {
            this.conn = DriverManager.getConnection(Url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*if(conn==null)
            JOptionPane.showMessageDialog(null, "sorry , database not connected !!");*/
    }
    public void createTableAdmin() throws SQLException {
        String create="create table Admin(userName varchar(60),password varchar(60))";

            conn.createStatement().execute(create);

    }
    public void createTableStudent() throws SQLException {
        String create="create table Student(userName varchar(60),password varchar(60),studentId varchar(60),DateOfBirth varchar(60),Gender varchar(60),year1 varchar(60),CN4101 varchar (60), CN4102 varchar(60), CN4104 varchar(60), CN4106 varchar(60),CN4107 varchar(60), CN5101 varchar(60), CN5122 varchar(60), CN5103 varchar(60), CN5120 varchar(60), CN5121 varchar(60), CN5104 varchar(60), CN5114 varchar(60), CN5222 varchar(60), CN5111 varchar(60), CN6103 varchar(60), CN6120 varchar(60), CN6107 varchar(60), CN6121 varchar(60), CN6204 varchar(60), CN6211 varchar(60))";

            conn.createStatement().execute(create);

    }
    public static boolean tableExist(Connection conn, String tableName) throws SQLException {

        ResultSet res = conn.getMetaData().getTables(null, "APP", tableName.toUpperCase(), null);//Default schema name is "APP"
                 if(res.next())
            {
             return  true;
          }
          else{
         return false;
           }

    }
    /*public void studentSubjectTable(String id )  {
        String trg= "stu"+id;
       /* conn.createStatement().executeUpdate("CREATE TABLE '"+id+"' " +
                "(moduleCode varchar(60), " +
                " moduleName varchar(60), " +
                " Grade VARCHAR(255), ");*/

        //String sql="create table '"+trg+"'(moduleCode TEXT, moduleName TEXT,Grade TEXT)";
        /*if(!tableExist(conn,id))
        {
            createTableAdmin();
        }
        try {
            //conn.createStatement().execute(sql);
            conn.createStatement().executeUpdate("CREATE TABLE "+trg+" " +
                    "  (moduleCode varchar(60), " +
                    " moduleName varchar(60), " +
                    " Grade VARCHAR(255), ");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }*/
    public void addDataAdmin(String user,String Pass) throws SQLException {
        String Sql="insert into Admin values('"+user+"','"+Pass+"') ";
        if(!tableExist(conn,"Admin"))
        {
            createTableAdmin();
        }
        conn.createStatement().execute(Sql);

    }
    public void addDataStudent(String User, String password, String id, String DateOfBarth, String gender,String Year ,String sub[]) throws SQLException
    {
        String tableName1="Admin";
        String tableName2="Student";

        System.out.println(DateOfBarth);
        String sql="insert into Student values('"+User+"','"+password+"','"+id+"','"+DateOfBarth+"','"+gender+"','"+Year+"','"+sub[0]+"','"+sub[1]+"','"+sub[2]+"','"+sub[3]+"','"+sub[4]+"','"+sub[5]+"','"+sub[6]+"','"+sub[7]+"','"+sub[8]+"','"+sub[9]+"','"+sub[10]+"','"+sub[11]+"','"+sub[12]+"','"+sub[13]+"','"+sub[14]+"','"+sub[15]+"','"+sub[16]+"','"+sub[17]+"','"+sub[18]+"','"+sub[19]+"')";
        // String value=sub.get( 0 ).get( 2 );
        //String exicute="insert into '"+id+"'values('""','""','""','""'";


           /*if(!tableExist( conn,tableName1 ))
           {
               createTableAdmin();
               return;
           }*/
       System.out.println("D: student test");
        if(!tableExist(conn,"Student"))
        {
            createTableStudent();
        }


        conn.createStatement().execute(sql);

       /* if(!checkid( id ))
        {
            System.out.println("D: student test 2");
           studentSubjectTable(id);
        }*

        int count=0;
        int size=sub.length;
        while(count<size)
        {
            String code="test"+sub[count][0];
            String mo=sub[count][1];
            String Grade= sub[count][2];
            String trg=id;
            System.out.println(code+" "+mo+" "+Grade);
            String exicute="insert into "+trg+"values('"+code+"','"+mo+"','"+Grade+"')";
            System.out.println("D : student :test 3");
            conn.createStatement().execute( exicute );
            count++;
        }
        */



    }

  /*  public boolean checkid(String id) throws SQLException {
        String quary="Select *from Student where studentId='"+id+"'";
        Statement st= this.conn.createStatement();
        ResultSet rs= st.executeQuery(quary);
        return rs.next();
    }
    */
    public boolean getDeta(String user, String pass,String tableName) throws SQLException
    {
        // String tableName1="Admin";
        //String tableName="Student";
        String quary="Select *from "+tableName+" where userName='"+user+"'and password='"+pass+"'";
       // System.out.println("test 3");
        //Statement st= conn.createStatement().executeQuery();
      //  System.out.println("Test 4");
        ResultSet rs= conn.createStatement().executeQuery(quary);
      //  System.out.println("Test 5");
        //int count=0;
        //System.out.println("name : "+rs.getString(0)+"  pass : "+rs.getString(1));
        //System.out.println(rs.next());
        boolean r= rs.next();
        if(tableName=="Student"){
        id=rs.getString("studentId");}

        return  r;
    }
    public  ResultSet getStudentData() throws SQLException {
        if(!tableExist(conn,"Student"))
        {
            createTableStudent();
        }
        String sql = "select studentId,userName,year1 from Student ";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        //System.out.println(rs.next());
       /* while(rs.next())
        {
            System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
        }*/
        return rs;
    }
    public ResultSet getSingleStudentData(String id) throws SQLException {
        String sql ="select *from student where studentId='"+id+"'";

        ResultSet rs=conn.createStatement().executeQuery(sql);
      return rs;

    }
    public ResultSet getgradeStudent(String id) throws SQLException {
        //String temp="stu"+id;
        String Sql="Select *from Student where studentId='"+id+"'";
        ResultSet rs= conn.createStatement().executeQuery(Sql);
        return rs;
    }
    public void Update(String User, String id, String DateOfBarth, String gender,String Year ,String sub[]) throws SQLException {
        //userName varchar(60),password varchar(60),studentId varchar(60),DateOfBirth varchar(60),Gender varchar(60),year1 varchar(60),CN4101 varchar (60), CN4102 varchar(60), CN4104 varchar(60), CN4106 varchar(60),CN4107 varchar(60), CN5101 varchar(60), CN5122 varchar(60), CN5103 varchar(60), CN5120 varchar(60), CN5121 varchar(60), CN5104 varchar(60), CN5114 varchar(60), CN5222 varchar(60), CN5111 varchar(60), CN6103 varchar(60), CN6120 varchar(60), CN6107 varchar(60), CN6121 varchar(60), CN6204 varchar(60), CN6211 varchar(60))";
        //
        //            conn.createStatement().execute(create);
        String Sql="Update Student Set userName='"+User+"',studentId='"+id+"',DateOfBirth='"+DateOfBarth+"',Gender='"+gender+"',year1='"+Year+"',CN4101='"+sub[0]+"',CN4102='"+sub[1]+"' , CN4104='"+sub[2]+"' , CN4106='"+sub[3]+"',CN4107='"+sub[4]+"', CN5101='"+sub[5]+"', CN5122='"+sub[6]+"', CN5103='"+sub[7]+"' ,CN5120='"+sub[8]+"', CN5121='"+sub[9]+"',CN5104='"+sub[10]+"',CN5114='"+sub[11]+"',CN5222='"+sub[12]+"' ,CN5111='"+sub[13]+"',CN6103='"+sub[14]+"' ,CN6120='"+sub[15]+"',CN6107='"+sub[16]+"' ,CN6121='"+sub[17]+"', CN6204='"+sub[18]+"',CN6211='"+sub[19]+"' where studentId='"+id+"'";
        conn.createStatement().execute(Sql);
    }
}
