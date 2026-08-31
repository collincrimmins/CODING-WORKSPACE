package Projects.awsbilling;

public class ServiceEC2 extends Service {

    public ServiceEC2(int usageUnits, RateStrategy rateStrategy) {
        super("EC2 Instances", usageUnits, rateStrategy);
    }

    @Override
    public String getUsageUnitsType() {
        return "Hours";
    }
    
}
