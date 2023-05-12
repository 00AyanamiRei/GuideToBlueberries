package com.ayanami.model;

import com.ayanami.model.interfaces.BlueberryTasteI;

import java.util.StringJoiner;

public class BlueberryTaste implements BlueberryTasteI {
    private int id;
    private String taste;

    public BlueberryTaste(int id, String taste) {
        this.id = id;
        this.taste = taste;
    }

    public static BlueberryTasteBuilderId builder() {
        return id -> taste -> () -> new BlueberryTaste(id, taste);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTaste() {
        return taste;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public interface BlueberryTasteBuilderId {
        BlueberryTasteBuilderTaste id(int id);
    }

    public interface BlueberryTasteBuilderTaste {
        BlueberryTasteBuilder taste(String taste);
    }

    public interface BlueberryTasteBuilder {
        BlueberryTaste build();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "", "")
                .add("" + id)
                .add(taste)
                .toString();
    }
}
