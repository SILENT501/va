# VARZCLIENT v7.2.0 - Fabric 1.21.11 (Java)

Merged from the HTML concept GUI and the Python source generator, ported to a real Fabric mod.

## What's new in v7.2.0
* Branded release metadata as VARZCLIENT.
* Includes Player Alert: configurable proximity radius and toast notification when another player enters range.
* Uses a Gradle wrapper so the project can be built with JDK 21 without a preinstalled Gradle.
* **ClickGUI settings panel.** Right-click any module card to open a panel with its settings:
  click a toggle to flip it, click a mode to cycle it, click the left/right half of a number row
  to step it down/up, and click the "Key" row then press a key to bind that module (Esc clears it).
* **Toast notifications.** A small sliding box in the top-right shows `ModuleName ON/OFF` whenever
  a module is toggled (from the GUI or its keybind), and modules can push their own messages
  (e.g. `Notifier`'s low-health warning).
* **10 more real modules** (26 total, up from 16): Criticals, Auto Tool, Armor HUD, Coordinates HUD,
  Zoom, Keystrokes, Auto GG, Screenshot Helper, Waypoints, Notifier.

## Overview
* **265 modules** registered (105 from the original list, 8 Base Finder, 100 "new" batch, now with 26 real ones).
* **26 modules have real behaviour:** Sprint, Fullbright, Auto Respawn, Anti AFK, Flight, No Fall, Speed,
  Kill Aura, Stash Finder, Auto Eat, Jesus, Auto Walk, Sneak, Auto Jump, Death Coords, HUD, Criticals,
  Auto Tool, Armor HUD, Coordinates HUD, Zoom, Keystrokes, Auto GG, Screenshot Helper, Waypoints, Notifier.
* The rest are `GenericModule`s: they toggle, show in the GUI (greyed text), save to config and now
  have an editable settings panel if you give them settings — but they do nothing yet.
* ClickGUI: **Right Shift**. Tabs, search, scroll, status bar, per-module settings panel, toast popups.
* Config is saved to `config/varzclient.json` (enabled state, keybind id `key` = GLFW key code, settings).

## Build
1. Install JDK 21.
2. On Windows run `gradlew.bat build`; on Linux/macOS run `./gradlew build`. The first build needs internet access to download Gradle and Fabric dependencies.
3. The mod jar is written to `build/libs/varzclient-7.2.0.jar`.
4. If dependency resolution fails, verify the Minecraft 1.21.11 Yarn, Fabric Loader, and Fabric API versions in `gradle.properties` against the official Fabric developer page.

## Adding real behaviour to a module
Create a class extending `Module` in `module/modules/`, then in `ModuleManager.init()` replace the matching
`add(new GenericModule("Name", ...))` line with `add(new YourModule())`.

## Notes / likely compile risk spots
Written against 1.21.11 Yarn from memory, not compiled against the real mappings — if a method name
differs, the compiler error will point right at it. Most likely spots, roughly in order of risk:
* `Click` in `ClickGuiScreen#mouseClicked` (the newer input-object style for mouse events).
* `PlayerMoveC2SPacket.OnGroundOnly` in `NoFall`.
* `ItemStack#getMiningSpeedMultiplier(BlockState)` in `AutoTool`.
* `GameOptions#getFov()` / `SimpleOption#setValue` in `Zoom`.
* `ScreenshotRecorder.saveScreenshot(...)` signature in `ScreenshotHelper`.
* `ClientPlayNetworkHandler#sendChatMessage(String)` in `AutoGG`.
* `DrawContext#drawCenteredTextWithShadow` in `Keystrokes`.

None of these are guesses about *game behaviour* (the logic itself is straightforward), only about
exact method/class names in this specific Minecraft version's mappings. Paste any compiler error back
and it can be fixed directly.

## Server use
Many servers forbid movement/combat hacks and run detection tools. Use on singleplayer or servers
that explicitly allow this kind of client — this project does not include and will not include any
code meant to evade anti-cheat or detection tools.
