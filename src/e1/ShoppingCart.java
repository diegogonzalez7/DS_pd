package e1;

public class ShoppingCart implements Phase {
    private static final ShoppingCart instanciaShoppingCart = new ShoppingCart();

    ShoppingCart() {
    }

    public static ShoppingCart getInstance() {
        return instanciaShoppingCart;
    }

    @Override
    public void addProducts(Order o, Product product, int quantity) {
        if (quantity <= product.getStock()) {
            o.Cart.putIfAbsent(product.getProductId(), quantity);
            o.log = o.log.concat("\n- Add : Item : " + product.getProductId() + "- Quantity : " + quantity + " -> Shopping Cart -- Products : " + o.Cart.size());
        } else
            throw new IllegalArgumentException("Invalid value for quantity, must be minor/equal than the stock of the product");
    }

    @Override
    public void modifyQuantity(Order o, Product product, int quantity) {
        if (quantity <= product.getStock()) {
            if (quantity == 0) o.Cart.remove(product.getProductId());
            else
                o.Cart.replace(product.getProductId(), quantity);
            o.log = o.log.concat("\n- Modify : Item : " + product.getProductId() + "- Quantity : " + quantity + " -> Shopping Cart -- Products : " + o.Cart.size());
        } else
            throw new IllegalArgumentException("Invalid value for quantity, must be minor/equal than the stock of the product");
    }

    @Override
    public void deleteProduct(Order o, int productId) {
        o.Cart.remove(productId);
        o.log = o.log.concat("\n- Remove : Item : " + productId + " -> Shopping Cart -- Products : " + o.Cart.size());
    }

    @Override
    public void nextState(Order o) {
        o.setOrderPhase(Checkout.getInstance());
        o.log = o.log.concat("\nOrder " + o.getOrderNumber() + " : Check Out Phase");
    }

    @Override
    public void cancelOrder(Order o) {
        throw new UnsupportedOperationException("Cannot cancel an uncompleted order");
    }

    @Override
    public void completeOrder(Order o) {
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
