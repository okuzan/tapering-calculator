package service;

import model.Medication;
import model.Pill;
import model.TaperingStep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BasicCalculator implements Calculator {
    @Override
    public List<TaperingStep> calculate(Medication medication,
                                        double reductionRate,
                                        boolean firstStep) {

        double granularity = medication.getFinalDosage();
        Collections.sort(medication.getPills()); // Sort pill strengths in descending order for the greedy algorithm
        List<Pill> sortedPills = new ArrayList<>();
        for (int i = medication.getPills().size() - 1; i >= 0; i--) {
            sortedPills.add(medication.getPills().size() - 1 - i, medication.getPills().get(i));
        }

        List<TaperingStep> steps = new ArrayList<>();
        double currentDosage = medication.getInitialDosage();

        while (currentDosage > medication.getFinalDosage()) {
            double targetDosage;
            if (firstStep) {
                targetDosage = currentDosage;
                firstStep = false;
            } else {
                double reductionAmount = Math.max(granularity, Math.floor((currentDosage * reductionRate) / granularity) * granularity);
                targetDosage = Math.max(medication.getFinalDosage(), currentDosage - reductionAmount);
                targetDosage = Math.round(targetDosage / granularity) * granularity; // Align with granularity
            }

            TaperingStep step = new TaperingStep(currentDosage, targetDosage);
            step.calculatePillComposition(sortedPills);
            steps.add(step);

            currentDosage = targetDosage;
        }

        return steps;
    }
}
