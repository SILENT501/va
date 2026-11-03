package com.varz.client.module.modules;

import com.varz.client.module.*;
import com.varz.client.module.setting.BoolSetting;
import com.varz.client.module.Module;
import net.minecraft.client.option.GameOptions;

public class Sprint extends Module {
    private final BoolSetting omni = add(new BoolSetting("Omni-directional", false));

    public Sprint() { super("Sprint", "Automatically sprints while you move.", Category.MOVEMENT); }

    @Override public void onTick() {
        GameOptions o = mc.options;
        boolean go = omni.get() ? com.varz.client.util.MoveUtil.isMoving() : o.forwardKey.isPressed();
        if (go && !mc.player.isSneaking() && !mc.player.horizontalCollision
                && mc.player.getHungerManager().getFoodLevel() > 6) {
            mc.player.setSprinting(true);
        }
    }
}
