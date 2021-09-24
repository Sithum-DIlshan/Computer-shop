package controller;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.ItemsTM;
import org.jetbrains.annotations.NotNull;
import util.Dialog;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ItemsController implements Initializable {
    public VBox vbox;
    public StackPane stack;
    public TableView<ItemsTM> tblItems;
    public TableColumn<ItemsTM, String> colItemCode;
    public TableColumn<ItemsTM, String> colDescription;
    public TableColumn<ItemsTM, String> colBrand;
    public TableColumn<ItemsTM, Double> colUnitPrice;
    public TableColumn<ItemsTM, Integer> colQty;
    public TableColumn colRemovebtn;
    public Button addItem;
    ObservableList<ItemsTM> obs = FXCollections.observableArrayList();
    Pattern itemId = Pattern.compile("^(I-)[0-9]{3,4}$");
    Pattern brandOrDescription = Pattern.compile("^(.|\\s)*[a-zA-Z]+(.|\\s)*$");
    Pattern price = Pattern.compile("^[1-9][0-9]*([.][0-9]{1,2})?$");
    Pattern qty = Pattern.compile("^[1-9][0-9]*?$");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbl_init();
        addItem_init();
    }

    private void addItem_init() {
        addItem.setOnMouseClicked(event -> {
            try {
                Parent load = null;
                load = FXMLLoader.load(getClass().getResource("/view/inventoryManager/AddItemForm.fxml"));
                Scene scene = new Scene(load);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public void tbl_init() {
        tblItems.setEditable(true);
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemCode.setCellFactory(TextFieldTableCell.forTableColumn());

        colItemCode.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ItemsTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ItemsTM, String> event) {
                if (!itemId.matcher(event.getNewValue()).matches()) {

                } else {
                    updateItemid(event.getOldValue(), event.getNewValue());
                }
            }
        });


        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDescription.setCellFactory(TextFieldTableCell.forTableColumn());

        colDescription.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ItemsTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ItemsTM, String> event) {
                if (!brandOrDescription.matcher(String.valueOf(event.getNewValue())).matches()) {

                } else {
                    updateItemDescription(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });


        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colBrand.setCellFactory(TextFieldTableCell.forTableColumn());

        colBrand.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ItemsTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ItemsTM, String> event) {
                if (!brandOrDescription.matcher(String.valueOf(event.getNewValue())).matches()) {

                } else {
                    updateBrand(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });

        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colUnitPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        colUnitPrice.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ItemsTM, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ItemsTM, Double> event) {
                if (!price.matcher(String.valueOf(event.getNewValue())).matches()) {
                    Dialog.showDialog("Invalid Unit Price", stack, "Close");
                } else {
                    updatePrice(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });

        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colQty.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        colQty.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ItemsTM, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ItemsTM, Integer> event) {
                if (!qty.matcher(String.valueOf(event.getNewValue())).matches()) {
                    Dialog.showDialog("Invalid Qty", stack, "Close");
                } else {
                    updateQty(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });


        try {
            ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(
                    "SELECT * FROM Item").executeQuery();

            while (rst.next()) {
                obs.add(new ItemsTM(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getDouble(4),
                        rst.getInt(5)
                ));
            }

            colRemovebtn.setCellFactory(getCellFactory());
            tblItems.setItems(obs);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Callback<TableColumn<ItemsTM, String>, TableCell<ItemsTM, String>> getCellFactory() {
        Callback<TableColumn<ItemsTM, String>, TableCell<ItemsTM, String>> cellFactory
                = param -> {
            final TableCell<ItemsTM, String> cell = new TableCell<ItemsTM, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final JFXButton button = new JFXButton("Remove");

                        button.setStyle("-fx-border-radius: 10px; " +
                                "-fx-background-color: #ef5350; " +
                                "-fx-background-radius: 10px; " +
                                // "-fx-background-color: #2c3e50; " +
                                "-fx-border-color: #ef5350; " +
                                "-fx-border-width: 1px; " +
                                "-fx-text-fill: #ffffff");

                        button.setOnAction(event -> {
                            ItemsTM itemsTM = getTableView().getItems().get(getIndex());
                            if (removeOnAction(itemsTM)) {
                                tblItems.getItems().clear();
                                tbl_init();
                            }
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }

                private boolean removeOnAction(ItemsTM item) {
                    try {
                        int i = DbConnection.getInstance().getConnection().
                                prepareStatement("Delete from Item where code = '" + item.getCode() + "'").executeUpdate();

                        return i > 0;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            };
            return cell;
        };
        return cellFactory;
    }

    private void updateItemid(String oldValue, String newValue) {
        try {
            DbConnection.getInstance()
                    .getConnection().prepareStatement(
                            "UPDATE Item SET code = '" + newValue + "'" + "WHERE code = '" + oldValue + "'").executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void updateItemDescription(String oldValue, String newValue, @NotNull ItemsTM rowValue) {
        try {
            DbConnection.getInstance()
                    .getConnection().prepareStatement(
                            "UPDATE Item SET itemDescription = '" + newValue + "'" + "WHERE code = '" + rowValue.getCode() + "'").executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateBrand(String oldValue, String newValue, ItemsTM rowValue) {
        try {
            DbConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE Item SET brand = '" + newValue + "'" + "WHERE code = '" + rowValue.getCode() + "'").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updatePrice(Double oldValue, Double newValue, ItemsTM rowValue) {
        try {
            DbConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE Item SET itemUnitPrice = '" + newValue + "'" + "WHERE code = '" + rowValue.getCode() + "'").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateQty(Integer oldValue, Integer newValue, ItemsTM rowValue) {
        try {
            DbConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE Item SET qtyOnHand = '" + newValue + "'" + "WHERE code = '" + rowValue.getCode() + "'").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void tableLoad(){
        tbl_init();
    }
}
