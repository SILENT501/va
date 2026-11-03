package com.varz.client.module.modules;

import com.varz.client.module.*;
import com.varz.client.module.Module;
import net.minecraft.text.Text;

public class DeathCoords extends Module {
    private boolean reported;

    public DeathCoords() { super("Death Coords", "Prints your coordinates in chat when you die.", Category.MISC); }

    @Override public void onTick() {
        if (mc.player.isDead()) {
            if (reported) return;
            reported = true;
            mc.player.sendMessage(Text.literal(String.format("§c[Varz] You died at X %d Y %d Z %d",
                (int) mc.player.getX(), (int) mc.player.getY(), (int) mc.player.getZ())), false);
        } else {
            reported = false;
        }
    }
}
