package e1;

interface Phase {
    void add_products(Order O, Product product, int quantity);

    void modify_quantity(Order O, Product product, int quantity);

    void delete_product(Order O, int product_id);

    void next_state(Order O);

    void cancel_order(Order O);

    void complete_order(Order O);
}
