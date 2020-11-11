package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ShowInformation extends Application {

    private TableView<RestaurantInfo> table = new TableView<RestaurantInfo>();
    private final ObservableList<RestaurantInfo> data =
            FXCollections.observableArrayList(
                    new RestaurantInfo("$$$","1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$", "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$", "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$", "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$",  "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$",  "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$", "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$",  "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$",  "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$", "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$", "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$", "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$", "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$", "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$",  "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$",  "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$",  "1401 K", "Austria", "Salzburg", "Esszimmer",  "French"),
                    new RestaurantInfo("$$$", "1401 K", "Austria", "Salzburg", "Esszimmer",  "French")
            );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Restaurants info");
        stage.setWidth(935);
        stage.setHeight(500);

        final Label label = new Label("Restaurants Info");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn PriceCol = new TableColumn("Price");
        PriceCol.setMinWidth(100);
        PriceCol.setCellValueFactory(
                new PropertyValueFactory<RestaurantInfo, String>("price"));



        TableColumn ZipCodeCol = new TableColumn("ZipCode");
        ZipCodeCol.setMinWidth(100);
        ZipCodeCol.setCellValueFactory(
                new PropertyValueFactory<RestaurantInfo, String>("zipCode"));

        TableColumn RegionCol = new TableColumn("Region");
        RegionCol.setMinWidth(200);
        RegionCol.setCellValueFactory(
                new PropertyValueFactory<RestaurantInfo, String>("region"));


        TableColumn CityCol = new TableColumn("City");
        CityCol.setMinWidth(200);
        CityCol.setCellValueFactory(
                new PropertyValueFactory<RestaurantInfo, String>("city"));

        TableColumn NameCol = new TableColumn("Name");
        NameCol.setMinWidth(200);
        NameCol.setCellValueFactory(
                new PropertyValueFactory<RestaurantInfo, String>("name"));

        TableColumn CuisineCol = new TableColumn("Cuisine");
        CuisineCol.setMinWidth(100);
        CuisineCol.setCellValueFactory(
                new PropertyValueFactory<RestaurantInfo, String>("Cuisine"));

        table.setItems(data);
        table.getColumns().addAll(PriceCol, ZipCodeCol, RegionCol, CityCol, NameCol,CuisineCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public static class RestaurantInfo {

        private final SimpleStringProperty price;
        private final SimpleStringProperty zipCode;
        private final SimpleStringProperty region;
        private final SimpleStringProperty city;
        private final SimpleStringProperty name;
        final SimpleStringProperty cuisine;

        public RestaurantInfo(String price, String zipCode, String region, String city, String name , String cuisine) {

            this.price = new SimpleStringProperty(price);
            this.zipCode = new SimpleStringProperty(zipCode);
            this.region = new SimpleStringProperty(region);
            this.city = new SimpleStringProperty(city);
            this.name = new SimpleStringProperty(name);
            this.cuisine = new SimpleStringProperty(cuisine);


        }

        public String getPrice() {
            return price.get();
        }

        public void setprice(String price) {
            this.price.set(price);
        }



        public String getZipCode() {
            return zipCode.get();
        }

        public void setZipCode(String zipCode) {
            this.zipCode.set(zipCode);
        }

        public String getCuisine() {
            return cuisine.get();
        }

        public void setCuisine(String cuisine) {
            this.cuisine.set(cuisine);
        }


        public String getRegion() {
            return region.get();
        }

        public void setRegion(String region) {
            this.region.set(region);
        }

        public String getCity() {
            return city.get();
        }

        public void setCity(String city) {
            this.city.set(city);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }
    }
}