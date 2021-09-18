package util;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import model.OrdersTM;
import model.OverviewTM;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetItems {
    //public static JFXButton button = null;

    public void initialize() {

    }

    public static ObservableList<OverviewTM> loadOverview(ObservableList<OverviewTM> obs) {
        try {
            ResultSet rst =
                    DbConnection.getInstance().getConnection().prepareStatement(
                            "SELECT * FROM Orders").executeQuery();

            while (rst.next()) {
                obs.add(new OverviewTM(
                        "NoaxComputers",
                        rst.getString(2),
                        rst.getString(1),
                        rst.getDate(3),
                        "6"
                ));
            }

            return obs;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return obs;
    }

    public static ObservableList loadOrders(ObservableList<OrdersTM> obs, TableView<OrdersTM> tblOrders) {
        try {
            ResultSet rst =
                    DbConnection.getInstance().getConnection().prepareStatement(
                            "SELECT * FROM Orders").executeQuery();


            // JFXButton button = null;
            ObservableList<JFXButton> obButton = FXCollections.observableArrayList();

            int i = 0;
            while (rst.next()) {
                // obButton.add(new JFXButton("Update"));
                obs.add(new OrdersTM(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getDate(3),
                        rst.getDouble(4)
                        //new JFXButton("Update")
                        //       obButton.get(i)
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
                                final JFXButton button = new JFXButton("Update");

                                button.setStyle("-fx-border-radius: 10px; " +
                                        "-fx-background-color: #039be5; " +
                                        "-fx-background-radius: 10px; " +
                                        "-fx-background-color: #2c3e50; " +
                                        "-fx-border-color: #039be5; " +
                                        "-fx-border-width: 1px; " +
                                        "-fx-text-fill: #ffffff");

                                button.setOnAction(event -> {
                                    OrdersTM o = getTableView().getItems().get(getIndex());

                                    new Alert(Alert.AlertType.CONFIRMATION, "" + o.getCustomerId()).show();
                                });
                                setGraphic(button);
                                setText(null);
                            }
                        }
                    };


                    return cell;
                };

            }


            return obs;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return obs;
    }
}
