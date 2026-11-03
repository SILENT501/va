package com.varz.client.module.modules;

import com.varz.client.module.*;
import com.varz.client.module.setting.*;
import com.varz.client.module.Module;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;

public class AutoEat extends Module {
    private final NumberSetting hunger = add(new NumberSetting("Eat below", 1, 19, 14));
    private int previousSlot = -1;
    private boolean eating;

    public AutoEat() { super("Auto Eat", "Eats food from your hotbar when hungry.", Category.PLAYER); }

    @Override public void onTick() {
        int food = mc.player.getHungerManager().getFoodLevel();
        if (eating) {
            if (food >= 20 || mc.player.isDead() || !isFood(mc.player.getMainHandStack())) stop();
            else mc.options.useKey.setPressed(true);
            return;
        }
        if (food > hunger.i() || mc.currentScreen != null) return;
        for (int i = 0; i < 9; i++) {
            if (isFood(mc.player.getInventory().getStack(i))) {
                previousSlot = mc.player.getInventory().getSelectedSlot();
                mc.player.getInventory().setSelectedSlot(i);
                eating = true;
                return;
            }
        }
    }

    private boolean isFood(ItemStack s) { return !s.isEmpty() && s.contains(DataComponentTypes.FOOD); }

    private void stop() {
        mc.options.useKey.setPressed(false);
        if (previousSlot >= 0) mc.player.getInventory().setSelectedSlot(previousSlot);
        previousSlot = -1;
        eating = false;
    }

    @Override public void onDisable() { if (eating && mc.player != null) stop(); }
}
