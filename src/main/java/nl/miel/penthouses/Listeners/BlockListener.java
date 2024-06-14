package nl.miel.penthouses.Listeners;

import nl.miel.penthouses.Logger;
import nl.miel.penthouses.Main;
import nl.miel.penthouses.Managers.ConfigManager;
import nl.miel.penthouses.Managers.DataManager;
import nl.miel.penthouses.Managers.PageManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.bukkit.inventory.EquipmentSlot.HAND;

public class BlockListener  implements Listener {
    private Main main;
    public BlockListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onClickEvent(PlayerInteractEvent e) {
        Player p = (Player) e.getPlayer();
        if(e.getHand() == null) return;
        if(!e.getHand().equals(HAND)) {
            return;
        }
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            String block = ConfigManager.get().getString("Block");
            if (block.equalsIgnoreCase(e.getClickedBlock().getType().toString())) {
                Block c = e.getClickedBlock();
                String gebouw = DataManager.getBuildingByBlock(c.getX(), c.getY(), c.getZ());
                if (gebouw != null) {
                    PageManager.GUI(p, 1, gebouw);
                    p.sendMessage(Logger.color("&aJe opent het huizen menu"));

                }
            }
        }
    }
}
