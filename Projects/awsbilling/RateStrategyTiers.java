package Projects.awsbilling;

import java.util.List;

public class RateStrategyTiers implements RateStrategy {
    List<RateTier> tiers;

    public RateStrategyTiers(List<RateTier> tiers) {
        this.tiers = tiers;
    }

    @Override
    public int calculateCost(int units) {
        int result = 0;
        for (RateTier tier : tiers) {
            // Greater Than
            if (units >= tier.getEndUnit()) {
                int amount = tier.getEndUnit() - tier.getStartUnit();
                result = result + (amount * tier.getRate());
                continue;
            }

            // In Bounds
            if (units <= tier.getEndUnit() && units >= tier.getStartUnit()) {
                int amount = units - tier.getStartUnit();
                result = result + (amount * tier.getRate());
                continue;
            }
        }

        return result;
    }


    @Override
    public String getTiersExplanation() {
        String result = "";

        result = result + "[?] ";

        for (RateTier tier : tiers) {
            result = result + tier.getStartUnit() + " to " + tier.getEndUnit() + " is $" + tier.getRate() + " | ";
        }


        return result;
    }
    
}
