package com.varz.client.module.modules;

import com.varz.client.module.*;
import com.varz.client.module.setting.*;
import com.varz.client.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class KillAura extends Module {
    private final NumberSetting range = add(new NumberSetting("Range", 1, 6, 4.2));
    private final BoolSetting playersOnly = add(new BoolSetting("Players only", false));
    private final BoolSetting rotate = add(new BoolSetting("Rotate", true));
    private final BoolSetting waitCooldown = add(new BoolSetting("Wait for cooldown", true));

    public KillAura() { super("Kill Aura", "Attacks the closest living entity in range.", Category.COMBAT); }

    @Override public void onTick() {
        if (mc.currentScreen != null || mc.player.isDead()) return;
        if (waitCooldown.get() && mc.player.getAttackCooldownProgress(0.5f) < 1f) return;

        LivingEntity best = null;
        double bestDist = range.get();
        for (Entity e : mc.world.getEntities()) {
            if (!(e instanceof LivingEntity le) || e == mc.player || !le.isAlive()) continue;
            if (e instanceof ArmorStandEntity) continue;
            if (playersOnly.get() && !(e instanceof PlayerEntity)) continue;
            double dist = mc.player.distanceTo(e);
            if (dist <= bestDist) { bestDist = dist; best = le; }
        }
        if (best == null) return;

        if (rotate.get()) face(best);
        mc.interactionManager.attackEntity(mc.player, best);
        mc.player.swingHand(Hand.MAIN_HAND);
    }

    private void face(Entity t) {
        double dx = t.getX() - mc.player.getX();
        double dz = t.getZ() - mc.player.getZ();
        double dy = (t.getY() + t.getHeight() * 0.5) - mc.player.getEyeY();
        double flat = Math.sqrt(dx * dx + dz * dz);
        mc.player.setYaw((float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0));
        mc.player.setPitch((float) -Math.toDegrees(Math.atan2(dy, flat)));
    }
}
