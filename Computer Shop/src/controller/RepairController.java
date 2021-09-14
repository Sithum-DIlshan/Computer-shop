package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RepairController implements Initializable {
    @FXML
    Pane pane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/admin/nodes/RepairNode.fxml"));
            pane.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
