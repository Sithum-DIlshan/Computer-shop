package util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class Dialog {
    public static void showDialog(String content, StackPane stackPane, String btnText) {
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setBody(new Text(content));
        JFXButton close = new JFXButton("Close");
        close.setButtonType(JFXButton.ButtonType.FLAT);
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(stackPane, dialogContent, JFXDialog.DialogTransition.TOP);
        close.setOnMouseClicked(event1 -> {
            dialog.close();
        });

        dialog.show();

    }
}
