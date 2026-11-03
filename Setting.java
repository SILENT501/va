package com.varz.client.module.setting;

import com.google.gson.JsonElement;

public abstract class Setting<T> {
    public final String name;
    protected T value;

    protected Setting(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public T get() { return value; }
    public void set(T value) { this.value = value; }
    public abstract JsonElement save();
    public abstract void load(JsonElement e);
}
