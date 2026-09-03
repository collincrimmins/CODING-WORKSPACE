package Projects.splitwise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Expense {
    String id;
    String desc;
    Group group;
    double amount;
    User paidByUser;
    List<Split> splits;
    boolean splitsCalculated;
    SplitStrat splitStrat;
    // Input
    Map<User, Double> mapUserSplits;

    public Expense(String desc, Group group, double amount, User paidByUser, SplitStrat splitStrat) {
        this.id = "EXPENSE-" + UUID.randomUUID().toString().substring(0, 10);
        this.desc = desc;
        this.group = group;
        this.amount = amount;
        this.paidByUser = paidByUser;
        this.splitStrat = splitStrat;
        this.mapUserSplits = new HashMap<>();
        this.splitsCalculated = false;
    }

    // SplitStrat (used for custom inputs, like SplitStratExactAmounts and SplitStratPercentages)
    public void setMapUserSplits(User user, Double amount) {
        mapUserSplits.put(user, amount);
    }

    // Calculate & create List<Splits>
    public void calculateUserSplits() {
        if (splitsCalculated) {
            System.out.println("User Splits already calculated");
            return;
        }

        // Update List<Split>
        this.splits = splitStrat.getSplits(this);

        // Mark as Calculated
        splitsCalculated = true;
    }

    public Map<User, Double> getMapUserSplits() {
        return mapUserSplits;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public Group getGroup() {
        return group;
    }

    public double getAmount() {
        return amount;
    }

    public User getPaidByUser() {
        return paidByUser;
    }

    public List<Split> getSplits() {
        if (!splitsCalculated) {
            System.out.println("User Splits have not been calculated yet...");
            return null;
        }

        return splits;
    }
}
