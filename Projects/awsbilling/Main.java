package Projects.awsbilling;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Pricing
        RateStrategy EC2PricingStrategy = new RateStrategyFlat(100);

        List<RateTier> S3ListTiers = new ArrayList<>();
        S3ListTiers.add(new RateTier(0, 50, 0)); // Free Tier 0 to 50
        S3ListTiers.add(new RateTier(50, 100, 10)); // 50 to 100 = $10/unit
        S3ListTiers.add(new RateTier(100, Integer.MAX_VALUE, 25)); // 100+ = $15/unit
        RateStrategy S3PricingStrategy = new RateStrategyTiers(S3ListTiers);

        // Create User & Services
        User user1 = new User("Bob");

        Service S3 = new ServiceS3(150, S3PricingStrategy); // 100 TB
        Service EC2 = new ServiceEC2(50, EC2PricingStrategy); // 50 Hours

        // Create Bill
        List<Service> myServicesUsed = new ArrayList<>();
        myServicesUsed.add(S3);
        myServicesUsed.add(EC2);

        Bill myBill = BillingService.generateBill(user1, myServicesUsed, "January");
        
        myBill.printBill();
    }

    /*
        Prompt: Design a billing service for AWS for a single user where 
            the usage and rate of all the services used are given.

        Requirements:
        - Multiple Services (EC2, S3)
        - Multiple Tiered Rates (S3 free below 10TB, X cost above 10 TB)
        - User gets a "Billing Report" based on their usage

        Entities
        - User
        - BillingService
        - Bill
        - Service
        - Rates (Tiered)

        Class Design

        User
        - id
        - List<Bills>

        Bill
        - totalCost
        - List<Services>
        
        Service
        - name
        - usage (int units)
        
        Rates (Strategy)
        - calculateCost()

        RatesFlat - numHours X price
        RateTiered - free tier of units vs. paid $ above num of units
    */
}
