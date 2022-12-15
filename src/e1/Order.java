package e1;

import java.util.Random;
import java.util.HashMap;

public class Order {
    Phase orderPhase = ShoppingCart.getInstance();
    HashMap<Integer,Integer> cart = new HashMap<>();
    boolean hAfterPayment = false;
    boolean doneOrder = false;
    boolean started = false;
    int orderNumber;
    String log;

    Order() {
        Random rmdMethod = new Random();
        this.orderNumber = rmdMethod.nextInt(1000);
        this.log = "Order " + orderNumber + ": Shopping Phase";
    }

    public Phase getOrderPhase() {
        return orderPhase;
    }

    public void setOrderPhase(Phase orderPhase) {
        this.orderPhase = orderPhase;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    void addProducts(Product product, int quantity) {
        this.orderPhase.addProducts(this, product, quantity);
    }

    void modifyQuantity(Product product, int quantity) {
        this.orderPhase.modifyQuantity(this, product, quantity);
    }

    void deleteProduct(int product_id) {
        this.orderPhase.deleteProduct(this, product_id);
    }

    void nextState() {
        this.orderPhase.nextState(this);
    }

    void cancelOrder() {
        this.orderPhase.cancelOrder(this);
    }

    void completeOrder() {
        this.orderPhase.completeOrder(this);
    }

    public String printLog() {
        System.out.println(log);
        return log;
    }

    public String screenInfo(){return this.orderPhase.screenInfo(this);}
}
