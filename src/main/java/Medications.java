import model.Medication;
import model.Pill;

import java.util.ArrayList;
import java.util.List;

public final class Medications {
    public static final String APL = "APL";
    public static final String REGULAR = "Regular";

    // Agomelatin
    public static final Medication AGOMELATIN = new Medication(
            "Agomelatin",
            25,
            1,
            new ArrayList<>(List.of(
                    new Pill(APL, 5),
                    new Pill(APL, 1)
            ))
    );

    // Venlafaxine
    public static final Medication VENLAFAXINE = new Medication(
            "Venlafaxine",
            225,
            2,
            new ArrayList<>(List.of(
                    new Pill(REGULAR, 37.5),
                    new Pill(REGULAR, 75),
                    new Pill(APL, 2),
                    new Pill(APL, 5),
                    new Pill(APL, 18.75)
            ))
    );

    // Paroxetin
    public static final Medication PAROXETIN = new Medication(
            "Paroxetin",
            20,
            1,
            new ArrayList<>(List.of(
                    new Pill(REGULAR, 10),
                    new Pill(APL, 1),
                    new Pill(APL, 2.5)
            ))
    );

    // Nortriptylin
    public static final Medication NORTRIPTYLIN = new Medication(
            "Nortriptylin",
            25,
            2,
            new ArrayList<>(List.of(
                    new Pill(APL, 5),
                    new Pill(APL, 2)
            ))
    );

    // Moklobemid
    public static final Medication MOKLOBEMID = new Medication(
            "Moklobemid",
            150,
            5,
            new ArrayList<>(List.of(
                    new Pill(APL, 5),
                    new Pill(APL, 30),
                    new Pill(APL, 90)
            ))
    );

    // Fluoxetin
    public static final Medication FLUOXETIN = new Medication(
            "Fluoxetin",
            20,
            2,
            new ArrayList<>(List.of(
                    new Pill(APL, 2),
                    new Pill(REGULAR, 10)
            ))
    );

    // Bupropion
    public static final Medication BUPROPION = new Medication(
            "Bupropion",
            300,
            5,
            new ArrayList<>(List.of(
                    new Pill(APL, 5),
                    new Pill(APL, 10),
                    new Pill(APL, 20),
                    new Pill(APL, 50),
                    new Pill(REGULAR, 150)
            ))
    );

    // amitriptyline
    public static final Medication AMITRIPTYLINE = new Medication(
            "amitriptyline",
            50,
            2.5,
            new ArrayList<>(List.of(
                    new Pill(APL, 2.5),
                    new Pill(APL, 5),
                    new Pill(APL, 10),
                    new Pill(REGULAR, 44.2),
                    new Pill(REGULAR, 25),
                    new Pill(REGULAR, 22.1),
                    new Pill(REGULAR, 10),
                    new Pill(REGULAR, 8.8)
            ))
    );


    // Citalopram
    public static final Medication CITALOPRAM = new Medication(
            "Citalopram",
            70,
            2,
            new ArrayList<>(List.of(
                    new Pill(APL, 2),
                    new Pill(APL, 5),
                    new Pill(REGULAR, 10),
                    new Pill(REGULAR, 15),
                    new Pill(REGULAR, 20),
                    new Pill(REGULAR, 30)
            ))
    );
    public static final Medication SERTRALIN = new Medication(
            "Sertralin",
            100,
            2,
            new ArrayList<>(List.of(
                    new Pill(APL, 2),
                    new Pill(APL, 5),
                    new Pill(REGULAR, 25),
                    new Pill(REGULAR, 50)
            ))
    );
}
