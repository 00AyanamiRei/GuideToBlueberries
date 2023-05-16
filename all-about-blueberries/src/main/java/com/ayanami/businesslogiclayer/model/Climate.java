package com.ayanami.businesslogiclayer.model;

import com.ayanami.businesslogiclayer.model.interfaces.ClimateI;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.StringJoiner;

public class Climate implements ClimateI {
    @NotNull
    @Positive
    private Integer id;

    @NotNull
    @Size(min = 2, max = 50)
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