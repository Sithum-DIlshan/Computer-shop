package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {
    public Pane orderPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/nodes/OrderNode.fxml"));
            orderPane.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
