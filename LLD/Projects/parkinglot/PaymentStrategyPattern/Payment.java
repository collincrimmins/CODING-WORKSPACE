package LLD.Projects.parkinglot.PaymentStrategyPattern;

public class Payment {
    private double amount;
    private PaymentStrategy paymentStrategy; // interface

    public Payment(double amount, PaymentStrategy paymentStrategy) {
        this.amount = amount;
        this.paymentStrategy = paymentStrategy;
    }

    // Process using the strategy
    public void processPayment() {
        if (amount > 0) {
            paymentStrategy.processPayment(amount);
        } else {
            System.out.println("[Error] Invalid payment amount");
        }
    }
}
