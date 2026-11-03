package com.varz.client.module.modules;

import com.varz.client.module.Category;
import com.varz.client.module.Module;
import com.varz.client.module.setting.BoolSetting;
import net.minecraft.util.hit.HitResult;

/** Nudges you upward right as you start an attack so more of your hits land as critical hits. */
public class Criticals extends Module {
    private final BoolSetting onlyGround = add(new BoolSetting("Require ground", true));
    private boolean primed;

    public Criticals() { super("Criticals", "Hops you slightly before you hit so more attacks land as critical hits.", Category.COMBAT); }

    @Override public void onTick() {
        if (mc.player == null || mc.crosshairTarget == null) return;
        boolean targetingEntity = mc.crosshairTarget.getType() == HitResult.Type.ENTITY;
        boolean attacking = mc.options.attackKey.isPressed();

        if (attacking && targetingEntity && !primed) {
            if (!onlyGround.get() || mc.player.isOnGround()) {
                var v = mc.player.getVelocity();
                mc.player.setVelocity(v.x, Math.max(v.y, 0.1), v.z);
            }
            primed = true;
        } else if (!attacking) {
            primed = false;
        }
    }
}
