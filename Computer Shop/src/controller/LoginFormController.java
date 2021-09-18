package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LoginFormController implements Initializable {
    public JFXTextField txtUsrName;
    public JFXPasswordField txtPassword;
    public JFXButton signInButton;

    public void exit(ActionEvent actionEvent) {
        System.exit(1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signInButton.setDisable(true);
    }

    public void loginOnClick(MouseEvent event) throws IOException {
        if (txtUsrName.getText().equals("Admin") && txtPassword.getText().equals("1234")) {
            Stage stage = (Stage) txtUsrName.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("/view/admin/AdminAccess.fxml"));
            stage.setScene(new Scene(parent));
        } else if (txtUsrName.getText().equals("Cashier") && txtPassword.getText().equals("1234")) {
            Stage stage = (Stage) txtUsrName.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("/view/cashier/CashierAccess.fxml"));
            stage.setScene(new Scene(parent));
        } else if (txtUsrName.getText().equals("Inventory") && txtPassword.getText().equals("1234")) {
            Stage stage = (Stage) txtUsrName.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("/view/inventoryManager/InventoryManagerAccess.fxml"));
            stage.setScene(new Scene(parent));
        }
    }

    public void enableSignIn(KeyEvent keyEvent) {
        if (txtUsrName.getText().equalsIgnoreCase("Admin") && txtPassword.getText().equals("1234")) {
            signInButton.setDisable(false);
        }else if (txtUsrName.getText().equalsIgnoreCase("Cashier") && txtPassword.getText().equals("1234")){
            signInButton.setDisable(false);
        }else if (txtUsrName.getText().equalsIgnoreCase("Inventory") && txtPassword.getText().equals("1234")){
            signInButton.setDisable(false);
        } else {
            signInButton.setDisable(true);
        }
    }

}
