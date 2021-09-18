package controller;

import javafx.scene.control.Label;
import model.OverviewTM;

public class NodesController {
    public Label companyName;
    public Label customerId;
    public Label orderId;
    public Label qty;
    public Label orderDate;
    public OverviewTM overviewTM;

    /*
            public NodesController(*/
/*Order order*//*
) {
       */
/* companyName.setText("order.getCompanyName()");
        customerId.setText("order.getCustId()");
        orderId.setText("order.getOrderId()");
        qty.setText("order.getQty()");
        orderDate.setText("String.valueOf(order.getOrderDate())");*//*

    }
*/
    public void initialize(){
/*
        companyName.setText(order.getCompanyName());
        customerId.setText(order.getCustId());
        orderId.setText(order.getOrderId());
        qty.setText(order.getQty());
        orderDate.setText("2012-10-19");
*/
    }



    public void loadNodes(OverviewTM overviewTM) {
        companyName.setText("order.getCompanyName()");
        customerId.setText("order.getCustId()");
        orderId.setText("order.getOrderId()");
        qty.setText("order.getQty()");
        orderDate.setText("2012-10-19");
    }
}
