package e1;

import java.util.Random;
import java.util.HashMap;

public class Order {
    Phase OrderPhase = ShoppingCart.getInstance();
    HashMap<Integer,Integer> Cart = new HashMap<>();
    boolean h_after_payment = false;
    boolean done_order = false;
    boolean started = false;
    int order_number;
    String Log;

    Order() {
        Random rmd_method = new Random();
        this.order_number = rmd_method.nextInt(1000);
        Log = "Order " + order_number + ": Shopping Phase";
    }

    public Phase getOrderPhase() {
        return OrderPhase;
    }

    public void setOrderPhase(Phase orderPhase) {
        OrderPhase = orderPhase;
    }

    public int getOrder_number() {
        return order_number;
    }

    void add_products(Product product, int quantity) {
        this.OrderPhase.add_products(this, product, quantity);
    }

    void modify_quantity(Product product, int quantity) {
        this.OrderPhase.modify_quantity(this, product, quantity);
    }

    void delete_product(int product_id) {
        this.OrderPhase.delete_product(this, product_id);
    }

    void next_state() {
        this.OrderPhase.next_state(this);
    }

    void cancel_order() {
        this.OrderPhase.cancel_order(this);
    }

    void complete_order() {
        this.OrderPhase.complete_order(this);
    }

    public void printLog() {
        System.out.println(Log);
    }

    public void screenInfo(){this.OrderPhase.screenInfo(this);this.started=true;}
}
