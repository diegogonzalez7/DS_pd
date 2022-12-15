package e1;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    Product cebolla = new Product(10, 111);
    Product manzana = new Product(12, 222);
    Product pollo = new Product(8, 333);
    Product tenedor = new Product(8, 444);
    Order o = new Order();

    @Test
    void testGetOrderPhase() {
        Order o1 = new Order();
        assertEquals(o1.getOrderPhase().getClass(), ShoppingCart.class);
        o1.nextState();
        assertEquals(o1.getOrderPhase().getClass(), Checkout.class);
    }

    @Test
    void testSetOrderPhase() {
        Order o1 = new Order();
        o1.setOrderPhase(Completed.getInstance());
        assertEquals(o1.getOrderPhase().getClass(), Completed.class);
    }

    @Test
    void testAdd_products() {
        //Añade un producto con una cantidad invalida
        assertThrows(IllegalArgumentException.class, () -> o.addProducts(cebolla, 80));
        o.addProducts(cebolla, 3);
        assertTrue(o.cart.size() == 1 && o.cart.get(cebolla.getProductId()) == 3);
        o.addProducts(cebolla, 7);
        o.addProducts(cebolla, 8);
        assertTrue(o.cart.size() == 1 && o.cart.get(cebolla.getProductId()) == 3);
        o.addProducts(tenedor, 2);
        assertTrue(o.cart.size() == 2 && o.cart.get(tenedor.getProductId()) == 2);
        o.setOrderPhase(Checkout.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.addProducts(cebolla, 0));
        o.setOrderPhase(Payment.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.addProducts(cebolla, 0));
        o.setOrderPhase(Cancelled.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.addProducts(cebolla, 0));
        o.setOrderPhase(Completed.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.addProducts(cebolla, 0));
    }

    @Test
    void testModify_quantity() {
        o.addProducts(cebolla, 7);
        o.addProducts(tenedor, 2);
        o.modifyQuantity(cebolla, 5);
        assertEquals(o.cart.get(cebolla.getProductId()), 5);
        assertThrows(IllegalArgumentException.class, () -> o.modifyQuantity(cebolla, 47));
        o.modifyQuantity(tenedor, 0);
        assertEquals(1, o.cart.size());

        o.setOrderPhase(Checkout.getInstance());
        o.modifyQuantity(cebolla, 7);
        assertEquals(o.cart.get(cebolla.getProductId()), 7);
        assertThrows(IllegalArgumentException.class, () -> o.modifyQuantity(cebolla, 47));
        o.modifyQuantity(tenedor, 0);
        assertEquals(1, o.cart.size());

        o.setOrderPhase(Payment.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.modifyQuantity(cebolla, 0));
        o.setOrderPhase(Cancelled.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.modifyQuantity(cebolla, 0));
        o.setOrderPhase(Completed.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.modifyQuantity(cebolla, 0));
    }

    @Test
    void testDelete_product() {
        o.addProducts(manzana, 4);
        o.addProducts(cebolla, 2);
        o.deleteProduct(cebolla.getProductId());
        assertEquals(1, o.cart.size());
        o.setOrderPhase(Checkout.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.deleteProduct(111));
        o.setOrderPhase(Payment.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.deleteProduct(111));
        o.setOrderPhase(Cancelled.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.deleteProduct(cebolla.getProductId()));
        o.setOrderPhase(Completed.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.deleteProduct(cebolla.getProductId()));
    }

    @Test
    void testNext_state() {
        o.nextState();
        assertEquals(o.getOrderPhase().getClass(), Checkout.class);
        o.nextState();
        assertEquals(o.getOrderPhase().getClass(), Payment.class);
        assertThrows(UnsupportedOperationException.class, o::nextState);
        this.o.setOrderPhase(Cancelled.getInstance());
        assertThrows(UnsupportedOperationException.class, o::nextState);
        this.o.setOrderPhase(Completed.getInstance());
        assertThrows(UnsupportedOperationException.class, o::nextState);
    }

    @Test
    void testCancel_order() {
        assertThrows(UnsupportedOperationException.class, () -> o.cancelOrder());
        o.setOrderPhase(Checkout.getInstance());
        o.cancelOrder();
        assertEquals(o.getOrderPhase().getClass(), ShoppingCart.class);
        o.setOrderPhase(Payment.getInstance());
        o.cancelOrder();
        assertEquals(o.getOrderPhase().getClass(), Cancelled.class);
        o.hAfterPayment =true;
        assertThrows(IllegalStateException.class,() -> o.cancelOrder());
        o.hAfterPayment =false;
        o.cancelOrder();
        assertEquals(o.getOrderPhase().getClass(), ShoppingCart.class);
        o.setOrderPhase(Payment.getInstance());
        o.completeOrder();
        assertThrows(UnsupportedOperationException.class, () -> o.cancelOrder());
    }

    @Test
    void testComplete_order() {
        assertThrows(UnsupportedOperationException.class, () -> o.completeOrder());
        o.setOrderPhase(Checkout.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.completeOrder());
        o.setOrderPhase(Payment.getInstance());
        o.completeOrder();
        assertEquals(o.getOrderPhase().getClass(), Completed.class);
        o.hAfterPayment =false;
        assertThrows(IllegalStateException.class,() -> o.completeOrder());
        o.hAfterPayment =true;
        o.completeOrder();
        assertTrue(o.doneOrder);
        o.setOrderPhase(Payment.getInstance());
        o.cancelOrder();
        assertThrows(UnsupportedOperationException.class, () -> o.completeOrder());
    }

    @Test
    void testPrintLog() {
        o.addProducts(cebolla, 10);
        o.addProducts(manzana, 2);
        o.addProducts(pollo, 3);
        o.addProducts(tenedor, 4);
        o.deleteProduct(tenedor.getProductId());
        o.addProducts(tenedor, 5);
        o.nextState();
        o.modifyQuantity(cebolla, 1);
        o.nextState();
        assertEquals(o.printLog(),"Order " + o.getOrderNumber() + ": Shopping Phase" + "\n"+
                "- Add : Item : 111- Quantity : 10 -> Shopping Cart -- Products : 1\n" +
                "- Add : Item : 222- Quantity : 2 -> Shopping Cart -- Products : 2\n" +
                "- Add : Item : 333- Quantity : 3 -> Shopping Cart -- Products : 3\n" +
                "- Add : Item : 444- Quantity : 4 -> Shopping Cart -- Products : 4\n" +
                "- Remove : Item : 444 -> Shopping Cart -- Products : 3\n" +
                "- Add : Item : 444- Quantity : 5 -> Shopping Cart -- Products : 4\n" +
                "Order "+o.getOrderNumber()+" : Check Out Phase\n" +
                "- Modify : Item : 111- Quantity : 1 -> CheckOut Order -- Products : 4\n" +
                "Order "+ o.getOrderNumber()+" : Payment Phase");

    }

    @Test
    void testScreenInfo() {
        o.addProducts(cebolla, 7);
        o.addProducts(manzana, 3);
        o.addProducts(pollo, 8);
        o.addProducts(tenedor, 8);
        assertEquals(o.screenInfo(),"\nOrder Number : " + o.getOrderNumber() + "\nPhase : Shopping -- Welcome to online shop");
        assertEquals(o.screenInfo(),"\nOrder Number : " + o.getOrderNumber() + "\nPhase : Shopping -- " + o.cart.size() + " products");
        o.setOrderPhase(Checkout.getInstance());
        assertEquals(o.screenInfo(),"\nOrder Number : " + o.getOrderNumber() + "\nPhase : Check Out -- " + o.cart.size() + " products");
        o.setOrderPhase(Payment.getInstance());
        LocalDateTime time = LocalDateTime.now();
        assertEquals(o.screenInfo(),"\nOrder Number : " + o.getOrderNumber() + "\nPhase : Paid Order : " + o.cart.size() +
                " products -- date " + LocalDate.now() + " " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());
        o.setOrderPhase(Cancelled.getInstance());
        assertEquals(o.screenInfo(),"\nOrder Number : " + o.getOrderNumber() + "\nPhase : Cancelled Order");
        o.setOrderPhase(Completed.getInstance());
        assertEquals(o.screenInfo(),"\nOrder Number : " + o.getOrderNumber() + "\nPhase : Completed Order : " + o.cart.size() + " products");
    }
}