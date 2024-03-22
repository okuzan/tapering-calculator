package service;

import model.TaperingStep;

import java.util.List;

public class Generator {
    public String writeAndPrintTranscript(List<TaperingStep> steps) {
        StringBuilder transcriptBuilder = new StringBuilder();

        // Printing steps to console and appending to StringBuilder
        for (int i = 0; i < steps.size(); i++) {
            TaperingStep step = steps.get(i);
            step.setNumber(i + 1);
            System.out.println(step);
            transcriptBuilder.append(step).append("\n");
        }

        return transcriptBuilder.toString();
    }

    public String generateTaperingPlanString(List<TaperingStep> steps) {
        StringBuilder content = new StringBuilder();
        content.append("Calculated Dose,Regular,APL,Regenboogen\n");

        for (TaperingStep step : steps) {
            content.append((step.getDosageAfter()));
            content.append(",");
            // For each producer, collect pills into a comma-separated list enclosed in quotes
            content.append("\"");
            content.append(String.join(",", step.getPillsForCSVByProducer("Regular")));
            content.append("\"");
            content.append(",");
            content.append("\"");
            content.append(String.join(",", step.getPillsForCSVByProducer("APL")));
            content.append("\"");
            content.append(",");
            content.append("\"");
            content.append(String.join(",", step.getPillsForCSVByProducer("Regenboogen")));
            content.append("\"");
            content.append("\n");
        }
        return content.toString();
    }
}
