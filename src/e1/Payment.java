package e1;

import java.time.*;

public class Payment implements Phase {
    private static final Payment instanciaPayment = new Payment();

    Payment() {
    }

    public static Payment getInstance() {
        return instanciaPayment;
    }

    @Override
    public void addProducts(Order o, Product product, int quantity) {
        throw new UnsupportedOperationException("You cannot modify the order because is already done");
    }

    @Override
    public void modifyQuantity(Order o, Product product, int quantity) {
        throw new UnsupportedOperationException("You cannot modify the order because is already done");
    }

    @Override
    public void deleteProduct(Order o, int productId) {
        throw new UnsupportedOperationException("You cannot modify the order because is already done");
    }

    @Override
    public void nextState(Order o) {
        throw new UnsupportedOperationException("There are not more phases after");
    }

    @Override
    public void cancelOrder(Order o) {
        //Doble confirmacion para cancelar orden
        o.hAfterPayment = false;
        o.setOrderPhase(Cancelled.getInstance());
        o.log = o.log.concat("\nOrder " + o.getOrderNumber() + " : Cancellation Phase");
    }

    @Override
    public void completeOrder(Order o) {
        o.hAfterPayment = true;
        o.setOrderPhase(Completed.getInstance());
        o.log = o.log.concat("\nOrder " + o.getOrderNumber() + " : Completition Phase");
    }

    @Override
    public String screenInfo(Order o) {
        LocalTime time = LocalTime.now();
        System.out.println("\nOrder Number : " + o.getOrderNumber() + "\nPhase : Paid Order : " + o.Cart.size() +
                " products -- date " + LocalDate.now() + " " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());
        return "\nOrder Number : " + o.getOrderNumber() + "\nPhase : Paid Order : " + o.Cart.size() +
                " products -- date " + LocalDate.now() + " " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond();
    }
}
