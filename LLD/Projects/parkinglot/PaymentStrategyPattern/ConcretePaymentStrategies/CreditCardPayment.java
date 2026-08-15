package LLD.Projects.parkinglot.PaymentStrategyPattern.ConcretePaymentStrategies;

import LLD.Projects.parkinglot.PaymentStrategyPattern.PaymentStrategy;

public class CreditCardPayment implements PaymentStrategy  {
    public CreditCardPayment(double fee) {
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
    }
}
