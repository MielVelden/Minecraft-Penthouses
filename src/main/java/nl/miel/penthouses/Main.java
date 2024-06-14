package nl.miel.penthouses;

import net.milkbowl.vault.economy.Economy;
import nl.miel.penthouses.Managers.EconomyManager;
import nl.miel.penthouses.Commands.onAdmin;
import nl.miel.penthouses.Commands.onCommand;
import nl.miel.penthouses.Listeners.BlockListener;
import nl.miel.penthouses.Listeners.ClickListener;
import nl.miel.penthouses.Listeners.OnNPCClick;
import nl.miel.penthouses.Managers.ConfigManager;
import nl.miel.penthouses.Managers.DataManager;
import nl.miel.penthouses.Managers.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private Economy econ;

    @Override
    public void onEnable() {
        ConfigManager.setupConfig(this);
        DataManager.setup();
        ConfigManager.setup();
        MessageManager.setup();
        if(!new EconomyManager(this, "Q1GA3-NQP9V-5RS3I-66ULB-78J4D", "http://license.limecode.nl:54321/api/client", "49778d023259324d0b8af697285b7c44107b2eda").verify()) {
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getScheduler().cancelTasks(this);
            return;
        }
        if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        //DataManager.save();
        Boolean block = ConfigManager.get().getBoolean("Use-Block");
        if(block) {
            Bukkit.getPluginManager().registerEvents(new BlockListener(this), this);
        } else {
            Bukkit.getPluginManager().registerEvents(new OnNPCClick(this), this);
        }
        Bukkit.getPluginManager().registerEvents(new ClickListener(this), this);

        getCommand("penthouse").setExecutor(new onCommand(this));
        getCommand("padmin").setExecutor(new onAdmin(this));
        System.out.println(Logger.color("§2╭────────────────────────────────────────╮"));
        System.out.println(Logger.color("§2│        §aPenthouses §2- §aDevelopment§2        │"));
        System.out.println(Logger.color("§2│                §aPlugin§2                  │"));
        System.out.println(Logger.color("§2│                                        │"));
        System.out.println(Logger.color("§2│               Version §a1.0§2              │"));
        System.out.println(Logger.color("§2│   Discord: §adiscord.gg/beKZbQ9vxe§2   │"));
        System.out.println(Logger.color("§2╰────────────────────────────────────────╯"));

    }
    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Economy getEconomy() {
        return econ;
    }
}
