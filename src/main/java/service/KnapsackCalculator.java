package service;

import model.Duration;
import model.Medication;
import model.Result;
import model.TaperingStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class KnapsackCalculator implements Calculator {
    public static final int BOUND = 6;
    public static final int WEEK = 7;


    public static final int FORTNIGHT = 14;
    public static final int MONTH = 28;

    @Override
    public List<TaperingStep> calculate(Medication medication,
                                        double reductionRate,
                                        boolean firstStep,
                                        Integer period) {

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
            double roundedDosage = round(result.value); //rounded up to 10th

            double actualReduction = round(currentDosage - roundedDosage);
            double safeReduction = round(currentDosage * reductionRate);

            int duration = Objects.requireNonNullElseGet(period,
                    () -> actualReduction > safeReduction
                            ? Duration.MONTH.getDays() : Duration.WEEK.getDays());

            TaperingStep step = new TaperingStep(currentDosage, roundedDosage, duration);
            step.setPillCompositionDetailed(result.composition);
            steps.add(step);

            currentDosage = roundedDosage;
        }

        return steps;
    }

    private static double round(double number) {
        return Math.round(number * 100.0) / 100.0;
    }
}
