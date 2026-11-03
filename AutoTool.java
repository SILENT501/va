package com.varz.client.module.modules;

import com.varz.client.module.Category;
import com.varz.client.module.Module;
import com.varz.client.module.setting.BoolSetting;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

/** Switches your hotbar to the fastest tool for the block you are currently mining. */
public class AutoTool extends Module {
    private final BoolSetting onlyIfBetter = add(new BoolSetting("Only if faster", true));
    private int previousSlot = -1;

    public AutoTool() { super("Auto Tool", "Switches to the best tool for the block you are looking at while mining.", Category.PLAYER); }

    @Override public void onTick() {
        if (mc.player == null || mc.world == null || mc.crosshairTarget == null
                || mc.crosshairTarget.getType() != HitResult.Type.BLOCK
                || !mc.options.attackKey.isPressed()) {
            restore();
            return;
        }

        BlockHitResult bhr = (BlockHitResult) mc.crosshairTarget;
        BlockState state = mc.world.getBlockState(bhr.getBlockPos());
        if (state.isAir()) { restore(); return; }

        var inv = mc.player.getInventory();
        int best = -1;
        float bestSpeed = -1f;
        for (int i = 0; i < 9; i++) {
            float speed = inv.getStack(i).getMiningSpeedMultiplier(state);
            if (onlyIfBetter.get() && speed <= 1.0f) continue;
            if (speed > bestSpeed) { bestSpeed = speed; best = i; }
        }
        if (best >= 0 && inv.getSelectedSlot() != best) {
            if (previousSlot < 0) previousSlot = inv.getSelectedSlot();
            inv.setSelectedSlot(best);
        }
    }

    private void restore() {
        if (previousSlot >= 0 && mc.player != null) mc.player.getInventory().setSelectedSlot(previousSlot);
        previousSlot = -1;
    }

    @Override public void onDisable() { restore(); }
}
