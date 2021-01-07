package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Homepage implements Initializable , ControlledScreen {
    String un,pass;
    boolean ad=true,st=false;
    ScreensController myController;
    @FXML
    private TextField UserName = new TextField();
    @FXML
    private TextField Password = new TextField();
    @FXML
    private RadioButton Admin = new RadioButton();
    @FXML
    private RadioButton Student = new RadioButton();

    Database database;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Admin.setSelected(true);
       ad=true;
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
    @FXML
    private void Login(ActionEvent event ) throws SQLException {
        un=UserName.getText();
        pass=Password.getText();


    }
    @FXML
    private void Student(ActionEvent event){
        Student.setSelected(true);
        st=true;
        Admin.setSelected(false);
        ad=false;
    }
    @FXML
    private void Admin(ActionEvent event){
        Admin.setSelected(true);
        ad=true;
        Student.setSelected(false);
        st=false;
    }

}
