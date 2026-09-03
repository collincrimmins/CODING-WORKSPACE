package Projects.splitwise;

public class Transaction {
    User userFrom;
    User userTo;
    double amount;

    public Transaction(User userFrom, User userTo, double amount) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return userFrom.toString() + " paid " + userTo.toString() + " = $" + amount;
    }
}
