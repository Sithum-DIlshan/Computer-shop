package controller;

import com.jfoenix.controls.JFXButton;
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
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddCustomerFormController implements Initializable {

    public StackPane stack;
    public JFXTextField customerId;
    public JFXTextField customerName;
    public JFXButton addButton;
    public JFXTextField customerAddress;
    public JFXTextField customerContact;
    public JFXTextField customerCity;
    public JFXTextField customerEmail;
    LinkedHashMap<TextField, Pattern> allValidation = new LinkedHashMap<>();
    Pattern cId = Pattern.compile("^(C-)[0-9]{3,4}$");
    Pattern nameOraddress = Pattern.compile("^(.|\\s)*[a-zA-Z]+(.|\\s)*$");
    Pattern contact = Pattern.compile("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$");
    Pattern city = Pattern.compile("^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$");
    Pattern email = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validate_init();
        addButton_init();
    }

    private void addButton_init() {
        addButton.setOnMouseClicked(event -> {
            try {
                PreparedStatement stm = DbConnection.getInstance().
                        getConnection().prepareStatement("INSERT INTO Customer VALUES (?,?,?,?,?)");

                stm.setObject(1, customerId.getText());
                stm.setObject(2, customerName.getText());
                stm.setObject(3, customerAddress.getText());
                stm.setObject(4, customerContact.getText());
                stm.setObject(5, customerCity.getText());
                if (stm.executeUpdate() > 0) {
                    Dialog.showDialog("Customer Added Successfully", stack, "Done");
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
        allValidation.put(customerId, cId);
        allValidation.put(customerName, nameOraddress);
        allValidation.put(customerAddress, nameOraddress);
        allValidation.put(customerContact, contact);
        allValidation.put(customerCity, city);
        allValidation.put(customerEmail, email);
        addButton.setDisable(true);
    }

    public void release(KeyEvent keyEvent) {
        KeyRelease.btnReleaseOnAction(keyEvent, allValidation, addButton);
    }

}
