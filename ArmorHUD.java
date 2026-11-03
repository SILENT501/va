package com.varz.client.module.modules;

import com.varz.client.module.Category;
import com.varz.client.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

/** Draws the four equipped armor pieces as item icons in the bottom-left corner. */
public class ArmorHUD extends Module {
    private static final EquipmentSlot[] SLOTS = {
        EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD
    };

    public ArmorHUD() { super("Armor HUD", "Draws your four equipped armor pieces in the corner of the screen.", Category.RENDER); }

    @Override public void onRender2D(DrawContext ctx) {
        if (mc.player == null) return;
        int sh = mc.getWindow().getScaledHeight();
        int x = 6, y = sh - 24;
        for (EquipmentSlot slot : SLOTS) {
            ItemStack stack = mc.player.getEquippedStack(slot);
            if (stack.isEmpty()) continue;
            ctx.fill(x - 1, y - 1, x + 17, y + 17, 0x80150F2B);
            ctx.drawItem(stack, x, y);
            x += 20;
        }
    }
}
