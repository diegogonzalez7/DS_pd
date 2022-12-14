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
        if (quantity <= product.getStock()) {
            O.Cart.putIfAbsent(product.getProduct_id(), quantity);
            O.Log = O.Log.concat("\n- Add : Item : " + product.getProduct_id() + "- Quantity : " + quantity + " -> Shopping Cart -- Products : " + O.Cart.size());
        } else
            throw new IllegalArgumentException("Invalid value for quantity, must be minor/equal than the stock of the product");
    }

    @Override
    public void modify_quantity(Order O, Product product, int quantity) {
        if (quantity <= product.getStock()) {
            if (quantity == 0) O.Cart.remove(product.getProduct_id());
            else
                O.Cart.replace(product.getProduct_id(), quantity);
            O.Log = O.Log.concat("\n- Modify : Item : " + product.getProduct_id() + "- Quantity : " + quantity + " -> Shopping Cart -- Products : " + O.Cart.size());
        } else
            throw new IllegalArgumentException("Invalid value for quantity, must be minor/equal than the stock of the product");
    }

    @Override
    public void delete_product(Order O, int product_id) {
        O.Cart.remove(product_id);
        O.Log = O.Log.concat("\n- Remove : Item : " + product_id + " -> Shopping Cart -- Products : " + O.Cart.size());
    }

    @Override
    public void next_state(Order O) {
        O.setOrderPhase(Checkout.getInstance());
        O.Log = O.Log.concat("\nOrder " + O.getOrder_number() + " : Check Out Phase");
    }

    @Override
    public void cancel_order(Order O) {
        throw new UnsupportedOperationException("Cannot cancel an uncompleted order");
    }

    @Override
    public void complete_order(Order O) {
        throw new UnsupportedOperationException("Cannot complete the order without the payment");
    }

    @Override
    public void screenInfo(Order O) {
        if (!O.started)
            System.out.println("\nOrder Number : " + O.getOrder_number() + "\nPhase : Shopping -- Welcome to online shop");
        else
            System.out.println("\nOrder Number : " + O.getOrder_number() + "\nPhase : Shopping -- " + O.Cart.size() + " products");
    }
}
