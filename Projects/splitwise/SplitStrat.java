package Projects.splitwise;

import java.util.List;

public interface SplitStrat {
    public List<Split> getSplits(Expense expense);
}
