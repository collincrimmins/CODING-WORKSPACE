package Projects.splitwise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SplitwiseService {
    private final Set<User> users;
    private final Set<Group> groups;
    private final Set<Expense> expenses;
    private final Set<Transaction> transactions;

    public SplitwiseService() {
        this.users = new HashSet<>();
        this.groups = new HashSet<>();
        this.expenses = new HashSet<>();
        this.transactions = new HashSet<>();
    }

    // Main

    public User addUser(String id) {
        User user = new User(id);
        users.add(user);
        return user;
    }

    public Group addGroup() {
        Group group = new Group();
        groups.add(group);
        return group;
    }

    public Expense createExpense(String desc, Group group, double amount, User paidByUser, SplitStrat splitStrat) {
        Expense expense = new Expense(desc, group, amount, paidByUser, splitStrat);
        expenses.add(expense);
        return expense;
    }

    public double userGetTotalBalance(User user) {
        double balance = 0;
        for (Expense expense : expenses) {
            List<Split> splits = expense.getSplits();
            for (Split split : splits) {
                if (split.getUser() == user && !split.isPaid()) {
                    balance = balance + split.getAmount();
                }
            }
        }
        return balance;
    }

    public boolean paySplit(Split split) {
        // Check Already Paid
        if (split.isPaid()) {
            System.out.println("X - Split already paid by user");
            return false;
        }

        // Create Transaction (Payment Service)
        Transaction transaction = new Transaction(split.getUser(), split.getExpense().getPaidByUser(), split.getAmount());
        transactions.add(transaction);

        // Set Split as Paid
        split.setPaid(true);

        // Check if all Splits in Expense are paid
        Expense expense = split.getExpense();
        List<Split> splits = expense.getSplits();
        boolean allPaid = true;
        for (Split thisSplit : splits) {
            if (!thisSplit.isPaid()) {
                allPaid = false;
                break;
            }
        }
        if (allPaid) {
            // Close out Expense
            //System.out.println("Expense fully paid: $" + expense.getAmount() + " - " + expense.getId());
           // expenses.remove(expense);
        }
        
        return true;
    }

    public Map<User, Double> simplifyDebtAmounts() {
        // Create List<Split> which accounts for (Money I owe people) - (Money they owe me)

        Map<User, Double> balances = new HashMap<>();
        
        // for (User user : users) {
        //     balances.put(user, 0.0);
        // }

        // for (Expense expense : expenses) {
        //     User payer = expense.getPaidByUser();
        //     List<Split> splits = expense.getSplits();
        //     if (splits == null) continue;

        //     for (Split split : splits) {
        //         if (!split.isPaid()) {
        //             // Payer is owed money (+)
        //             balances.put(payer, balances.getOrDefault(payer, 0.0) + split.getAmount());
        //             // Borrower owes money (-)
        //             balances.put(split.getUser(), balances.getOrDefault(split.getUser(), 0.0) - split.getAmount());
        //         }
        //     }
        // }

        return balances;
    }

    public void payAllSplits() {
        // Pay List<Split>
        for (Expense exp : expenses) {
            List<Split> listSplits = exp.getSplits();
            if (listSplits != null) {
                for (Split split : exp.getSplits()) {
                    paySplit(split);
                }
            }
        }
    }

    public void printAllUserBalances() {
        System.out.println("=== All User Balances Remaining ===");
        for (User user : users) {
            System.out.println("- " + user.getId() + " balance: $" + userGetTotalBalance(user));
        }
    }

    public void printAllTransactions() {
        System.out.println("=== All Transactions ===");
        for (Transaction trans : transactions) {
            System.out.println("- [PAYMENT] " + trans.toString());
        }
    }

    // Getters

    public Set<User> getUsers() {
        return users;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }
}
