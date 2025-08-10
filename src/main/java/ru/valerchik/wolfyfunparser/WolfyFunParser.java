package ru.valerchik.wolfyfunparser;

import org.bukkit.plugin.java.JavaPlugin;
import ru.valerchik.wolfyfunparser.commands.FunParserCommand;
import ru.valerchik.wolfyfunparser.listeners.CommandListener;
import ru.valerchik.wolfyfunparser.managers.FunParser;
import ru.valerchik.wolfyfunparser.utils.PatternUtils;

import java.util.Objects;

public class WolfyFunParser extends JavaPlugin {

    private FunParser funParser;
    private PatternUtils patternUtils;
    private int maxKCount;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        loadConfig();

        Objects.requireNonNull(getCommand("wolfyfunparser")).setExecutor(new FunParserCommand(this));

        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

        getLogger().info("WolfyFunParser включился");
    }

    public void loadConfig() {
        try {
            reloadConfig();

            maxKCount = getConfig().getInt("max-k-count", 4);

            if (maxKCount < 1) {
                maxKCount = 1;
            } else if (maxKCount > 10) {
                maxKCount = 10;
            }

            funParser = new FunParser(maxKCount);

            patternUtils = new PatternUtils(getConfig().getStringList("commands"));
        } catch (Exception e) {
            getLogger().severe("Ошибка при загрузке конфигурации: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public FunParser getFunParser() {
        return funParser;
    }

    public PatternUtils getPatternUtils() {
        return patternUtils;
    }
}