package com.varz.client.module.modules;

import com.varz.client.module.*;
import com.varz.client.module.setting.*;
import com.varz.client.module.Module;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.text.Text;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;

import java.util.HashSet;
import java.util.Set;

/** Scans loaded chunks for clusters of storage block entities and reports them in chat. */
public class StashFinder extends Module {
    private final NumberSetting minContainers = add(new NumberSetting("Min containers", 1, 60, 8));
    private final BoolSetting shulkers = add(new BoolSetting("Count shulkers", true));
    private final Set<Long> reported = new HashSet<>();
    private int ticks;

    public StashFinder() { super("Stash Finder", "Reports chunks containing many chests, barrels or shulkers.", Category.WORLD); }

    @Override public void onEnable() { reported.clear(); ticks = 0; }

    @Override public void onTick() {
        if (++ticks % 40 != 0) return;
        int r = mc.options.getViewDistance().getValue();
        int pcx = mc.player.getBlockPos().getX() >> 4;
        int pcz = mc.player.getBlockPos().getZ() >> 4;

        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
                int cx = pcx + dx, cz = pcz + dz;
                long id = ((long) cx << 32) | (cz & 0xFFFFFFFFL);
                if (reported.contains(id)) continue;
                Chunk c = mc.world.getChunk(cx, cz, ChunkStatus.FULL, false);
                if (!(c instanceof WorldChunk wc)) continue;

                int count = 0;
                for (BlockEntity be : wc.getBlockEntities().values()) {
                    if (be instanceof ChestBlockEntity || be instanceof BarrelBlockEntity
                            || (shulkers.get() && be instanceof ShulkerBoxBlockEntity)) count++;
                }
                if (count >= minContainers.i()) {
                    reported.add(id);
                    mc.player.sendMessage(Text.literal("§d[Varz] §fStash candidate: §e" + count
                        + " §fcontainers near X " + (cx * 16 + 8) + " Z " + (cz * 16 + 8)), false);
                }
            }
        }
    }
}
