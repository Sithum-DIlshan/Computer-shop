package controller;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import model.OrdersTM;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class OrdersController implements Initializable {
    public Pane orderPane;
    public TableView<OrdersTM> tblOrders;
    public TableColumn<OrdersTM, String> colOrderId;
    public TableColumn<OrdersTM, String> colCustomerId;
    public TableColumn colOrderDate;
    public TableColumn<OrdersTM, Double> colOrderCost;
    public TableColumn colUpdateButton;
    public TableColumn colRemoveButton;
    ObservableList<OrdersTM> obs = FXCollections.observableArrayList();
    Pattern code = Pattern.compile("^(O-)[0-9]{3,4}$");
    Pattern cost = Pattern.compile("^[1-9][0-9]*([.][0-9]{1,2})?$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblOrders.setEditable(true);
        tbl_init();
    }

    private void tbl_init() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderId.setCellFactory(TextFieldTableCell.forTableColumn());

        colOrderId.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<OrdersTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<OrdersTM, String> event) {
                if (!code.matcher(event.getNewValue()).matches()) {
                    new Alert(Alert.AlertType.WARNING, "Invalid order id").show();
                } else {
                    updateOId(event.getOldValue(), event.getNewValue());
                }
            }

            private void updateOId(String oldValue, String newValue) {
                try {
                    DbConnection.getInstance().getConnection().
                            prepareStatement(
                                    "UPDATE Orders SET o_id = '" + newValue + "'" + "WHERE o_id = '" + oldValue + "'").executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colOrderCost.setCellValueFactory(new PropertyValueFactory<>("orderCost"));
        colOrderCost.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colOrderCost.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<OrdersTM, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<OrdersTM, Double> event) {
                if (!cost.matcher(String.valueOf(event.getNewValue())).matches()) {
                    new Alert(Alert.AlertType.WARNING, "Invalid cost").show();
                } else {
                    updateCost(event.getNewValue(), event.getOldValue(), event.getRowValue());
                }
            }

            private void updateCost(Double newValue, Double oldValue, OrdersTM rowValue) {
                try {

                    DbConnection.getInstance().
                            getConnection().prepareStatement(
                                    "UPDATE Orders SET orderCost = '" + newValue + "'" + " WHERE o_id = '" + rowValue.getOrderId() + "'").executeUpdate();

                    //  System.out.println(o.getOrderId());

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            ResultSet rst =
                    DbConnection.getInstance().getConnection().prepareStatement(
                            "SELECT * FROM Orders").executeQuery();

            while (rst.next()) {
                obs.add(new OrdersTM(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getDate(3),
                        rst.getDouble(4)
                ));
                Callback<TableColumn<OrdersTM, String>, TableCell<OrdersTM, String>> cellFactory
                        = param -> {
                    final TableCell<OrdersTM, String> cell = new TableCell<OrdersTM, String>() {
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
                                    OrdersTM o = getTableView().getItems().get(getIndex());
                                    if (removeOnAction(o)) {
                                        tblOrders.getItems().clear();
                                        tbl_init();
                                    }
                                });
                                setGraphic(button);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                };
                colRemoveButton.setCellFactory(cellFactory);
                tblOrders.setItems(obs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private boolean removeOnAction(@NotNull OrdersTM o) {
        try {
            int i = DbConnection.getInstance().getConnection().
                    prepareStatement("Delete from Orders where o_id = '" + o.getOrderId() + "'").executeUpdate();

            return i > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
