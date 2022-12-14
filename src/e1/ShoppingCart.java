package e1;

public class ShoppingCart implements Phase {
    private static final ShoppingCart instanciaShoppingCart = new ShoppingCart();

    ShoppingCart() {
    }

    public static ShoppingCart getInstance() {
        return instanciaShoppingCart;
    }

    @Override
    public void add_products(Order O, Product product, int quantity) {
        if (quantity <= product.getStock())
            O.Cart.putIfAbsent(product.getProduct_id(), quantity);
        else throw new IllegalArgumentException("Invalid value for quantity, must be minor/equal than the stock of the product");
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
        O.setOrderPhase(Checkout.getInstance());
    }

    @Override
    public void cancel_order(Order O) {
        throw new UnsupportedOperationException("Cannot cancel an uncompleted order");
    }

    @Override
    public void complete_order(Order O) {
        throw new UnsupportedOperationException("Cannot complete the order without the payment");
    }


}
