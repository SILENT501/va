package com.varz.client.module.modules;

import com.varz.client.module.*;
import com.varz.client.module.Module;
import com.varz.client.util.MoveUtil;

public class AutoJump extends Module {
    public AutoJump() { super("Auto Jump", "Jumps automatically while you move.", Category.MOVEMENT); }

    @Override public void onTick() {
        if (mc.player.isOnGround() && MoveUtil.isMoving() && !mc.player.isSneaking()) mc.player.jump();
    }
}
