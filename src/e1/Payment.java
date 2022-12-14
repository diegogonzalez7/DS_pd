package e1;

public class Payment implements Phase {
    private static final Payment instanciaPayment = new Payment();

    Payment() {
    }

    public static Payment getInstance() {
        return instanciaPayment;
    }

    @Override
    public void add_products(Order O, Product product, int quantity) {
        throw new UnsupportedOperationException("You cannot modify the order because is already done");
    }

    @Override
    public void modify_quantity(Order O,Product product, int quantity) {
        throw new UnsupportedOperationException("You cannot modify the order because is already done");
    }

    @Override
    public void delete_product(Order O, int product_id) {
        throw new UnsupportedOperationException("You cannot modify the order because is already done");
    }

    @Override
    public void next_state(Order O) {
        throw new UnsupportedOperationException("There are not more phases after");
    }

    @Override
    public void cancel_order(Order O) {
        //Doble confirmacion para cancelar orden
        O.h_after_payment =true;
        O.setOrderPhase(Cancelled.getInstance());
    }

    @Override
    public void complete_order(Order O) {
        O.h_after_payment =true;
        O.setOrderPhase(Completed.getInstance());
    }
}
