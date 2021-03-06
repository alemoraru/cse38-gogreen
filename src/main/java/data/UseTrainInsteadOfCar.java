package data;

import tools.CarbonCalculator;

/**
 * Activity: Travel by train instead of travelling by car.
 * @author Kostas Lyrakis
 */
public class UseTrainInsteadOfCar extends TransportationActivity {
    /**
     * Constructor.
     */
    public UseTrainInsteadOfCar() {
        super();
        this.setName("Use Train Instead of Car");
    }

    @Override
    public double calculateCarbonSaved(User user) {

        // calculate total kilometres travelled today
        double totalKilometresTraveledToday = this.calculateTotalKilometresTravelledToday(user);

        // calculate daily carbon emissions
        double dailyCarbonEmissions = this.calculateDailyCarEmissions(user);

        // calculate maximum CO2 this activity can save
        double maxC02SavedByThisActivity;
        if (user.getCarType().equals("small")) {
            maxC02SavedByThisActivity = CarbonCalculator.smallCarEmissions(
                    this.getKilometres()) - CarbonCalculator.trainEmissions(this.getKilometres());
        } else if (user.getCarType().equals("medium")) {
            maxC02SavedByThisActivity = CarbonCalculator.mediumCarEmissions(
                    this.getKilometres()) - CarbonCalculator.trainEmissions(this.getKilometres());
        } else if (user.getCarType().equals("large")) {
            maxC02SavedByThisActivity = CarbonCalculator.largeCarEmissions(
                    this.getKilometres()) - CarbonCalculator.trainEmissions(this.getKilometres());
        } else {
            maxC02SavedByThisActivity = 0;
        }

        // The user can save carbon only for the daily kilometres he/she travels by car
        if (totalKilometresTraveledToday >= user.getDailyCarKilometres()) {
            return 0;
        }

        if (this.getKilometres() > user.getDailyCarKilometres()) {
            return 0;
        }

        return maxC02SavedByThisActivity;
    }
}
