package Projects.parkinggarage.Payments.Concrete;

import Projects.parkinggarage.Payments.PaymentStrategy;

public class CashPayment implements PaymentStrategy {
    public CashPayment() {}

    @Override
    public void pay(double amount) {
        //System.out.println("Paid in cash amount " + amount);
    }
}
