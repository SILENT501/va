package com.varz.client.module.modules;

import com.varz.client.module.*;
import com.varz.client.module.setting.*;
import com.varz.client.module.Module;

public class Flight extends Module {
    private final NumberSetting speed = add(new NumberSetting("Speed", 0.5, 10, 2));

    public Flight() { super("Flight", "Creative-style flying (kicked by most servers).", Category.MOVEMENT); }

    @Override public void onTick() {
        mc.player.getAbilities().allowFlying = true;
        mc.player.getAbilities().flying = true;
        mc.player.getAbilities().setFlySpeed((float) (speed.get() * 0.05));
    }

    @Override public void onDisable() {
        if (mc.player == null) return;
        mc.player.getAbilities().setFlySpeed(0.05f);
        if (!mc.player.isCreative() && !mc.player.isSpectator()) {
            mc.player.getAbilities().flying = false;
            mc.player.getAbilities().allowFlying = false;
        }
    }
}
