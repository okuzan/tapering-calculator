package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Medication {
    private String name;
    private double initialDosage;
    private double finalDosage;
    private List<Pill> pills;
}
