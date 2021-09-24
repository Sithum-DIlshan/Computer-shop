package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryManagerAccessFormController implements Initializable {
    public BorderPane border;
    public Button overview;
    public Button item;
    public Button signOut;
    public Pane pane;
    public VBox getItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        overviewClicked();
        itemClicked();
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

    private void itemClicked() {
        item.setOnMouseClicked(event -> {
            //overviewPane.setVisible(true);
            try {
                border.setCenter(FXMLLoader.load(getClass().getResource("/view/inventoryManager/Items.fxml")));
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
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }
}
