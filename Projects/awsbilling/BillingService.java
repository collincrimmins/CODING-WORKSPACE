package Projects.awsbilling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillingService {
    public static Bill generateBill(User user, List<Service> myServicesUsed, String month) {
        Bill.Builder myBill = new Bill.Builder(user.getName(), month);

        for (Service service : myServicesUsed) {
            
            myBill.addService(service);
        }

        return myBill.build();
    }

}
