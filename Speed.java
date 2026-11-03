package com.varz.client.module.modules;

import com.varz.client.module.*;
import com.varz.client.module.setting.*;
import com.varz.client.module.Module;
import com.varz.client.util.MoveUtil;
import net.minecraft.util.math.Vec3d;

public class Speed extends Module {
    private final NumberSetting speed = add(new NumberSetting("Speed", 0.1, 1.5, 0.4));
    private final BoolSetting onlyGround = add(new BoolSetting("Only on ground", false));

    public Speed() { super("Speed", "Moves faster horizontally.", Category.MOVEMENT); }

    @Override public void onTick() {
        if (!MoveUtil.isMoving() || mc.player.isGliding() || mc.player.isTouchingWater()) return;
        if (onlyGround.get() && !mc.player.isOnGround()) return;
        double[] d = MoveUtil.direction();
        Vec3d v = mc.player.getVelocity();
        mc.player.setVelocity(d[0] * speed.get(), v.y, d[1] * speed.get());
    }
}
