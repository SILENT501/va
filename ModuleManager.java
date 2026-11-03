package com.varz.client.module;

import com.varz.client.VarzClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;

import java.util.*;

import com.varz.client.module.modules.AntiAFK;
import com.varz.client.module.modules.ArmorHUD;
import com.varz.client.module.modules.AutoEat;
import com.varz.client.module.modules.AutoGG;
import com.varz.client.module.modules.AutoJump;
import com.varz.client.module.modules.AutoRespawn;
import com.varz.client.module.modules.AutoTool;
import com.varz.client.module.modules.AutoWalk;
import com.varz.client.module.modules.CoordinatesHUD;
import com.varz.client.module.modules.Criticals;
import com.varz.client.module.modules.DeathCoords;
import com.varz.client.module.modules.Flight;
import com.varz.client.module.modules.Fullbright;
import com.varz.client.module.modules.HUD;
import com.varz.client.module.modules.Jesus;
import com.varz.client.module.modules.Keystrokes;
import com.varz.client.module.modules.KillAura;
import com.varz.client.module.modules.NoFall;
import com.varz.client.module.modules.Notifier;
import com.varz.client.module.modules.PlayerAlert;
import com.varz.client.module.modules.ScreenshotHelper;
import com.varz.client.module.modules.Sneak;
import com.varz.client.module.modules.Speed;
import com.varz.client.module.modules.Sprint;
import com.varz.client.module.modules.StashFinder;
import com.varz.client.module.modules.Waypoints;
import com.varz.client.module.modules.Zoom;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();
    private final Map<Integer, Boolean> keyState = new HashMap<>();

    private void add(Module m) { modules.add(m); }

    public void init() {
        add(new GenericModule("Aim Assist", "Aim Assist (Combat)", Category.COMBAT));
        add(new GenericModule("Anchor Aura", "Anchor Aura (Combat)", Category.COMBAT));
        add(new GenericModule("Anti Anvil", "Anti Anvil (Combat)", Category.COMBAT));
        add(new GenericModule("Anti Bed", "Anti Bed (Combat)", Category.COMBAT));
        add(new GenericModule("Arrow Dodge", "Arrow Dodge (Combat)", Category.COMBAT));
        add(new GenericModule("Auto Anvil", "Auto Anvil (Combat)", Category.COMBAT));
        add(new GenericModule("Auto Armor", "Auto Armor (Combat)", Category.COMBAT));
        add(new GenericModule("Auto City", "Auto City (Combat)", Category.COMBAT));
        add(new GenericModule("Auto Log", "Auto Log (Combat)", Category.COMBAT));
        add(new GenericModule("Auto Totem", "Auto Totem (Combat)", Category.COMBAT));
        add(new GenericModule("Auto Trap", "Auto Trap (Combat)", Category.COMBAT));
        add(new GenericModule("Auto Weapon", "Auto Weapon (Combat)", Category.COMBAT));
        add(new GenericModule("Auto Web", "Auto Web (Combat)", Category.COMBAT));
        add(new GenericModule("Bed Aura", "Bed Aura (Combat)", Category.COMBAT));
        add(new GenericModule("Bow Aimbot", "Bow Aimbot (Combat)", Category.COMBAT));
        add(new GenericModule("Bow Spam", "Bow Spam (Combat)", Category.COMBAT));
        add(new GenericModule("Burrow", "Burrow (Combat)", Category.COMBAT));
        add(new Criticals());
        add(new GenericModule("Crystal Aura", "Crystal Aura (Combat)", Category.COMBAT));
        add(new GenericModule("Hitboxes", "Hitboxes (Combat)", Category.COMBAT));
        add(new GenericModule("Hole Filler", "Hole Filler (Combat)", Category.COMBAT));
        add(new KillAura());
        add(new GenericModule("Offhand", "Offhand (Combat)", Category.COMBAT));
        add(new GenericModule("Quiver", "Quiver (Combat)", Category.COMBAT));
        add(new GenericModule("Self Anvil", "Self Anvil (Combat)", Category.COMBAT));
        add(new GenericModule("Self Trap", "Self Trap (Combat)", Category.COMBAT));
        add(new GenericModule("Self Web", "Self Web (Combat)", Category.COMBAT));
        add(new GenericModule("Surround", "Surround (Combat)", Category.COMBAT));
        add(new GenericModule("Better Tooltips", "Better Tooltips (Render)", Category.RENDER));
        add(new GenericModule("Block Selection", "Block Selection (Render)", Category.RENDER));
        add(new GenericModule("Blur", "Blur (Render)", Category.RENDER));
        add(new GenericModule("Boss Stack", "Boss Stack (Render)", Category.RENDER));
        add(new GenericModule("Breadcrumbs", "Breadcrumbs (Render)", Category.RENDER));
        add(new GenericModule("Break Indicators", "Break Indicators (Render)", Category.RENDER));
        add(new GenericModule("Camera Tweaks", "Camera Tweaks (Render)", Category.RENDER));
        add(new GenericModule("Chams", "Chams (Render)", Category.RENDER));
        add(new GenericModule("City ESP", "City ESP (Render)", Category.RENDER));
        add(new GenericModule("Custom Fov", "Custom Fov (Render)", Category.RENDER));
        add(new GenericModule("Entity Owner", "Entity Owner (Render)", Category.RENDER));
        add(new GenericModule("ESP", "ESP (Render)", Category.RENDER));
        add(new GenericModule("Free Look", "Free Look (Render)", Category.RENDER));
        add(new GenericModule("Freecam", "Freecam (Render)", Category.RENDER));
        add(new Fullbright());
        add(new HUD());
        add(new GenericModule("Hand View", "Hand View (Render)", Category.RENDER));
        add(new GenericModule("Hole ESP", "Hole ESP (Render)", Category.RENDER));
        add(new GenericModule("Item Highlight", "Item Highlight (Render)", Category.RENDER));
        add(new GenericModule("Item Physics", "Item Physics (Render)", Category.RENDER));
        add(new GenericModule("Light Overlay", "Light Overlay (Render)", Category.RENDER));
        add(new GenericModule("Logout Spots", "Logout Spots (Render)", Category.RENDER));
        add(new GenericModule("Nametags", "Nametags (Render)", Category.RENDER));
        add(new GenericModule("No Render", "No Render (Render)", Category.RENDER));
        add(new GenericModule("Search", "Search (Render)", Category.RENDER));
        add(new GenericModule("Storage ESP", "Storage ESP (Render)", Category.RENDER));
        add(new GenericModule("Time Changer", "Time Changer (Render)", Category.RENDER));
        add(new GenericModule("Tracers", "Tracers (Render)", Category.RENDER));
        add(new GenericModule("Trail", "Trail (Render)", Category.RENDER));
        add(new GenericModule("Trajectories", "Trajectories (Render)", Category.RENDER));
        add(new GenericModule("Ambience", "Ambience (World)", Category.WORLD));
        add(new GenericModule("Anti Cactus", "Anti Cactus (World)", Category.WORLD));
        add(new GenericModule("Auto Breed", "Auto Breed (World)", Category.WORLD));
        add(new GenericModule("Auto Brewer", "Auto Brewer (World)", Category.WORLD));
        add(new GenericModule("Auto Mount", "Auto Mount (World)", Category.WORLD));
        add(new GenericModule("Auto Nametag", "Auto Nametag (World)", Category.WORLD));
        add(new GenericModule("Auto Shearer", "Auto Shearer (World)", Category.WORLD));
        add(new GenericModule("Auto Sign", "Auto Sign (World)", Category.WORLD));
        add(new GenericModule("Auto Smelter", "Auto Smelter (World)", Category.WORLD));
        add(new GenericModule("Build Height", "Build Height (World)", Category.WORLD));
        add(new GenericModule("Echest Farmer", "Echest Farmer (World)", Category.WORLD));
        add(new GenericModule("Enderchest Look", "Enderchest Look (World)", Category.WORLD));
        add(new GenericModule("Flamethrower", "Flamethrower (World)", Category.WORLD));
        add(new GenericModule("Infinity Miner", "Infinity Miner (World)", Category.WORLD));
        add(new GenericModule("Liquid Filler", "Liquid Filler (World)", Category.WORLD));
        add(new GenericModule("Mount Bypass", "Mount Bypass (World)", Category.WORLD));
        add(new GenericModule("Nuker", "Nuker (World)", Category.WORLD));
        add(new GenericModule("Packet Mine", "Packet Mine (World)", Category.WORLD));
        add(new GenericModule("Spawn Proofer", "Spawn Proofer (World)", Category.WORLD));
        add(new StashFinder());
        add(new GenericModule("Timer", "Timer (World)", Category.WORLD));
        add(new GenericModule("Vein Miner", "Vein Miner (World)", Category.WORLD));
        add(new GenericModule("Air Place", "Air Place (Player)", Category.PLAYER));
        add(new AntiAFK());
        add(new GenericModule("Anti Hunger", "Anti Hunger (Player)", Category.PLAYER));
        add(new GenericModule("Auto Clicker", "Auto Clicker (Player)", Category.PLAYER));
        add(new AutoEat());
        add(new GenericModule("Auto Fish", "Auto Fish (Player)", Category.PLAYER));
        add(new GenericModule("Auto Gap", "Auto Gap (Player)", Category.PLAYER));
        add(new GenericModule("Auto Replenish", "Auto Replenish (Player)", Category.PLAYER));
        add(new AutoRespawn());
        add(new AutoTool());
        add(new GenericModule("Chest Swap", "Chest Swap (Player)", Category.PLAYER));
        add(new GenericModule("Exp Thrower", "Exp Thrower (Player)", Category.PLAYER));
        add(new GenericModule("Fake Player", "Fake Player (Player)", Category.PLAYER));
        add(new GenericModule("Fast Use", "Fast Use (Player)", Category.PLAYER));
        add(new GenericModule("Ghost Hand", "Ghost Hand (Player)", Category.PLAYER));
        add(new GenericModule("Insta Mine", "Insta Mine (Player)", Category.PLAYER));
        add(new GenericModule("Liquid Interact", "Liquid Interact (Player)", Category.PLAYER));
        add(new GenericModule("Name Protect", "Name Protect (Player)", Category.PLAYER));
        add(new GenericModule("No Break Delay", "No Break Delay (Player)", Category.PLAYER));
        add(new GenericModule("No Interact", "No Interact (Player)", Category.PLAYER));
        add(new GenericModule("No Mining Trace", "No Mining Trace (Player)", Category.PLAYER));
        add(new GenericModule("No Rotate", "No Rotate (Player)", Category.PLAYER));
        add(new GenericModule("Portals", "Portals (Player)", Category.PLAYER));
        add(new GenericModule("Potion Saver", "Potion Saver (Player)", Category.PLAYER));
        add(new GenericModule("Potion Spoof", "Potion Spoof (Player)", Category.PLAYER));
        add(new GenericModule("Reach", "Reach (Player)", Category.PLAYER));
        add(new GenericModule("Rotation", "Rotation (Player)", Category.PLAYER));
        add(new GenericModule("Speed Mine", "Speed Mine (Player)", Category.PLAYER));
        add(new GenericModule("Air Jump", "Air Jump (Movement)", Category.MOVEMENT));
        add(new GenericModule("Anchor", "Anchor (Movement)", Category.MOVEMENT));
        add(new GenericModule("Anti Levitation", "Anti Levitation (Movement)", Category.MOVEMENT));
        add(new GenericModule("Anti Void", "Anti Void (Movement)", Category.MOVEMENT));
        add(new AutoJump());
        add(new AutoWalk());
        add(new GenericModule("Blink", "Blink (Movement)", Category.MOVEMENT));
        add(new GenericModule("Boat Fly", "Boat Fly (Movement)", Category.MOVEMENT));
        add(new GenericModule("Click Tp", "Click Tp (Movement)", Category.MOVEMENT));
        add(new GenericModule("Elytra Boost", "Elytra Boost (Movement)", Category.MOVEMENT));
        add(new GenericModule("Elytra Fly", "Elytra Fly (Movement)", Category.MOVEMENT));
        add(new GenericModule("Entity Control", "Entity Control (Movement)", Category.MOVEMENT));
        add(new GenericModule("Entity Speed", "Entity Speed (Movement)", Category.MOVEMENT));
        add(new GenericModule("Fast Climb", "Fast Climb (Movement)", Category.MOVEMENT));
        add(new Flight());
        add(new GenericModule("Gui Move", "Gui Move (Movement)", Category.MOVEMENT));
        add(new GenericModule("High Jump", "High Jump (Movement)", Category.MOVEMENT));
        add(new Jesus());
        add(new GenericModule("Long Jump", "Long Jump (Movement)", Category.MOVEMENT));
        add(new NoFall());
        add(new GenericModule("No Slow", "No Slow (Movement)", Category.MOVEMENT));
        add(new GenericModule("Parkour", "Parkour (Movement)", Category.MOVEMENT));
        add(new GenericModule("Reverse Step", "Reverse Step (Movement)", Category.MOVEMENT));
        add(new GenericModule("Safe Walk", "Safe Walk (Movement)", Category.MOVEMENT));
        add(new GenericModule("Scaffold", "Scaffold (Movement)", Category.MOVEMENT));
        add(new GenericModule("Slippy", "Slippy (Movement)", Category.MOVEMENT));
        add(new Sneak());
        add(new Speed());
        add(new GenericModule("Spider", "Spider (Movement)", Category.MOVEMENT));
        add(new Sprint());
        add(new GenericModule("Announcer", "Announcer (Misc)", Category.MISC));
        add(new GenericModule("Anti Packet Kick", "Anti Packet Kick (Misc)", Category.MISC));
        add(new GenericModule("Auto Reconnect", "Auto Reconnect (Misc)", Category.MISC));
        add(new GenericModule("Better Chat", "Better Chat (Misc)", Category.MISC));
        add(new GenericModule("Better Tab", "Better Tab (Misc)", Category.MISC));
        add(new GenericModule("Book Bot", "Book Bot (Misc)", Category.MISC));
        add(new GenericModule("Discord Presence", "Discord Presence (Misc)", Category.MISC));
        add(new GenericModule("Inventory Tweaks", "Inventory Tweaks (Misc)", Category.MISC));
        add(new GenericModule("Message Aura", "Message Aura (Misc)", Category.MISC));
        add(new GenericModule("Middle Click Friend", "Middle Click Friend (Misc)", Category.MISC));
        add(new GenericModule("Notebot", "Notebot (Misc)", Category.MISC));
        add(new Notifier());
        add(new PlayerAlert());
        add(new GenericModule("Offhand Crash", "Offhand Crash (Misc)", Category.MISC));
        add(new GenericModule("Packet Canceller", "Packet Canceller (Misc)", Category.MISC));
        add(new GenericModule("Sound Blocker", "Sound Blocker (Misc)", Category.MISC));
        add(new GenericModule("Spam", "Spam (Misc)", Category.MISC));
        add(new GenericModule("Swarm", "Swarm (Misc)", Category.MISC));
        add(new GenericModule("Tps Sync", "Tps Sync (Misc)", Category.MISC));
        add(new GenericModule("Vanilla Spoof", "Vanilla Spoof (Misc)", Category.MISC));
        add(new GenericModule("Base ESP", "Highlights player-constructed blocks and artificial structures.", Category.BASE_FINDER));
        add(new GenericModule("Storage Cluster Scan", "Filters and highlights concentrated chest and barrel clusters.", Category.BASE_FINDER));
        add(new GenericModule("Tunnel ESP", "Detects human-mined 1x2 and 2x2 strip shafts underground.", Category.BASE_FINDER));
        add(new GenericModule("Hidden Room Detector", "Analyzes enclosed void spaces surrounded by unnatural blocks.", Category.BASE_FINDER));
        add(new GenericModule("Suspicious Chunks", "Scans chunk data for high entity density and block updates.", Category.BASE_FINDER));
        add(new GenericModule("Amethyst Traces", "Traces player paths through underground geodes and caves.", Category.BASE_FINDER));
        add(new GenericModule("Block Pattern Analysis", "Identifies unnatural block placement ratios (obsidian, glass...).", Category.BASE_FINDER));
        add(new GenericModule("Waypoint Saver", "Auto-saves coordinates of suspicious finds for review.", Category.BASE_FINDER));
        add(new GenericModule("Auto Pot", "Auto Pot (Combat)", Category.COMBAT));
        add(new GenericModule("Auto Soup", "Auto Soup (Combat)", Category.COMBAT));
        add(new GenericModule("Shield Breaker", "Shield Breaker (Combat)", Category.COMBAT));
        add(new GenericModule("Velocity", "Velocity (Combat)", Category.COMBAT));
        add(new GenericModule("Target Strafe", "Target Strafe (Combat)", Category.COMBAT));
        add(new GenericModule("Auto Crit Jump", "Auto Crit Jump (Combat)", Category.COMBAT));
        add(new GenericModule("Trigger Bot", "Trigger Bot (Combat)", Category.COMBAT));
        add(new GenericModule("Sword Block", "Sword Block (Combat)", Category.COMBAT));
        add(new GenericModule("Mace Aura", "Mace Aura (Combat)", Category.COMBAT));
        add(new GenericModule("Lunge Aura", "Lunge Aura (Combat)", Category.COMBAT));
        add(new GenericModule("Auto Mace Swap", "Auto Mace Swap (Combat)", Category.COMBAT));
        add(new GenericModule("Wind Charge Boost", "Wind Charge Boost (Combat)", Category.COMBAT));
        add(new GenericModule("Pearl Catch", "Pearl Catch (Combat)", Category.COMBAT));
        add(new GenericModule("Auto Pearl", "Auto Pearl (Combat)", Category.COMBAT));
        add(new GenericModule("Crystal Placer", "Crystal Placer (Combat)", Category.COMBAT));
        add(new GenericModule("Anti Crystal", "Anti Crystal (Combat)", Category.COMBAT));
        add(new GenericModule("Hotbar Refill", "Hotbar Refill (Combat)", Category.COMBAT));
        add(new GenericModule("Fast Bow", "Fast Bow (Combat)", Category.COMBAT));
        add(new ArmorHUD());
        add(new CoordinatesHUD());
        add(new Waypoints());
        add(new GenericModule("Minimap", "Minimap (Render)", Category.RENDER));
        add(new GenericModule("Radar", "Radar (Render)", Category.RENDER));
        add(new GenericModule("Damage Indicators", "Damage Indicators (Render)", Category.RENDER));
        add(new GenericModule("Health Bars", "Health Bars (Render)", Category.RENDER));
        add(new GenericModule("No Hurt Cam", "No Hurt Cam (Render)", Category.RENDER));
        add(new GenericModule("No Overlay", "No Overlay (Render)", Category.RENDER));
        add(new GenericModule("No Weather", "No Weather (Render)", Category.RENDER));
        add(new GenericModule("No Fog", "No Fog (Render)", Category.RENDER));
        add(new Zoom());
        add(new GenericModule("Block ESP", "Block ESP (Render)", Category.RENDER));
        add(new GenericModule("X-Ray", "X-Ray (Render)", Category.RENDER));
        add(new GenericModule("Chunk Borders", "Chunk Borders (Render)", Category.RENDER));
        add(new GenericModule("Cave Finder", "Cave Finder (Render)", Category.RENDER));
        add(new GenericModule("Particle Control", "Particle Control (Render)", Category.RENDER));
        add(new Keystrokes());
        add(new GenericModule("Auto Farm", "Auto Farm (World)", Category.WORLD));
        add(new GenericModule("Auto Plant", "Auto Plant (World)", Category.WORLD));
        add(new GenericModule("Auto Bonemeal", "Auto Bonemeal (World)", Category.WORLD));
        add(new GenericModule("Tree Cutter", "Tree Cutter (World)", Category.WORLD));
        add(new GenericModule("Auto Torch", "Auto Torch (World)", Category.WORLD));
        add(new GenericModule("Fast Place", "Fast Place (World)", Category.WORLD));
        add(new GenericModule("Auto Bridge", "Auto Bridge (World)", Category.WORLD));
        add(new GenericModule("Chest Sorter", "Chest Sorter (World)", Category.WORLD));
        add(new GenericModule("Auto Trade", "Auto Trade (World)", Category.WORLD));
        add(new GenericModule("Villager Roller", "Villager Roller (World)", Category.WORLD));
        add(new GenericModule("Spawner Finder", "Spawner Finder (World)", Category.WORLD));
        add(new GenericModule("Auto Compost", "Auto Compost (World)", Category.WORLD));
        add(new GenericModule("Block Counter", "Block Counter (World)", Category.WORLD));
        add(new GenericModule("Tunneler", "Tunneler (World)", Category.WORLD));
        add(new GenericModule("Slime Chunk Finder", "Slime Chunk Finder (World)", Category.WORLD));
        add(new GenericModule("Portal Finder", "Portal Finder (World)", Category.WORLD));
        add(new GenericModule("Auto Sort", "Auto Sort (Player)", Category.PLAYER));
        add(new GenericModule("Auto Drop", "Auto Drop (Player)", Category.PLAYER));
        add(new GenericModule("Auto Craft", "Auto Craft (Player)", Category.PLAYER));
        add(new GenericModule("Auto Enchant", "Auto Enchant (Player)", Category.PLAYER));
        add(new GenericModule("Auto Repair", "Auto Repair (Player)", Category.PLAYER));
        add(new GenericModule("Inventory Cleaner", "Inventory Cleaner (Player)", Category.PLAYER));
        add(new GenericModule("Chest Stealer", "Chest Stealer (Player)", Category.PLAYER));
        add(new GenericModule("Quick Move", "Quick Move (Player)", Category.PLAYER));
        add(new GenericModule("Auto Sleep", "Auto Sleep (Player)", Category.PLAYER));
        add(new GenericModule("Auto Elytra Swap", "Auto Elytra Swap (Player)", Category.PLAYER));
        add(new GenericModule("Auto Command", "Auto Command (Player)", Category.PLAYER));
        add(new GenericModule("Auto Tpa Accept", "Auto Tpa Accept (Player)", Category.PLAYER));
        add(new GenericModule("Hotbar Scroll", "Hotbar Scroll (Player)", Category.PLAYER));
        add(new GenericModule("Item Scroller", "Item Scroller (Player)", Category.PLAYER));
        add(new GenericModule("Shulker Unload", "Shulker Unload (Player)", Category.PLAYER));
        add(new GenericModule("Auto Totem Refill", "Auto Totem Refill (Player)", Category.PLAYER));
        add(new GenericModule("Step", "Step (Movement)", Category.MOVEMENT));
        add(new GenericModule("Phase", "Phase (Movement)", Category.MOVEMENT));
        add(new GenericModule("Glide", "Glide (Movement)", Category.MOVEMENT));
        add(new GenericModule("Bunny Hop", "Bunny Hop (Movement)", Category.MOVEMENT));
        add(new GenericModule("Strafe", "Strafe (Movement)", Category.MOVEMENT));
        add(new GenericModule("Air Control", "Air Control (Movement)", Category.MOVEMENT));
        add(new GenericModule("Bounce", "Bounce (Movement)", Category.MOVEMENT));
        add(new GenericModule("Dolphin", "Dolphin (Movement)", Category.MOVEMENT));
        add(new GenericModule("Fast Fall", "Fast Fall (Movement)", Category.MOVEMENT));
        add(new GenericModule("Water Walk", "Water Walk (Movement)", Category.MOVEMENT));
        add(new GenericModule("Auto Firework", "Auto Firework (Movement)", Category.MOVEMENT));
        add(new GenericModule("Elytra Swap", "Elytra Swap (Movement)", Category.MOVEMENT));
        add(new GenericModule("Wall Climb", "Wall Climb (Movement)", Category.MOVEMENT));
        add(new GenericModule("Levitation Control", "Levitation Control (Movement)", Category.MOVEMENT));
        add(new GenericModule("Moon Jump", "Moon Jump (Movement)", Category.MOVEMENT));
        add(new GenericModule("Bridge Assist", "Bridge Assist (Movement)", Category.MOVEMENT));
        add(new GenericModule("Chat Filter", "Chat Filter (Misc)", Category.MISC));
        add(new GenericModule("Chat Timestamps", "Chat Timestamps (Misc)", Category.MISC));
        add(new GenericModule("Auto Reply", "Auto Reply (Misc)", Category.MISC));
        add(new GenericModule("Auto Greet", "Auto Greet (Misc)", Category.MISC));
        add(new AutoGG());
        add(new DeathCoords());
        add(new GenericModule("Chat Translator", "Chat Translator (Misc)", Category.MISC));
        add(new ScreenshotHelper());
        add(new GenericModule("Server Info", "Server Info (Misc)", Category.MISC));
        add(new GenericModule("Ping Spoof", "Ping Spoof (Misc)", Category.MISC));
        add(new GenericModule("Anti Kick", "Anti Kick (Misc)", Category.MISC));
        add(new GenericModule("Command Aliases", "Command Aliases (Misc)", Category.MISC));
        add(new GenericModule("Friend Sync", "Friend Sync (Misc)", Category.MISC));
        add(new GenericModule("Config Backup", "Config Backup (Misc)", Category.MISC));
        add(new GenericModule("Auto Login", "Auto Login (Misc)", Category.MISC));
        add(new GenericModule("Anti Bot", "Anti Bot (Misc)", Category.MISC));
        modules.sort(Comparator.comparing((Module m) -> m.category).thenComparing(m -> m.name));
        get("HUD").setEnabled(true);
    }

    public List<Module> all() { return modules; }

    public List<Module> byCategory(Category c) {
        List<Module> out = new ArrayList<>();
        for (Module m : modules) if (m.category == c) out.add(m);
        return out;
    }

    public Module get(String name) {
        for (Module m : modules) if (m.name.equalsIgnoreCase(name)) return m;
        return null;
    }

    public int activeCount() {
        int n = 0;
        for (Module m : modules) if (m.isEnabled()) n++;
        return n;
    }

    public void tick(MinecraftClient mc) {
        if (mc.currentScreen == null) pollKeybinds(mc);
        for (Module m : modules) {
            if (!m.isEnabled()) continue;
            try {
                m.onTick();
            } catch (Throwable t) {
                VarzClient.LOGGER.error("Module {} crashed and was disabled", m.name, t);
                m.setEnabled(false);
            }
        }
    }

    public void render2D(DrawContext ctx) {
        for (Module m : modules) {
            if (!m.isEnabled()) continue;
            try {
                m.onRender2D(ctx);
            } catch (Throwable t) {
                VarzClient.LOGGER.error("Module {} render error, disabling", m.name, t);
                m.setEnabled(false);
            }
        }
    }

    private void pollKeybinds(MinecraftClient mc) {
        long handle = mc.getWindow().getHandle();
        for (Module m : modules) {
            int k = m.getKey();
            if (k <= 0) continue;
            boolean down = GLFW.glfwGetKey(handle, k) == GLFW.GLFW_PRESS;
            boolean was = keyState.getOrDefault(k, false);
            if (down && !was) m.toggle();
            keyState.put(k, down);
        }
    }
}
