package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.sun.javafx.scene.control.skin.Utils.getResource;


public class Controller {
    @FXML
    public BorderPane MapPlacer;
    @FXML
    private TextField Countries;
    public int michelinStars = -1;
    private String rest = "";

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
    }

    @FXML
    public void setMap(ActionEvent event) {
        event.consume();
        System.out.println(Countries.getText());


        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        webEngine.load("file:///C:/Users/idan2/Desktop/leaflet.html");
        webEngine.setJavaScriptEnabled(true);
        MapPlacer.setCenter(browser);
    }

    @FXML
    public void setMapStartingMap() {
        ImageView img = new ImageView();
        Image image = new Image("file:///C:/Users/idan2/Desktop/MguideProf.png");
        img.setImage(image);
        MapPlacer.setCenter(img);

    }

    @FXML
    private void setData(ActionEvent event) {
        event.consume();
        System.out.println(Countries.getText());
    }

}
