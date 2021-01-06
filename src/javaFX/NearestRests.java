package javaFX;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.util.ArrayList;


public class NearestRests extends Application {
    private DbConnection dbConnection = new DbConnection();
    private double lat = -200;
    private double lon = -200;
    private String city;
    private String country;
    private TableView<nearRestaurantInfo> table = new TableView<nearRestaurantInfo>();
    private ObservableList<nearRestaurantInfo> data =
            FXCollections.observableArrayList();
    final HBox hb = new HBox();

    public void setdataArrayFromDb() throws Exception {
        String countrySearch = "";
        String citySearch = "";
        String latSearch = "";
        String lonSearch = "";
        String price;
        String sqlQuery = "SELECT * FROM restaurants_dbs.restaurants JOIN restaurants_dbs.locations USING(Name)";
        if (lat > -200) {
            latSearch = "Lat BETWEEN " + (lat - 5) + " AND " + (lat + 5);
        }
        if (lon > -200) {
            if (!latSearch.equals("")) {
                latSearch += " AND ";
            }
            lonSearch = "Lon BETWEEN " + (lon - 5) + " AND " + (lon + 5) ;
        }
        if (!this.city.equals("")) {
            if (!lonSearch.equals("")) {
                lonSearch += " AND ";
            }else if(!latSearch.equals("")) {
                latSearch += " AND ";
            }
            citySearch = "City ='" + city + "'";
        }
        if (!this.country.equals("")) {
            if (!citySearch.equals("")) {
                citySearch += " AND ";
            }else if (!lonSearch.equals("")) {
                    lonSearch += " AND ";
            }else if(!latSearch.equals("")) {
                    latSearch += " AND ";

            }
            countrySearch = "Country ='" + country + "'";
        }
        if (!countrySearch.equals("") || !citySearch.equals("") || !lonSearch.equals("") || !latSearch.equals("")) {
            sqlQuery = sqlQuery + " WHERE " + latSearch + lonSearch + citySearch + countrySearch + ";";
        }
        ArrayList<DataObj> list = dbConnection.AskDataBaseQuery(sqlQuery);
        ObservableList<nearRestaurantInfo> temp = FXCollections.observableArrayList();
        int counter = 0;
        for (DataObj d : list) {
            switch (d.getCost()) {
                case 1:
                    price = "$ - Cheap";
                    break;
                case 2:
                    price = "$$ - Not Expensive";
                    break;
                case 3:
                    price = "$$$ - Expensive";
                    break;
                case 4:
                    price = "$$$$ - Very Expensive";
                    break;
                case 5:
                    price = "$$$$$- Extremely Expensive";
                    break;
                default:
                    price = "$ - Cheap";
                    break;
            }
            temp.add(new nearRestaurantInfo(price, d.getCountry(), d.getCity(), d.getName(), d.getCouisine()));
            if (counter == 150) {
                break;
            }
            counter++;
        }
        this.data = temp;
        table.setItems(data);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new Group());
        stage.setTitle("Near Restaurants");
        stage.setWidth(1000);
        stage.setHeight(550);
        final Label label = new Label("Near Restaurants");
        label.setFont(new Font("Arial", 20));


        TableColumn CountryCol = new TableColumn("Country");
        CountryCol.setMinWidth(200);
        CountryCol.setCellValueFactory(
                new PropertyValueFactory<nearRestaurantInfo, String>("Country"));


        TableColumn CityCol = new TableColumn("City");
        CityCol.setMinWidth(200);
        CityCol.setCellValueFactory(
                new PropertyValueFactory<nearRestaurantInfo, String>("city"));


        TableColumn NameCol = new TableColumn("Name");
        NameCol.setMinWidth(200);
        NameCol.setCellValueFactory(
                new PropertyValueFactory<nearRestaurantInfo, String>("name"));


        TableColumn CuisineCol = new TableColumn("Cuisine");
        CuisineCol.setMinWidth(215);
        CuisineCol.setCellValueFactory(
                new PropertyValueFactory<nearRestaurantInfo, String>("Cuisine"));


        TableColumn PriceCol = new TableColumn("Price");
        PriceCol.setMinWidth(150);
        PriceCol.setCellValueFactory(
                new PropertyValueFactory<nearRestaurantInfo, String>("Price"));


        table.setItems(data);
        table.getColumns().addAll(NameCol, PriceCol, CountryCol, CityCol, CuisineCol);

        final TextField addCountry = new TextField();
        addCountry.setPromptText("Country");
        addCountry.setMaxWidth(CountryCol.getPrefWidth());
        addCountry.setMinWidth(100);


        final TextField addCity = new TextField();
        addCity.setPromptText("City");
        addCity.setMaxWidth(CityCol.getPrefWidth());
        addCity.setMinWidth(100);

        final TextField addLatitude = new TextField();
        addLatitude.setPromptText("latitude");
        addLatitude.setMaxWidth(NameCol.getPrefWidth());
        addLatitude.setMinWidth(100);

        final TextField addLongitude = new TextField();
        addLongitude.setPromptText("longitude");
        addLongitude.setMaxWidth(CuisineCol.getPrefWidth());
        addLongitude.setMinWidth(100);

        final Button addButton = new Button("Search");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    lat = Double.parseDouble(addLatitude.getText());
                } catch (Exception e1) {
                    lat = -200;
                }
                try {
                    lon = Double.parseDouble(addLongitude.getText());
                } catch (Exception e1) {
                    lon = -200;
                }
                try {
                    city = addCity.getText();
                } catch (Exception e1) {
                    city = "";
                }
                try {
                    country = addCountry.getText();
                } catch (Exception e1) {
                    country = "";
                }

                try {
                    setdataArrayFromDb();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        hb.getChildren().addAll(addCountry, addCity, addLatitude, addLongitude, addButton);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);


        stage.setOnCloseRequest(ev -> dbConnection.disconnect());
        stage.setScene(scene);
        stage.show();
    }

    public static class nearRestaurantInfo {
        private final SimpleStringProperty price;
        private final SimpleStringProperty country;
        private final SimpleStringProperty city;
        private final SimpleStringProperty name;
        final SimpleStringProperty cuisine;

        public nearRestaurantInfo(String price, String country, String city, String name, String cuisine) {
            this.country = new SimpleStringProperty(country);
            this.price = new SimpleStringProperty(price);
            this.city = new SimpleStringProperty(city);
            this.name = new SimpleStringProperty(name);
            this.cuisine = new SimpleStringProperty(cuisine);


        }

        public String getCountry() {
            return country.get();
        }

        public void setCountry(String price) {
            this.country.set(price);
        }

        public String getPrice() {
            return price.get();
        }

        public void setprice(String price) {
            this.price.set(price);
        }


        public String getCuisine() {
            return cuisine.get();
        }

        public void setCuisine(String cuisine) {
            this.cuisine.set(cuisine);
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