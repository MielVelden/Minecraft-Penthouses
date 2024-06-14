package nl.miel.penthouses.Listeners;

import net.milkbowl.vault.economy.Economy;
import nl.miel.penthouses.Logger;
import nl.miel.penthouses.Main;
import nl.miel.penthouses.Managers.DataManager;
import nl.miel.penthouses.Managers.PageManager;
import nl.miel.penthouses.Managers.WorldGuardManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickListener implements Listener {
    private Main main;
    public ClickListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onClickEvent(InventoryClickEvent e) {

        if(e.getInventory() != null && e.getCurrentItem() != null && e.getView().getTitle().contains("Penthouses")) {
            int page = Integer.parseInt(e.getInventory().getItem(47).getItemMeta().getLocalizedName());
            String gebouw = e.getInventory().getItem(51).getItemMeta().getLocalizedName();

            if(e.getRawSlot() == 47 && e.getCurrentItem().getType().equals(Material.SPECTRAL_ARROW)) {
                PageManager.GUI((Player) e.getWhoClicked(), page - 1, gebouw);
            } else if(e.getRawSlot() == 51 && e.getCurrentItem().getType().equals(Material.SPECTRAL_ARROW)) {
                PageManager.GUI((Player) e.getWhoClicked(), page + 1, gebouw);

            } else if(e.getCurrentItem().getItemMeta().getLocalizedName() != null) {

                if(e.getCurrentItem().getItemMeta().getLocalizedName().equals(e.getWhoClicked().getUniqueId().toString()) && e.getCurrentItem().getType().equals(Material.PAPER)) {
                    e.getWhoClicked().sendMessage(Logger.color("&aJe wordt geteleporteerd naar je huis"));
                    String name = e.getCurrentItem().getItemMeta().getDisplayName().replace("§a","").replace("&a","");;
                    Location spawn = DataManager.getPenthouseSpawn(e.getWhoClicked().getWorld(), gebouw, name);
                    e.getWhoClicked().teleport(spawn);
                } else {
                    if(e.getCurrentItem().getItemMeta().getLocalizedName().equalsIgnoreCase("te-koop")) {
                        Economy economy = main.getEconomy();
                        String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());

                        OfflinePlayer player = (OfflinePlayer) e.getWhoClicked();
                        double prijs = DataManager.getPrice(gebouw, name);
                        if(economy.has((OfflinePlayer) e.getWhoClicked(), prijs)) {
                            economy.withdrawPlayer((OfflinePlayer) e.getWhoClicked(), prijs);
                            Location spawn = DataManager.getPenthouseSpawn(e.getWhoClicked().getWorld(), gebouw, name);
                            e.getWhoClicked().sendMessage(Logger.color("&aJe kocht het huis: &2" + e.getCurrentItem().getItemMeta().getDisplayName()));
                            DataManager.get().set("Data." + gebouw + ".Rooms." + name + ".Owner", e.getWhoClicked().getUniqueId().toString());
                            DataManager.save();
                            e.getWhoClicked().closeInventory();
                            e.getWhoClicked().teleport(spawn);
                            WorldGuardManager.setOwner(spawn, (Player) e.getWhoClicked(), name);
                        } else {
                            e.getWhoClicked().sendMessage(Logger.color("&cJe hebt niet genoeg geld om dit huis te kopen"));
                        }
                    }



                }
            }
            e.setCancelled(true);
        }

    }

}
