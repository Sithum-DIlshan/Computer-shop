package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import model.PermissionTM;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PermissionController implements Initializable {
    public StackPane stack;
    public TableView<PermissionTM> tblPermission;
    public TableColumn<PermissionTM, String> colPermissionId;
    public TableColumn<PermissionTM, String> colAccountId;
    Pattern pId = Pattern.compile("^(P-)[0-9]{3,4}$");
    Pattern aId = Pattern.compile("^(A-)[0-9]{3,4}$");
    ObservableList<PermissionTM> obs = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbl_init();
    }

    private void tbl_init() {

        tblPermission.setEditable(true);
        colPermissionId.setCellValueFactory(new PropertyValueFactory<>("permissionId"));
        colPermissionId.setCellFactory(TextFieldTableCell.forTableColumn());

        colPermissionId.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<PermissionTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<PermissionTM, String> event) {
                if (!pId.matcher(event.getNewValue()).matches()) {

                } else {
                    updatePermissionId(event.getOldValue(), event.getNewValue());
                }
            }
        });


        colAccountId.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        colAccountId.setCellFactory(TextFieldTableCell.forTableColumn());

        colAccountId.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<PermissionTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<PermissionTM, String> event) {
                if (!aId.matcher(String.valueOf(event.getNewValue())).matches()) {

                } else {
                    updateAccountId(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });


        try {
            ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(
                    "SELECT * FROM Permission").executeQuery();

            while (rst.next()) {
                obs.add(new PermissionTM(
                        rst.getString(1),
                        rst.getString(2)
                ));
            }


            tblPermission.setItems(obs);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updatePermissionId(String oldValue, String newValue) {
        try {
            DbConnection.getInstance()
                    .getConnection().prepareStatement(
                            "UPDATE Permission SET permission_id = '" + newValue + "'" + "WHERE permission_id = '" + oldValue + "'").executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void updateAccountId(String oldValue, String newValue, @NotNull PermissionTM rowValue) {
        try {
            DbConnection.getInstance()
                    .getConnection().prepareStatement(
                            "UPDATE Permission SET account_id = '" + newValue + "'" + "WHERE permission_id = '" + rowValue.getPermissionId() + "'").executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
