package Projects.splitwise;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SplitStratEqual implements SplitStrat {
    @Override
    public List<Split> getSplits(Expense expense) {
        // Get Users & Remove Paying User
        Set<User> users = new HashSet<>(expense.getGroup().getUsers());
        users.remove(expense.getPaidByUser());

        double totalAmount = expense.getAmount();
        double splitAmount = totalAmount / users.size();
        
        // Create Splits
        List<Split> list = new ArrayList<>();
        for (User user : users) {
            Split split = new Split(user, splitAmount, expense);
            list.add(split);
        }

        return list;
    }
    
}
