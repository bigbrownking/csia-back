package org.agro.agrohack.utils;

import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TimeTranslator {

    private static final Pattern TIME_PATTERN = Pattern.compile(
            "(?i)(?:Once(?: every)? (\\d+)(?:-(\\d+))? (day|week|month)s?)|" + // Matches "Once every 2 days", "Once every 1-2 weeks"
                    "(?:Once a (day|week|month))|" + // Matches "Once a week"
                    "(?:Every (day|week|month))|" + // Matches "Every day"
                    "(?:(\\d+) times a (week|month))|" + // Matches "2 times a week"
                    "(?:Twice a (week|month))" // Matches "Twice a week"
    );

    public int parseTimeToDays(String timeString) {
        if (timeString == null || timeString.isEmpty()) {
            throw new IllegalArgumentException("Time string cannot be null or empty");
        }

        Matcher matcher = TIME_PATTERN.matcher(timeString.toLowerCase());
        if (matcher.find()) {
            if (matcher.group(1) != null) { // Case: "Once every X days/weeks/months" or "Once every X-Y weeks"
                int minValue = Integer.parseInt(matcher.group(1));
                int maxValue = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : minValue;
                String unit = matcher.group(3);

                int factor = switch (unit) {
                    case "week" -> 7;
                    case "month" -> 30;
                    default -> 1;
                };

                return (int) Math.round(((minValue + maxValue) / 2.0) * factor);
            }

            if (matcher.group(4) != null || matcher.group(5) != null) { // Case: "Once a week" or "Every week"
                String unit = matcher.group(4) != null ? matcher.group(4) : matcher.group(5);
                return switch (unit) {
                    case "week" -> 7;
                    case "month" -> 30;
                    default -> 1;
                };
            }

            if (matcher.group(6) != null) { // Case: "2 times a week"
                int times = Integer.parseInt(matcher.group(6));
                String unit = matcher.group(7);

                int totalDays = switch (unit) {
                    case "week" -> 7;
                    case "month" -> 30;
                    default -> 1;
                };

                return totalDays / times; // "2 times a week" -> every 3-4 days
            }

            if (matcher.group(8) != null) { // Case: "Twice a week"
                String unit = matcher.group(8);
                int totalDays = switch (unit) {
                    case "week" -> 7;
                    case "month" -> 30;
                    default -> 1;
                };

                return totalDays / 2; // "Twice a week" = 3-4 days
            }
        }

        throw new IllegalArgumentException("Invalid time format: " + timeString);
    }
}
