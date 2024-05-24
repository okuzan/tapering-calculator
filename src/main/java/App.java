import lombok.SneakyThrows;
import model.Medication;
import model.Step;
import model.TaperingStep;
import service.Calculator;
import service.Generator;
import service.KnapsackCalculator;
import service.Writer;
import util.Utility;

import java.util.List;

public class App {
    private static final String RESOURCES = "src/main/resources/";
    private static final String PLAN_TEMPLATE = RESOURCES + "%s_%s.csv";
    private static final String TRANSCRIPT_TEMPLATE = RESOURCES + "%s_%s_transcript.txt";
    private static final Writer writer = new Writer();
    private final static Calculator planner = new KnapsackCalculator();
    private final static Generator generator = new Generator();


    public static void main(String[] args) {
        Medication medication = Medications.CITALOPRAM;
        prepareWholePackage(medication);
//        prepareOnePlan(medication, Step.SLOW);
    }

    public static void prepareWholePackage(Medication medication) {
        for (Step step : Step.values()) {
            prepareOnePlan(medication, step);
        }
    }

    public static void prepareOnePlan(Medication medication, Step step) {
        List<TaperingStep> steps = planner.calculate(
                medication,
                step.getDosage(),
                true
        );
        generateFiles(steps, medication, step);
    }

    @SneakyThrows
    public static void generateFiles(List<TaperingStep> steps,
                                     Medication medication,
                                     Step step) {

        String transcriptFilename = getFilename(TRANSCRIPT_TEMPLATE, medication.getName(), step);
        String transcript = generator.writeAndPrintTranscript(steps);
        writer.write(transcriptFilename, transcript);

        String taperingFilename = getFilename(PLAN_TEMPLATE, medication.getName(), step);
        String plan = generator.generateTaperingPlanString(steps);
        writer.write(taperingFilename, plan);
    }

    public static String getFilename(String template, String medication, Step step) {
        return String.format(
                template,
                medication,
                Utility.dosageToNumber(step)
        );
    }
}
