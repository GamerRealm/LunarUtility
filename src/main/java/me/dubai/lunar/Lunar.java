package me.dubai.lunar;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import me.dubai.lunar.utils.*;
import me.dubai.lunar.commands.*;
import me.dubai.lunar.listeners.*;
import java.util.concurrent.TimeUnit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.dubai.lunar.utils.command.CommandFramework;
import com.lunarclient.bukkitapi.cooldown.LCCooldown;
import com.lunarclient.bukkitapi.cooldown.LunarClientAPICooldown;

@Getter
public class Lunar extends JavaPlugin {

    @Getter
    public static Lunar instance;
    private CommandFramework commandFramework;

    @Override
    public void onEnable() {
        instance = this;
        commandFramework = new CommandFramework(this);
        saveDefaultConfig();
        registerlunar();
        CC.StartupMessage();
    }

    @Override
    public void onDisable() {
        instance = null;
        CC.StopMessage();
    }

    private void registerlunar() {
        int gapple = ConfigFile.getConfig().getInt("COOLDOWN.ENDERPEARL.DELAY");
        int enderpearl = ConfigFile.getConfig().getInt("COOLDOWN.GAPPLE.DELAY");

        commandFramework.registerCommands(new LunarCommand());
        commandFramework.registerCommands(new LunarStaffCommand());

        LunarClientAPICooldown.registerCooldown(new LCCooldown("Enderpearl", gapple, TimeUnit.SECONDS, Material.ENDER_PEARL));
        LunarClientAPICooldown.registerCooldown(new LCCooldown("Gapple", enderpearl, TimeUnit.SECONDS, Material.GOLDEN_APPLE));

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new LunarListener(), this);
    }
}
//TODO: Add a toggleable option for the cooldowns, Add a config handler to make the code look cleaner.