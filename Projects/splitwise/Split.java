package Projects.splitwise;

public class Split {
    private final User user;
    private final double amount;
    private boolean paid;
    private final Expense expense;

    public Split(User user, double amount, Expense expense) {
        this.user = user;
        this.amount = amount;
        this.expense = expense;
        this.paid = false;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Expense getExpense() {
        return expense;
    }

    public User getUser() {
        return user;
    }

    public double getAmount() {
        return amount;
    }
}
