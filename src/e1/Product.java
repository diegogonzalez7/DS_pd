package e1;

public class Product {
    private final int stock;
    private final int product_id;

    Product(int stock, int product_id) {
        this.stock = stock;
        this.product_id = product_id;
    }

    public int getStock() {
        return stock;
    }

    public int getProduct_id() {
        return product_id;
    }
}
