package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CashierOverviewController implements Initializable {
    public VBox vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/admin/nodes/OverviewNode.fxml"));
            vbox.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
