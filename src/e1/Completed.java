package e1;

public class Completed implements Phase {
    private static final Completed instanciaCompleted = new Completed();

    Completed() {
    }

    public static Completed getInstance() {
        return instanciaCompleted;
    }

    @Override
    public void add_products(Order O, Product product, int quantity) {
        throw new UnsupportedOperationException("You cannot modify the order because is already done");
    }

    @Override
    public void modify_quantity(Order O, Product product, int quantity) {
        throw new UnsupportedOperationException("You cannot modify the order because is already done");
    }

    @Override
    public void delete_product(Order O, int product_id) {
        throw new UnsupportedOperationException("You cannot modify the order because is already done");
    }

    @Override
    public void next_state(Order O) {
        //Aquí podemos implementar nuevas clases con siguientes hipotéticos estados como por ejemplo la Entrega de dichas entregas, siendo la siguiente fase Entrega
        throw new UnsupportedOperationException("There are not more phases after");
    }

    @Override
    public void cancel_order(Order O) {
        throw new UnsupportedOperationException("The order cannot be cancelled");
    }

    @Override
    public void complete_order(Order O) {
        //Completa la orden
        if (O.h_after_payment) {
            O.done_order = true;
            O.Log = O.Log.concat("\n- Order " + O.order_number + " : Completed Order --");
            //Si hubiera un estado se añadiria un  O.setOrderPhase(NuevoEstado.getInstance())
        }
        else throw new IllegalStateException("The completition time has not ended yet");
    }

    @Override
    public void screenInfo(Order O) {
        System.out.println("Order Number : " + O.getOrder_number() + "\nPhase : Completed Order : " + O.Cart.size() + " products");
    }
}