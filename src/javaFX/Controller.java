package javaFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Controller {
    private DbConnection dbConnection = new DbConnection();
    private boolean rest_choosed = false;
    @FXML
    private ChoiceBox restSelection;
    @FXML
    public BorderPane MapPlacer;
    @FXML
    private TextField Countries;
    @FXML
    private TextField City;
    public int michelinStars = -1;
    private int price = 0;
    private Stage primaryStage;


    @FXML
    public void getExtraInformationbtn(ActionEvent event) {
        event.consume();
        Stage stage = new Stage();
        stage.initOwner(this.primaryStage);

        GetExtraInformation getExtraInformation = new GetExtraInformation();
        getExtraInformation.start(stage);
    }

    @FXML
    public void setRestSelection(ActionEvent event) {
        event.consume();
        ObservableList<String> list = FXCollections.observableArrayList();
        ArrayList<String> data = SetRestSelection(this.City.getText(), this.Countries.getText(),
                this.michelinStars, this.price);
        for(int i = 0; i < data.size();i++){
            list.add(data.get(i));
        }
        restSelection.setItems(list);
    }

    @FXML
    public void priceSelectionModified(ActionEvent event) {
        event.consume();
        ChoiceBox<String> priceChoice = (ChoiceBox<String>) event.getSource();
        switch (priceChoice.getValue()) {
            case "$ - Cheap":
                price = 1;
                break;
            case "$$ - Not Expensive":
                price = 2;
                break;
            case "$$$ - Expensive":
                price = 3;
                break;
            case "$$$$ - Very Expensive":
                price = 4;
                break;
            case "$$$$$- Extremely Expensive":
                price = 5;
                break;
            default:
                price = 1;
                break;
        }
        System.out.println(priceChoice.getValue());
    }

    private ArrayList<String> SetRestSelection(String city, String country, int michelinStars, int cost) {
        String countrySearch = "";
        String citySearch = "";
        String michelinStarsSearch = "";
        String costSearch = "";
        String sqlQuery = "SELECT * FROM restaurants_dbs.restaurants JOIN restaurants_dbs.locations USING(Name)";
        if (michelinStars >= 1) {
            switch (michelinStars) {
                case 1:
                    michelinStarsSearch = "Rate='1star' AND ";
                    break;
                case 2:
                    michelinStarsSearch = "Rate='2star' AND ";
                    break;
                case 3:
                    michelinStarsSearch = "Rate='3star' AND ";
                    break;
                default:
                    michelinStarsSearch = "Rate='1star' AND ";
                    break;
            }
        }
        if (cost > 0) {
            costSearch = "Cost =" + cost + " ";
        }
        if (country != "") {
            countrySearch = "Country ='" + country + "' AND ";
        }
        if (city != "") {
            countrySearch = "City ='" + city + "' AND ";
        }
        if (countrySearch != "" || citySearch != "" || michelinStarsSearch != "" || costSearch != "") {
            sqlQuery = sqlQuery + " WHERE " + countrySearch + citySearch + michelinStarsSearch + costSearch + ";";
        }
        try {
            return dbConnection.AskDataBaseQuery(sqlQuery);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error!");
            alert.setHeaderText(null);
            alert.setContentText("failed: error occurred, please try again...");
            alert.showAndWait();
        }
        return null;
    }

    @FXML
    public void restSelectionModified(ActionEvent event) {
        event.consume();
        rest_choosed = true;
        // todo: add query
    }


    @FXML
    private void setEveryDayRest(ActionEvent event) {
        event.consume();
        System.out.println("standart rest choosed");

    }

    @FXML
    private void addRest(ActionEvent event) {
        event.consume();
        System.out.println("add rest choosed");
        Stage stage = new Stage();
        stage.initOwner(this.primaryStage);

        AddData addData = new AddData();
        addData.start(stage);

    }

    @FXML
    private void getNearRest(ActionEvent event) {
        event.consume();
        System.out.println("near rest choosed");

        Stage stage = new Stage();
        stage.initOwner(this.primaryStage);

        NearestRests addData = new NearestRests();
        addData.start(stage);
    }

    @FXML
    private void setMichelintoOne(ActionEvent event) {
        event.consume();
        michelinStars = 1;
        System.out.println("michelin stars set to : " + Integer.toString(michelinStars));

    }

    @FXML
    private void setMichelintoTwo(ActionEvent event) {
        event.consume();
        michelinStars = 2;
        System.out.println("michelin stars set to : " + Integer.toString(michelinStars));

    }

    @FXML
    private void setMichelintoThree(ActionEvent event) {
        event.consume();
        michelinStars = 3;
        System.out.println("michelin stars set to : " + Integer.toString(michelinStars));

    }

    @FXML
    private void setLink(ActionEvent event) {
        event.consume();
        if (this.rest_choosed) {
            System.out.println(Countries.getText());
            final WebView browser = new WebView();
            final WebEngine webEngine = browser.getEngine();
            webEngine.load("https://www.oetkercollection.com/fr/hotels/le-bristol-paris/restaurants-et-bar/restaurants/epicure/");
            webEngine.setJavaScriptEnabled(true);
            MapPlacer.setCenter(browser);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hey!");
            alert.setHeaderText(null);
            alert.setContentText("You didn't chose restaurant");
            alert.showAndWait();
        }

    }

    @FXML
    public void setMap(ActionEvent event) {
        event.consume();
        if (this.rest_choosed) {
            System.out.println(Countries.getText());
            final WebView browser = new WebView();
            final WebEngine webEngine = browser.getEngine();
            webEngine.load(getClass().getResource("/javaFX/leaflet.html").toExternalForm());
            webEngine.setJavaScriptEnabled(true);
            MapPlacer.setCenter(browser);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hey!");
            alert.setHeaderText(null);
            alert.setContentText("You didn't chose restaurant");
            alert.showAndWait();
        }

    }

    @FXML
    public void StartingMap(Stage _primaryStage) {
        this.primaryStage = _primaryStage;
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        webEngine.load(getClass().getResource("/javaFX/leaflet.html").toExternalForm());
        webEngine.setJavaScriptEnabled(true);
        MapPlacer.setCenter(browser);


    }

    @FXML
    private void setData(ActionEvent event) {
        event.consume();
        if (this.rest_choosed) {
            Stage stage = new Stage();
            stage.initOwner(this.primaryStage);
            ShowInformation addData = new ShowInformation();
            addData.start(stage);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hey!");
            alert.setHeaderText(null);
            alert.setContentText("You didn't chose restaurant");
            alert.showAndWait();
        }
    }

}
