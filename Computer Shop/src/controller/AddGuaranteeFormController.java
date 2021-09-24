package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import util.Dialog;
import util.KeyRelease;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddGuaranteeFormController implements Initializable {

    public JFXTextField guaranteeId;
    public JFXTextField guaranteePeriod;
    public ComboBox<String> itemComboBox;
    public JFXButton addButton;
    public StackPane stack;
    ObservableList<String> obs = FXCollections.observableArrayList();
    LinkedHashMap<TextField, Pattern> allValidations = new LinkedHashMap<>();

    Pattern id = Pattern.compile("^(G-)[0-9]{3,4}$");
    Pattern gPeriod = Pattern.compile("^[1-9][0-9]*?$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadComboBoxData();
        validate_init();
        saveGuarantee();
    }

    private void saveGuarantee() {
        addButton.setOnMouseClicked(event -> {
            try {
                PreparedStatement stm = DbConnection.getInstance().
                        getConnection().prepareStatement("INSERT INTO Guarantee VALUES (?,?,?)");

                stm.setObject(1, guaranteeId.getText());
                stm.setObject(2, Integer.parseInt(guaranteePeriod.getText()));
                stm.setObject(3, itemComboBox.getValue());

                if (itemHaveNotGuarantee()) {
                    if (stm.executeUpdate() > 0) {
               /*     String content = "Guarantee added successfully...";
                    JFXDialogLayout dialogContent = new JFXDialogLayout();
                    dialogContent.setBody(new Text(content));
                    JFXButton close = new JFXButton("Close");
                    close.setButtonType(JFXButton.ButtonType.FLAT);
                    dialogContent.setActions(close);
                    JFXDialog dialog = new JFXDialog(stack, dialogContent, JFXDialog.DialogTransition.TOP);
                    close.setOnMouseClicked(event1 -> {
                        dialog.close();
                    });

                    dialog.show();

               */
                        Dialog.showDialog("Guarantee added successfully...", stack, "Done");
                    } else {
                        Dialog.showDialog("Guarantee not added", stack, "Close");
                    }
                }else {
                    Dialog.showDialog("Item already has Guarantee", stack, "Close");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        });
    }

    private boolean itemHaveNotGuarantee() {
        try {
            ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(
                    "Select * FROM Guarantee").executeQuery();

            while (rst.next()) {
                if (rst.getString(3).equals(itemComboBox.getValue())) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void validate_init() {
        allValidations.put(guaranteeId, id);
        allValidations.put(guaranteePeriod, gPeriod);
        addButton.setDisable(true);
    }

    private void loadComboBoxData() {
        try {
            ResultSet rst = DbConnection.getInstance().
                    getConnection().prepareStatement(
                            "SELECT * FROM Item").executeQuery();

            while (rst.next()) {
                obs.add(rst.getString(1));
            }

            itemComboBox.setItems(obs);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void release(KeyEvent event) {
        KeyRelease.btnReleaseOnAction(event, allValidations, addButton);

    }
}
