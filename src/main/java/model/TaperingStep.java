package model;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class TaperingStep {
    public static final int THRESHOLD = 6;
    double dosageBefore;
    double dosageAfter;
    double actualReduction;
    int number;
    // New map to keep track of pills by producer and strength
    Map<Pill, Integer> pillCompositionDetailed;

    public TaperingStep(double dosageBefore, double dosageAfter) {
        this.dosageBefore = dosageBefore;
        this.dosageAfter = dosageAfter;
        this.actualReduction = dosageBefore - dosageAfter;
        this.pillCompositionDetailed = new HashMap<>();
    }

    public List<String> getPillsForCSVByProducer(String producer) {
        return pillCompositionDetailed.keySet().stream()
                .filter(pill -> pill.getProducer().equals(producer))
                .sorted(Comparator.comparingDouble(Pill::getStrength).reversed())
                .flatMap(pill -> Collections.nCopies(
                                pillCompositionDetailed.get(pill),
                                String.format(Locale.ROOT, "%.1f", pill.getStrength())
                        ).stream()
                )
                .collect(Collectors.toList());
    }

    // Calculate and set the pill composition for achieving the actual dosage after
    public void calculatePillComposition(List<Pill> pills) {
        double remainingDosage = dosageAfter;

        pills.sort((p1, p2) -> Double.compare(p2.getStrength(), p1.getStrength())); // Sort pills by strength in descending order

        for (Pill pill : pills) {
            int count = (int) (remainingDosage / pill.getStrength());
            if (count > 0) {
                pillCompositionDetailed.put(pill, pillCompositionDetailed.getOrDefault(pill, 0) + count);
                remainingDosage -= count * pill.getStrength();
            }
            if (remainingDosage <= 0) break;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Step %d. Dosage %.1fmg => %.1fmg, Actual reduction: %.1f%%, Pills: ",
                number, dosageBefore, dosageAfter, (actualReduction / dosageBefore) * 100));

        pillCompositionDetailed.forEach((pill, count) -> sb.append(String.format("%.1fmg x %d, ", pill.getStrength(), count)));
        if (!pillCompositionDetailed.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length()); // Remove trailing comma and space
        }

        // Calculate the total number of pills
        int totalPills = pillCompositionDetailed.values().stream().mapToInt(Integer::intValue).sum();

        // Add warning for more than 6 pills at the end of the line
        if (totalPills > THRESHOLD) {
            sb.append(" [!] ").append(totalPills).append(" pills");
        }

        return sb.toString();
    }
}
