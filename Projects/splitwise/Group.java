package Projects.splitwise;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Group {
    String id;
    Set<User> users;
    Set<Expense> expenses;

    public Group() {
        this.id = "GROUP-" + UUID.randomUUID().toString().substring(0, 10);
        this.users = new HashSet<>();
        this.expenses = new HashSet<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void removeExpense(Expense expense) {
        // This Expense has been paid
        expenses.remove(expense);
    }

    public Set<User> getUsers() {
        return users;
    }

}
