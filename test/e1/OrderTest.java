package e1;

import org.junit.jupiter.api.Test;

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
        o1.next_state();
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
        assertThrows(IllegalArgumentException.class, () -> o.add_products(cebolla, 80));
        o.add_products(cebolla, 3);
        assertTrue(o.Cart.size() == 1 && o.Cart.get(cebolla.getProduct_id()) == 3);
        o.add_products(cebolla, 7);
        o.add_products(cebolla, 8);
        assertTrue(o.Cart.size() == 1 && o.Cart.get(cebolla.getProduct_id()) == 3);
        o.add_products(tenedor, 2);
        assertTrue(o.Cart.size() == 2 && o.Cart.get(tenedor.getProduct_id()) == 2);
        o.setOrderPhase(Checkout.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.add_products(cebolla, 0));
        o.setOrderPhase(Payment.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.add_products(cebolla, 0));
        o.setOrderPhase(Cancelled.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.add_products(cebolla, 0));
        o.setOrderPhase(Completed.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.add_products(cebolla, 0));
    }

    @Test
    void testModify_quantity() {
        o.add_products(cebolla, 7);
        o.add_products(tenedor, 2);
        o.modify_quantity(cebolla, 5);
        assertEquals(o.Cart.get(cebolla.getProduct_id()), 5);
        assertThrows(IllegalArgumentException.class, () -> o.modify_quantity(cebolla, 47));
        o.modify_quantity(tenedor, 0);
        assertEquals(1, o.Cart.size());

        o.setOrderPhase(Checkout.getInstance());
        o.modify_quantity(cebolla, 7);
        assertEquals(o.Cart.get(cebolla.getProduct_id()), 7);
        assertThrows(IllegalArgumentException.class, () -> o.modify_quantity(cebolla, 47));
        o.modify_quantity(tenedor, 0);
        assertEquals(1, o.Cart.size());

        o.setOrderPhase(Payment.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.modify_quantity(cebolla, 0));
        o.setOrderPhase(Cancelled.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.modify_quantity(cebolla, 0));
        o.setOrderPhase(Completed.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.modify_quantity(cebolla, 0));
    }

    @Test
    void testDelete_product() {
        o.add_products(manzana, 4);
        o.add_products(cebolla, 2);
        o.delete_product(cebolla.getProduct_id());
        assertEquals(1, o.Cart.size());
        o.setOrderPhase(Checkout.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.delete_product(111));
        o.setOrderPhase(Payment.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.delete_product(111));
        o.setOrderPhase(Cancelled.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.delete_product(cebolla.getProduct_id()));
        o.setOrderPhase(Completed.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.delete_product(cebolla.getProduct_id()));
    }

    @Test
    void testNext_state() {
        o.next_state();
        assertEquals(o.getOrderPhase().getClass(), Checkout.class);
        o.next_state();
        assertEquals(o.getOrderPhase().getClass(), Payment.class);
        assertThrows(UnsupportedOperationException.class, o::next_state);
        this.o.setOrderPhase(Cancelled.getInstance());
        assertThrows(UnsupportedOperationException.class, o::next_state);
        this.o.setOrderPhase(Completed.getInstance());
        assertThrows(UnsupportedOperationException.class, o::next_state);
    }

    @Test
    void testCancel_order() {
        assertThrows(UnsupportedOperationException.class, () -> o.cancel_order());
        o.setOrderPhase(Checkout.getInstance());
        o.cancel_order();
        assertEquals(o.getOrderPhase().getClass(), ShoppingCart.class);
        o.setOrderPhase(Payment.getInstance());
        o.cancel_order();
        assertEquals(o.getOrderPhase().getClass(), Cancelled.class);
        o.h_after_payment=true;
        assertThrows(IllegalStateException.class,() -> o.cancel_order());
        o.h_after_payment=false;
        o.cancel_order();
        assertEquals(o.getOrderPhase().getClass(), ShoppingCart.class);
        o.setOrderPhase(Payment.getInstance());
        o.complete_order();
        assertThrows(UnsupportedOperationException.class, () -> o.cancel_order());
    }

    @Test
    void testComplete_order() {
        assertThrows(UnsupportedOperationException.class, () -> o.complete_order());
        o.setOrderPhase(Checkout.getInstance());
        assertThrows(UnsupportedOperationException.class, () -> o.complete_order());
        o.setOrderPhase(Payment.getInstance());
        o.complete_order();
        assertEquals(o.getOrderPhase().getClass(), Completed.class);
        o.h_after_payment=false;
        assertThrows(IllegalStateException.class,() -> o.complete_order());
        o.h_after_payment=true;
        o.complete_order();
        assertTrue(o.done_order);
        o.setOrderPhase(Payment.getInstance());
        o.cancel_order();
        assertThrows(UnsupportedOperationException.class, () -> o.complete_order());
    }

    @Test
    void testPrintLog() {
        o.add_products(cebolla, 10);
        o.add_products(manzana, 2);
        o.add_products(pollo, 3);
        o.add_products(tenedor, 4);
        o.delete_product(tenedor.getProduct_id());
        o.add_products(tenedor, 5);
        o.next_state();
        o.modify_quantity(cebolla, 1);
        o.next_state();
        o.printLog();
    }

    @Test
    void testScreenInfo() {
        o.add_products(cebolla, 7);
        o.add_products(manzana, 3);
        o.add_products(pollo, 8);
        o.add_products(tenedor, 8);
        o.screenInfo();
        o.screenInfo();
        o.setOrderPhase(Checkout.getInstance());
        o.screenInfo();
        o.setOrderPhase(Payment.getInstance());
        o.screenInfo();
        o.setOrderPhase(Cancelled.getInstance());
        o.screenInfo();
        o.setOrderPhase(Completed.getInstance());
        o.screenInfo();
    }
}