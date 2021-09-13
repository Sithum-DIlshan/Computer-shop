package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CashierAccessFormController implements Initializable {
    public BorderPane border;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/admin/Orders.fxml"));
            border.setCenter(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
