package controller;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import model.GuaranteeTM;
import model.RepairsTM;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GuaranteeController implements Initializable {
    public Pane pane;
    public TableView<GuaranteeTM> tblGuarantee;
    public TableColumn<GuaranteeTM, String> colGuaranteeId;
    public TableColumn<GuaranteeTM, String> colGuaranteePeriod;
    public TableColumn<GuaranteeTM, String> colItemCode;
    public TableColumn colRemoveButton;
    ObservableList<GuaranteeTM> obs = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbl_init();
    }

    private void tbl_init() {
        tblGuarantee.setEditable(true);
        colGuaranteeId.setCellValueFactory(new PropertyValueFactory<>("guaranteeId"));
        colGuaranteePeriod.setCellValueFactory(new PropertyValueFactory<>("guaranteePeriod"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));


        try {
            ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(
                    "SELECT * FROM Guarantee").executeQuery();

            while (rst.next()) {
                obs.add(new GuaranteeTM(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3)
                ));
            }

            colRemoveButton.setCellFactory(getCellFactory());
            tblGuarantee.setItems(obs);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Callback<TableColumn<GuaranteeTM, String>, TableCell<GuaranteeTM, String>> getCellFactory() {
        Callback<TableColumn<GuaranteeTM, String>, TableCell<GuaranteeTM, String>> cellFactory
                = param -> {
            final TableCell<GuaranteeTM, String> cell = new TableCell<GuaranteeTM, String>() {
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
                            GuaranteeTM guarantee = getTableView().getItems().get(getIndex());
                            /*if (removeOnAction(guarantee)){
                                tblGuarantee.getItems().clear();
                                tbl_init();
                            }*/
                        });
                        setGraphic(button);
                        setText(null);
                    }

                }

                private boolean removeOnAction(GuaranteeTM guarantee) {
                    try {
                        int i = DbConnection.getInstance().getConnection().
                                prepareStatement("Delete from Guarantee where guaranteeId = '" + guarantee.getGuaranteeId() + "'").executeUpdate();

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
