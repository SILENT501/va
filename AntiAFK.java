package com.varz.client.module.modules;

import com.varz.client.module.*;
import com.varz.client.module.setting.*;
import com.varz.client.module.Module;
import net.minecraft.util.Hand;

public class AntiAFK extends Module {
    private final NumberSetting delay = add(new NumberSetting("Delay (s)", 5, 60, 20));
    private final BoolSetting jump = add(new BoolSetting("Jump", true));
    private final BoolSetting swing = add(new BoolSetting("Swing", true));
    private int ticks;

    public AntiAFK() { super("Anti AFK", "Performs small actions so you are not kicked for idling.", Category.PLAYER); }

    @Override public void onTick() {
        if (++ticks < delay.i() * 20) return;
        ticks = 0;
        if (jump.get() && mc.player.isOnGround()) mc.player.jump();
        if (swing.get()) mc.player.swingHand(Hand.MAIN_HAND);
    }
}
