package controller;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.AddtoCartTM;
import model.ItemDetails;
import model.OrderPlaceTM;
import util.Dialog;
import util.SendMail;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {
    public VBox vbox;
    public ImageView imageView;
    public Label title;
    public ComboBox<String> itemComboBox;
    public ComboBox<String> customerComboBox;
    public Label price;
    public TextField qtyBuy;
    public Button addToCart;
    public Button placeOrder;
    public TextField qtyAvailable;
    public TableView<AddtoCartTM> tblAddToCart;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colOrderedQty;
    public TableColumn colTotal;
    public TableColumn colRemoveBtn;
    public StackPane stack;
    ObservableList<String> items = FXCollections.observableArrayList();
    ObservableList<String> customers = FXCollections.observableArrayList();
    Image image;
    ObservableList<AddtoCartTM> cart = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colOrderedQty.setCellValueFactory(new PropertyValueFactory<>("orderedQty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colRemoveBtn.setCellFactory(getCellFactory());

        addToCartFire();
        comboBoxes_init();
        itemComboBox_listener();
        placeOrder_init();
    }

    private void placeOrder_init() {
        placeOrder.setOnMouseClicked(event -> {
            if (!customerComboBox.getSelectionModel().isEmpty()) {

                ArrayList<ItemDetails> items = new ArrayList<>();

                double orderTotalCost = 0;

                for (AddtoCartTM temp :
                        cart) {
                    items.add(new ItemDetails(
                            temp.getItemCode(),
                            temp.getOrderedQty(),
                            0
                    ));

                    orderTotalCost += temp.getTotal();
                }


                try {
                    OrderPlaceTM order = new OrderPlaceTM(
                            getOrderId(),
                            new Date(),
                            customerComboBox.getValue(),
                            orderTotalCost,
                            items
                    );

                    Connection con = DbConnection.getInstance().getConnection();
                    con.setAutoCommit(false);

                    PreparedStatement stm =
                            con.prepareStatement("INSERT INTO Orders VALUES (?,?,?,?)");

                    stm.setObject(1, order.getOrderId());
                    stm.setObject(2, order.getCustomerId());
                    stm.setObject(3, order.getOrderDate());
                    stm.setObject(4, order.getTotalCost());

                    if (stm.executeUpdate() > 0) {
                        if (order_detailsInit(order.getOrderId(), order.getItems())) {
                            Dialog.showDialog("Order Placed Successfully", stack, "Done");
                            SendMail.sendMail(customerComboBox.getValue());
                            con.commit();
                        } else {
                            con.rollback();
                        }
                    } else {
                        con.rollback();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Dialog.showDialog("Please Select Customer", stack, "Close");
            }
        });
    }

    private boolean order_detailsInit(String orderId, ArrayList<ItemDetails> items) {
        for (ItemDetails temp :
                items) {
            try {
                PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                        "INSERT INTO `Order Detail` VALUES (?,?,?,?)");

                stm.setObject(1, temp.getItemCode());
                stm.setObject(2, orderId);
                stm.setObject(3, temp.getOrderedQty());
                stm.setObject(4, 0);

                if (stm.executeUpdate() > 0 && updateQty(temp)) {
                    return true;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    private boolean updateQty(ItemDetails temp) {
        try {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                    "UPDATE Item SET qtyOnHand = (qtyOnhand - " + temp.getOrderedQty() + ") WHERE code = ?");

            stm.setObject(1, temp.getItemCode());

            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    private int isExists(AddtoCartTM tm) {
        for (int i = 0; i < cart.size(); i++) {
            if (tm.getItemCode().equals(cart.get(i).getItemCode())) {
                return i;
            }
        }
        return -1;
    }


    private void itemComboBox_listener() {
        itemComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected");
            try {
                String s = itemComboBox.getSelectionModel().getSelectedItem();
                ResultSet rst =
                        DbConnection.getInstance().getConnection().prepareStatement(
                                "SELECT * FROM Item WHERE code = '" + s + "'").executeQuery();
                if (rst.next()) {
                    Blob blob = rst.getBlob(6);
                    InputStream is = blob.getBinaryStream();
                    image = new Image(is);
                    imageView.setImage(image);
                    title.setText(rst.getString(2));
                    qtyAvailable.setText(String.valueOf(rst.getInt(5)));
                    price.setText(String.valueOf(rst.getDouble(4)));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    private void comboBoxes_init() {
        try {
            ResultSet rst =
                    DbConnection.getInstance().
                            getConnection().prepareStatement("SELECT * FROM Item").executeQuery();

            while (rst.next()) {
                items.add(rst.getString(1));
            }

            ResultSet rst2 =
                    DbConnection.getInstance().
                            getConnection().prepareStatement("SELECT * FROM Customer").executeQuery();

            while (rst2.next()) {
                customers.add(rst2.getString(1));
            }

            itemComboBox.setItems(items);
            customerComboBox.setItems(customers);

            //itemComboBox_listener();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Callback<TableColumn<AddtoCartTM, String>, TableCell<AddtoCartTM, String>> getCellFactory() {
        Callback<TableColumn<AddtoCartTM, String>, TableCell<AddtoCartTM, String>> cellFactory
                = param -> {
            final TableCell<AddtoCartTM, String> cell = new TableCell<AddtoCartTM, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final JFXButton button = new JFXButton("Remove");

                        button.setStyle("-fx-border-radius: 10px; " +
                                "-fx-background-color: #ef5350; " +
                                "-fx-background-radius: 10px; " +
                                // "-fx-background-color: #2c3e50; " +
                                "-fx-border-color: #ef5350; " +
                                "-fx-border-width: 1px; " +
                                "-fx-text-fill: #ffffff");

                        button.setOnAction(event -> {
                            /*AddtoCartTM cart = getTableView().getItems().get(getIndex());*/
                            int index = getIndex();
                            //if
                            removeOnAction(index); //{
                            // tblAddToCart.getItems().clear();
                            /*    tblAddToCart.refresh();
                            }*/
                        });
                        setGraphic(button);
                        setText(null);
                    }
                }

                private void removeOnAction(int row) {
                    cart.remove(row);
                    tblAddToCart.refresh();
                }
            };
            return cell;
        };
        return cellFactory;
    }

    public void addToCartFire() {
        addToCart.setOnAction(event -> {

            if (!price.getText().isEmpty() && !qtyBuy.getText().isEmpty() && Integer.parseInt(qtyBuy.getText()) < Integer.parseInt(qtyAvailable.getText())) {
                Double ttl = Double.parseDouble(price.getText()) * Double.parseDouble(qtyBuy.getText());

                AddtoCartTM tm = new AddtoCartTM(
                        itemComboBox.getValue(),
                        title.getText(),
                        Double.parseDouble(price.getText()),
                        Integer.parseInt(qtyBuy.getText()),
                        ttl
                );

                int rowNumber = isExists(tm);

                if (rowNumber == -1) {
                    cart.add(tm);
                } else {
                    AddtoCartTM temp = cart.get(rowNumber);
                    Integer qty = temp.getOrderedQty() + Integer.parseInt(qtyBuy.getText());
                    Double ttlCost = temp.getTotal() + (Double.parseDouble(qtyBuy.getText()) * temp.getUnitPrice());

                    if (qty > Integer.parseInt(qtyAvailable.getText())) {
                        Dialog.showDialog("Invalid qty", stack, "Close");
                    } else {
                        cart.add(new AddtoCartTM(
                                temp.getItemCode(),
                                temp.getDescription(),
                                temp.getUnitPrice(),
                                qty,
                                ttlCost
                        ));
                        cart.remove(rowNumber);
                    }
                    tblAddToCart.setItems(cart);
                }

                tblAddToCart.setItems(cart);
            }
        });


    }

    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT o_id FROM Orders ORDER BY o_id DESC LIMIT 1"
                ).executeQuery();

        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                return "O-00" + tempId;
            } else if (tempId < 99) {
                return "O-0" + tempId;
            } else {
                return "O-" + tempId;
            }

        } else {
            return "O-001";
        }
    }

}
