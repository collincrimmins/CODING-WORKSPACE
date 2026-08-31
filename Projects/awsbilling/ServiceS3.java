package Projects.awsbilling;

public class ServiceS3 extends Service {

    public ServiceS3(int usageUnits, RateStrategy rateStrategy) {
        super("S3 Storage", usageUnits, rateStrategy);
    }

    @Override
    public String getUsageUnitsType() {
        return "TB";
    }
    
}
