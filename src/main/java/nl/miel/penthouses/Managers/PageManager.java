package nl.miel.penthouses.Managers;

import nl.miel.penthouses.Logger;
import nl.miel.penthouses.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PageManager {
    private static Main plugin;


    public static void GUI(Player player, int page, String building) {
        Inventory gui = Bukkit.createInventory(null, 54, "Penthouses - " + page);
        ArrayList<String> lore = new ArrayList();

        List<ItemStack> allItems = new ArrayList<>();
        ItemStack item;
        ItemMeta itemMeta;


        //gui.setItem(45, item);
        for(String str : DataManager.get().getConfigurationSection("Data." + building + ".Rooms").getKeys(false)) {
            String bestaat = DataManager.get().getString("Data." + building + ".Rooms." + str + ".Owner");
            lore.add(Logger.color(""));
            item = new ItemStack(Material.PAPER);
            itemMeta = item.getItemMeta();
            String prijs = DataManager.get().getString("Data." + building + ".Rooms." + str + ".Price");
            if(bestaat.equalsIgnoreCase("")) {
                lore.add(Logger.color("&aEigenaar: &2Geen eigenaar"));

                lore.add(Logger.color("&aPrijs: &2€ " + prijs));
                itemMeta.setLocalizedName("te-koop");
            } else {
                String Eigenaarnaam = "";
                String owner = DataManager.get().getString("Data." + building + ".Rooms." + str + ".Owner");
                final Player pp = PageManager.plugin.getServer().getPlayer(UUID.fromString(owner));
                if (pp != null) {
                    Eigenaarnaam = pp.getName();
                }
                lore.add(Logger.color("&aEigenaar: &2" + Eigenaarnaam));
                lore.add(Logger.color("&aPrijs: &2€ " + prijs));
                if(owner.equalsIgnoreCase(player.getUniqueId().toString())) {
                    lore.add("");
                    lore.add(Logger.color("&aKlik om te teleporteren"));
                    itemMeta.setLocalizedName(owner);

                }


            }

            List<String> members = DataManager.get().getStringList("Data." + building + ".Rooms." + str + ".Members");
            if(members.contains(player.getUniqueId().toString())) {
                lore.add("");
                lore.add(Logger.color("&aKlik om te teleporteren"));
                itemMeta.setLocalizedName(player.getUniqueId().toString());
            }

            itemMeta.setDisplayName(Logger.color("&a" + str));
            itemMeta.setLore(lore);
            lore.clear();
            item.setItemMeta(itemMeta);
            allItems.add(item);
        }
        /*for (int i = 0; i < 135; i++) {


            allItems.add(new ItemStack(Material.BOOK));
        }*/

        ItemStack left;
        ItemMeta leftMeta;
        if(PageManager.isPageValid(allItems, page - 1, 45)) {
            left = new ItemStack(Material.SPECTRAL_ARROW);
            leftMeta = left.getItemMeta();
            leftMeta.setDisplayName(Logger.color("&aGO PAGE LEFT"));
        } else {
            left = new ItemStack(Material.STAINED_GLASS_PANE);
            leftMeta = left.getItemMeta();
            leftMeta.setDisplayName(Logger.color(" "));
        }
        leftMeta.setLocalizedName(page + "");
        left.setItemMeta(leftMeta);

        ItemStack right;
        ItemMeta rightMeta;
        if(PageManager.isPageValid(allItems, page + 1, 45)) {
            right = new ItemStack(Material.SPECTRAL_ARROW);
            rightMeta = right.getItemMeta();
            rightMeta.setDisplayName(Logger.color("&aGO PAGE RIGHT"));
        } else {
            right = new ItemStack(Material.STAINED_GLASS_PANE);
            rightMeta = right.getItemMeta();
            rightMeta.setDisplayName(Logger.color(" "));
        }
        rightMeta.setLocalizedName(building + "");
        right.setItemMeta(rightMeta);

        ItemStack glass;
        ItemMeta glassMeta;
        glass = new ItemStack(Material.STAINED_GLASS_PANE);
        glassMeta = glass.getItemMeta();
        glassMeta.setDisplayName(" ");
        glass.setItemMeta(glassMeta);
        gui.setItem(45, glass);
        gui.setItem(46, glass);
        gui.setItem(48, glass);
        gui.setItem(49, glass);
        gui.setItem(50, glass);
        gui.setItem(52, glass);
        gui.setItem(53, glass);

        gui.setItem(47,left);
        gui.setItem(51,right);

        for(ItemStack is : PageManager.getPageItems(allItems, page, 45)) {
            gui.setItem(gui.firstEmpty(), is);
        }

        player.openInventory(gui);
    }

    public static List<ItemStack> getPageItems(List<ItemStack> items, int page, int spaces) {
        int upperBound = page * spaces;
        int lowerBound = upperBound - spaces;

        List<ItemStack> newItems = new ArrayList<>();
        for (int i = lowerBound; i < upperBound; i++) {
            try {
                newItems.add(items.get(i));
            } catch (IndexOutOfBoundsException x) {
                break;
            }
        }

        return newItems;
    }
    public static boolean isPageValid(List<ItemStack> items, int page, int spaces) {
        if(page <= 0) { return false; }
        int upperBound = page * spaces;
        int lowerBound = upperBound - spaces;

        return items.size() > lowerBound;
    }

    static {
        PageManager.plugin = (Main)Main.getPlugin((Class)Main.class);
    }

}
