package nl.miel.penthouses.Commands;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.util.Colorizer;
import net.milkbowl.vault.economy.Economy;
import nl.miel.penthouses.Logger;
import nl.miel.penthouses.Main;
import nl.miel.penthouses.Managers.ConfigManager;
import nl.miel.penthouses.Managers.DataManager;
import nl.miel.penthouses.Managers.WorldGuardManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.Iterator;
import java.util.List;

public class onCommand implements CommandExecutor {
    private Main main;
    public onCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        PluginDescriptionFile pdfFile = main.getDescription();
        String version = pdfFile.getVersion();
        if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            if (p.hasPermission("penthouses.admin")) {
                p.sendMessage(Logger.color(""));
                p.sendMessage(Logger.color("&aMINETOPIA&8-&2PENTHOUSES"));
                p.sendMessage(Logger.color(""));
                p.sendMessage(Logger.color("&a/penthouse &2addmember &2(speler) &8- &aVoeg een speler toe "));
                p.sendMessage(Logger.color("&a/penthouse &2removemember &2(speler) &8- &aVerwijder een speler"));
                p.sendMessage(Logger.color("&a/penthouse &2verlaat &8- &aVerlaat een gebouw"));
                p.sendMessage(Logger.color("&a/penthouse &2verkoop &8- &aVerkoop dit huis"));
                p.sendMessage(Logger.color(""));
                p.sendMessage(Logger.color("&a/padmin &8- &aKrijg het admin menu"));
                p.sendMessage(Logger.color(""));
                p.sendMessage(Logger.color("&2Versie: &a" + version));
                p.sendMessage(Logger.color("&2Door: &aMister_Miel"));
                p.sendMessage(Logger.color("&2Discord: &aMister_Miel#9949"));
            } else {
                p.sendMessage(Logger.color(""));
                p.sendMessage(Logger.color("&aMINETOPIA&8-&2PENTHOUSES"));
                p.sendMessage(Logger.color(""));
                p.sendMessage(Logger.color("&a/penthouse &2addmember &2(speler) &8- &aVoeg een speler toe "));
                p.sendMessage(Logger.color("&a/penthouse &2removemember &2(speler) &8- &aVerwijder een speler"));
                p.sendMessage(Logger.color("&a/penthouse &2verlaat &8- &aVerlaat een gebouw"));
                p.sendMessage(Logger.color("&a/penthouse &2verkoop &8- &aVerkoop dit huis"));
                p.sendMessage(Logger.color(""));
                p.sendMessage(Logger.color("&2Versie: &a" + version));
                p.sendMessage(Logger.color("&2Door: &aMister_Miel"));
                p.sendMessage(Logger.color("&2Discord: &aMister_Miel#9949"));
            }
        } else if (args.length >= 1 && args[0].equalsIgnoreCase("addmember")) {
            if (args.length == 2) {
                if(WorldGuardManager.plotExists(p.getLocation())) {
                    if(WorldGuardManager.getRegionOwner(p.getLocation()).equalsIgnoreCase(p.getUniqueId().toString())) {
                        String plotName = WorldGuardManager.getRegionName(p.getLocation());
                        String gebouw = DataManager.getBuildingByPenthouse(plotName);
                        Player m = Bukkit.getPlayer(args[1]);
                            if (m != null) {
                                if(DataManager.getMembers(gebouw, plotName).contains(m.getUniqueId().toString())) {
                                    p.sendMessage(Logger.color("&cDeze speler is al in deze gebouw!"));
                                } else {
                                    WorldGuardManager.addMember(p.getLocation(), m);
                                    DataManager.addMember(gebouw, plotName, m.getUniqueId().toString());
                                    p.sendMessage(Logger.color("&aDe speler is toegevoegd aan deze gebouw!"));
                                }
                            } else {
                                p.sendMessage(Logger.color("&cDeze speler is niet online!"));
                            }

                    } else {
                        p.sendMessage(Logger.color("&cJe bent niet de eigenaar van dit plot!"));
                        return true;
                    }
                } else {
                    p.sendMessage(Logger.color("&cJe moet in een plot zitten."));
                }
            } else {
                p.sendMessage(Logger.color("&cGebruik /penthouse &4addmember (naam)"));
            }
        } else if (args.length >= 1 && args[0].equalsIgnoreCase("removemember")) {
            if (args.length == 2) {
                if(WorldGuardManager.plotExists(p.getLocation())) {
                    if(WorldGuardManager.getRegionOwner(p.getLocation()).equalsIgnoreCase(p.getUniqueId().toString())) {
                        String plotName = WorldGuardManager.getRegionName(p.getLocation());
                        String gebouw = DataManager.getBuildingByPenthouse(plotName);
                        Player m = Bukkit.getPlayer(args[1]);
                        if (m != null) {
                            if(DataManager.getMembers(gebouw, plotName).contains(m.getUniqueId().toString())) {
                                DataManager.removeMember(gebouw, plotName, m.getUniqueId().toString());
                                WorldGuardManager.removeMember(p.getLocation(), m);
                                p.sendMessage(Logger.color("&cDeze speler is verwijderd uit deze kamer!"));
                            } else {
                                p.sendMessage(Logger.color("&cDe speler behoort niet tot dit gebouw!"));
                            }
                        } else {
                            p.sendMessage(Logger.color("&cDeze speler is niet online!"));
                        }

                    } else {
                        p.sendMessage(Logger.color("&cJe bent niet de eigenaar van dit plot!"));
                        return true;
                    }
                } else {
                    p.sendMessage(Logger.color("&cJe moet in een plot zitten."));
                }
            } else {
                p.sendMessage(Logger.color("&cGebruik /penthouse &4removemember (naam)"));
            }
        } else if (args.length >= 1 && args[0].equalsIgnoreCase("verlaat")) {
            if(!WorldGuardManager.plotExists(p.getLocation())){
                p.sendMessage(Logger.color("Je staat niet op het plot"));
                return true;
            } else {
                String plotName = WorldGuardManager.getRegionName(p.getLocation());
                String building = DataManager.getBuildingByPenthouse(plotName);
                if(DataManager.penthouseExists(building, plotName)) {
                    Location spawn = DataManager.getBuildingSpawn(p.getLocation().getWorld(), building);
                    p.teleport(spawn);
                    p.sendMessage(Logger.color("&aJe wordt geteleporteerd naar de uitgang"));
                } else {
                    p.sendMessage(Logger.color("&cDit plot is geen appartement"));
                    return true;
                }

            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("verkoop")) {
            if (!WorldGuardManager.plotExists(p.getLocation())) {
                p.sendMessage(Logger.color("Je staat niet op het plot"));
                return true;
            } else {
                String plotName = WorldGuardManager.getRegionName(p.getLocation());
                String building = DataManager.getBuildingByPenthouse(plotName);
                if (DataManager.penthouseExists(building, plotName)) {
                    if (WorldGuardManager.getRegionOwner(p.getLocation()).contains(p.getUniqueId().toString())) {
                        WorldGuardManager.clearPlot(p.getLocation());
                        DataManager.clearPenthouse(building, plotName);
                        Location spawn = DataManager.getBuildingSpawn(p.getLocation().getWorld(), building);
                        p.teleport(spawn);
                        p.sendMessage(Logger.color("&aJe wordt geteleporteerd naar de uitgang en je plot word verkocht"));
                        Economy economy = main.getEconomy();
                        double price = DataManager.getPrice(building, plotName);
                        double percentage = ConfigManager.getPercentage();
                        double newPrice = price * (percentage/100);
                        economy.depositPlayer(p, newPrice);
                    } else {
                        p.sendMessage(Logger.color("&cJe bent niet de eigenaar van dit plot!"));
                        return true;
                    }
                } else {
                    p.sendMessage(Logger.color("&cDit plot is geen appartement"));
                    return true;
                }
            }
        } else {
            if (p.hasPermission("penthouses.admin")) {
                p.sendMessage(Logger.color(""));
                p.sendMessage(Logger.color("&aMINETOPIA&8-&2PENTHOUSES"));
                p.sendMessage(Logger.color(""));
                p.sendMessage(Logger.color("&a/penthouse &2addmember &2(speler) &8- &aVoeg een speler toe "));
                p.sendMessage(Logger.color("&a/penthouse &2removemember &2(speler) &8- &aVerwijder een speler"));
                p.sendMessage(Logger.color("&a/penthouse &2verlaat &8- &aVerlaat een gebouw"));
                p.sendMessage(Logger.color("&a/penthouse &2verkoop &8- &aVerkoop dit huis"));
                p.sendMessage(Logger.color(""));
                p.sendMessage(Logger.color("&a/padmin &8- &aKrijg het admin menu"));
                p.sendMessage(Logger.color(""));
                p.sendMessage(Logger.color("&2Versie: &a" + version));
                p.sendMessage(Logger.color("&2Door: &aMister_Miel"));
                p.sendMessage(Logger.color("&2Discord: &aMister_Miel#9949"));
            } else {
                p.sendMessage(Logger.color(""));
                p.sendMessage(Logger.color("&aMINETOPIA&8-&2PENTHOUSES"));
                p.sendMessage(Logger.color(""));
                p.sendMessage(Logger.color("&a/penthouse &2addmember &2(speler) &8- &aVoeg een speler toe "));
                p.sendMessage(Logger.color("&a/penthouse &2removemember &2(speler) &8- &aVerwijder een speler"));
                p.sendMessage(Logger.color("&a/penthouse &2verlaat &8- &aVerlaat een gebouw"));
                p.sendMessage(Logger.color("&a/penthouse &2verkoop &8- &aVerkoop dit huis"));
                p.sendMessage(Logger.color(""));
                p.sendMessage(Logger.color("&2Versie: &a" + version));
                p.sendMessage(Logger.color("&2Door: &aMister_Miel"));
                p.sendMessage(Logger.color("&2Discord: &aMister_Miel#9949"));
            }
        }

        return false;
    }
}
