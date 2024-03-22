package model;

import lombok.Getter;

@Getter
public enum Step {
    SLOW(0.05),

    STANDARD(0.10),

    FAST(0.15);

    private final double dosage;

    Step(double dosage) {
        this.dosage = dosage;
    }
}
