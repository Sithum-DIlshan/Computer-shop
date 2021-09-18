package model;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;

import java.net.MalformedURLException;
import java.util.Date;

public class OrdersTM {
    private String orderId;
    private String customerId;
    private Date orderDate;
    private double orderCost;
 //   private JFXButton updateButton/* = new JFXButton("Update")*/;

    public OrdersTM(String orderId, String customerId, Date orderDate, double orderCost) throws MalformedURLException {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderCost = orderCost;
       // this.updateButton = jfxButton;
        // this.updateButton = new Button("Update");
      /*  updateButton.setStyle("    -fx-border-radius: 10px;\n" +
                "    -fx-background-radius: 10px;\n" +
                "    -fx-background-color: #2c3e50;\n" +
                "    -fx-border-color: #039be5;\n" +
                "    -fx-border-width: 1px;\n" +
                "    -fx-text-fill: #bdc3c7");
        */
        /*updateButton.getStylesheets().add(new File("/home/sithum/Documents/GDSE58/Final Coursework/Final-CourseWork/Computer Shop/src/style/update.css").toURI().toURL().toExternalForm());*/
  /*      updateButton.setStyle("\n" +
                "    -fx-border-radius: 10px;\n" +
                "-fx-background-color: #039be5;" +
                "    -fx-background-radius: 10px;\n" +
                //  "    -fx-background-color: #2c3e50;\n" +
                "    -fx-border-color: #039be5;\n" +
                "    -fx-border-width: 1px;\n" +
                "    -fx-text-fill: #ffffff");
  */  }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(double orderCost) {
        this.orderCost = orderCost;
    }


    @Override
    public String toString() {
        return "OrdersTM{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderCost='" + orderCost + '\'' +
                '}';
    }
}
