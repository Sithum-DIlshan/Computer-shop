package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.Dialog;
import util.KeyRelease;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddItemFormController implements Initializable {

    public StackPane stack;
    public JFXButton addButton;
    public JFXButton selectBtn;
    public Label path;
    public ImageView imageView;
    public JFXTextField txtItemId;
    public JFXTextField itemDescription;
    public JFXTextField itemBrand;
    public JFXTextField itemUnitPrice;
    public JFXTextField itemQtyOnHand;
    private Image image;
    Pattern itemId = Pattern.compile("^(I-)[0-9]{3,4}$");
    Pattern brandOrDescription = Pattern.compile("^(.|\\s)*[a-zA-Z]+(.|\\s)*$");
    Pattern price = Pattern.compile("^[1-9][0-9]*([.][0-9]{1,2})?$");
    Pattern qty = Pattern.compile("^[1-9][0-9]*?$");
    LinkedHashMap<TextField, Pattern> allValidation = new LinkedHashMap<>();
    private FileChooser fileChooser;
    private File file;
    private FileInputStream fis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton_init();
        selectBtn_init();
        validate_init();
    }

    private void validate_init() {
        allValidation.put(txtItemId, itemId);
        allValidation.put(itemDescription, brandOrDescription);
        allValidation.put(itemBrand, brandOrDescription);
        allValidation.put(itemUnitPrice, price);
        allValidation.put(itemQtyOnHand, qty);
        addButton.setDisable(true);
    }

    private void addButton_init() {
        addButton.setOnMouseClicked(event -> {
            try {
                PreparedStatement stm = DbConnection.getInstance().
                        getConnection().prepareStatement("INSERT INTO Item VALUES (?,?,?,?,?,?)");

                stm.setObject(1, txtItemId.getText());
                stm.setObject(2, itemDescription.getText());
                stm.setObject(3, itemBrand.getText());
                stm.setObject(4, itemUnitPrice.getText());
                stm.setObject(5, itemQtyOnHand.getText());
                fis = new FileInputStream(file);
                stm.setBinaryStream(6, (InputStream) fis, (int) file.length());
                if (stm.executeUpdate() > 0) {
                    util.Dialog.showDialog("Item Added Successfully", stack, "Done");
                } else {
                    Dialog.showDialog("Try Again", stack, "Ok");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void selectBtn_init() {
        selectBtn.setOnAction(event -> {
            fileChooser = new FileChooser();
            fileChooser.setTitle("Choose file");
            Stage stage = (Stage) stack.getScene().getWindow();

            file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                Desktop desktop = Desktop.getDesktop();
                //   path.setText(file.getAbsolutePath());
                image = new Image(file.toURI().toString(), 400, 400, true, true);
                imageView.setImage(image);
            }
        });
    }

    public void release(KeyEvent keyEvent) {
        KeyRelease.btnReleaseOnAction(keyEvent, allValidation, addButton);
    }

}
