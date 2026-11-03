package com.varz.client.module;

public enum Category {
    COMBAT("Combat"), RENDER("Render"), WORLD("World"), PLAYER("Player"),
    MOVEMENT("Movement"), MISC("Misc"), BASE_FINDER("Base Finder");

    public final String label;
    Category(String label) { this.label = label; }
}
