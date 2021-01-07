package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static String screen1id="login";
    public static String screen1file="Homepage.fxml";
    public static String screen2id ="StudentSummery";
    public static String screen2file="StudentSummery.fxml";
    public static String screen3id="AdminSummery";
    public static String screen3file="AdminSummery.fxml";
    public static String screen4id="All_Course";
    public static String screen4file="All_Course.fxml";
    public static String screen5id="PresentCourse";
    public static String screen5file="PresentCourse.fxml";
    public static String screen6id="AcademyStudent";
    public static String screen6file="AcademyStudent.fxml";



    @Override
    public void start(Stage primaryStage) throws Exception{
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Main.screen1id, Main.screen1file);
        mainContainer.loadScreen(Main.screen2id, Main.screen2file);
        mainContainer.loadScreen(Main.screen3id, Main.screen3file);
        mainContainer.loadScreen(Main.screen4id, Main.screen4file);
        mainContainer.loadScreen(Main.screen5id, Main.screen5file);
        mainContainer.loadScreen(Main.screen6id, Main.screen6file);

        mainContainer.setScreen(Main.screen1id);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
