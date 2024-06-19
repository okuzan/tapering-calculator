package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Medication;
import model.Result;
import model.TaperingStep;
import util.Utility;

public class KnapsackCalculator implements Calculator {
    public static final int MAX_DAILY_PILL_COUNT = 6;
    public static final double EXTRA_TOLERANCE = 0.01; // Extra tolerance for merging small steps

    @Override
    public List<TaperingStep> calculate(Medication medication,
                                        double reductionRate,
                                        boolean firstStep) {

        List<TaperingStep> steps = new ArrayList<>();
        double currentDosage = medication.getInitialDosage();
        double cumulativeReduction = 0.0;
        double maxCumulativeReduction = reductionRate + EXTRA_TOLERANCE; // Set the dynamic max cumulative reduction

        while (currentDosage > medication.getFinalDosage()) {
            double targetDosage;
            if (firstStep) {
                targetDosage = currentDosage; // Maintain current dosage for the first step
            } else {
                double minimumNewDosage = Math.ceil(currentDosage * (1 - reductionRate));
                if (minimumNewDosage >= currentDosage) minimumNewDosage = currentDosage - 0.01;
                targetDosage = Math.max(medication.getFinalDosage(), minimumNewDosage);
            }

            Result result = FractionalKnapsackWithFloor.closestCombination(
                    medication.getPills(),
                    targetDosage,
                    new HashMap<>(),
                    0);

            if (result.value >= currentDosage) {
                result = FractionalKnapsackWithCeiling.closestCombination(
                        medication.getPills(),
                        targetDosage,
                        new HashMap<>(),
                        0);
            }
            double roundedDosage = Utility.round(result.value); //rounded up to 1/100th
            double reductionPercentage = (currentDosage - roundedDosage) / currentDosage;

            if (cumulativeReduction + reductionPercentage <= maxCumulativeReduction) {
                if (!firstStep && cumulativeReduction != 0.0) {
                    cumulativeReduction += reductionPercentage;
                    currentDosage = roundedDosage;
                    continue; // Skip creating a new step, continue to the next iteration
                }
            }

            TaperingStep step = new TaperingStep(currentDosage, roundedDosage);
            step.setPillCompositionDetailed(result.composition);
            steps.add(step);

            currentDosage = roundedDosage;
            cumulativeReduction = reductionPercentage; // Reset cumulative reduction for the next step
            if (firstStep) {
                firstStep = false;
            }
        }

        return steps;
    }
}
