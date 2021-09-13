package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminAccessController implements Initializable {

    public VBox getItems;
    public ScrollPane scroll;
    public Pane pane;
    public BorderPane border;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       Node[] nodes = new Node[10];
  /*     for (int i = 0; i < nodes.length; i++){
            try {
                nodes[i] = FXMLLoader.load(getClass().getResource("/view/OverviewNode.fxml"));
                getItems.getChildren().addAll(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        /*try {
            nodes[0] = FXMLLoader.load(getClass().getResource("/view/SystemReports.fxml"));
            getItems.getChildren().add(nodes[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*try {
            Node node = FXMLLoader.load(getClass().getResource("/view/OverviewNode.fxml"));
            getItems.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            nodes[0] = FXMLLoader.load(getClass().getResource("/view/admin/Orders.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        border.setCenter(nodes[0]);
    }
}
