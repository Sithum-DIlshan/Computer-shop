package controller;

import com.jfoenix.controls.JFXButton;
import com.sun.istack.internal.NotNull;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import model.GuaranteeTM;
import model.RepairsTM;
//import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class GuaranteeController implements Initializable {
    public Pane pane;
    public TableView<GuaranteeTM> tblGuarantee;
    public TableColumn<GuaranteeTM, String> colGuaranteeId;
    public TableColumn<GuaranteeTM, Integer> colGuaranteePeriod;
    public TableColumn<GuaranteeTM, String> colItemCode;
    public TableColumn colRemoveButton;
    public Button addGuaranteeButton;
    ObservableList<GuaranteeTM> obs = FXCollections.observableArrayList();
    Pattern gId = Pattern.compile("^(G-)[0-9]{3,4}$");
    Pattern gPeriod = Pattern.compile("^[1-9][0-9]*?$");
    Pattern iCode = Pattern.compile("^(I-)[0-9]{3,4}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        guaranteeButtonOnAction();
        tbl_init();
    }

    private void guaranteeButtonOnAction() {
        addGuaranteeButton.setOnMouseClicked(event -> {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/view/admin/AddGuaranteeForm.fxml"));
                Scene scene = new Scene(parent);
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
        tblGuarantee.setEditable(true);
        colGuaranteeId.setCellValueFactory(new PropertyValueFactory<>("guaranteeId"));
        colGuaranteeId.setCellFactory(TextFieldTableCell.forTableColumn());

        colGuaranteeId.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GuaranteeTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<GuaranteeTM, String> event) {
                if (!gId.matcher(event.getNewValue()).matches()) {

                } else {
                    updateGid(event.getOldValue(), event.getNewValue());
                }
            }
        });


        colGuaranteePeriod.setCellValueFactory(new PropertyValueFactory<>("guaranteePeriod"));
        colGuaranteePeriod.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        colGuaranteePeriod.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GuaranteeTM, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<GuaranteeTM, Integer> event) {
                if (!gPeriod.matcher(String.valueOf(event.getNewValue())).matches()) {

                } else {
                    updateGperiod(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });


        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemCode.setCellFactory(TextFieldTableCell.forTableColumn());

        colItemCode.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GuaranteeTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<GuaranteeTM, String> event) {
                if (!iCode.matcher(String.valueOf(event.getNewValue())).matches()) {

                } else {
                    updateItemCode(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });


        try {
            ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(
                    "SELECT * FROM Guarantee").executeQuery();

            while (rst.next()) {
                obs.add(new GuaranteeTM(
                        rst.getString(1),
                        rst.getInt(2),
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

    private void updateItemCode(String oldValue, String newValue, GuaranteeTM rowValue) {
        try {
            DbConnection.getInstance()
                    .getConnection().prepareStatement(
                            "UPDATE Guarantee SET itemCode = '" + newValue + "'" + "WHERE guaranteeId = '" + rowValue.getGuaranteeId() + "'").executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void updateGperiod(Integer oldValue, Integer newValue, @NotNull GuaranteeTM rowValue) {
        try {
            DbConnection.getInstance()
                    .getConnection().prepareStatement(
                            "UPDATE Guarantee SET guaranteePeriod = '" + newValue + "'" + "WHERE guaranteeId = '" + rowValue.getGuaranteeId() + "'").executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateGid(String oldValue, String newValue) {
        try {
            DbConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE Guarantee SET guaranteeId = '" + newValue + "'" + "WHERE guaranteeId = '" + oldValue + "'").executeUpdate();
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
                            if (removeOnAction(guarantee)) {
                                tblGuarantee.getItems().clear();
                                tbl_init();
                            }
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
