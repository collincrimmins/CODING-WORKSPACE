package Projects.awsbilling;

public abstract class Service {
    RateStrategy rateStrategy;
    String name;
    int usageUnits;
   
    public Service(String name, int usageUnits, RateStrategy rateStrategy) {
        this.name = name;
        this.usageUnits = usageUnits;
        this.rateStrategy = rateStrategy;
    }

    public abstract String getUsageUnitsType();

     public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getUsageUnits() {
        return usageUnits;
    }
    public void setUsageUnits(int usageUnits) {
        this.usageUnits = usageUnits;
    }

    public RateStrategy getRateStrategy() {
        return rateStrategy;
    }
}
