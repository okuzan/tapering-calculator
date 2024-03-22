package model;

import lombok.Getter;

@Getter
public enum Duration {
    MONTH(28),
    FORTNIGHT(14),
    WEEK(7);

    private final int days;

    Duration(int i) {
        days = i;
    }
}
