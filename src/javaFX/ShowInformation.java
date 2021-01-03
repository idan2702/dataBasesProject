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
    private DataObj db;
    private String id;
    private String pass;
    private  DbConnection dbConnection = new DbConnection();
    private ObservableList<RestaurantInfo> data =
            FXCollections.observableArrayList();

    public ShowInformation(DataObj db,String id,String pass) {
        this.db = db;
        this.id = id;
        this.pass = pass;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setdataArrayFromDb() {
        String price;
        switch (db.getCost()) {
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
        data = FXCollections.observableArrayList(
                new RestaurantInfo(price, this.db.getCity(), this.db.getName(), this.db.getCouisine())
        );

    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Restaurants info");
        stage.setWidth(850);
        stage.setHeight(500);
        setdataArrayFromDb();
        final Label label = new Label("Restaurants Info");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn PriceCol = new TableColumn("Price");
        PriceCol.setMinWidth(200);
        PriceCol.setCellValueFactory(
                new PropertyValueFactory<RestaurantInfo, String>("price"));


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
        table.getColumns().addAll(PriceCol, CityCol, NameCol, CuisineCol);

        addButtonToTable();
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setOnCloseRequest( ev -> dbConnection.disconnect());
        stage.setScene(scene);
        stage.show();
    }
    private boolean checkIfRestaurantLikesBefore(DbConnection connection) throws Exception {
        String query = "SELECT * FROM restaurants_dbs.likedrest WHERE userName = '"
                + this.id + "' AND restName = '" + this.db.getName() + "';";
        ArrayList<String> data = connection.isExist(query);
        if(data.size()>0){
            return true;
        }
        return false;
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
                            try {
                                if (!checkIfRestaurantLikesBefore(dbConnection)) {
                                    alert.setContentText("You liked \"" + rest.getName() + "\"");
                                    btn.setText("Unlike");
                                    btn.minWidth(100);
                                    String query = "INSERT INTO `restaurants_dbs`.`likedrest` (`userName`, `restName`, `Like`, `Review`) VALUES ('"
                                            + id + "', '" + db.getName() + "', 'yes', 'empty');";

                                    try{
                                        if(dbConnection.AddtoDataBase(query)){

                                        }else{
                                            alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("error!");
                                            alert.setHeaderText(null);
                                            alert.setContentText("failed:error occurred, please try again...");
                                            alert.showAndWait();
                                        }
                                    }catch (Exception e){
                                        alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("error!");
                                        alert.setHeaderText(null);
                                        alert.setContentText("failed:error occurred, please try again...");
                                        alert.showAndWait();
                                    }
                                } else {
                                    alert.setContentText("You unliked \"" + rest.getName() + "\"");
                                    btn.setText("Like");
                                    btn.minWidth(100);
                                    String query = "DELETE FROM `restaurants_dbs`.`likedrest` WHERE (`userName` = '"
                                            + id + "') and (`restName` = '" + db.getName() + "');";
                                    try{
                                        if(dbConnection.deleteValInDataBase(query)){

                                        }else{
                                            alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("error!");
                                            alert.setHeaderText(null);
                                            alert.setContentText("failed:error occurred, please try again...");
                                            alert.showAndWait();
                                        }
                                    }catch (Exception e){
                                        alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("error!");
                                        alert.setHeaderText(null);
                                        alert.setContentText("failed:error occurred, please try again...");
                                        alert.showAndWait();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
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
        private final SimpleStringProperty price;
        private final SimpleStringProperty city;
        private final SimpleStringProperty name;
        final SimpleStringProperty cuisine;

        public RestaurantInfo(String price, String city, String name, String cuisine) {

            this.price = new SimpleStringProperty(price);
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