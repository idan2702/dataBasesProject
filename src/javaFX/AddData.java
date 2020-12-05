package javaFX;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AddData extends Application {
    private TableView<MyFavouritesRestaurantInfo> table = new TableView<MyFavouritesRestaurantInfo>();
    private ObservableList<MyFavouritesRestaurantInfo> data =
            FXCollections.observableArrayList();
    final HBox hb = new HBox();

    public static void main(String[] args) {
        launch(args);
    }

    public void setdataArrayFromDb() {
        //todo : get from db
        data = FXCollections.observableArrayList(
                new MyFavouritesRestaurantInfo("test1", "good"),
                new MyFavouritesRestaurantInfo("test2", "shit"),
                new MyFavouritesRestaurantInfo("test3", "nice"),
                new MyFavouritesRestaurantInfo("test4", "very good"),
                new MyFavouritesRestaurantInfo("test5", "i want to live there")
        );

    }

    private void addButtonToTable() {
        TableColumn<MyFavouritesRestaurantInfo, Void> colBtn = new TableColumn("Unlike Restaurant..");
        colBtn.setMinWidth(200);
        Callback<TableColumn<MyFavouritesRestaurantInfo, Void>, TableCell<MyFavouritesRestaurantInfo, Void>> cellFactory = new Callback<TableColumn<MyFavouritesRestaurantInfo, Void>, TableCell<MyFavouritesRestaurantInfo, Void>>() {
            @Override
            public TableCell<MyFavouritesRestaurantInfo, Void> call(final TableColumn<MyFavouritesRestaurantInfo, Void> param) {
                final TableCell<MyFavouritesRestaurantInfo, Void> cell = new TableCell<MyFavouritesRestaurantInfo, Void>() {

                    private final Button btn = new Button("Unlike");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            MyFavouritesRestaurantInfo rest = getTableView().getItems().get(getIndex());
                            int index_to_remove = find_index_of_rest(rest.getName());
                            if(index_to_remove >= 0){
                                data.remove(index_to_remove);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Hey!");
                                alert.setHeaderText(null);
                                alert.setContentText("You unliked \"" + rest.getName() + "\"");
                                btn.minWidth(100);
                                //todo:remove fome database
                                alert.showAndWait();
                            }else{
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Hey!");
                                alert.setHeaderText(null);
                                alert.setContentText("error: failed with deletion, please try again...");
                                btn.minWidth(100);
                                alert.showAndWait();
                            }
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
    private int find_index_of_rest(String name){
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getName() == name){
                return i;
            }
        }
        return -1;
    }
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Your Favourites");
        stage.setWidth(935);
        stage.setHeight(550);
        setdataArrayFromDb();
        final Label label = new Label("Your Favourites");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };

        TableColumn NameCol = new TableColumn("Name");
        NameCol.setMinWidth(200);
        NameCol.setCellValueFactory(
                new PropertyValueFactory<ShowInformation.RestaurantInfo, String>("name"));

        TableColumn DescriptionCol = new TableColumn("Description");
        DescriptionCol.setMinWidth(500);
        DescriptionCol.setCellValueFactory(
                new PropertyValueFactory<ShowInformation.RestaurantInfo, String>("description"));
        DescriptionCol.setCellFactory(cellFactory);
        DescriptionCol.setOnEditCommit(
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
        table.getColumns().addAll(NameCol, DescriptionCol);
        addButtonToTable();

        final Button SaveAndQuitButton = new Button("Save And Quit");
        SaveAndQuitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                //todo : add to table and close page... ( add from the list )

            }
        });
        hb.getChildren().addAll(SaveAndQuitButton);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }


    class EditingCell extends TableCell<MyFavouritesRestaurantInfo, String> {

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

    public static class MyFavouritesRestaurantInfo {
        private final SimpleStringProperty name;
        private final SimpleStringProperty description;


        public MyFavouritesRestaurantInfo(String name, String description) {

            this.name = new SimpleStringProperty(name);
            this.description = new SimpleStringProperty(description);


        }

        public String getDescription() {
            return description.get();
        }

        public void setDescription(String description) {
            this.description.set(description);
        }


        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }
    }
}