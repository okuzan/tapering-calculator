package service;

import model.Pill;
import model.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static service.KnapsackCalculator.BOUND;

public class FractionalKnapsackWithCeiling {

    // Recursive function to find the closest combination with a bound on the number of pills
    public static Result closestCombination(List<Pill> divisors, double target, Map<String, Result> memo, int currentCount) {
        String key = target + "@" + currentCount; // Include currentCount in the memoization key
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        if (target <= 0 || currentCount >= BOUND) {
            return new Result(0);
        }

        Result closest = new Result(0);
        for (Pill pill : divisors) {
            if (pill.getStrength() <= target && currentCount < BOUND) {
                Result result = closestCombination(divisors, target - pill.getStrength(), memo, currentCount + 1);
                double newValue = result.value + pill.getStrength();
                if (newValue > closest.value && newValue <= target) {
                    closest = new Result(newValue);
                    closest.composition.putAll(result.composition);
                    closest.composition.put(pill, closest.composition.getOrDefault(pill, 0) + 1);
                }
            }
        }

        memo.put(key, closest);
        return closest;
    }

    public static void main(String[] args) {
        List<Pill> divisors = new ArrayList<>(List.of(
                new Pill("", 5),
                new Pill("", 2.5)));

        double target = 9;
        Map<String, Result> memo = new HashMap<>();

        Result result = closestCombination(divisors, target, memo, 0); // Start with a count of 0
        System.out.println("Closest Value: " + result.value);
        System.out.println("Composition: ");
        result.composition.forEach((k, v) -> System.out.println(k.getStrength() + "mg x " + v));
    }
}
