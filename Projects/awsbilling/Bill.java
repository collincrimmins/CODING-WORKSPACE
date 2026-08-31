package Projects.awsbilling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bill {
    List<BillLineItem> list;
    int totalCost;
    String month;
    String user;

    private Bill(String user, String month) {
        this.list = new ArrayList<>();
        this.totalCost = 0;
        this.month = month;
        this.user = user;
    }

    public void printBill() {
        System.out.println("=== Bill ===");
        System.out.println("User: " + user);
        System.out.println("Month: " + month);
        System.out.println("");

        // Print LineItems
        for (BillLineItem item : list) {
            // Cost
            System.out.println(item.getService().getName() + ": Usage " + item.getService().getUsageUnits() + " " + item.getService().getUsageUnitsType()
            + " = $" + item.getCost());

            // Explain Tier Pricing
            System.out.println(item.getStringExplainTiers());

            System.out.println("");
        }

        // Print Total Cost
        System.out.println("");
        System.out.println("Total Cost: $" + totalCost);
    }

    public static class Builder {
        Bill myBill;

        public Builder(String user, String month) {
            this.myBill = new Bill(user, month);
        }

        public Builder addService(Service service) {
            // Add Line Item
            BillLineItem item = new BillLineItem(service);
            myBill.list.add(item);

            // Add to Total Cost
            myBill.totalCost = myBill.totalCost + item.getCost();

            return this;
        }

        public Bill build() {
            return myBill;
        }
    }
}