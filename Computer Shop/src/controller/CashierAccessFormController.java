package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CashierAccessFormController implements Initializable {
    public BorderPane border;
    public Button overview;
    public Button placeOrder;
    public Button manageOrder;
    public Button signOut;
    public Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        overviewClicked();
        placeOrderClicked();
        manageOrderClicked();
        //signOutClicked();
    }

    @FXML
    private void signOutClicked() throws IOException {
        Stage stage = (Stage) border.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/admin/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        SetStageStyle.setStyle(scene, parent, stage);
        stage.setScene(scene);
    }

    private void manageOrderClicked() {
        manageOrder.setOnMouseClicked(event -> {
            //overviewPane.setVisible(true);
            try {
                border.setCenter(FXMLLoader.load(getClass().getResource("/view/admin/Orders.fxml")));
                border.setStyle("-fx-background-color: #2c3e50");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    private void placeOrderClicked() {
        placeOrder.setOnMouseClicked(event -> {
            //overviewPane.setVisible(true);
            try {
                border.setCenter(FXMLLoader.load(getClass().getResource("/view/cashier/nodes/PlaceOrder.fxml")));

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    private void overviewClicked() {
        overview.setOnMouseClicked(event -> {
            //overviewPane.setVisible(true);
            try {
                border.setCenter(FXMLLoader.load(getClass().getResource("/view/admin/Overview.fxml")));
                border.setStyle("-fx-background-color: #2c3e50");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}
