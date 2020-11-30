package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Register {
    private Stage primaryStage;
    @FXML
    private TextField reg_id;
    @FXML
    private TextField reg_pass;


    public void set_stage(Stage _primaryStage){
        this.primaryStage = _primaryStage;
    }
    @FXML
    private void save_and_log(ActionEvent event)throws Exception {
        event.consume();
        save_user_in_database(reg_id.getText(),reg_pass.getText());
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
    private void save_user_in_database(String id,String pass) {
        System.out.println("user added");
    }
}
