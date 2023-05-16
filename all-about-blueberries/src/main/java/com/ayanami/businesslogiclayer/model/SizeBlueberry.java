package com.ayanami.businesslogiclayer.model;

import com.ayanami.businesslogiclayer.model.interfaces.SizeBlueberryI;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.StringJoiner;

public class SizeBlueberry implements SizeBlueberryI {
    @NotNull
    @Positive
    private Integer id;

    @NotEmpty
    @Size(max = 100)
    private String size;

    public SizeBlueberry(int id, String size) {
        this.id = id;
        this.size = size;
    }

    public static SizeBlueberryBuilderId builder() {
        return id -> size -> () -> new SizeBlueberry(id, size);
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "", "")
                .add("uuid = " + id)
                .add(size)
                .toString();
    }

    public interface SizeBlueberryBuilderId {
        SizeBlueberryBuilderSize id(int id);
    }

    public interface SizeBlueberryBuilderSize {
        SizeBlueberryBuilder size(String size);
    }

    public interface SizeBlueberryBuilder {
        SizeBlueberry build();
    }
}
