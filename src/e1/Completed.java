package e1;

public class Completed implements Phase {
    private static final Completed instanciaCompleted = new Completed();

    Completed() {
    }

    public static Completed getInstance() {
        return instanciaCompleted;
    }

    @Override
    public void addProducts(Order o, Product product, int quantity) {
        throw new UnsupportedOperationException("You cannot modify the order because is already done");
    }

    @Override
    public void modifyQuantity(Order o, Product product, int quantity) {
        throw new UnsupportedOperationException("You cannot modify the order because is already done");
    }

    @Override
    public void deleteProduct(Order o, int productId) {
        throw new UnsupportedOperationException("You cannot modify the order because is already done");
    }

    @Override
    public void nextState(Order o) {
        //Aquí podemos implementar nuevas clases con siguientes hipotéticos estados como por ejemplo la Entrega de los pedidos, siendo la siguiente fase Entrega
        throw new UnsupportedOperationException("There are not more phases after");
    }

    @Override
    public void cancelOrder(Order o) {
        throw new UnsupportedOperationException("The order cannot be cancelled");
    }

    @Override
    public void completeOrder(Order o) {
        //Completa la orden
        if (o.hAfterPayment) {
            o.doneOrder = true;
            o.log = o.log.concat("\n- Order " + o.orderNumber + " : Completed Order --");
            //Si hubiera un estado se añadiria un  o.setOrderPhase(NuevoEstado.getInstance())
        }
        else throw new IllegalStateException("The completition time has not ended yet");
    }

    @Override
    public String screenInfo(Order o) {
        System.out.println("\nOrder Number : " + o.getOrderNumber() + "\nPhase : Completed Order : " + o.Cart.size() + " products");
        return "\nOrder Number : " + o.getOrderNumber() + "\nPhase : Completed Order : " + o.Cart.size() + " products";
    }
}