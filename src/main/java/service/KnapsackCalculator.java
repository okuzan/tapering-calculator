package service;

import model.Medication;
import model.Result;
import model.TaperingStep;
import util.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KnapsackCalculator implements Calculator {
    public static final int MAX_DAILY_PILL_COUNT = 6;

    @Override
    public List<TaperingStep> calculate(Medication medication,
                                        double reductionRate,
                                        boolean firstStep) {

        List<TaperingStep> steps = new ArrayList<>();
        double currentDosage = medication.getInitialDosage();

        while (currentDosage > medication.getFinalDosage()) {
            double targetDosage;
            if (firstStep) {
                targetDosage = currentDosage; // Maintain current dosage for the first step
                firstStep = false; // Ensure that this branch only runs once
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
            double roundedDosage = Utility.round(result.value); //rounded up to 10th

            TaperingStep step = new TaperingStep(currentDosage, roundedDosage);
            step.setPillCompositionDetailed(result.composition);
            steps.add(step);

            currentDosage = roundedDosage;
        }

        return steps;
    }
}