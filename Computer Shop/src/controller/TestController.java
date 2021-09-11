package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TestController implements Initializable {

    public VBox getItems;
    public ScrollPane scroll;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       Node[] nodes = new Node[10];
        for (int i = 0; i < nodes.length; i++){
            try {
                nodes[i] = FXMLLoader.load(getClass().getResource("/view/OverviewNode.fxml"));
                getItems.getChildren().addAll(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*try {
            nodes[0] = (Node) FXMLLoader.load(getClass().getResource("view/OverviewNode.fxml"));
            getItems.getChildren().add((javafx.scene.Node) nodes[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*try {
            Node node = FXMLLoader.load(getClass().getResource("/view/OverviewNode.fxml"));
            getItems.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
