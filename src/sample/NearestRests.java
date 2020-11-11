package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

public class NearestRests extends Application {
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
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };


        TableColumn RegionCol = new TableColumn("Region");
        RegionCol.setMinWidth(200);
        RegionCol.setCellValueFactory(
                new PropertyValueFactory<ShowInformation.RestaurantInfo, String>("region"));
        RegionCol.setCellFactory(cellFactory);
        RegionCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ShowInformation.RestaurantInfo, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ShowInformation.RestaurantInfo, String> t) {
                        ((ShowInformation.RestaurantInfo) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setRegion(t.getNewValue());
                    }
                }
        );

        TableColumn CityCol = new TableColumn("City");
        CityCol.setMinWidth(200);
        CityCol.setCellValueFactory(
                new PropertyValueFactory<ShowInformation.RestaurantInfo, String>("city"));
        CityCol.setCellFactory(cellFactory);
        CityCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ShowInformation.RestaurantInfo, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ShowInformation.RestaurantInfo, String> t) {
                        ((ShowInformation.RestaurantInfo) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setCity(t.getNewValue());
                    }
                }
        );

        TableColumn NameCol = new TableColumn("Name");
        NameCol.setMinWidth(200);
        NameCol.setCellValueFactory(
                new PropertyValueFactory<ShowInformation.RestaurantInfo, String>("name"));
        NameCol.setCellFactory(cellFactory);
        NameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ShowInformation.RestaurantInfo, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ShowInformation.RestaurantInfo, String> t) {
                        ((ShowInformation.RestaurantInfo) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                    }
                }
        );

        TableColumn CuisineCol = new TableColumn("Cuisine");
        CuisineCol.setMinWidth(100);
        CuisineCol.setCellValueFactory(
                new PropertyValueFactory<ShowInformation.RestaurantInfo, String>("Cuisine"));
        CuisineCol.setCellFactory(cellFactory);
        CuisineCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ShowInformation.RestaurantInfo, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ShowInformation.RestaurantInfo, String> t) {
                        ((ShowInformation.RestaurantInfo) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setCuisine(t.getNewValue());
                    }
                }
        );
        table.setItems(data);
        table.getColumns().addAll(RegionCol, CityCol, NameCol, CuisineCol);

        final TextField addCountry = new TextField();
        addCountry.setPromptText("Country");
        addCountry.setMaxWidth(RegionCol.getPrefWidth());
        addCountry.setMinWidth(100);


        final TextField addRegion = new TextField();
        addRegion.setPromptText("Region");
        addRegion.setMaxWidth(RegionCol.getPrefWidth());
        addRegion.setMinWidth(100);


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
                //todo : get text from this fields serch in database and add...
                // data.add(restaurantInfo);
            }
        });

        hb.getChildren().addAll(addCountry, addRegion, addCity, addLatitude, addLongitude, addButton);
        hb.setSpacing(3);

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