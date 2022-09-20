package controller;

import com.jfoenix.controls.JFXButton;
import com.sun.istack.internal.NotNull;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.CustomerTM;
//import org.jetbrains.annotations.NotNull;
import util.Dialog;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CustomerController implements Initializable {
    public TableView<CustomerTM> tblCustomer;
    public TableColumn<CustomerTM, String> colCustomerId;
    public TableColumn<CustomerTM, String> colCustName;
    public TableColumn<CustomerTM, String> colAddress;
    public TableColumn<CustomerTM, String> colContact;
    public TableColumn<CustomerTM, String> colCity;
    public TableColumn<CustomerTM, String> colRemoveButton;
    public StackPane stack;
    public Button addCustomer;
    Pattern cId = Pattern.compile("^(C-)[0-9]{3,4}$");
    Pattern nameOraddress = Pattern.compile("^(.|\\s)*[a-zA-Z]+(.|\\s)*$");
    Pattern contact = Pattern.compile("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$");
    Pattern city = Pattern.compile("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$");
    ObservableList<CustomerTM> obs = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbl_init();
        addCustomer_init();
    }

    private void addCustomer_init() {
        addCustomer.setOnMouseClicked(event -> {
            try {
                Parent load = FXMLLoader.load(getClass().getResource("/view/admin/AddCustomerForm.fxml"));
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

    private void tbl_init() {
        tblCustomer.setEditable(true);
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerId.setCellFactory(TextFieldTableCell.forTableColumn());

        colCustomerId.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CustomerTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CustomerTM, String> event) {
                if (!cId.matcher(event.getNewValue()).matches()) {

                } else {
                    updateCid(event.getOldValue(), event.getNewValue());
                }
            }
        });


        colCustName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustName.setCellFactory(TextFieldTableCell.forTableColumn());

        colCustName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CustomerTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CustomerTM, String> event) {
                if (!nameOraddress.matcher(String.valueOf(event.getNewValue())).matches()) {

                } else {
                    updateCustName(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });


        colAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colAddress.setCellFactory(TextFieldTableCell.forTableColumn());

        colAddress.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CustomerTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CustomerTM, String> event) {
                if (!nameOraddress.matcher(String.valueOf(event.getNewValue())).matches()) {

                } else {
                    updateAddress(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });

        colContact.setCellValueFactory(new PropertyValueFactory<>("customerContact"));
        colContact.setCellFactory(TextFieldTableCell.forTableColumn());

        colContact.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CustomerTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CustomerTM, String> event) {
                if (!contact.matcher(String.valueOf(event.getNewValue())).matches()) {
                    Dialog.showDialog("Invalid Customer Address", stack, "Close");
                } else {
                    updateContact(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });

        colCity.setCellValueFactory(new PropertyValueFactory<>("customerCity"));
        colCity.setCellFactory(TextFieldTableCell.forTableColumn());

        colCity.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CustomerTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CustomerTM, String> event) {
                if (!city.matcher(String.valueOf(event.getNewValue())).matches()) {
                    Dialog.showDialog("Invalid Customer City", stack, "Close");
                } else {
                    updateCity(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });


        try {
            ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(
                    "SELECT * FROM Customer").executeQuery();

            while (rst.next()) {
                obs.add(new CustomerTM(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5)
                ));
            }

            colRemoveButton.setCellFactory(getCellFactory());
            tblCustomer.setItems(obs);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Callback<TableColumn<CustomerTM, String>, TableCell<CustomerTM, String>> getCellFactory() {
        Callback<TableColumn<CustomerTM, String>, TableCell<CustomerTM, String>> cellFactory
                = param -> {
            final TableCell<CustomerTM, String> cell = new TableCell<CustomerTM, String>() {
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
                            CustomerTM customer = getTableView().getItems().get(getIndex());
                            if (removeOnAction(customer)) {
                                tblCustomer.getItems().clear();
                                tbl_init();
                            }
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }

                private boolean removeOnAction(CustomerTM customer) {
                    try {
                        int i = DbConnection.getInstance().getConnection().
                                prepareStatement("Delete from Customer where customerId = '" + customer.getCustomerId() + "'").executeUpdate();

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

    private void updateCid(String oldValue, String newValue) {
        try {
            DbConnection.getInstance()
                    .getConnection().prepareStatement(
                            "UPDATE Customer SET customerId = '" + newValue + "'" + "WHERE customerId = '" + oldValue + "'").executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void updateCustName(String oldValue, String newValue, @NotNull CustomerTM rowValue) {
        try {
            DbConnection.getInstance()
                    .getConnection().prepareStatement(
                            "UPDATE Customer SET customerName = '" + newValue + "'" + "WHERE customerId = '" + rowValue.getCustomerId() + "'").executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateAddress(String oldValue, String newValue, CustomerTM rowValue) {
        try {
            DbConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE Customer SET customerAddress = '" + newValue + "'" + "WHERE customerId = '" + rowValue.getCustomerAddress() + "'").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateCity(String oldValue, String newValue, CustomerTM rowValue) {
        try {
            DbConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE Customer SET city = '" + newValue + "'" + "WHERE customerId = '" + rowValue.getCustomerId() + "'").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateContact(String oldValue, String newValue, CustomerTM rowValue) {
        try {
            DbConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE Customer SET customerContact = '" + newValue + "'" + "WHERE customerId = '" + rowValue.getCustomerId() + "'").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
