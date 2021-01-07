package sample;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {



    Database db= new Database();
    //String id;
    String n[][]=new String[20][3];

    @Override

    public void start(Stage primaryStage) throws Exception{
        //db.createTableAdmin();
       db.addDataAdmin("Admin","1234");
       db.addDataAdmin("test","test");
       // db.createTableStudent();
        n= new String[][]{
                {"CN4101", "Information Systems Modelling and Design", "0"},
                {"CN4102", "Introduction to Software Development", "0"},
                {"CN4104", "Introduction to Computer Systems and Networks", "0"},
                {"CN4106", "Introduction to Web Technologies", "0"},
                {"CN4107", "Maths for Computing", "0"},
                {"CN5101", "Database Systems", "0"},
                {"CN5122", "Data Communications and Networks", "0"},
                {"CN5103", "Operating Systems", "0"},
                {"CN5120", "Advanced Programming", "0"},
                {"CN5121", "Data Structures and Algorithms", "0"},
                {"CN5104", "Computing in Practice", "0"},
                {"CN5114", "Placement Module", "0"},
                {"CN5222", "Data Communications and Networks", "0"},
                {"CN5111", "Database Systems", "0"},
                {"CN6103", "Project", "0"},
                {"CN6120", "Formal Methods", "0"},
                {"CN6107", "Computer & Network Security", "0"},
                {"CN6121", "Artificial Intelligence", "0"},
                {"CN6204", "Distributed Systems", "0"},
                {"CN6211", "Mobile Application Development", "0"}
        };

        loginUI(primaryStage);
    }

    //Showing the massege
    public void messagebox(String s)
    {
        Alert alt = new Alert(Alert.AlertType.INFORMATION);
        alt.setHeaderText("Massage: ");
        alt.setContentText(s);
        alt.showAndWait();
    }






    public void loginUI(Stage stage)
{
    //creating label User Name
    Text text1 = new Text("User Name");
    //creating label password
    Text text2 = new Text("Password");

    //Check Box for Admin
    CheckBox admin = new CheckBox("Admin");
    admin.setIndeterminate(false);


    //Creating Text Filed for email
    TextField textField1 = new TextField();
    //Creating Text Filed for password
    PasswordField textField2 = new PasswordField();
    //Creating Buttons
    Button button2 = new Button("Login");
    //Creating a Grid Pane
    GridPane Login_Pane = new GridPane();
    //Setting size for the pane
    Login_Pane.setMinSize(400, 200);
    //Setting the padding
    Login_Pane.setPadding(new Insets(10, 10, 10, 10));
    //Setting the vertical and horizontal gaps between the columns
    Login_Pane.setVgap(5);
    Login_Pane.setHgap(5);
    //Setting the Grid alignment
    Login_Pane.setAlignment(Pos.CENTER);
    //Arranging all the nodes in the grid
    Login_Pane.add(text1, 0, 0);
    Login_Pane.add(textField1, 1, 0);
    Login_Pane.add(text2, 0, 1);
    Login_Pane.add(textField2, 1, 1);
    // gridPane.add(button1, 0, 2);
    Login_Pane.add(button2, 1, 2);
    Login_Pane.add(admin, 0, 2);
    //Styling nodes
    button2.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
    text1.setStyle("-fx-font: normal bold 20px 'serif' ");
    text2.setStyle("-fx-font: normal bold 20px 'serif' ");
    Login_Pane.setStyle("-fx-background-color: WHITE;");
    Scene sc=new Scene(Login_Pane);
    stage.setScene(sc);
    stage.show();
    button2.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

            String name=textField1.getText();

            String tableName1="Student";
            String tableName2="Admin";


            boolean trg=admin.isSelected();
            String password= textField2.getText();
            if(trg)
            {

                System.out.println("test  ");
                try {
                    System.out.println("Test 2");
                    if(db.getDeta( name,password,tableName2))
                    {
                        AdminUI(stage);
                    }
                    else
                    {
                        messagebox("Admin : You are enter wrong username or password");
                    }
                } catch (SQLException e) {
                    String s=e.getMessage();
                  messagebox(s);
                }
            }
            else
            {
                try {
                    System.out.println(db.getDeta(name,password,tableName1));
                    if(db.getDeta(name,password,tableName1))
                    {
                        StudentUI(stage);
                    }
                    else
                    {
                        messagebox("Student : You are enter wrong username or password");
                    }
                } catch (SQLException e) {
                    String s=e.getMessage();
                    messagebox(s);
                }
            }
            }
    });

}
public  void StudentUI(Stage stage)
{
    Text Name_lebel= new Text("Name:");
    Text ID_lebel = new Text("Student ID:");
    Text Gender_lebel = new Text("Gender:");
    Text Year_lebel = new Text("Year:");
    Text DOB_Lebel = new Text("Date of Birth:");
    Text Grade_lebel = new Text("Grade:");
    //Creating TextField
    Text Name_lebel1= new Text();
    Text ID_lebel1 = new Text();
    Text Gender_lebel1 = new Text();
    Text Year_lebel1 = new Text();
    Text DOB_Lebel1 = new Text();
    Text Grade_lebel1 = new Text();
    //Create GridPane
    GridPane Student_Pane = new GridPane();
    //Create TableView
    TableView <StudentTableView> information_Table = new TableView<StudentTableView>();
    information_Table.setEditable( false );
    TableColumn Sub_code =new TableColumn( "Code" );
    TableColumn Sub_name =new TableColumn( "Subject" );
    TableColumn Sub_grade =new TableColumn( "Grade" );
    Sub_code.setCellValueFactory(new PropertyValueFactory<StudentTableView,String>("Code"));
    Sub_name.setCellValueFactory(new PropertyValueFactory<StudentTableView,String>("Mod"));
    Sub_grade.setCellValueFactory(new PropertyValueFactory<StudentTableView,String>("Grd"));
    Sub_code.setMinWidth(100);
    Sub_name.setMinWidth(300);
    Sub_grade.setMinWidth(50);
    information_Table.getColumns().addAll( Sub_code,Sub_name,Sub_grade );
    information_Table.setEditable(false);
    String id=db.id;
    try {
        ResultSet rs= db.getSingleStudentData(id);
        rs.next();
        System.out.println(rs.getString("username")+" "+rs.getString("studentid"));
         Name_lebel1.setText(rs.getString("userName"));
         ID_lebel1.setText(rs.getString("studentId"));
         Gender_lebel1.setText(rs.getString("Gender"));
         Year_lebel1.setText(rs.getString("year1"));
         DOB_Lebel1.setText(rs.getString("DateOfBirth"));
         int count=0;
         double mark=0;
         for(int i=7;i<27;i++)
         {
             Double temp=Double.parseDouble( rs.getString( i ) );
             if(temp>0)
             {
                 mark+=temp;
                 count++;
             }
         }
         System.out.println( mark+" "+count );
         if(count!=0)
         {
             mark/=count;
         }
         else
         {
             Gender_lebel1.setText( "Absent" );
         }

             if(mark>=70)
             {
                 Grade_lebel1.setText( "First Class" );

             }
             else if(mark>=60)
             {
                 Grade_lebel1.setText( "Upper Second Class" );

             }
             else if(mark>=50)
             {
                 Grade_lebel1.setText( "Lower Second Class" );

             }
             else if(mark>=40)
             {
                 Grade_lebel1.setText( "Third Class" );

             }
             else
             {
                 Grade_lebel1.setText( "Fail" );
             }





    } catch (SQLException e) {
        String s =e.getMessage();
        messagebox(s+"through");
    }
    ObservableList<StudentTableView>obj= FXCollections.observableArrayList();
    try {
        ResultSet res=db.getgradeStudent(id);
        int count=0;
        res.next();
        while (count<20)
        {
            StudentTableView temp= new StudentTableView();
            temp.code=n[count][0];
            temp.Mod=n[count][1];
            temp.Grd=res.getString(count+7);
            System.out.println(temp.Mod+" "+temp.Grd);

            obj.add(temp);
            count++;
        }
        information_Table.setItems(obj);

    } catch (SQLException e) {
        String s=e.getMessage();
        messagebox(s);
    }
    // Adding Lebel
    Student_Pane.add( Name_lebel,0,0 );
    Student_Pane.add( ID_lebel,0,1 );
    Student_Pane.add( Grade_lebel,0,2 );
    Student_Pane.add( Year_lebel,0,3);
    Student_Pane.add( DOB_Lebel,0,4 );
    Student_Pane.add( Gender_lebel,0,5 );
    //Adding Text
    Student_Pane.add( Name_lebel1,1,0 );
    Student_Pane.add( ID_lebel1,1,1 );
    Student_Pane.add( Grade_lebel1,1,2 );
    Student_Pane.add( Year_lebel1,1,3);
    Student_Pane.add( DOB_Lebel1,1,4 );
    Student_Pane.add( Gender_lebel1,1,5 );
    //Adding table
    Student_Pane.add( information_Table,1,8 );
    Student_Pane.setMinSize(400, 200);
    Student_Pane.setPadding(new Insets(20, 60, 20, 60));
    Scene sc= new Scene(Student_Pane);
    stage.setScene(sc);
    stage.show();

}
public void AdminUI(Stage stage)
{

    Button Update_button =new Button( "Update" );
    Button Reg =new Button( "Register" );

    TableView <AdminTablrView> Admin_table= new TableView<AdminTablrView>();
    Admin_table.setEditable(true);
    Admin_table.setRowFactory(tv -> {
        TableRow  row = new TableRow();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                //stage.setTitle( "Register" );
                //Scene sc= new Scene(Register_Pane);
                //stage.setScene(sc);

                //String id=
                //moduleUI(stage,id);
            }
        });
        return row ;
    });
    TableColumn Student_ID = new TableColumn( "ID" );
    TableColumn Student_Name =new TableColumn( "Name" );
    TableColumn Student_Year=new TableColumn( "Year" );
    Student_ID.setCellValueFactory(new PropertyValueFactory<AdminTablrView, String>("Id"));
    Student_Name.setCellValueFactory(new PropertyValueFactory<AdminTablrView, String>("Name"));
    Student_Year.setCellValueFactory(new PropertyValueFactory<AdminTablrView, String>("Year"));
    Student_ID.setMinWidth(100);
    Student_Name.setMinWidth(100);
    Student_Year.setMinWidth(100);


    GridPane Admin_Pane = new GridPane();

    Admin_table.getColumns().addAll( Student_ID,Student_Name,Student_Year );
    ObservableList<AdminTablrView> obj = FXCollections.observableArrayList();
    try {
        ResultSet rs=db.getStudentData();
        int count=1;

        while(rs.next())
        {
            AdminTablrView t= new AdminTablrView();
            t.Id=rs.getString(1);
            t.name=rs.getString(2);
            t.Year=rs.getString(3);
            //System.out.println(t.Id);
            obj.add(t);
           // System.out.println(t.Id);

        }
       Admin_table.setItems(obj);
    } catch (SQLException e) {
        e.getNextException();
        String s=e.getMessage();
        messagebox(s+"Test : D");
    }

 TextField Update_box= new TextField();
    Admin_Pane.add(Admin_table,0,0);
    Admin_Pane.add(Update_button ,0,3);
    Admin_Pane.add(Update_box,0,2);
    Admin_Pane.add(Reg,0,1);


    Admin_Pane.setMinSize(400, 200);
    Admin_Pane.setPadding(new Insets(20, 60, 20, 60));
