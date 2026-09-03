package Projects.splitwise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SplitStratPercent implements SplitStrat {
    @Override
    public List<Split> getSplits(Expense expense) {
        Map<User, Double> userPercentages = expense.getMapUserSplits();
        
        // Validate total percentage equals 100%
        double totalPercent = userPercentages.values().stream().mapToDouble(Double::doubleValue).sum();
        if (Math.abs(totalPercent - 100.0) > 0.001) {
            throw new IllegalArgumentException("Percentages must add up to 100%. Got: " + totalPercent);
        }

        List<Split> splits = new ArrayList<>();
        double totalAmount = expense.getAmount();

        for (Map.Entry<User, Double> entry : userPercentages.entrySet()) {
            double splitAmount = (totalAmount * entry.getValue()) / 100.0;
            splits.add(new Split(entry.getKey(), splitAmount, expense));
        }

        return splits;
    }
    
}
