package javaFX;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private ArrayList<ShowInformation.RestaurantInfo> arraylisRestaurantInfo = new ArrayList<ShowInformation.RestaurantInfo>();
    private TableView<ShowInformation.RestaurantInfo> table = new TableView<ShowInformation.RestaurantInfo>();
    private final ObservableList<ShowInformation.RestaurantInfo> data =
            FXCollections.observableArrayList();
    final HBox hb = new HBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Near Restaurants");
        stage.setWidth(735);
        stage.setHeight(550);

        final Label label = new Label("Near Restaurants");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);


        TableColumn RegionCol = new TableColumn("Region");
        RegionCol.setMinWidth(200);
        RegionCol.setCellValueFactory(
                new PropertyValueFactory<ShowInformation.RestaurantInfo, String>("region"));


        TableColumn CityCol = new TableColumn("City");
        CityCol.setMinWidth(200);
        CityCol.setCellValueFactory(
                new PropertyValueFactory<ShowInformation.RestaurantInfo, String>("city"));



        TableColumn NameCol = new TableColumn("Name");
        NameCol.setMinWidth(200);
        NameCol.setCellValueFactory(
                new PropertyValueFactory<ShowInformation.RestaurantInfo, String>("name"));


        TableColumn CuisineCol = new TableColumn("Cuisine");
        CuisineCol.setMinWidth(100);
        CuisineCol.setCellValueFactory(
                new PropertyValueFactory<ShowInformation.RestaurantInfo, String>("Cuisine"));


        table.setItems(data);
        table.getColumns().addAll(RegionCol, CityCol, NameCol, CuisineCol);


        final Button cheapest_restaurants = new Button("Cheapest Restaurants");
        cheapest_restaurants.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //todo : get text from this fields serch in database and add...
                // data.add(restaurantInfo);
                final ObservableList<ShowInformation.RestaurantInfo> data =
                        FXCollections.observableArrayList(
                               // new ShowInformation.RestaurantInfo("$", "4935067", "Israel", "Tel-Aviv", "test2", "Israel")
                        );
                table.getItems().clear();
                table.setItems(data);

            }
        });
        final Button most_expensive_restaurants = new Button("Most Expensive Restaurants");
        most_expensive_restaurants.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //todo : get text from this fields serch in database and add...
                // data.add(restaurantInfo);
                final ObservableList<ShowInformation.RestaurantInfo> data =
                        FXCollections.observableArrayList(
                                //new ShowInformation.RestaurantInfo("$$$$$", "1401 K", "Austria", "Salzburg", "test1", "French")
                        );
                table.getItems().clear();
                table.setItems(data);
            }
        });
        final Button most_liked_restaurants = new Button("Most Liked Restaurants");
        most_liked_restaurants.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //todo : get text from this fields serch in database and add...
                // data.add(restaurantInfo);
            }
        });

        hb.getChildren().addAll(cheapest_restaurants, most_expensive_restaurants, most_liked_restaurants);
        hb.setSpacing(5);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }


    class EditingCell extends TableCell<ShowInformation.RestaurantInfo, String> {

        private TextField textField;

        public EditingCell() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                                    Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(textField.getText());
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
}
