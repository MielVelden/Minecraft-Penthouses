package nl.miel.penthouses.Listeners;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import nl.miel.penthouses.Logger;
import nl.miel.penthouses.Main;
import nl.miel.penthouses.Managers.ConfigManager;
import nl.miel.penthouses.Managers.DataManager;
import nl.miel.penthouses.Managers.PageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnNPCClick implements Listener {
    private Main main;
    public OnNPCClick(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onNPCClick(final NPCRightClickEvent e) {

        Player p = (Player) e.getClicker();
        NPC npc = e.getNPC();
        String npcname = npc.getName().replace("§","");
        Boolean npcEnabled = main.getConfig().getBoolean("BuildingEnter.Use-NPC-Instead");
        if(npcEnabled) {
            for (String str : DataManager.get().getConfigurationSection("Data.").getKeys(false)) {
                String configname = main.getConfig().getString("BuildingEnter.NPC.Name").replace("&","").replace("§","");
                configname = configname.replace("%building%", str);
                if(npcname.equalsIgnoreCase(configname)) {
                    p.sendMessage(Logger.color(ConfigManager.getMessage("Prefix") + "&aOpen menu voor dit"));
                    PageManager.GUI(p, 1, str);
                }
            }

            return;
        }
    }
}
