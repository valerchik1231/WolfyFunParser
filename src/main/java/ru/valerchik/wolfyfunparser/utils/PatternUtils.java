package ru.valerchik.wolfyfunparser.utils;

import org.bukkit.Bukkit;
import ru.valerchik.wolfyfunparser.managers.FunParser;

import java.util.ArrayList;
import java.util.List;

public class PatternUtils {

    private final List<CommandPattern> commandPatterns;

    public PatternUtils(List<String> commandStrings) {
        this.commandPatterns = new ArrayList<>();

        if (commandStrings != null && !commandStrings.isEmpty()) {
            for (String cmd : commandStrings) {
                if (cmd == null || cmd.trim().isEmpty()) {
                    continue;
                }

                try {
                    commandPatterns.add(new CommandPattern(cmd.trim()));
                } catch (Exception e) {
                    Bukkit.getLogger().warning("Ошибка при загрузке команды: " + cmd + " - " + e.getMessage());
                }
            }
        }
    }

    public String processCommand(String command, FunParser parser) {
        if (command == null || command.trim().isEmpty() || parser == null) {
            return command;
        }

        for (CommandPattern pattern : commandPatterns) {
            try {
                if (pattern.matches(command)) {
                    return pattern.processCommand(command, parser);
                }
            } catch (Exception e) {
                Bukkit.getLogger().warning("Ошибка при обработке команды с паттерном: " + e.getMessage());
            }
        }
        return command;
    }
}