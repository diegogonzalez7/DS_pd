package e1;

public class Cancelled implements Phase {
    private static final Cancelled instanciaCancelled = new Cancelled();

    Cancelled() {
    }

    public static Cancelled getInstance() {
        return instanciaCancelled;
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
        //cancela orden si 24h esta a true, vacia la lista,los dos bool a false y estado actual shopping cart
        if (!o.hAfterPayment) {
            o.Cart.clear();
            o.doneOrder = false;
            o.started = false;
            o.setOrderPhase(ShoppingCart.getInstance());
            o.log = o.log.concat("\n- Order " + o.getOrderNumber() + " : Cancelled Order --");
            o.log = o.log.concat("\nOrder " + o.getOrderNumber() + ": Shopping Phase");
        } else throw new IllegalStateException("The cancel time has ended");
    }

    @Override
    public void completeOrder(Order o) {
        throw new UnsupportedOperationException("This operation does not belong to this phase");
    }

    @Override
    public String screenInfo(Order o) {
        System.out.println("\nOrder Number : " + o.getOrderNumber() + "\nPhase : Cancelled Order");
        return "\nOrder Number : " + o.getOrderNumber() + "\nPhase : Cancelled Order";
    }
}
