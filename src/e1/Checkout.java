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
        throw new UnsupportedOperationException("Cannot add more new products while on the check out");
    }

    @Override
    public void modify_quantity(Order O, Product product, int quantity) {
        if (quantity <= product.getStock()) {
            if (quantity == 0) O.Cart.remove(product.getProduct_id());
            else
                O.Cart.replace(product.getProduct_id(), quantity);
            O.Log = O.Log.concat("\n- Modify : Item : " + product.getProduct_id() + "- Quantity : " + quantity + " -> CheckOut Order -- Products : " + O.Cart.size());
        } else
            throw new IllegalArgumentException("Invalid value for quantity, must be minor/equal than the stock of the product");
    }

    @Override
    public void delete_product(Order O, int product_id) {
        throw new UnsupportedOperationException("Wrong operation for the check out, use the modify quantity operation instead");
    }

    @Override
    public void next_state(Order O) {
        O.setOrderPhase(Payment.getInstance());
        O.Log = O.Log.concat("\nOrder " + O.getOrder_number() + " : Payment Phase");
    }

    @Override
    public void cancel_order(Order O) {
        //Vuelta a la fase de elección de productos para el carrito.
        O.setOrderPhase(ShoppingCart.getInstance());
        O.Log = O.Log.concat("\nOrder " + O.getOrder_number() + " : Shopping Phase");
    }

    @Override
    public void complete_order(Order O) {
        throw new UnsupportedOperationException("Cannot complete the order without the payment");
    }

    @Override
    public void screenInfo(Order O) {
        System.out.println("\nOrder Number : " + O.getOrder_number() + "\nPhase : Check Out -- " + O.Cart.size() + " products");
    }
}
