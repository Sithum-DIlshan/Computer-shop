package util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static Object validateFields(LinkedHashMap<TextField, Pattern> allValidations, JFXButton addButton) {
        for (TextField field :
                allValidations.keySet()) {
            Pattern pattern = allValidations.get(field);
            if (!pattern.matcher(field.getText()).matches()) {
                if (!field.getText().isEmpty()) {
                    field.setStyle("-jfx-focus-color: #ff0000;-fx-prompt-text-fill: #ff0000");
                }
                addButton.setDisable(true);
                return field;
            }
            field.setStyle("-jfx-focus-color: #2dd000; -fx-prompt-text-fill: #2dd000");

        }
        return true;
    }

}

