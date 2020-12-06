package javaFX;

import InterfacesAndAbstracts.NewMember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Login extends NewMember {
    private Stage primaryStage;
    @FXML
    private TextField log_id;
    @FXML
    private TextField log_pass;

    public void set_stage(Stage _primaryStage) {
        this.primaryStage = _primaryStage;
    }

    @FXML
    private void Login(ActionEvent event) throws Exception {
        event.consume();
        try{
            if(this.checkIfUserExist(log_id.getText(),log_pass.getText())){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                Parent root = loader.load();
                Controller myController = loader.getController();
                primaryStage.setTitle("Restaurants Guide");
                primaryStage.setMaximized(true);
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
                myController.setUserInfo(this.log_id.getText(), this.log_pass.getText());
                myController.StartingMap(primaryStage);
            }else{
                System.out.println("failed: error occurred, please try again...");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error!");
                alert.setHeaderText(null);
                alert.setContentText("failed: error occurred, please try again...");
                alert.showAndWait();
            }

        }catch (Exception e){
            System.out.println("failed: error occurred, please try again...");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error!");
            alert.setHeaderText(null);
            alert.setContentText("failed: error occurred, please try again...");
            alert.showAndWait();
        }

    }

    @FXML
    private void move_to_registration(ActionEvent event) throws Exception {
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
