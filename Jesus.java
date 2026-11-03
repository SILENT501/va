package com.varz.client.module.modules;

import com.varz.client.module.*;
import com.varz.client.module.Module;
import net.minecraft.util.math.Vec3d;

public class Jesus extends Module {
    public Jesus() { super("Jesus", "Lets you float on top of water.", Category.MOVEMENT); }

    @Override public void onTick() {
        if (mc.player.isSneaking() || !mc.player.isTouchingWater()) return;
        Vec3d v = mc.player.getVelocity();
        mc.player.setVelocity(v.x, Math.max(v.y, 0.11), v.z);
    }
}
