package util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class KeyRelease {

    public static void btnReleaseOnAction(KeyEvent keyEvent, LinkedHashMap<TextField, Pattern> allValidations, JFXButton addBtn) {
        Object response = ValidationUtil.validateFields(allValidations, addBtn);

        if (response instanceof Boolean) {
            addBtn.setDisable(false);
        }

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField txtField = (TextField) response;
                txtField.requestFocus();
            }
        }
    }

}
