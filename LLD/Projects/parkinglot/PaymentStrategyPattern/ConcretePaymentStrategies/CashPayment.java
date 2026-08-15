package LLD.Projects.parkinglot.PaymentStrategyPattern.ConcretePaymentStrategies;

import LLD.Projects.parkinglot.PaymentStrategyPattern.PaymentStrategy;

public class CashPayment implements PaymentStrategy {
    public CashPayment(double fee) {

    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing cash payment of $" + amount);
    }
}
