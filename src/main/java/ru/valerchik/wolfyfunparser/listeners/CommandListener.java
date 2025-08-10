package ru.valerchik.wolfyfunparser.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import ru.valerchik.wolfyfunparser.WolfyFunParser;
import ru.valerchik.wolfyfunparser.managers.FunParser;
import ru.valerchik.wolfyfunparser.utils.PatternUtils;

public class CommandListener implements Listener {

    private final WolfyFunParser plugin;

    public CommandListener(WolfyFunParser plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        try {
            String originalCommand = event.getMessage();
            if (originalCommand.trim().isEmpty()) {
                return;
            }

            String processedCommand = processCommand(originalCommand);

            if (!originalCommand.equals(processedCommand)) {
                event.setMessage(processedCommand);
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Ошибка при обработке команды игрока: " + e.getMessage());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerCommand(ServerCommandEvent event) {
        try {
            String originalCommand = "/" + event.getCommand();
            String processedCommand = processCommand(originalCommand);

            if (!originalCommand.equals(processedCommand)) {
                event.setCommand(processedCommand.substring(1));
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Ошибка при обработке серверной команды: " + e.getMessage());
        }
    }

    private String processCommand(String command) {
        if (command == null || command.trim().isEmpty()) {
            return command;
        }

        PatternUtils patternUtils = plugin.getPatternUtils();
        FunParser numberParser = plugin.getFunParser();

        if (patternUtils == null || numberParser == null) {
            return command;
        }

        return patternUtils.processCommand(command, numberParser);
    }
}