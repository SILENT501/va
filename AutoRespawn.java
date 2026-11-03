package com.varz.client.module.modules;

import com.varz.client.module.*;
import com.varz.client.module.Module;

public class AutoRespawn extends Module {
    public AutoRespawn() { super("Auto Respawn", "Respawns instantly when you die.", Category.PLAYER); }

    @Override public void onTick() {
        if (mc.player.isDead()) mc.player.requestRespawn();
    }
}
