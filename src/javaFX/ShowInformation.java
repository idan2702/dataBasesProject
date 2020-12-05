package javaFX;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

public class ShowInformation extends Application {
    private TableView<RestaurantInfo> table = new TableView<RestaurantInfo>();
    private ObservableList<RestaurantInfo> data =
            FXCollections.observableArrayList();
    public ShowInformation() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setdataArrayFromDb() {
        //todo : get from db
        data = FXCollections.observableArrayList(
                new RestaurantInfo("$$$", "1401 K", "Austria", "Salzburg", "Esszimmer", "French")
        );

    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Restaurants info");
        stage.setWidth(1080);
        stage.setHeight(500);
        setdataArrayFromDb();
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
        table.getColumns().addAll(PriceCol, ZipCodeCol, RegionCol, CityCol, NameCol, CuisineCol);

        addButtonToTable();
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    private void addButtonToTable() {
        TableColumn<RestaurantInfo, Void> colBtn = new TableColumn("Like/Unlike");

        Callback<TableColumn<RestaurantInfo, Void>, TableCell<RestaurantInfo, Void>> cellFactory = new Callback<TableColumn<RestaurantInfo, Void>, TableCell<RestaurantInfo, Void>>() {
            @Override
            public TableCell<RestaurantInfo, Void> call(final TableColumn<RestaurantInfo, Void> param) {
                final TableCell<RestaurantInfo, Void> cell = new TableCell<RestaurantInfo, Void>() {

                    private final Button btn = new Button("Like");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            RestaurantInfo rest = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Hey!");
                            alert.setHeaderText(null);
                            if (rest.get_is_clicked()) {
                                alert.setContentText("You liked \"" + rest.getName() + "\"");
                                rest.set_is_clicked();
                                btn.setText("Unlike");
                                btn.minWidth(100);
                                //todo:add to database
                            } else {
                                alert.setContentText("You unliked \"" + rest.getName() + "\"");
                                rest.set_is_clicked();
                                btn.setText("Like");
                                btn.minWidth(100);
                                //todo:remove fome database
                            }
                            alert.showAndWait();
                        });
                        btn.minWidth(100);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        table.getColumns().add(colBtn);

    }

    public static class RestaurantInfo {
        private boolean is_not_clicked = true;
        private final SimpleStringProperty price;
        private final SimpleStringProperty zipCode;
        private final SimpleStringProperty region;
        private final SimpleStringProperty city;
        private final SimpleStringProperty name;
        final SimpleStringProperty cuisine;

        public RestaurantInfo(String price, String zipCode, String region, String city, String name, String cuisine) {

            this.price = new SimpleStringProperty(price);
            this.zipCode = new SimpleStringProperty(zipCode);
            this.region = new SimpleStringProperty(region);
            this.city = new SimpleStringProperty(city);
            this.name = new SimpleStringProperty(name);
            this.cuisine = new SimpleStringProperty(cuisine);


        }

        public boolean get_is_clicked() {
            return this.is_not_clicked;
        }

        public void set_is_clicked() {
            if (is_not_clicked) {
                is_not_clicked = false;
            } else {
                is_not_clicked = true;
            }

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