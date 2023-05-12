package com.ayanami.dao;

import com.ayanami.model.BlueberryReview;

import java.util.List;

public interface BlueberryReviewDAO {
    void save(BlueberryReview review);

    void update(BlueberryReview review);

    void delete(BlueberryReview review);

    BlueberryReview findById(int id);

    List<BlueberryReview> findAll();
}
