package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminAccessController implements Initializable {

    public VBox getItems;
    public ScrollPane scroll;
    //public Pane overviewPane;
    public BorderPane borderPane;
    public Button systemReports;
    public Button orders;
    public Button repairs;
    public Button guarantee;
    public Button users;
    public Button permission;
    public Button customers;
    public Button signOut;
    public Button overview;
    public VBox overviewNode;
    public VBox overviewNodeClick;
    public Pane overviewPane;
    public AnchorPane getScene;
    public Pane mainPane;

    public AdminAccessController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        overview.setOnMouseClicked(event -> {
            //overviewPane.setVisible(true);
            try {
                borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/admin/Overview.fxml")));

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        // overviewClicked();
        systemReportsClicked();
        ordersClicked();
        repairsClicked();
        guaranteeClicked();
        usersClicked();
        permissionClicked();
        customersClicked();
        //signOutClicked();

    }

    @FXML
    private void signOutClicked() throws IOException {
        Stage stage = (Stage) getScene.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/admin/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        SetStageStyle.setStyle(scene, parent, stage);
        stage.setScene(scene);

    }

    private void customersClicked() {
        customers.setOnMouseClicked(event -> {
            try {
                borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/admin/Customer.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void permissionClicked() {
        permission.setOnMouseClicked(event -> {
            try {
                borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/admin/Permission.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void usersClicked() {
        users.setOnMouseClicked(event -> {
            try {
                borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/admin/Users.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void guaranteeClicked() {
        guarantee.setOnMouseClicked(event -> {
            try {
                borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/admin/Guarantee.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void repairsClicked() {
        repairs.setOnMouseClicked(event -> {
            try {
                borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/admin/Repairs.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void ordersClicked() {
        orders.setOnMouseClicked(event -> {
            try {
                borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/admin/Orders.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void systemReportsClicked() {
        systemReports.setOnMouseClicked(event -> {
            try {
                borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/admin/SystemReports.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void overviewClicked() throws IOException, NullPointerException {

        /*overview.setOnMouseClicked(event -> {
            //overviewPane.setVisible(true);
            try {
                borderPane.setCenter(FXMLLoader.load(getClass().getResource("/view/admin/Overview.fxml")));

            } catch (IOException e) {
                e.printStackTrace();
            }

        });*/
    }

}
