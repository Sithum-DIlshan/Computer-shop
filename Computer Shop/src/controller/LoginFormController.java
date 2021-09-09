package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LoginFormController implements Initializable {
    public JFXTextField txtUsrName;
    public JFXPasswordField txtPassword;
    LinkedHashMap<JFXTextField, Pattern> txtFieldValidate = new LinkedHashMap();
    LinkedHashMap<JFXPasswordField, Pattern> passwordFieldValidate = new LinkedHashMap();

    public void exit(ActionEvent actionEvent) {
        System.exit(1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validate_init();
    }

    public void validate_data(KeyEvent keyEvent) {

    }

    private void validate_init() {

    }

}
