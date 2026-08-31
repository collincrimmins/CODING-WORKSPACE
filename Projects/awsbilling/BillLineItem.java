package Projects.awsbilling;

import java.util.Map;

public class BillLineItem {
    Service service;
    int cost;

    public BillLineItem(Service service) {
        this.service = service;

        // Calculate Cost
        String serviceName = service.getName();
        int serviceUsage = service.getUsageUnits();

        this.cost = service.getRateStrategy().calculateCost(serviceUsage);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getStringExplainTiers() {
        return service.getRateStrategy().getTiersExplanation();
    }
    
}
