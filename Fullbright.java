package com.varz.client.module.modules;

import com.varz.client.module.*;
import com.varz.client.module.Module;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class Fullbright extends Module {
    public Fullbright() { super("Fullbright", "Client-side night vision.", Category.RENDER); }

    @Override public void onTick() {
        if (!mc.player.hasStatusEffect(StatusEffects.NIGHT_VISION) ||
                mc.player.getStatusEffect(StatusEffects.NIGHT_VISION).getDuration() < 400) {
            mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 1200, 0, false, false, false));
        }
    }

    @Override public void onDisable() {
        if (mc.player != null) mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
    }
}
