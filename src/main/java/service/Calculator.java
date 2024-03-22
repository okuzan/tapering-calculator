package service;

import model.Medication;
import model.TaperingStep;

import java.util.List;

public interface Calculator {
    List<TaperingStep> calculate(Medication medication,
                                 double reductionRate,
                                 boolean firstStep,
                                 Integer duration
    );
}
