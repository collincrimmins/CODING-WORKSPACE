package LLD.Projects.parkinggarage.Fee.Concrete;

import LLD.Projects.parkinggarage.Fee.FeeStrategy;

public class MediumFee implements FeeStrategy {
    private final double baseCost = 10.0;

    @Override
    public double calculatePayment(int numHours) {
        return numHours * baseCost;
    }
}
