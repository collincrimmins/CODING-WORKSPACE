package Projects.awsbilling;

public class RateTier {
    int startUnit;
    int endUnit;
    int rate;
    
    public int getStartUnit() {
        return startUnit;
    }

    public int getEndUnit() {
        return endUnit;
    }

    public int getRate() {
        return rate;
    }

    public RateTier(int startUnit, int endUnit, int rate) {
        this.startUnit = startUnit;
        this.endUnit = endUnit;
        this.rate = rate;
    }


}
