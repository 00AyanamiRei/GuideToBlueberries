package com.ayanami.dataaccesslayer.dao;

import com.ayanami.businesslogiclayer.model.BlueberryReview;

import java.util.List;

/**
 * BlueberryReview DAO interface
 */
public interface BlueberryReviewDAO {
    void save(BlueberryReview review);

    void update(BlueberryReview review);

    void delete(BlueberryReview review);

    BlueberryReview findById(int id);

    List<BlueberryReview> findAll();
}
