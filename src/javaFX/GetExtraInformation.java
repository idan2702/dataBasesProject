package javaFX;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.util.ArrayList;

public class GetExtraInformation extends Application {
    private DbConnection dbConnection = new DbConnection();
    private TableView<priceRestData> tableRests = new TableView<priceRestData>();
    private TableView<likedRestData> tableLikes = new TableView<likedRestData>();
    private ObservableList<priceRestData> dataRests = FXCollections.observableArrayList();
    private ObservableList<likedRestData> dataLikes = FXCollections.observableArrayList();
    final HBox hb = new HBox();
    private VBox vbox = new VBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Extra Information");
        stage.setWidth(735);
        stage.setHeight(550);

        final Label label = new Label("Near Restaurants");
        label.setFont(new Font("Arial", 20));

        tableRests.setEditable(true);
        tableLikes.setEditable(true);

        TableColumn PriceCol = new TableColumn("Cost");
        PriceCol.setMinWidth(125);
        PriceCol.setCellValueFactory(
                new PropertyValueFactory<priceRestData, String>("Cost"));


        TableColumn RateCol = new TableColumn("Rate");
        RateCol.setMinWidth(125);
        RateCol.setCellValueFactory(
                new PropertyValueFactory<priceRestData, String>("Rate"));


        TableColumn NameCol = new TableColumn("Name");
        NameCol.setMinWidth(225);
        NameCol.setCellValueFactory(
                new PropertyValueFactory<priceRestData, String>("Name"));


        TableColumn CuisineCol = new TableColumn("Cuisine");
        CuisineCol.setMinWidth(225);
        CuisineCol.setCellValueFactory(
                new PropertyValueFactory<priceRestData, String>("Cuisine"));


        tableRests.setItems(dataRests);
        tableRests.getColumns().addAll(NameCol, PriceCol, RateCol, CuisineCol);


        TableColumn LikeCol = new TableColumn("Likes");
        LikeCol.setMinWidth(350);
        LikeCol.setCellValueFactory(
                new PropertyValueFactory<likedRestData, Integer>("likes"));


        TableColumn NameColLikes = new TableColumn("Name");
        NameColLikes.setMinWidth(350);
        NameColLikes.setCellValueFactory(
                new PropertyValueFactory<likedRestData, String>("Name"));


        tableLikes.setItems(dataLikes);
        tableLikes.getColumns().addAll(NameColLikes, LikeCol);


        final Button cheapest_restaurants = new Button("Cheapest Restaurants");
        cheapest_restaurants.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    vbox.getChildren().add(tableRests);
                } catch (Exception E) {
                }
                try {
                    vbox.getChildren().remove(tableLikes);
                } catch (Exception E) {
                }
                try {

                } catch (Exception E) {
                }
                try {
                    String sqlQuery = "SELECT Cost,Rate,Name,Cuisine FROM restaurants_dbs.restaurants WHERE rate !=" +
                            " '1star' AND  Rate != '2stars' AND Rate != '3stars' order by cost asc limit 20;";
                    ArrayList<priceRestData> list = dbConnection.AskDataBaseQueryforPriceExtraInformation(sqlQuery);
                    ObservableList<priceRestData> temp = FXCollections.observableArrayList();
                    for (int i = 0; i < list.size(); i++) {
                        temp.add(list.get(i));
                    }
                    tableRests.getItems().clear();
                    tableRests.setItems(temp);
                } catch (Exception E) {
                }
            }
        });
        final Button most_expensive_restaurants = new Button("Most Expensive Restaurants");
        most_expensive_restaurants.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    vbox.getChildren().add(tableRests);
                } catch (Exception E) {
                }
                try {
                    vbox.getChildren().remove(tableLikes);
                } catch (Exception E) {
                }
                try {
                    String sqlQuery = "SELECT Cost,Rate,Name,Cuisine FROM restaurants_dbs.restaurants WHERE rate" +
                            " = '3stars' order by cost desc limit 20;";
                    ArrayList<priceRestData> list = dbConnection.AskDataBaseQueryforPriceExtraInformation(sqlQuery);
                    ObservableList<priceRestData> temp = FXCollections.observableArrayList();
                    for (int i = 0; i < list.size(); i++) {
                        temp.add(list.get(i));
                    }
                    tableRests.getItems().clear();
                    tableRests.setItems(temp);
                } catch (Exception E) {
                }

            }
        });
        final Button most_liked_restaurants = new Button("Most Liked Restaurants");
        most_liked_restaurants.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    vbox.getChildren().remove(tableRests);
                } catch (Exception E) {
                }
                try {
                    vbox.getChildren().add(tableLikes);
                    String sqlQuery = "SELECT * FROM (SELECT isLiked.restName, count(isLiked.Like)" +
                            " AS likes FROM (SELECT restaurants_dbs.likedrest.restName, restaurants_dbs.likedrest.Like" +
                            " FROM restaurants_dbs.likedrest WHERE restaurants_dbs.likedrest.Like='yes') AS isLiked" +
                            " group by isLiked.restName) AS ordered ORDER BY ordered.likes desc limit 150;";
                    ArrayList<likedRestData> list = dbConnection.AskDataBaseQueryforExtraInformation(sqlQuery);
                    ObservableList<likedRestData> temp = FXCollections.observableArrayList();
                    for (int i = 0; i < list.size(); i++) {
                        temp.add(list.get(i));
                    }
                    tableLikes.getItems().clear();
                    tableLikes.setItems(temp);
                } catch (Exception E) {
                }
            }
        });

        hb.getChildren().addAll(cheapest_restaurants, most_expensive_restaurants, most_liked_restaurants);
        hb.setSpacing(5);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }


    public static class priceRestData {
        private final SimpleIntegerProperty Cost;
        private final SimpleStringProperty Name;
        private final SimpleStringProperty Cuisine;
        private final SimpleStringProperty Rate;

        public priceRestData(Integer Cost, String Rate, String Name, String Cuisine) {

            this.Cost = new SimpleIntegerProperty(Cost);
            this.Rate = new SimpleStringProperty(Rate);
            this.Name = new SimpleStringProperty(Name);
            this.Cuisine = new SimpleStringProperty(Cuisine);


        }


        public Integer getCost() {
            return Cost.get();
        }

        public void setCost(Integer price) {
            this.Cost.set(price);
        }


        public String getCuisine() {
            return Cuisine.get();
        }

        public void setCuisine(String cuisine) {
            this.Cuisine.set(cuisine);
        }

        public String getRate() {
            return Rate.get();
        }

        public void setRate(String rate) {
            this.Rate.set(rate);
        }

        public String getName() {
            return Name.get();
        }

        public void setName(String name) {
            this.Name.set(name);
        }
    }

    public static class likedRestData {
        private final SimpleStringProperty restName;
        private final SimpleIntegerProperty likes;

        public likedRestData(String restName, Integer likes) {
            this.likes = new SimpleIntegerProperty(likes);
            this.restName = new SimpleStringProperty(restName);
        }


        public Integer getLikes() {
            return likes.get();
        }

        public void setLikes(Integer likes) {
            this.likes.set(likes);
        }

        public String getName() {
            return restName.get();
        }

        public void setName(String name) {
            this.restName.set(name);
        }
    }
}

