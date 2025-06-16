package nl.miel.penthouses.Managers;

import com.sk89q.worldguard.bukkit.*;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nl.miel.penthouses.Logger;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.*;

import java.util.Iterator;

public class WorldGuardManager
{
    private static WorldGuardPlugin worldGuard;

    public static WorldGuardPlugin getWorldGuard() {
        return WorldGuardManager.worldGuard;
    }

    static {
        final Plugin worldGuardPlugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        if (worldGuardPlugin instanceof WorldGuardPlugin) {
            WorldGuardManager.worldGuard = (WorldGuardPlugin)worldGuardPlugin;
        }
    }

    public static boolean plotExists(final Location plotLoc) {
        return WorldGuardManager.worldGuard.getRegionManager(plotLoc.getWorld()).getApplicableRegions(plotLoc).getRegions().size() != 0;
    }

    public static String getRegionName(final Location plotLoc) {
        return WorldGuardManager.worldGuard.getRegionManager(plotLoc.getWorld()).getApplicableRegions(plotLoc).getRegions().iterator().next().getId();
    }

    public static String getRegionOwner(final Location plotLoc) {
        final ApplicableRegionSet regions = ((WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard")).getRegionManager(plotLoc.getWorld()).getApplicableRegions(plotLoc);
        if (!plotExists(plotLoc)) {
            return null;
        }
        final Iterator iterator = regions.iterator();
        if (iterator.hasNext()) {
            final ProtectedRegion r = (ProtectedRegion) iterator.next();
            final String uuid = r.getOwners().getUniqueIds().toString().replace("[", "").replace("]", "");
            return uuid;
        } else {
            return null;
        }
    }

    public static void setOwner(Location plotLoc, Player owner, String regionName) {
        if (!plotExists(plotLoc)) {
            return;
        }
        final RegionContainer container = ((WorldGuardPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldGuard")).getRegionContainer();
        final RegionManager regions = container.get(owner.getLocation().getWorld());
        final ProtectedRegion region = regions.getRegion(regionName);
        region.getOwners().addPlayer(owner.getUniqueId());

    }
    
    public static void clearPlot(final Location plotLoc) {
        final ApplicableRegionSet regions = ((WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard")).getRegionManager(plotLoc.getWorld()).getApplicableRegions(plotLoc);
        if (!plotExists(plotLoc)) {
            return;
        }
        final Iterator iterator = regions.iterator();
        if (iterator.hasNext()) {
            final ProtectedRegion r = (ProtectedRegion) iterator.next();
            r.getOwners().clear();
            r.getMembers().clear();
            return;
        }
    }

    public static void addMember(Location plotLoc, Player member) {
        final ApplicableRegionSet regions = ((WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard")).getRegionManager(plotLoc.getWorld()).getApplicableRegions(plotLoc);
        if (!plotExists(plotLoc)) {
            return;
        }
        final Iterator iterator = regions.iterator();
        if (iterator.hasNext()) {
            final ProtectedRegion r = (ProtectedRegion) iterator.next();
            r.getMembers().addPlayer(member.getUniqueId());
            return;
        }
    }

    public static void removeMember(Location plotLoc, Player member) {
        final ApplicableRegionSet regions = ((WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard")).getRegionManager(plotLoc.getWorld()).getApplicableRegions(plotLoc);
        if (!plotExists(plotLoc)) {
            return;
        }
        final Iterator iterator = regions.iterator();
        if (iterator.hasNext()) {
            final ProtectedRegion r = (ProtectedRegion) iterator.next();
            r.getMembers().removePlayer(member.getUniqueId());
            return;
        }
    }


}
