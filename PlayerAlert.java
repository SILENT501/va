package com.varz.client.module.modules;

import com.varz.client.gui.Toast;
import com.varz.client.module.Category;
import com.varz.client.module.Module;
import com.varz.client.module.setting.NumberSetting;
import net.minecraft.entity.player.PlayerEntity;

/** Alerts the user when another player enters the configured radius. It does not
 * automate movement, disconnect, alter packets, or interfere with server protections. */
public final class PlayerAlert extends Module {
    private final NumberSetting radius = add(new NumberSetting("Alert radius", 8, 256, 48));
    private String lastNearby = "";
    private int clearTicks;

    public PlayerAlert() {
        super("Player Alert", "Notifies you when another player enters your nearby radius.", Category.MISC);
    }

    @Override public void onEnable() {
        lastNearby = "";
        clearTicks = 0;
    }

    @Override public void onDisable() {
        lastNearby = "";
        clearTicks = 0;
    }

    @Override public void onTick() {
        if (mc.player == null || mc.world == null) return;

        PlayerEntity nearest = null;
        double nearestDistance = radius.get();
        for (PlayerEntity other : mc.world.getPlayers()) {
            if (other == mc.player || other.isRemoved()) continue;
            double distance = mc.player.distanceTo(other);
            if (distance <= nearestDistance) {
                nearest = other;
                nearestDistance = distance;
            }
        }

        if (nearest == null) {
            if (++clearTicks >= 20) lastNearby = "";
            return;
        }

        clearTicks = 0;
        String name = nearest.getName().getString();
        if (!name.equals(lastNearby)) {
            lastNearby = name;
            Toast.push("§dPlayer nearby: §f" + name + " §7(" + Math.round(nearestDistance) + "m)");
        }
    }
}
