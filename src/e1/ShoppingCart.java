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
            o.Cart.putIfAbsent(product.getProductId(), quantity);
            o.log = o.log.concat("\n- Add : Item : " + product.getProductId() + "- Quantity : " + quantity + " -> Shopping Cart -- Products : " + o.Cart.size());
        } else
            throw new IllegalArgumentException("Invalid value for quantity, must be minor/equal than the stock of the product");
    }

    @Override
    public void modify_quantity(Order O, Product product, int quantity) {
        if (quantity <= product.getStock()) {
            if (quantity == 0) o.Cart.remove(product.getProductId());
            else
                o.Cart.replace(product.getProductId(), quantity);
            o.log = o.log.concat("\n- Modify : Item : " + product.getProductId() + "- Quantity : " + quantity + " -> Shopping Cart -- Products : " + o.Cart.size());
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
    public String screenInfo(Order o) {
        if (!o.started) {
            System.out.println("\nOrder Number : " + o.getOrderNumber() + "\nPhase : Shopping -- Welcome to online shop");
            o.started=true;
            return "\nOrder Number : " + o.getOrderNumber() + "\nPhase : Shopping -- Welcome to online shop";
        } else {
            System.out.println("\nOrder Number : " + o.getOrderNumber() + "\nPhase : Shopping -- " + o.Cart.size() + " products");
            return "\nOrder Number : " + o.getOrderNumber() + "\nPhase : Shopping -- " + o.Cart.size() + " products";
        }
    }
}
