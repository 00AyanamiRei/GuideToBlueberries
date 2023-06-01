package com.ayanami.businesslogiclayer.model.interfaces;

/**
 * Blueberry entity interface
 */
public interface BlueberryI {
    int getId();
    String getName();
    int getSizeBlueberryID();
    int getPeriodID();
    int getTasteID();
    int getClimateID();
    String getLandingDistance();
    String getPollination();
    String getDescription();
    String getPhoto();

}
