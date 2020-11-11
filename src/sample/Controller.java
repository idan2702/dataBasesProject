package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;



public class Controller {
    @FXML
    public BorderPane MapPlacer;
    @FXML
    private TextField Countries;
    public int michelinStars = -1;
    private Stage primaryStage;
    private String rest = "";

    @FXML
    public void priceSelectionModified(ActionEvent event) {
        event.consume();
        ChoiceBox<String> priceChoice = (ChoiceBox<String>) event.getSource();
        System.out.println(priceChoice.getValue());
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
    private void getNearRest(ActionEvent event)throws Exception {
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
        System.out.println(Countries.getText());
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        webEngine.load("https://www.oetkercollection.com/fr/hotels/le-bristol-paris/restaurants-et-bar/restaurants/epicure/");
        webEngine.setJavaScriptEnabled(true);
        MapPlacer.setCenter(browser);
    }

    @FXML
    public void setMap(ActionEvent event) {
        event.consume();
        System.out.println(Countries.getText());

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        webEngine.load(getClass().getResource("/sample/leaflet.html").toExternalForm());
        webEngine.setJavaScriptEnabled(true);
        MapPlacer.setCenter(browser);
    }

    @FXML
    public void StartingMap(Stage _primaryStage) {
        this.primaryStage = _primaryStage;
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        webEngine.load(getClass().getResource("/sample/leaflet.html").toExternalForm());
        webEngine.setJavaScriptEnabled(true);
        MapPlacer.setCenter(browser);


    }

    @FXML
    private void setData(ActionEvent event) {
        event.consume();
        Stage stage = new Stage();
        stage.initOwner(this.primaryStage);

        ShowInformation addData = new ShowInformation();
        addData.start(stage);
    }

}
