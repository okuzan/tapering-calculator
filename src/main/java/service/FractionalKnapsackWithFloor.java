package service;

import model.Pill;
import model.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static service.KnapsackCalculator.MAX_DAILY_PILL_COUNT;

public class FractionalKnapsackWithFloor {

    // Recursive function to find the closest combination with a bound on the number of pills
    public static Result closestCombination(List<Pill> divisors, double target, Map<String, Result> memo, int currentCount) {
        String key = target + "@" + currentCount; // Include currentCount in the memoization key
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        if (target <= 0 && currentCount <= MAX_DAILY_PILL_COUNT) {
            return new Result(0); // Adjust base case for starting situation
        }

        Result closestAbove = null; // Change to track the closest above the target

        for (Pill pill : divisors) {
            if (currentCount < MAX_DAILY_PILL_COUNT) {
                Result result = closestCombination(divisors, target - pill.getStrength(), memo, currentCount + 1);
                double newValue = result.value + pill.getStrength();

                // Logic to determine the closest above
                if ((closestAbove == null || Math.abs(target - newValue) < Math.abs(target - closestAbove.value)) && newValue >= target) {
                    closestAbove = new Result(newValue);
                    closestAbove.composition.putAll(result.composition);
                    closestAbove.composition.put(pill, closestAbove.composition.getOrDefault(pill, 0) + 1);
                }
            }
        }

        if (closestAbove == null) {
            closestAbove = new Result(0); // Ensure non-null return
        }

        memo.put(key, closestAbove);
        return closestAbove;
    }

    public static void main(String[] args) {
        List<Pill> divisors = new ArrayList<>(List.of(
                new Pill("producer1", 44.2),
                new Pill("producer2", 10),
                new Pill("producer2", 2.5)));

        double target = 91;
        Map<String, Result> memo = new HashMap<>();

        Result result = closestCombination(divisors, target, memo, 0); // Start with a count of 0
        System.out.println("Closest Value: " + result.value);
        System.out.println("Composition: ");
        result.composition.forEach((k, v) -> System.out.println(k.getStrength() + "mg x " + v));
    }
}
