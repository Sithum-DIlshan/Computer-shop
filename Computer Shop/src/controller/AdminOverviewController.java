package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.OverviewTM;
import util.GetItems;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminOverviewController implements Initializable {
    public VBox vbox2;
    public TableView<OverviewTM> tblOverview;
    public TableColumn colCompanyName;
    public TableColumn colCustomerId;
    public TableColumn colOrderId;
    public TableColumn colQuantity;
    public TableColumn colOrderDate;
    public Label totalOrders;
    public Label totalRepairs;
    ObservableList<OverviewTM> obs = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbl_init();
    }

    private void tbl_init() {
        colCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));

        tblOverview.setItems(GetItems.loadOverview(obs));
        totalOrders.setText(String.valueOf(obs.size()));
    }


}
