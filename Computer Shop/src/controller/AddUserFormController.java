package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import util.Dialog;
import util.KeyRelease;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddUserFormController implements Initializable {

    public JFXTextField userId;
    public JFXTextField usrName;
    public JFXButton addButton;
    public JFXTextField usrAddress;
    public JFXTextField usrCity;
    public JFXDatePicker datePicker;
    public StackPane stack;
    Pattern id = Pattern.compile("^(U-)[0-9]{3,4}$");
    Pattern nameOrAddress = Pattern.compile("^(.|\\s)*[a-zA-Z]+(.|\\s)*$");
    Pattern city = Pattern.compile("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$");
    LinkedHashMap<TextField, Pattern> allValidation = new LinkedHashMap<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validate_init();
        addButton_init();
    }

    private void addButton_init() {
        addButton.setOnMouseClicked(event -> {
            try {
                PreparedStatement stm = DbConnection.getInstance().
                        getConnection().prepareStatement("INSERT INTO Users VALUES (?,?,?,?,?)");

                stm.setObject(1, userId.getText());
                stm.setObject(2, usrName.getText());
                stm.setObject(3, usrAddress.getText());
                stm.setObject(4, usrCity.getText());
                LocalDate localDate = datePicker.getValue();
                stm.setObject(5, localDate);
                if (stm.executeUpdate() > 0) {
                    Dialog.showDialog("User Added Successfully", stack, "Done");
                } else {
                    Dialog.showDialog("Try Again", stack, "Ok");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void validate_init() {
        allValidation.put(userId, id);
        allValidation.put(usrName, nameOrAddress);
        allValidation.put(usrAddress, nameOrAddress);
        allValidation.put(usrCity, city);
        addButton.setDisable(true);
    }

    public void release(KeyEvent keyEvent) {
        KeyRelease.btnReleaseOnAction(keyEvent, allValidation, addButton);
    }
}
