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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Controller {
    private String id;
    private String pass;
    private File f;
    private DataObj db;
    ArrayList<DataObj> data = new ArrayList<DataObj>();
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

    public void setUserInfo(String id, String pass) {
        this.id = id;
        this.pass = pass;
    }

    @FXML
    public void setRestSelection(ActionEvent event) {
        event.consume();
        ObservableList<String> list = FXCollections.observableArrayList();
        this.data = SetRestSelection(this.City.getText(), this.Countries.getText(),
                this.michelinStars, this.price);
        int len = data.size();
        if(data.size() > 150){
            len = 150;
        }
        for (int i = 0; i < len; i++) {
            list.add(data.get(i).getName());
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
    }


    private ArrayList<DataObj> SetRestSelection(String city, String country, int michelinStars, int cost) {
        String countrySearch = "";
        String citySearch = "";
        String michelinStarsSearch = "";
        String costSearch = "";
        String sqlQuery = "SELECT * FROM restaurants_dbs.restaurants JOIN restaurants_dbs.locations USING(Name)";

        if (!country.equals("")) {
            countrySearch = "Country ='" + country + "'";
        }
        if (!city.equals("")) {
            if(!countrySearch.equals("")){
                countrySearch += " AND ";
            }
            citySearch = "City ='" + city + "'";
        }
        if (michelinStars >= 1) {
            if(!citySearch.equals("")){
                citySearch += " AND ";
            }else{
                if(!countrySearch.equals("")){
                    countrySearch += " AND ";
                }
            }
            switch (michelinStars) {
                case 1:
                    michelinStarsSearch = "Rate='1star'";
                    break;
                case 2:
                    michelinStarsSearch = "Rate='2stars'";
                    break;
                case 3:
                    michelinStarsSearch = "Rate='3stars'";
                    break;
                default:
                    michelinStarsSearch = "Rate='1star'";
                    break;
            }
        }
        if (cost > 0) {
            if(!michelinStarsSearch.equals("")){
                michelinStarsSearch += " AND ";
            }else{
                if(!citySearch.equals("")){
                    citySearch += " AND ";
                }else{
                    if(!countrySearch.equals("")){
                        countrySearch += " AND ";
                    }
                }
            }
            costSearch = "Cost =" + cost + " ";
        }
        if (!countrySearch.equals("") || !citySearch.equals("") || !michelinStarsSearch.equals("") || !costSearch.equals("")) {
            sqlQuery = sqlQuery + " WHERE " + countrySearch + citySearch + michelinStarsSearch + costSearch + ";";
        }
        try {
            DbConnection dbConnection = new DbConnection();
            ArrayList<DataObj> ans = dbConnection.AskDataBaseQuery(sqlQuery);
            dbConnection.disconnect();
            return ans;


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
        ChoiceBox<String> priceChoice = (ChoiceBox<String>) event.getSource();
        for (DataObj dataObj : this.data) {
            if (priceChoice.getValue() == dataObj.getName()) {
                this.db = dataObj;
            }
        }
    }


    @FXML
    private void setEveryDayRest(ActionEvent event) {
        event.consume();
        this.michelinStars = 0;
        System.out.println("every kind rest selected");

    }

    @FXML
    private void addRest(ActionEvent event) throws Exception {
        event.consume();
        DbConnection dbConnection = new DbConnection();
        ArrayList<LikedRestObj> likedRestObj = dbConnection.AskDataBaseLikedRestQuery(this.id);
        dbConnection.disconnect();
        System.out.println("add rest selected");
        Stage stage = new Stage();
        stage.initOwner(this.primaryStage);

        AddData addData = new AddData(likedRestObj);
        addData.start(stage);

    }

    @FXML
    private void getNearRest(ActionEvent event) throws Exception {
        event.consume();
        System.out.println("near rest selected");

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
            try {
                if (this.db.getUrl() != null) {
                    webEngine.load(this.db.getUrl());
                } else {
                    f = new File("src/javaFX/404.html");
                    webEngine.load(f.toURI().toString());
                }
                webEngine.setJavaScriptEnabled(true);
                MapPlacer.setCenter(browser);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error!");
                alert.setHeaderText(null);
                alert.setContentText("failed: error occurred...");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hey!");
            alert.setHeaderText(null);
            alert.setContentText("You didn't chose restaurant");
            alert.showAndWait();
        }

    }

    @FXML
    public void setMap(ActionEvent event) throws IOException {
        event.consume();
        if (this.rest_choosed) {
            System.out.println(Countries.getText());
            final WebView browser = new WebView();
            final WebEngine webEngine = browser.getEngine();
            HtmlHandler.editHtml(this.db.getLat(), this.db.getLon());
            f = new File("src/javaFX/leafletWithMarker.html");
            webEngine.load(f.toURI().toString());
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
    public void StartingMap(Stage _primaryStage) throws IOException {
        this.primaryStage = _primaryStage;
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        HtmlHandler.startMapHtml();
        f = new File("src/javaFX/leafletWithMarker.html");
        webEngine.load(f.toURI().toString());
        webEngine.setJavaScriptEnabled(true);
        MapPlacer.setCenter(browser);
    }

    @FXML
    private void setData(ActionEvent event) {
        event.consume();
        if (this.rest_choosed) {
            Stage stage = new Stage();
            stage.initOwner(this.primaryStage);
            ShowInformation addData = new ShowInformation(this.db, this.id, this.pass);
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
