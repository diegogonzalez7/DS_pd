package e1;

import java.util.Random;
import java.util.HashMap;

public class Order {
    Phase OrderPhase = ShoppingCart.getInstance();
    HashMap<Integer,Integer> Cart = new HashMap<>();
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
        return OrderPhase;
    }

    public void setOrderPhase(Phase orderPhase) {
        OrderPhase = orderPhase;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    void addProducts(Product product, int quantity) {
        this.OrderPhase.addProducts(this, product, quantity);
    }

    void modifyQuantity(Product product, int quantity) {
        this.OrderPhase.modifyQuantity(this, product, quantity);
    }

    void deleteProduct(int product_id) {
        this.OrderPhase.deleteProduct(this, product_id);
    }

    void nextState() {
        this.OrderPhase.nextState(this);
    }

    void cancelOrder() {
        this.OrderPhase.cancelOrder(this);
    }

    void completeOrder() {
        this.OrderPhase.completeOrder(this);
    }

    public String printLog() {
        System.out.println(log);
        return log;
    }

    public String screenInfo(){return this.OrderPhase.screenInfo(this);}
}
