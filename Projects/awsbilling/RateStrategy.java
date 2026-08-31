package Projects.awsbilling;

public interface RateStrategy {
    int calculateCost(int units);
    String getTiersExplanation();
}
