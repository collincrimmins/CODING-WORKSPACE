package Projects.parkinggarage.Fee.Concrete;

import Projects.parkinggarage.Fee.FeeStrategy;

public class LargeFee implements FeeStrategy {
    private final double baseCost = 15.0;

    @Override
    public double calculatePayment(int numHours) {
        return numHours * baseCost;
    }
}
