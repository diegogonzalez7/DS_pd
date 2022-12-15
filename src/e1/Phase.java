package e1;

interface Phase {
    void addProducts(Order o, Product product, int quantity);

    void modifyQuantity(Order o, Product product, int quantity);

    void deleteProduct(Order o, int productId);

    void nextState(Order o);

    void cancelOrder(Order o);

    void completeOrder(Order o);

    String screenInfo(Order o);
}
