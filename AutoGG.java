package com.varz.client.module.modules;

import com.varz.client.module.Category;
import com.varz.client.module.Module;
import com.varz.client.module.setting.ModeSetting;

/** Sends a short chat message automatically the moment you respawn after dying. */
public class AutoGG extends Module {
    private final ModeSetting message = add(new ModeSetting("Message", "gg", "gg", "gg wp", "ggwp", "well played"));
    private boolean wasDead;

    public AutoGG() { super("Auto GG", "Sends a chat message automatically after you respawn from death.", Category.MISC); }

    @Override public void onTick() {
        if (mc.player == null) return;
        boolean dead = mc.player.isDead();
        if (wasDead && !dead && mc.getNetworkHandler() != null) {
            mc.getNetworkHandler().sendChatMessage(message.get());
        }
        wasDead = dead;
    }
}
