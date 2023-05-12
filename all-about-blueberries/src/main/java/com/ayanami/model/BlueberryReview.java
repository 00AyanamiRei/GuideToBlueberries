package com.ayanami.model;

import com.ayanami.model.interfaces.BlueberryReviewI;

import java.util.StringJoiner;

public class BlueberryReview implements BlueberryReviewI {
    private int id;
    private int blueberryID;
    private int userID;
    private String review;
    private int rating;

    public BlueberryReview(int id, int blueberryID, int userID, String review, int rating) {
        this.id = id;
        this.blueberryID = blueberryID;
        this.userID = userID;
        this.review = review;
        this.rating = rating;
    }

    public static BlueberryReviewBuilderId builder() {
        return id -> blueberryID -> userID -> review -> rating -> () -> new BlueberryReview(id, blueberryID, userID, review, rating);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getBlueberryID() {
        return blueberryID;
    }

    @Override
    public int getUserID() {
        return userID;
    }

    @Override
    public String getReview() {
        return review;
    }

    @Override
    public int getRating() {
        return rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBlueberryID(int blueberryID) {
        this.blueberryID = blueberryID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public interface BlueberryReviewBuilderId {
        BlueberryReviewBuilderBlueberryID id(int id);
    }

    public interface BlueberryReviewBuilderBlueberryID {
        BlueberryReviewBuilderUserID blueberryID(int blueberry);
    }

    private interface BlueberryReviewBuilderUserID  {
        BlueberryReviewBuilderReview userID(int userID);
    }

    public interface BlueberryReviewBuilderReview {
        BlueberryReviewBuilderRating review(String review);
    }

    public interface BlueberryReviewBuilderRating {
        BlueberryReviewBuilder rating(int rating);
    }

    public interface BlueberryReviewBuilder {
        BlueberryReview build();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "", "")
                .add("" + id)
                .add("" + blueberryID)
                .add("" + userID)
                .add(review)
                .add("" + rating)
                .toString();
    }
}
