package e1;

public class Cancelled implements Phase {
    private static final Cancelled instanciaCancelled = new Cancelled();

    Cancelled() {
    }

    public static Cancelled getInstance() {
        return instanciaCancelled;
    }

    @Override
    public void add_products(Order O, Product product, int quantity) {
        throw new UnsupportedOperationException("You cannot modify the order because is already done");
    }

    @Override
    public void modify_quantity(Order O, Product product, int quantity) {
        throw new UnsupportedOperationException("You cannot modify the order because is already done");
    }

    @Override
    public void delete_product(Order O, int product_id) {
        throw new UnsupportedOperationException("You cannot modify the order because is already done");
    }

    @Override
    public void next_state(Order O) {
        throw new UnsupportedOperationException("There are not more phases after");
    }

    @Override
    public void cancel_order(Order O) {
        //cancela orden si 24h esta a true, vacia la lista,los dos bool a false y estado actual shopping cart
        if (O.h_after_payment) {
            O.Cart.clear();
            O.h_after_payment = false;
            O.done_order = false;
            O.setOrderPhase(ShoppingCart.getInstance());
        } else throw new IllegalStateException("The cancel time has ended");
    }

    @Override
    public void complete_order(Order O) {
        throw new UnsupportedOperationException("This operation does not belong to this phase");
    }
}
