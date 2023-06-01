package com.ayanami.businesslogiclayer.model;

import com.ayanami.businesslogiclayer.model.interfaces.BlueberryTasteI;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.StringJoiner;

/**
 * BlueberryTaste entity
 */
public class BlueberryTaste implements BlueberryTasteI {
    @NotNull
    @Positive
    private Integer id;

    @NotNull
    @Size(min = 2, max = 50)
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
