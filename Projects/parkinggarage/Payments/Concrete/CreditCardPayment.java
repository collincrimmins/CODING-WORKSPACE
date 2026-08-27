package Projects.parkinggarage.Payments.Concrete;

import Projects.parkinggarage.Payments.PaymentStrategy;

public class CreditCardPayment implements PaymentStrategy {
    public CreditCardPayment() {}
    
    @Override
    public void pay(double amount) {
        //System.out.println("Paid with credit card amount " + amount);
    }
}
