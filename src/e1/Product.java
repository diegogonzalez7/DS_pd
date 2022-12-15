package e1;

public class Product {
    private final int stock;
    private final int productId;

    Product(int stock, int productId) {
        this.stock = stock;
        this.productId = productId;
    }

    public int getStock() {
        return stock;
    }

    public int getProductId() {
        return productId;
    }
}
