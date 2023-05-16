package com.ayanami.businesslogiclayer.model;

import com.ayanami.businesslogiclayer.model.interfaces.BlueberryI;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.StringJoiner;

public class Blueberry implements BlueberryI {
    @NotNull
    private Integer id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    private Integer sizeBlueberryID;

    @NotNull
    private Integer periodID;

    @NotNull
    private Integer tasteID;

    @NotNull
    private Integer climateID;

    @NotBlank
    @Size(max = 100)
    private String landingDistance;

    @NotBlank
    @Size(max = 100)
    private String pollination;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotBlank
    @Size(max = 255)
    private String photo;

    public Blueberry(int id, String name, int sizeBlueberryID, int periodID, int tasteID, int climateID, String landingDistance, String pollination, String description, String photo) {
        this.id = id;
        this.name = name;
        this.sizeBlueberryID = sizeBlueberryID;
        this.periodID = periodID;
        this.tasteID = tasteID;
        this.climateID = climateID;
        this.landingDistance = landingDistance;
        this.pollination = pollination;
        this.description = description;
        this.photo = photo;
    }

    public static BlueberryBuilderId builder() {
        return id -> name -> sizeBlueberryID -> periodID -> tasteID -> climateID -> landingDistance -> pollination -> description -> photo -> () -> new Blueberry(id, name, sizeBlueberryID, periodID ,tasteID, climateID, landingDistance, pollination, description, photo);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSizeBlueberryID() {
        return sizeBlueberryID;
    }

    @Override
    public int getPeriodID() {
        return periodID;
    }

    @Override
    public int getTasteID() {
        return tasteID;
    }

    @Override
    public int getClimateID() {
        return climateID;
    }

    @Override
    public String getLandingDistance() {
        return landingDistance;
    }

    @Override
    public String getPollination() {
        return pollination;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getPhoto() {
        return photo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSizeBlueberryID(int sizeBlueberryID) {
        this.sizeBlueberryID = sizeBlueberryID;
    }

    public void setPeriodID(int periodID) {
        this.periodID = periodID;
    }

    public void setTasteID(int tasteID) {
        this.tasteID = tasteID;
    }

    public void setClimateID(int climateID) {
        this.climateID = climateID;
    }

    public void setLandingDistance(String landingDistance) {
        this.landingDistance = landingDistance;
    }

    public void setPollination(String pollination) {
        this.pollination = pollination;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public interface BlueberryBuilderId {
        BlueberryBuilderName id(int id);
    }

    public interface BlueberryBuilderName {
        BlueberryBuilderSizeID name(String name);
    }

    public interface BlueberryBuilderSizeID {
        BlueberryBuilderPeriodID sizeBlueberryID(int sizeBlueberryID);
    }

    public interface BlueberryBuilderPeriodID {
        BlueberryBuilderTasteID periodID(int periodID);
    }

    public interface BlueberryBuilderTasteID {
        BlueberryBuilderClimateID tasteID(int tasteID);
    }

    public interface BlueberryBuilderClimateID {
        BlueberryBuilderLandingDistance climateID(int climateID);
    }

    public  interface BlueberryBuilderLandingDistance {
        BlueberryBuilderPollination landingDistance(String landingDistance);
    }

    public interface BlueberryBuilderPollination {
        BlueberryBuilderDescription pollination(String pollination);
    }

    public interface BlueberryBuilderDescription {
        BlueberryBuilderPhoto description(String description);
    }

    public interface BlueberryBuilderPhoto {
        BlueberryBuilder photo(String photo);
    }

    public interface BlueberryBuilder {
        Blueberry build();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "", "")
                .add("" + id)
                .add(name)
                .add("" + sizeBlueberryID)
                .add("" + periodID)
                .add("" + tasteID)
                .add("" + climateID)
                .add(landingDistance)
                .add(pollination)
                .add(description)
                .add(photo)
                .toString();
    }
}