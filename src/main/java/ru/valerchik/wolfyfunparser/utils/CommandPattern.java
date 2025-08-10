package ru.valerchik.wolfyfunparser.utils;

import ru.valerchik.wolfyfunparser.managers.FunParser;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class CommandPattern {

    private final String originalPattern;
    private final Pattern compiledPattern;
    private final int parseStartIndex;

    public CommandPattern(String pattern) throws PatternSyntaxException {
        if (pattern == null || pattern.trim().isEmpty()) {
            throw new IllegalArgumentException("Паттерн команды не может быть пустым");
        }

        this.originalPattern = pattern.trim();

        String[] parts = originalPattern.split("\\s+");
        StringBuilder regexBuilder = new StringBuilder("^");

        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                regexBuilder.append("\\s+");
            }

            String part = parts[i];
            if ("$arg".equals(part)) {
                regexBuilder.append("\\S+");
            } else {
                regexBuilder.append(Pattern.quote(part));
            }
        }

        this.parseStartIndex = parts.length;

        regexBuilder.append("(?:\\s+.*)?$");

        try {
            this.compiledPattern = Pattern.compile(regexBuilder.toString(), Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException e) {
            throw new PatternSyntaxException("Ошибка в паттерне команды: " + pattern, regexBuilder.toString(), e.getIndex());
        }
    }

    public boolean matches(String command) {
        if (command == null || command.trim().isEmpty()) {
            return false;
        }
        return compiledPattern.matcher(command.trim()).matches();
    }

    public String processCommand(String command, FunParser parser) {
        if (command == null || parser == null || !matches(command)) {
            return command;
        }

        String trimmedCommand = command.trim();
        String[] commandParts = trimmedCommand.split("\\s+");

        if (commandParts.length <= parseStartIndex) {
            return command;
        }

        String[] resultParts = commandParts.clone();

        boolean hasChanges = false;
        for (int i = parseStartIndex; i < resultParts.length; i++) {
            String originalArg = resultParts[i];
            if (parser.containsNumberNotation(originalArg)) {
                String parsedArg = parser.parseNumbers(originalArg);
                if (!originalArg.equals(parsedArg)) {
                    resultParts[i] = parsedArg;
                    hasChanges = true;
                }
            }
        }

        return hasChanges ? String.join(" ", resultParts) : command;
    }
}