package model;

import java.util.HashMap;
import java.util.Map;

public class Result {
    public double value;
    public Map<Pill, Integer> composition = new HashMap<>();

    public Result(double value) {
        this.value = value;
    }
}