package Projects.splitwise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SplitStratExactAmount implements SplitStrat {
    @Override
    public List<Split> getSplits(Expense expense) {
        // Get Precomputed Map of Exact Amounts
        Map<User, Double> mapExactAmounts = expense.getMapUserSplits();

        // Validate - Check that Precomputed List equals to Total Balance
        double totalAmount = 0;
        for (Map.Entry<User, Double> entry : mapExactAmounts.entrySet()) {
            totalAmount = totalAmount + entry.getValue();
        }
        if (totalAmount != expense.getAmount()) {
            throw new IllegalArgumentException("ExactAmountSplit total of $" + totalAmount + " is not enough to pay Expense Balance of $" + expense.getAmount());
        }

        // Create Splits
        List<Split> list = new ArrayList<>();
        for (Map.Entry<User, Double> entry : mapExactAmounts.entrySet()) {
            Split split = new Split(entry.getKey(), entry.getValue(), expense);
            list.add(split);
        }

        return list;
    }
    
}
