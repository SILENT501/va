package com.varz.client.module.modules;

import com.varz.client.module.*;
import com.varz.client.module.setting.*;
import com.varz.client.module.Module;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Module {
    private final NumberSetting distance = add(new NumberSetting("Min distance", 1, 10, 2.5));

    public NoFall() { super("No Fall", "Cancels fall damage by reporting on-ground.", Category.MOVEMENT); }

    @Override public void onTick() {
        if (mc.player.fallDistance > distance.get() && !mc.player.isGliding()) {
            mc.getNetworkHandler().sendPacket(
                new PlayerMoveC2SPacket.OnGroundOnly(true, mc.player.horizontalCollision));
        }
    }
}
