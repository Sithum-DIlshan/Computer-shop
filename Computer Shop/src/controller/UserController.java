package controller;

import com.jfoenix.controls.JFXButton;
import com.sun.istack.internal.NotNull;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import model.GuaranteeTM;
import model.UserTM;
//import org.jetbrains.annotations.NotNull;
import util.Dialog;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class UserController implements Initializable {
    public TableView<UserTM> tblUser;
    public TableColumn<UserTM, String> colUserId;
    public TableColumn<UserTM, String> colUserName;
    public TableColumn<UserTM, String> colAddress;
    public TableColumn<UserTM, String> colCity;
    public TableColumn colRemoveButton;
    public StackPane stack;
    public Button addUserBtn;
    Pattern uId = Pattern.compile("^(U-)[0-9]{3,4}$");
    Pattern nameOraddress = Pattern.compile("^(.|\\s)*[a-zA-Z]+(.|\\s)*$");
    Pattern city = Pattern.compile("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$");
    ObservableList<UserTM> obs = FXCollections.observableArrayList();


    @FXML
    Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbl_init();
        addUserBtn_init();
    }

    private void addUserBtn_init() {
        addUserBtn.setOnMouseClicked(event -> {
            try {
                Parent load = FXMLLoader.load(getClass().getResource("/view/admin/AddUserForm.fxml"));
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
        tblUser.setEditable(true);
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserId.setCellFactory(TextFieldTableCell.forTableColumn());

        colUserId.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<UserTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<UserTM, String> event) {
                if (!uId.matcher(event.getNewValue()).matches()) {

                } else {
                    updateUid(event.getOldValue(), event.getNewValue());
                }
            }
        });


        colUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUserName.setCellFactory(TextFieldTableCell.forTableColumn());

        colUserName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<UserTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<UserTM, String> event) {
                if (!nameOraddress.matcher(String.valueOf(event.getNewValue())).matches()) {

                } else {
                    updateUserName(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });


        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAddress.setCellFactory(TextFieldTableCell.forTableColumn());

        colAddress.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<UserTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<UserTM, String> event) {
                if (!nameOraddress.matcher(String.valueOf(event.getNewValue())).matches()) {

                } else {
                    updateAddress(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });

        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colCity.setCellFactory(TextFieldTableCell.forTableColumn());

        colCity.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<UserTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<UserTM, String> event) {
                if (!city.matcher(String.valueOf(event.getNewValue())).matches()) {
                    Dialog.showDialog("Invalid City Name", stack, "Close");
                } else {
                    updateCity(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });


        try {
            ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(
                    "SELECT * FROM Users").executeQuery();

            while (rst.next()) {
                obs.add(new UserTM(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4)
                ));
            }

            colRemoveButton.setCellFactory(getCellFactory());
            tblUser.setItems(obs);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateUid(String oldValue, String newValue) {
        try {
            DbConnection.getInstance()
                    .getConnection().prepareStatement(
                            "UPDATE Users SET userId = '" + newValue + "'" + "WHERE userId = '" + oldValue + "'").executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void updateUserName(String oldValue, String newValue, @NotNull UserTM rowValue) {
        try {
            DbConnection.getInstance()
                    .getConnection().prepareStatement(
                            "UPDATE Users SET name = '" + newValue + "'" + "WHERE userId = '" + rowValue.getUserId() + "'").executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateAddress(String oldValue, String newValue, UserTM rowValue) {
        try {
            DbConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE Users SET address = '" + newValue + "'" + "WHERE userId = '" + rowValue.getUserId() + "'").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateCity(String oldValue, String newValue, UserTM rowValue) {
        try {
            DbConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE Users SET city = '" + newValue + "'" + "WHERE userId = '" + rowValue.getUserId() + "'").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Callback<TableColumn<UserTM, String>, TableCell<UserTM, String>> getCellFactory() {
        Callback<TableColumn<UserTM, String>, TableCell<UserTM, String>> cellFactory
                = param -> {
            final TableCell<UserTM, String> cell = new TableCell<UserTM, String>() {
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
                            UserTM guarantee = getTableView().getItems().get(getIndex());
                            if (removeOnAction(guarantee)) {
                                tblUser.getItems().clear();
                                tbl_init();
                            }
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }

                private boolean removeOnAction(UserTM user) {
                    try {
                        int i = DbConnection.getInstance().getConnection().
                                prepareStatement("Delete from Users where userId = '" + user.getUserId() + "'").executeUpdate();

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
}

