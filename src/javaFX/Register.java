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


public class Register  extends NewMember {
    private boolean isUserExist = false;
    private Stage primaryStage;
    @FXML
    private TextField reg_id;
    @FXML
    private TextField reg_pass;


    public void set_stage(Stage _primaryStage) {
        this.primaryStage = _primaryStage;
    }

    @FXML
    private void save_and_log(ActionEvent event) throws Exception {
        event.consume();
        if (save_user_in_database(reg_id.getText(), reg_pass.getText())) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = loader.load();
            Controller myController = loader.getController();
            primaryStage.setTitle("Restaurants Guide");
            primaryStage.setMaximized(true);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            myController.StartingMap(primaryStage);
            System.out.println("user added");
        } else {
            if(isUserExist == false) {
                System.out.println("failed: error occurred, please try again...");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error!");
                alert.setHeaderText(null);
                alert.setContentText("failed: error occurred, please try again...");
                alert.showAndWait();
            }
        }
    }

    private boolean save_user_in_database(String id, String pass){
        try {
            if(!this.checkIfUserExist(id,pass)){
                String query = "INSERT INTO `restaurants_dbs`.`users` (`Username`, `Password`) VALUES ('"
                        + id + "', '" + pass + "');";
                DbConnection dbConnection = new DbConnection();
                return dbConnection.AddtoDataBase(query);
            }else{
                System.out.println("failed: username or password are exists, please try again...");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error!");
                alert.setHeaderText(null);
                alert.setContentText("failed: username or password are exists, please try again...");
                alert.showAndWait();
                isUserExist = true;
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }
}
