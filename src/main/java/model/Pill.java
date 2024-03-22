package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pill implements Comparable<Pill> {
    private String producer;
    private double strength;

    @Override
    public int compareTo(Pill other) {
        return Double.compare(other.strength, strength); //sort in descending order
    }
}