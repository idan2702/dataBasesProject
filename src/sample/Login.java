package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login {
    private Stage primaryStage;
    @FXML
    private TextField id;
    @FXML
    private TextField pass;

    public void set_stage(Stage _primaryStage){
        this.primaryStage = _primaryStage;
    }
    @FXML
    private void Login(ActionEvent event) throws Exception{
        event.consume();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller myController = loader.getController();
        primaryStage.setTitle("Restaurants Guide");
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        myController.StartingMap(primaryStage);

    }
    @FXML
    private void move_to_registration(ActionEvent event) throws Exception{
        event.consume();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
        Parent root = loader.load();
        Register myController = loader.getController();
        primaryStage.setTitle("Register");
        primaryStage.setMaximized(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        myController.set_stage(primaryStage);
    }
}
