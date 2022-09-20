package controller;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import model.RepairsTM;
//import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RepairController implements Initializable {
    public TableView<RepairsTM> tblRepair;
    public TableColumn<RepairsTM, String> colRepairId;
    public TableColumn colCustId;
    public TableColumn<RepairsTM, String> colRepairType;
    public TableColumn<RepairsTM, Double> colCost;
    public TableColumn colRemoveButton;
    ObservableList<RepairsTM> obs = FXCollections.observableArrayList();
    Pattern id = Pattern.compile("^(R-)[0-9]{3,4}$");
    Pattern type = Pattern.compile("^(.|\\s)*[a-zA-Z]+(.|\\s)*$");
    Pattern cost = Pattern.compile("^[1-9][0-9]*([.][0-9]{1,2})?$");
    @FXML
    VBox vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbl_init();
    }

    private void tbl_init() {
        tblRepair.setEditable(true);

        colRepairId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
        colRepairId.setCellFactory(TextFieldTableCell.forTableColumn());

        colRepairId.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<RepairsTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<RepairsTM, String> event) {
                if (!id.matcher((CharSequence) event.getNewValue()).matches()) {

                } else {
                    updateRepairId(event.getOldValue(), event.getNewValue());
                }
            }
        });
        /*colRepairId.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                if (!id.matcher((CharSequence) event.getNewValue()).matches()) {

                } else {
                    updateRepairId(event.getOldValue(), event.getNewValue());
                }
            }
        });*/


        colRepairType.setCellValueFactory(new PropertyValueFactory<>("repairType"));
        colRepairType.setCellFactory(TextFieldTableCell.forTableColumn());

        colRepairType.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<RepairsTM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<RepairsTM, String> event) {
                if (!type.matcher(event.getNewValue()).matches()) {

                } else {
                    updateRepairType(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });

       /* colRepairType.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            public void handle(TableColumn.CellEditEvent<RepairsTM, String> event) {
                if (!type.matcher((CharSequence) event.getNewValue()).matches()) {

                } else {
                    updateRepairType(event.getOldValue(), event.getNewValue(), );
                }
            }

        });*/


        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colCost.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        colCost.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<RepairsTM, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<RepairsTM, Double> event) {
                if (!cost.matcher(String.valueOf(event.getNewValue())).matches()) {

                } else {
                    updateRepairCost(event.getOldValue(), event.getNewValue(), event.getRowValue());
                }
            }
        });
        /*colCost.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                if (!cost.matcher(String.valueOf(event.getNewValue())).matches()) {

                } else {
                    updateRepairCost(event.getOldValue(), event.getNewValue(), (RepairsTM) event.getRowValue());
                }
            }
        });*/


        colCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));


        try {
            ResultSet rst = DbConnection.getInstance().getConnection().
                    prepareStatement("SELECT * FROM Repair").executeQuery();

            while (rst.next()) {
                obs.add(new RepairsTM(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getDouble(3),
                        rst.getString(4)
                ));

              /*  Callback<TableColumn<RepairsTM, String>, TableCell<RepairsTM, String>> cellFactory
                        = param -> {
                    final TableCell<RepairsTM, String> cell = new TableCell<RepairsTM, String>() {
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
                                    RepairsTM repair = getTableView().getItems().get(getIndex());
                                        if (removeOnAction(repair)){
                                            tblRepair.getItems().clear();
                                            tbl_init();
                                        }
                                });
                                setGraphic(button);
                                setText(null);
                            }

                        }
                    };
                    return cell;
                };*/
                colRemoveButton.setCellFactory(getCellFactory());
                tblRepair.setItems(obs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateRepairCost(Double oldValue, Double newValue, RepairsTM repair) {
        try {
            DbConnection.getInstance().getConnection().
                    prepareStatement(
                            "UPDATE Repair SET cost = '" + newValue + "'" + "WHERE repair_id = '" + repair.getRepairId() + "'").executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void updateRepairType(String oldValue, String newValue, RepairsTM repairsTM) {
        try {
            DbConnection.getInstance().getConnection().
                    prepareStatement(
                            "UPDATE Repair SET repair_type = '" + newValue + "'" + "WHERE repair_id = '" + repairsTM.getRepairId() + "'").executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateRepairId(String oldValue, String newValue) {
        try {
            DbConnection.getInstance().
                    getConnection().prepareStatement(
                            "UPDATE Repair SET repair_id = '" + newValue + "'" + "WHERE repair_id = '" + oldValue + "'").executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Callback<TableColumn<RepairsTM, String>, TableCell<RepairsTM, String>> getCellFactory() {
        Callback<TableColumn<RepairsTM, String>, TableCell<RepairsTM, String>> cellFactory
                = param -> {
            final TableCell<RepairsTM, String> cell = new TableCell<RepairsTM, String>() {
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
                            RepairsTM repair = getTableView().getItems().get(getIndex());
                            if (removeOnAction(repair)){
                                tblRepair.getItems().clear();
                                tbl_init();
                            }
                        });
                        setGraphic(button);
                        setText(null);
                    }

                }

                private boolean removeOnAction(RepairsTM repair) {
                    try {
                        int i = DbConnection.getInstance().getConnection().
                                prepareStatement("Delete from Repair where repair_id = '" + repair.getRepairId() + "'").executeUpdate();

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