Scene sc =new Scene(Admin_Pane);
 stage.setScene(sc);
 stage.show();
    Reg.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            RegisterUI(stage);
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    });
    Update_button.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          String id=  Update_box.getText();
          moduleUI(stage,id);
        }
    });

}
public void RegisterUI(Stage stage)
{
    Button back =new Button( "Back" );
    Text password =new Text( "Password" );
    TextField passwordField = new TextField(  );
    Text nameLabel = new Text("Name");
    //Text field for name
    TextField nameText = new TextField();
    //Label for date of birth
    Text dobLabel = new Text("Date of birth");
    //date picker to choose date
    DatePicker datePicker = new DatePicker();
    //Label for gender
    Text genderLabel = new Text("gender");
    //Toggle group of radio buttons
    ToggleGroup groupGender = new ToggleGroup();
    //RadioButton maleRadio = new RadioButton("male");
    /// maleRadio.setToggleGroup(groupGender);
    //RadioButton femaleRadio = new RadioButton("female");
    //femaleRadio.setToggleGroup(groupGender);
    ObservableList<String> choice= FXCollections.observableArrayList("Male","Female");
    ChoiceBox <String> gender= new ChoiceBox<>(choice);
    //Label for education
    Text educationLabel = new Text("Selected Module");
    //list View for educational qualification
    List<List<String>> modeule= new ArrayList<>();
    //ObservableList<String> names = FXCollections.observableArrayList( modeule.get(0).get(1),modeule.get(1).get(1),modeule.get(2).get(1), "", "");
    ListView<String> educationListView = new ListView<>();
    //Button for adding Module
    //Button Add_Module  = new Button("Add Module");
    //Label for register
    //add id
    Text Id = new Text("ID :");
    TextField id = new TextField();
    //Year choicebox
    Text Year=new Text("Year");
    ObservableList<String> input= FXCollections.observableArrayList("First","Second","Final");
    ChoiceBox <String> year= new ChoiceBox<>(input);
    //dept. choice
    Text Dept = new Text("Department");
    ObservableList<String> listDept =FXCollections.observableArrayList("CSE");
    ChoiceBox<String> dept= new ChoiceBox<>(listDept);
    Button buttonRegister = new Button("Register");
    //Creating a Grid Pane
    GridPane Register_Pane = new GridPane();
    Register_Pane.add( back,10,10 );
    //Setting size for the pane
    Register_Pane.setMinSize(500, 500);
    //Setting the padding
    Register_Pane.setPadding(new Insets(10, 10, 10, 10));
    //Setting the vertical and horizontal gaps between the columns
    Register_Pane.setVgap(5);
    Register_Pane.setHgap(5);
    //Setting the Grid alignment

    Register_Pane.setAlignment(Pos.CENTER);
    //Arranging all the nodes in the grid
    Register_Pane.add(nameLabel, 0, 0);
    Register_Pane.add(nameText, 1, 0);
    Register_Pane.add(dobLabel, 0, 1);
    Register_Pane.add(datePicker, 1, 1);
    Register_Pane.add(genderLabel, 0, 2);
    Register_Pane.add(Dept,0,3);
    Register_Pane.add(dept,1,3);
    // Register_Pane.add(maleRadio, 1, 2);
    // Register_Pane.add(femaleRadio, 2, 2);
    Register_Pane.add(gender,1,2);
    Register_Pane.add(Year,0,4);
    Register_Pane.add(year,1,4);
    Register_Pane.add(Id,0,5);
    Register_Pane.add(id,1,5);
    Register_Pane.add(password,0,7);
    Register_Pane.add(passwordField,1,7);
    //     Register_Pane.add(reservationLabel, 0, 3);
    //   Register_Pane.add(yes, 1, 3);
    //  Register_Pane.add(no, 2, 3);
    //   Register_Pane.add(technologiesLabel, 0, 4);
    //   Register_Pane.add(javaCheckBox, 1, 4);
    // Register_Pane.add(dotnetCheckBox, 2, 4);
    //Register_Pane.add(educationLabel, 0, 5);
    //Register_Pane.add(educationListView, 1, 5);
    //Register_Pane.add(Add_Module, 1, 6);
    buttonRegister.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
    Register_Pane.add(buttonRegister, 2, 8);
    //Styling nodes

    back.setOnAction( new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            AdminUI( stage );
        }
    } );

    nameLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
    dobLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
    genderLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
    educationLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
    //Add_Module.setStyle("-fx-font: normal bold 15px 'serif' ");
    //Declaring the module Grid
    Scene sc = new Scene(Register_Pane);
    stage.setScene(sc);
    stage.show();
    buttonRegister.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String name= nameText.getText();
            String pass= passwordField.getText();
            String date=datePicker.getValue().toString();

            String id1 =id.getText();
            String Gender=gender.getValue();
            String year1= year.getValue();
            String data= dept.getValue();



            String modeule[][]= new String[20][3];
            if(data=="CSE")
            {
                modeule= new String[][]{
                        {"CN4101", "Information Systems Modelling and Design", "0"},
                        {"CN4102", "Introduction to Software Development", "0"},
                        {"CN4104", "Introduction to Computer Systems and Networks", "0"},
                        {"CN4106", "Introduction to Web Technologies", "0"},
                        {"CN4107", "Maths for Computing", "0"},
                        {"CN5101", "Database Systems", "0"},
                        {"CN5122", "Data Communications and Networks", "0"},
                        {"CN5103", "Operating Systems", "0"},
                        {"CN5120", "Advanced Programming", "0"},
                        {"CN5121", "Data Structures and Algorithms", "0"},
                        {"CN5104", "Computing in Practice", "0"},
                        {"CN5114", "Placement Module", "0"},
                        {"CN5222", "Data Communications and Networks", "0"},
                        {"CN5111", "Database Systems", "0"},
                        {"CN6103", "Project", "0"},
                        {"CN6120", "Formal Methods", "0"},
                        {"CN6107", "Computer & Network Security", "0"},
                        {"CN6121", "Artificial Intelligence", "0"},
                        {"CN6204", "Distributed Systems", "0"},
                        {"CN6211", "Mobile Application Development", "0"}
                };
            }
            String mod[]=new String[20];
            mod= new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
            try {
                db.addDataStudent(name,pass,id1,date,Gender,year1,mod);
                messagebox("data inserted");
            } catch (SQLException e) {
                String s=e.getMessage();
                messagebox(s);
            }
        }
    });
}
public void moduleUI(Stage stage,String id)
{
    Text Name_lebel= new Text("Name:");
    Text ID_lebel = new Text("Student ID:");
    Text Gender_lebel = new Text("Gender:");
    Text Year_lebel = new Text("Year:");
    Text DOB_Lebel = new Text("Date of Birth:");
    Text Grade_lebel = new Text("Grade:");
    //Creating TextField
    Text Name_lebel1= new Text();
    Text ID_lebel1 = new Text();
    Text Gender_lebel1 = new Text();
    Text Year_lebel1 = new Text();
    Text DOB_Lebel1 = new Text();
    Text Grade_lebel1 = new Text();
    //Create GridPane
    GridPane Student_Pane = new GridPane();
    //Create TableView
    Text CN4101 = new Text( "CN4101" );
    Text CN4102 = new Text( "CN4102" );
    Text CN4104 = new Text( "CN4104" );
    Text CN4106 = new Text( "CN4106" );
    Text CN4107 = new Text( "CN4107" );
    Text CN5101 = new Text( "CN5101" );
    Text CN5122 = new Text( "CN5122" );
    Text CN5103 = new Text( "CN5103" );
    Text CN5120 = new Text( "CN5120" );
    Text CN5121 = new Text( "CN5121" );
    Text CN5104 = new Text( "CN5104" );
    Text CN5114 = new Text( "CN5114" );
    Text CN5222 = new Text( "CN5222" );
    Text CN5111 = new Text( "CN5111" );
    Text CN6103 = new Text( "CN6103" );
    Text CN6120 = new Text( "CN6120" );
    Text CN6107 = new Text( "CN6107" );
    Text CN6121 = new Text( "CN6121" );
    Text CN6204 = new Text( "CN6204" );
    Text CN6211 = new Text( "CN6211" );
    TextField CN4101t = new TextField( "0" );
    TextField CN4102t = new TextField( "0" );
    TextField CN4104t = new TextField( "0" );
    TextField CN4106t = new TextField( "0" );
    TextField CN4107t = new TextField( "0" );
    TextField CN5101t = new TextField( "0" );
    TextField CN5122t = new TextField( "0" );
    TextField CN5103t = new TextField( "0" );
    TextField CN5120t = new TextField( "0" );
    TextField CN5121t = new TextField( "0" );
    TextField CN5104t = new TextField( "0" );
    TextField CN5114t = new TextField( "0" );
    TextField CN5222t = new TextField( "0" );
    TextField CN5111t = new TextField( "0" );
    TextField CN6103t = new TextField( "0" );
    TextField CN6120t = new TextField( "0" );
    TextField CN6107t = new TextField( "0" );
    TextField CN6121t = new TextField( "0" );
    TextField CN6204t = new TextField( "0" );
    TextField CN6211t = new TextField( "0" );

    //String id=db.id;
    try {
        ResultSet rs= db.getSingleStudentData(id);
        rs.next();
        System.out.println(rs.getString("username")+" "+rs.getString("studentid"));
        Name_lebel1.setText(rs.getString("userName"));
        ID_lebel1.setText(rs.getString("studentId"));
        Gender_lebel1.setText(rs.getString("Gender"));
        Year_lebel1.setText(rs.getString("year1"));
        DOB_Lebel1.setText(rs.getString("DateOfBirth"));



    } catch (SQLException e) {
        String s =e.getMessage();
        messagebox(s+"through");
    }

    // Adding Lebel
    Student_Pane.add( Name_lebel,0,0 );
    Student_Pane.add( ID_lebel,0,1 );
    Student_Pane.add( Grade_lebel,0,2 );
    Student_Pane.add( Year_lebel,0,3);
    Student_Pane.add( DOB_Lebel,0,4 );
    Student_Pane.add( Gender_lebel,0,5 );
    //Adding Text
    Student_Pane.add( Name_lebel1,1,0 );
    Student_Pane.add( ID_lebel1,1,1 );
    Student_Pane.add( Grade_lebel1,1,2 );
    Student_Pane.add( Year_lebel1,1,3);
    Student_Pane.add( DOB_Lebel1,1,4 );
    Student_Pane.add( Gender_lebel1,1,5 );


    Student_Pane.add( CN4101,0,6 );
    Student_Pane.add( CN4102,0,7 );
    Student_Pane.add( CN4104,0,8 );
    Student_Pane.add( CN4106,0,9 );
    Student_Pane.add( CN4107,0,10 );
    Student_Pane.add( CN5101,0,11 );
    Student_Pane.add( CN5122,0,12 );
    Student_Pane.add( CN5103,0,13 );
    Student_Pane.add( CN5120,0,14 );
    Student_Pane.add( CN5121,0,15 );
    Student_Pane.add( CN5104,0,16 );
    Student_Pane.add( CN5114,0,17 );
    Student_Pane.add( CN5222,0,18 );
    Student_Pane.add( CN5111,0,19 );
    Student_Pane.add( CN6103,0,20 );
    Student_Pane.add( CN6120,0,21 );
    Student_Pane.add( CN6107,0,22 );
    Student_Pane.add( CN6121,0,23 );
    Student_Pane.add( CN6204,0,24 );
    Student_Pane.add( CN6211,0,25 );

    Student_Pane.add( CN4101t,1,6 );
    Student_Pane.add( CN4102t,1,7 );
    Student_Pane.add( CN4104t,01,8 );
    Student_Pane.add( CN4106t,01,9 );
    Student_Pane.add( CN4107t,01,10 );
    Student_Pane.add( CN5101t,01,11 );
    Student_Pane.add( CN5122t,01,12 );
    Student_Pane.add( CN5103t,01,13 );
    Student_Pane.add( CN5120t,01,14 );
    Student_Pane.add( CN5121t,01,15 );
    Student_Pane.add( CN5104t,01,16 );
    Student_Pane.add( CN5114t,01,17 );
    Student_Pane.add( CN5222t,01,18 );
    Student_Pane.add( CN5111t,01,19 );
    Student_Pane.add( CN6103t,01,20 );
    Student_Pane.add( CN6120t,01,21 );
    Student_Pane.add( CN6107t,01,22 );
    Student_Pane.add( CN6121t,01,23 );
    Student_Pane.add( CN6204t,01,24 );
    Student_Pane.add( CN6211t,01,25 );
    //Adding table

    Button back =new Button( "Back" );
    Student_Pane.add( back,10,26 );
    back.setOnAction( new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            AdminUI( stage );
        }
    } );

    Button Sub_Update = new Button("Submit");

    Student_Pane.add(Sub_Update,02,26);
    String update[]=new String[20];


    Sub_Update.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            update[0]=CN4101t.getText();
            update[1]=CN4102t.getText();
            update[2]=CN4104t.getText();
            update[3]=CN4106t.getText();
            update[4]=CN4107t.getText();
            update[5]=CN5101t.getText();
            update[6]=CN5122t.getText();
            update[7]=CN5103t.getText();
            update[8]=CN5120t.getText();
            update[9]=CN5121t.getText();
            update[10]=CN5104t.getText();
            update[11]=CN5114t.getText();
            update[12]=CN5222t.getText();
            update[13]=CN5111t.getText();
            update[14]=CN6103t.getText();
            update[15]=CN6120t.getText();
            update[16]=CN6107t.getText();
            update[17]=CN6121t.getText();
            update[18]=CN6204t.getText();
            update[19]=CN6211t.getText();
            String Name_Up = Name_lebel1.getText();
            String ID_UP = ID_lebel1.getText();
            String Grade_UP = Gender_lebel1.getText();
            String Year_UP = Year_lebel1.getText();
            String DOB_UP = DOB_Lebel1.getText();
            String Gender_UP = Gender_lebel1.getText();
            try {
                db.Update(Name_Up,ID_UP,DOB_UP,Gender_UP,Year_UP,update);
            } catch (SQLException e) {
                String s=e.getMessage();
                messagebox(s);
            }


        }
    });

    Student_Pane.setMinSize(400, 200);
    Student_Pane.setPadding(new Insets(20, 60, 20, 60));
    Scene sc= new Scene(Student_Pane);
    stage.setScene(sc);
    stage.show();
}

    public static void main(String[] args) {
        launch(args);
    }
}
