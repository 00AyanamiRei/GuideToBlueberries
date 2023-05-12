package com.ayanami.model;

import com.ayanami.model.interfaces.ClimateI;

import java.util.StringJoiner;

public class Climate implements ClimateI {
    private int id;
    private String climate;

    private Climate(int id, String climate) {
        this.id = id;
        this.climate = climate;
    }

    public static ClimateBuilderId builder() {
        return id -> climate -> () -> new Climate(id, climate);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getClimate() {
        return climate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public interface ClimateBuilderId {
        ClimateBuilderClimate id(int id);
    }

    public interface ClimateBuilderClimate {
        ClimateBuilder climate(String climate);
    }

    public interface ClimateBuilder{
        Climate build();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "", "")
                .add("" + id)
                .add(climate)
                .toString();
    }
}