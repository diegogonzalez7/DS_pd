package e1;

public class Checkout implements Phase {
    private static final Checkout instanciaCheckout = new Checkout();

    Checkout() {
    }

    public static Checkout getInstance() {
        return instanciaCheckout;
    }

    @Override
    public void addProducts(Order o, Product product, int quantity) {
        throw new UnsupportedOperationException("Cannot add more new products while on the check out");
    }

    @Override
    public void modifyQuantity(Order o, Product product, int quantity) {
        if (quantity <= product.getStock()) {
            if (quantity == 0) o.cart.remove(product.getProductId());
            else
                o.cart.replace(product.getProductId(), quantity);
            o.log = o.log.concat("\n- Modify : Item : " + product.getProductId() + "- Quantity : " + quantity + " -> CheckOut Order -- Products : " + o.cart.size());
        } else
            throw new IllegalArgumentException("Invalid value for quantity, must be minor/equal than the stock of the product");
    }

    @Override
    public void deleteProduct(Order o, int productId) {
        throw new UnsupportedOperationException("Wrong operation for the check out, use the modify quantity operation instead");
    }

    @Override
    public void nextState(Order o) {
        o.setOrderPhase(Payment.getInstance());
        o.log = o.log.concat("\nOrder " + o.getOrderNumber() + " : Payment Phase");
    }

    @Override
    public void cancelOrder(Order o) {
        //Vuelta a la fase de elección de productos para el carrito.
        o.setOrderPhase(ShoppingCart.getInstance());
        o.log = o.log.concat("\nOrder " + o.getOrderNumber() + " : Shopping Phase");
    }

    @Override
    public void completeOrder(Order o) {
        throw new UnsupportedOperationException("Cannot complete the order without the payment");
    }

    @Override
    public String screenInfo(Order o) {
        System.out.println("\nOrder Number : " + o.getOrderNumber() + "\nPhase : Check Out -- " + o.cart.size() + " products");
        return "\nOrder Number : " + o.getOrderNumber() + "\nPhase : Check Out -- " + o.cart.size() + " products";
    }
}
