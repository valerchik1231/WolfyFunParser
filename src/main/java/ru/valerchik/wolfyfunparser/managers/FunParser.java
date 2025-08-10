package ru.valerchik.wolfyfunparser.managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FunParser {

    private final Pattern numberPattern;
    private final int maxKCount;
    private static final long MAX_SAFE_VALUE = Long.MAX_VALUE / 1000;

    public FunParser(int maxKCount) throws PatternSyntaxException {
        if (maxKCount < 1) {
            throw new IllegalArgumentException("maxKCount должно быть больше 0");
        }

        this.maxKCount = maxKCount;

        String kPattern = "[kK]+";
        this.numberPattern = Pattern.compile("\\b(\\d+(?:\\.\\d+)?)(" + kPattern + ")\\b");
    }

    public String parseNumbers(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        Matcher matcher = numberPattern.matcher(input);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String numberStr = matcher.group(1);
            String suffix = matcher.group(2).toLowerCase();

            try {
                double number = Double.parseDouble(numberStr);

                if (suffix.length() > maxKCount) {
                    long finalNumber = (long) number;
                    matcher.appendReplacement(result, String.valueOf(finalNumber));
                    continue;
                }

                if (number < 0 || number > MAX_SAFE_VALUE) {
                    matcher.appendReplacement(result, Matcher.quoteReplacement(matcher.group(0)));
                    continue;
                }

                long multiplier = calculateMultiplier(suffix.length());

                if (number > (double)Long.MAX_VALUE / multiplier) {
                    matcher.appendReplacement(result, Matcher.quoteReplacement(matcher.group(0)));
                    continue;
                }

                long finalNumber = (long) (number * multiplier);
                matcher.appendReplacement(result, String.valueOf(finalNumber));

            } catch (NumberFormatException e) {
                matcher.appendReplacement(result, Matcher.quoteReplacement(matcher.group(0)));
            }
        }

        matcher.appendTail(result);
        return result.toString();
    }

    private long calculateMultiplier(int kCount) {
        return (long) Math.pow(1000, kCount);
    }

    public boolean containsNumberNotation(String text) {
        return text != null && !text.isEmpty() && numberPattern.matcher(text).find();
    }
}