package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemsController implements Initializable {
    public VBox vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/inventoryManager/nodes/ItemNode.fxml"));
            vbox.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}