package com.ayanami.businesslogiclayer.model;

import com.ayanami.businesslogiclayer.model.interfaces.BlueberryReviewI;
import jakarta.validation.constraints.*;

import java.util.StringJoiner;

public class BlueberryReview implements BlueberryReviewI {
    @NotNull
    private Integer id;

    @NotNull
    private Integer blueberryID;

    @NotNull
    private Integer userID;

    @NotBlank
    @Size(max = 500)
    private String review;

    @Min(1)
    @Max(5)
    private Integer rating;


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
