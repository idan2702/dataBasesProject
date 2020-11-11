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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

public class AddData extends Application {
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
        stage.setTitle("Add Data");
        stage.setWidth(935);
        stage.setHeight(550);

        final Label label = new Label("Add Data");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };

        TableColumn PriceCol = new TableColumn("Price");
        PriceCol.setMinWidth(100);
        PriceCol.setCellValueFactory(
                new PropertyValueFactory<ShowInformation.RestaurantInfo, String>("price"));
        PriceCol.setCellFactory(cellFactory);
        PriceCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ShowInformation.RestaurantInfo, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ShowInformation.RestaurantInfo, String> t) {
                        ((ShowInformation.RestaurantInfo) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setprice(t.getNewValue());
                    }
                }
        );


        TableColumn ZipCodeCol = new TableColumn("ZipCode");
        ZipCodeCol.setMinWidth(100);
        ZipCodeCol.setCellValueFactory(
                new PropertyValueFactory<ShowInformation.RestaurantInfo, String>("zipCode"));
        ZipCodeCol.setCellFactory(cellFactory);
        ZipCodeCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ShowInformation.RestaurantInfo, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ShowInformation.RestaurantInfo, String> t) {
                        ((ShowInformation.RestaurantInfo) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setZipCode(t.getNewValue());
                    }
                }
        );

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
        table.getColumns().addAll(PriceCol, ZipCodeCol, RegionCol, CityCol, NameCol,CuisineCol);


        final TextField addPrice = new TextField();
        addPrice.setPromptText("Price");
        addPrice.setMaxWidth(PriceCol.getPrefWidth());

        final TextField addZipCode = new TextField();
        addZipCode.setPromptText("Restaurant ZipCode");
        addZipCode.setMaxWidth(ZipCodeCol.getPrefWidth());
        addZipCode.setMinWidth(130);



        final TextField addRegion = new TextField();
        addRegion.setPromptText("Restaurant Region");
        addRegion.setMaxWidth(RegionCol.getPrefWidth());
        addRegion.setMinWidth(130);


        final TextField addCity = new TextField();
        addCity.setPromptText("Restaurant City");
        addCity.setMaxWidth(CityCol.getPrefWidth());
        addCity.setMinWidth(130);

        final TextField addName = new TextField();
        addName.setPromptText("Restaurant Name");
        addName.setMaxWidth(NameCol.getPrefWidth());
        addName.setMinWidth(130);

        final TextField addCuisine = new TextField();
        addCuisine.setPromptText("Restaurant Cuisine");
        addCuisine.setMaxWidth(CuisineCol.getPrefWidth());
        addCuisine.setMinWidth(130);

        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ShowInformation.RestaurantInfo restaurantInfo = new ShowInformation.RestaurantInfo(addPrice.getText(), addZipCode.getText(), addRegion.getText(),addCity.getText(), addName.getText(), addCuisine.getText());
                data.add(restaurantInfo);
                arraylisRestaurantInfo.add(restaurantInfo);
                addPrice.clear();
                addZipCode.clear();
                addRegion.clear();
                addCity.clear();
                addName.clear();
                addCuisine.clear();
            }
        });
        final Button SaveAndQuitButton = new Button("Save And Quit");
        SaveAndQuitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

              //todo : add to table and close page... ( add from the list )

            }
        });
        hb.getChildren().addAll(addPrice, addZipCode, addRegion, addCity,addName, addCuisine ,addButton, SaveAndQuitButton);
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
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
            textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
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