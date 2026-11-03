package com.varz.client.module;

/**
 * Registered, toggleable and saved in the config, but with no behaviour yet.
 * Replace one by writing a class that extends {@link Module} and
 * registering it in ModuleManager instead of the GenericModule line.
 */
public class GenericModule extends Module {
    public GenericModule(String name, String description, Category category) {
        super(name, description, category);
    }

    @Override
    public boolean isImplemented() { return false; }
}
