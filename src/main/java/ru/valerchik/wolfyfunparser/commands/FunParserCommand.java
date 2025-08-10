package ru.valerchik.wolfyfunparser.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.valerchik.wolfyfunparser.WolfyFunParser;

public class FunParserCommand implements CommandExecutor {

    private final WolfyFunParser plugin;

    public FunParserCommand(WolfyFunParser plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("wolfyfunparser")) {
            return false;
        }

        if (!sender.hasPermission("wolfyfunparser.admin")) {
            return true;
        }

        if (args.length == 0) return true;

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.loadConfig();
            sender.sendMessage("§aКонфигурация успешно перезагружена!");

            return true;
        }

        return true;
    }
}