import lombok.SneakyThrows;
import model.Duration;
import model.Medication;
import model.Step;
import model.TaperingStep;
import service.Calculator;
import service.Generator;
import service.KnapsackCalculator;
import service.Writer;

import java.util.List;

public class App {
    private static final String RESOURCES = "src/main/resources/one/";
    private static final String PLAN_TEMPLATE = RESOURCES + "%s_%s_%s.csv";
    private static final String TRANSCRIPT_TEMPLATE = RESOURCES + "%s_%s_%s_transcript.txt";
    private static final Writer writer = new Writer();
    private final static Calculator planner = new KnapsackCalculator();
    private final static Generator generator = new Generator();


    public static void main(String[] args) {
        Medication medication = Medications.AMITRIPTYLINE;
        prepareWholePackage(medication);
        prepareOnePlan(medication, Step.SLOW, null);
    }

    public static void prepareWholePackage(Medication medication) {
        for (Step step : Step.values()) {
            for (Duration duration : Duration.values()) {
                prepareOnePlan(medication, step, duration);
            }
        }
    }

    public static void prepareOnePlan(Medication medication, Step step, Duration duration) {
        List<TaperingStep> steps = planner.calculate(
                medication,
                step.getDosage(),
                true,
                duration == null ? null : duration.getDays()
        );
        generateFiles(steps, medication, step, duration);
    }

    @SneakyThrows
    public static void generateFiles(List<TaperingStep> steps,
                                     Medication medication,
                                     Step step,
                                     Duration duration) {

        String transcriptFilename = getFilename(TRANSCRIPT_TEMPLATE, medication.getName(), step, duration);
        String transcript = generator.writeAndPrintTranscript(steps);
        writer.write(transcriptFilename, transcript);

        String taperingFilename = getFilename(PLAN_TEMPLATE, medication.getName(), step, duration);
        String plan = generator.generateTaperingPlanString(steps);
        writer.write(taperingFilename, plan);
    }

    public static String getFilename(String template, String medication, Step step, Duration duration) {
        return String.format(
                template,
                medication,
                step.name().toLowerCase(),
                duration == null ? "_" : duration.name().toLowerCase()
        );
    }
}
