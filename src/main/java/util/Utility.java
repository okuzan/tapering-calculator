package util;

import lombok.experimental.UtilityClass;
import model.Step;

@UtilityClass
public final class Utility {
    public static double round(double number) {
        return Math.round(number * 100.0) / 100.0;
    }

    public static double dosageToNumber(Step step) {
        return (int) ((step.getDosage()) * 100);
    }
}
