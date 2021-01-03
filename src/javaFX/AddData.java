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
import javafx.util.Callback;

import java.util.ArrayList;

public class AddData extends Application {
    private TableView<LikedRestObj> table = new TableView<LikedRestObj>();
    private ObservableList<LikedRestObj> data =
            FXCollections.observableArrayList();
    final HBox hb = new HBox();

    private ArrayList<LikedRestObj> likedRestObj = new ArrayList<LikedRestObj>();
    private DbConnection dbConnection = new DbConnection();

    public AddData(ArrayList<LikedRestObj> likedRestObj) {
        this.likedRestObj = likedRestObj;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setdataArrayFromDb() {
        data = FXCollections.observableArrayList(likedRestObj);
    }

    private void addButtonToTable() {
        TableColumn<LikedRestObj, Void> colBtn = new TableColumn("Unlike Restaurant..");
        colBtn.setMinWidth(200);
        Callback<TableColumn<LikedRestObj, Void>, TableCell<LikedRestObj, Void>> cellFactory = new Callback<TableColumn<LikedRestObj, Void>, TableCell<LikedRestObj, Void>>() {
            @Override
            public TableCell<LikedRestObj, Void> call(final TableColumn<LikedRestObj, Void> param) {
                final TableCell<LikedRestObj, Void> cell = new TableCell<LikedRestObj, Void>() {

                    private final Button btn = new Button("Unlike");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            LikedRestObj rest = getTableView().getItems().get(getIndex());
                            int index_to_remove = find_index_of_rest(rest.getName());
                            if (index_to_remove >= 0) {
                                data.remove(index_to_remove);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Hey!");
                                alert.setHeaderText(null);
                                alert.setContentText("You unliked \"" + rest.getName() + "\"");
                                btn.minWidth(100);
                                String query = "DELETE FROM `restaurants_dbs`.`likedrest` WHERE (`userName` = '"
                                        +likedRestObj.get(index_to_remove).getUserName()+"')"+"and (`restName` = '"+likedRestObj.get(index_to_remove).getName()+"');";
                                try {
                                    if(!dbConnection.deleteValInDataBase(query)){
                                        newErorAlert();
                                    }
                                } catch (Exception e) {
                                    newErorAlert();
                                }
                                alert.showAndWait();
                            } else {
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

    private int find_index_of_rest(String name) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName() == name) {
                return i;
            }
        }
        return -1;
    }

    private void newErorAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("error!");
        alert.setHeaderText(null);
        alert.setContentText("failed:error occurred, please try again...");
        alert.showAndWait();
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
                new PropertyValueFactory<LikedRestObj, String>("name"));

        TableColumn DescriptionCol = new TableColumn("Description");
        DescriptionCol.setMinWidth(500);
        DescriptionCol.setCellValueFactory(
                new PropertyValueFactory<LikedRestObj, String>("Review"));
        DescriptionCol.setCellFactory(cellFactory);
        DescriptionCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<LikedRestObj, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<LikedRestObj, String> t) {
                        ((LikedRestObj) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDescription(t.getNewValue());
                    }
                }
        );
        table.setItems(data);
        table.getColumns().addAll(NameCol, DescriptionCol);
        addButtonToTable();


        stage.setOnCloseRequest( ev -> {
            for (int i = 0; i < likedRestObj.size(); i++) {
                System.out.println(likedRestObj.get(i).getReview());
                String sqlQuery = "UPDATE restaurants_dbs.likedrest SET Review = '" + likedRestObj.get(i).getReview() +
                        "' WHERE (userName = '" + likedRestObj.get(i).getUserName() + "') and (restName = '" + likedRestObj.get(i).getName() + "');";
                try {
                    if(!dbConnection.setValInDataBase(sqlQuery)){
                        newErorAlert();
                    }
                } catch (Exception e1) {
                    newErorAlert();
                }
            }
            dbConnection.disconnect();
        });
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }


    class EditingCell extends TableCell<LikedRestObj, String> {

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