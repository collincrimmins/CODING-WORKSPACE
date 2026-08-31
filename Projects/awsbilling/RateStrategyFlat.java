package Projects.awsbilling;

public class RateStrategyFlat implements RateStrategy {
    int rate;

    public RateStrategyFlat(int rate) {
        this.rate = rate;
    }

    @Override
    public int calculateCost(int units) {
        return rate * units;
    }

    @Override
    public String getTiersExplanation() {
        return "[?] Flat Rate of $" + rate + " per unit";
    }
    
}
