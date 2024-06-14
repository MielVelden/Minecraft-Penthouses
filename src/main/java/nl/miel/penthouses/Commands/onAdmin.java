package nl.miel.penthouses.Commands;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.util.Colorizer;
import nl.miel.penthouses.Logger;
import nl.miel.penthouses.Main;
import nl.miel.penthouses.Managers.ConfigManager;
import nl.miel.penthouses.Managers.DataManager;
import nl.miel.penthouses.Managers.MessageManager;
import nl.miel.penthouses.Managers.WorldGuardManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class onAdmin implements CommandExecutor {
    private Main main;
    public onAdmin(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if(p.hasPermission("penthouse.admin")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
                if (p.hasPermission("penthouses.admin")) {
                    p.sendMessage(Logger.color(""));
                    p.sendMessage(Logger.color("&aMINETOPIA&8-&2PENTHOUSES"));
                    p.sendMessage(Logger.color(""));
                    p.sendMessage(Logger.color("&a/padmin &2createbuilding &2(naam) &8- &aMaak een gebouw aan"));
                    p.sendMessage(Logger.color("&a/padmin &2setbuildingspawn &2(gebouw) &8- &aPas de plek van de spawn aan"));
                    p.sendMessage(Logger.color("&a/padmin &2deletebuilding &2(gebouw) &8- &aVerwijder een gebouw"));
                    p.sendMessage(Logger.color("&a/padmin &2create &2(gebouw) (prijs) &8- &aMaak een kamer aan"));
                    p.sendMessage(Logger.color("&a/padmin &2delete &8- &aVerwijder de huidige kamer"));
                    p.sendMessage(Logger.color("&a/padmin &2setspawn &8- &aMaak de plek van de spawn aan"));
                    p.sendMessage(Logger.color("&a/padmin &2spawnnpc (gebouw) &8- &aPlaats een npc voor een gebouw"));
                    p.sendMessage(Logger.color("&a/padmin &2setpc (gebouw) &8- &aMaak een gebouw pc aan"));
                } else {
                    p.sendMessage(Logger.color(MessageManager.getResponse("No-Permission")));
                }
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("createbuilding")) {
                if(p.hasPermission("penthouses.admin")) {
                    if (args.length == 2) {
                        String name = args[1];
                        if(DataManager.buildingExists(name)) {
                            p.sendMessage(Logger.color(MessageManager.getResponse("Building-Already-Exists")));
                        } else {
                            DataManager.createBuilding(name, p.getLocation());
                            p.sendMessage(Logger.color(MessageManager.getResponse("Building-Created")));
                        }
                    } else {
                        p.sendMessage(Logger.color("Gebruik: /padmin createbuilding (naam)"));
                    }
                } else {
                    p.sendMessage(Logger.color(MessageManager.getResponse("No-Permission")));
                }
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("setbuildingspawn")) {
                if(p.hasPermission("penthouses.admin")) {
                    if (args.length == 2) {
                        String name = args[1];
                        if(DataManager.buildingExists(name)) {
                            DataManager.setBuildingSpawn(name, p.getLocation());
                            p.sendMessage(Logger.color(MessageManager.getResponse("Building-Spawn-Set")));
                        } else {
                            p.sendMessage(Logger.color(MessageManager.getResponse("Building-Not-Found")));
                        }
                    } else {
                        p.sendMessage(Logger.color("Gebruik: /padmin setbuildingspawn (gebouw)"));
                    }
                } else {
                    p.sendMessage(Logger.color(MessageManager.getResponse("No-Permission")));
                }
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("deletebuilding")) {
                if(p.hasPermission("penthouse.admin")) {
                    if(args.length == 2) {
                        if(DataManager.buildingExists(args[1])) {
                            DataManager.get().set("Data." + args[1], null);
                            DataManager.save();
                            p.sendMessage(Logger.color(MessageManager.getResponse("Building-Deleted")));
                        } else {
                            p.sendMessage(Logger.color(MessageManager.getResponse("Building-Not-Found")));
                        }
                    } else {
                        p.sendMessage(Logger.color("&cGebruik: /padmin &4deletebuilding &4(gebouw)"));
                    }
                } else {
                    p.sendMessage(Logger.color(MessageManager.getResponse("No-Permission")));
                }
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("create")) {
                if(p.hasPermission("penthouse.admin")) {
                    if (args.length == 3) {
                        if(WorldGuardManager.plotExists(p.getLocation())) {
                            String plotName = WorldGuardManager.getRegionName(p.getLocation());
                            String building = args[1];
                            if(DataManager.penthouseExists(building, plotName)) {
                                p.sendMessage(Logger.color(MessageManager.getResponse("Appartement-Already-Exists")));
                                return true;
                            } else {
                                DataManager.createPenthouse(building, plotName, Double.parseDouble(args[2]), p.getLocation());
                                p.sendMessage(Logger.color(MessageManager.getResponse("Appartement-Created")));
                            }

                        } else {
                            p.sendMessage(Logger.color(MessageManager.getResponse("Not-On-A-Plot")));
                            return true;
                        }
                   } else {
                        p.sendMessage(Logger.color("&c/penthouse create (gebouw) (prijs)"));
                    }
                }else {
                    p.sendMessage(Logger.color(MessageManager.getResponse("No-Permission")));
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("delete")) {
                if(p.hasPermission("penthouse.admin")) {
                    if(WorldGuardManager.plotExists(p.getLocation())) {
                        String plotName = WorldGuardManager.getRegionName(p.getLocation());
                        String building = DataManager.getBuildingByPenthouse(plotName);
                        if(building != null) {
                            DataManager.get().set("Data." + building + ".Rooms." + plotName, null);
                            DataManager.save();
                            p.sendMessage(Logger.color(MessageManager.getResponse("Appartement-Deleted")));
                        } else {
                            p.sendMessage(Logger.color(MessageManager.getResponse("Appartement-Not-Found")));
                        }
                    } else {
                        p.sendMessage(Logger.color(MessageManager.getResponse("Not-On-A-Plot")));
                    }
                } else {
                    p.sendMessage(Logger.color(MessageManager.getResponse("No-Permission")));
                }
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("setspawn")) {
                if(p.hasPermission("penthouse.admin")) {
                        if (WorldGuardManager.plotExists(p.getLocation())) {
                            String plotname = WorldGuardManager.getRegionName(p.getLocation());
                            String building = DataManager.getBuildingByPenthouse(plotname);
                            if (DataManager.penthouseExists(building, plotname)) {
                                DataManager.get().set("Data." + building + ".Rooms." + plotname + ".Spawn-Location.X", p.getLocation().getX());
                                DataManager.get().set("Data." + building + ".Rooms." + plotname + ".Spawn-Location.Y", p.getLocation().getY());
                                DataManager.get().set("Data." + building + ".Rooms." + plotname + ".Spawn-Location.Z", p.getLocation().getZ());
                                DataManager.get().set("Data." + building + ".Rooms." + plotname + ".Spawn-Location.Yaw", p.getLocation().getYaw());
                                DataManager.get().set("Data." + building + ".Rooms." + plotname + ".Spawn-Location.Pitch", p.getLocation().getPitch());
                                DataManager.save();
                                p.sendMessage(Logger.color(MessageManager.getResponse("Appartement-Spawn-Set")));
                            } else {
                                p.sendMessage(Logger.color(MessageManager.getResponse("Appartement-Not-Found")));
                            }
                        } else {
                            p.sendMessage(Logger.color(MessageManager.getResponse("Not-On-A-Plot")));
                            return true;
                        }

                } else {
                    p.sendMessage(Logger.color(MessageManager.getResponse("No-Permission")));
                }
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("spawnnpc")) {
                if (p.hasPermission("penthouses.admin")) {
                    if (args.length == 2) {
                        if(DataManager.buildingExists(args[1])) {
                            String name = main.getConfig().getString("NPC-Name");
                            if(name.contains("%building%")) {
                                if(args[1].length() >= 16) {
                                    p.sendMessage(Logger.color("&cDeze naam is te lang"));
                                    return true;
                                } else {
                                    name = name.replace("%building%", args[1]);
                                    NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);
                                    npc.data().set(NPC.PLAYER_SKIN_UUID_METADATA, main.getConfig().getString("BuildingEnter.NPC.Skin"));
                                    npc.setName(Colorizer.parseColors(name));
                                    npc.spawn(p.getLocation());
                                    p.sendMessage(Logger.color(MessageManager.getResponse("Appartement-Spawn-NPC")));
                                }

                            } else {
                                p.sendMessage(Logger.color(MessageManager.getResponse("Appartement-Spawn-NPC-Invalid")));
                            }
                        } else {
                            p.sendMessage(Logger.color(MessageManager.getResponse("Building-Not-Found")));
                        }
                    } else {
                        p.sendMessage(Logger.color(MessageManager.getResponse("Not-Enough-Arguments")));
                    }
                } else {
                    p.sendMessage(Logger.color(MessageManager.getResponse("No-Permission")));
                }
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("setpc")) {
                if(p.hasPermission("penthouse.admin")) {
                    if(args.length == 2) {
                        Boolean isEnabled = ConfigManager.get().getBoolean("Use-Block");
                        if(isEnabled) {
                            String block = ConfigManager.get().getString("Block");
                            Block targetblock = p.getTargetBlock(null,4);
                            //p.sendMessage(block + " " + targetblock.getType());
                            if(block.equalsIgnoreCase(targetblock.getType().toString())) {
                                if(DataManager.buildingExists(args[1])) {
                                    DataManager.get().set("Data." + args[1] + ".PC-Location.X", targetblock.getX());
                                    DataManager.get().set("Data." + args[1] + ".PC-Location.Y", targetblock.getY());
                                    DataManager.get().set("Data." + args[1] + ".PC-Location.Z", targetblock.getZ());
                                    DataManager.save();
                                    p.sendMessage(Logger.color(MessageManager.getResponse("PC-Created")));
                                } else {
                                    p.sendMessage(Logger.color(MessageManager.getResponse("Building-Not-Found")));
                                }
                            } else {
                                p.sendMessage(Logger.color(MessageManager.getResponse("PC-No-Block")));
                                return true;
                            }
                        } else {
                            p.sendMessage(Logger.color(MessageManager.getResponse("Option-Not-Available")));
                        }

                    } else {
                        p.sendMessage(Logger.color(MessageManager.getResponse("Not-Enough-Arguments")));
                    }
                } else {
                    p.sendMessage(Logger.color(MessageManager.getResponse("No-Permission")));
                }
            } else {
                if (p.hasPermission("penthouses.admin")) {
                    p.sendMessage(Logger.color(""));
                    p.sendMessage(Logger.color("&aMINETOPIA&8-&2PENTHOUSES"));
                    p.sendMessage(Logger.color(""));
                    p.sendMessage(Logger.color("&a/padmin &2createbuilding &2(naam) &8- &aMaak een gebouw aan"));
                    p.sendMessage(Logger.color("&a/padmin &2setbuildingspawn &2(gebouw) &8- &aPas de plek van de spawn aan"));
                    p.sendMessage(Logger.color("&a/padmin &2deletebuilding &2(gebouw) &8- &aVerwijder een gebouw"));
                    p.sendMessage(Logger.color("&a/padmin &2create &2(gebouw) (prijs) &8- &aMaak een kamer aan"));
                    p.sendMessage(Logger.color("&a/padmin &2delete &8- &aVerwijder de huidige kamer"));
                    p.sendMessage(Logger.color("&a/padmin &2setspawn &8- &aMaak de plek van de spawn aan"));
                    p.sendMessage(Logger.color("&a/padmin &2spawnnpc (gebouw) &8- &aPlaats een npc voor een gebouw"));
                    p.sendMessage(Logger.color("&a/padmin &2setpc (gebouw) &8- &aMaak een gebouw pc aan"));
                } else {
                    p.sendMessage(Logger.color("Je hebt niet genoeg permissies om dit te doen"));
                }
            }
        } else {
            p.sendMessage(Logger.color(MessageManager.getResponse("No-Permission")));
        }
        return false;
    }
}
