package e1;

public class Checkout implements Phase {
    private static final Checkout instanciaCheckout = new Checkout();

    Checkout() {
    }

    public static Checkout getInstance() {
        return instanciaCheckout;
    }

    @Override
    public void add_products(Order O, Product product, int quantity) {
        if (quantity <= product.getStock())
            O.Cart.putIfAbsent(product.getProduct_id(), quantity);
        throw new IllegalArgumentException("Invalid value for quantity, must be minor/equal than the stock of the product");
    }

    @Override
    public void modify_quantity(Order O, Product product, int quantity) {
        if (quantity <= product.getStock())
            O.Cart.replace(product.getProduct_id(), quantity);
        else
            throw new IllegalArgumentException("Invalid value for quantity, must be minor/equal than the stock of the product");
    }

    @Override
    public void delete_product(Order O, int product_id) {
        O.Cart.remove(product_id);
    }

    @Override
    public void next_state(Order O) {
        O.setOrderPhase(Payment.getInstance());
    }

    @Override
    public void cancel_order(Order O) {
        //Vuelta a la fase de elección de productos para el carrito.
        O.setOrderPhase(ShoppingCart.getInstance());
    }

    @Override
    public void complete_order(Order O) {
        throw new UnsupportedOperationException("Cannot complete the order without the payment");
    }
}
