package Projects.splitwise;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SplitStratEqual implements SplitStrat {
    @Override
    public List<Split> getSplits(Expense expense) {
        Set<User> users = expense.getGroup().getUsers();

        // Remove Paying User
        users.remove(expense.getPaidByUser());

        // Create Splits
        List<Split> list = new ArrayList<>();
        double amountOwedPerPerson = expense.getAmount() / (double) users.size();
        for (User user : users) {
            Split split = new Split(user, amountOwedPerPerson, expense);
            list.add(split);
        }

        return list;
    }
    
}
