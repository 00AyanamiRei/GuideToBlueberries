package com.ayanami.dataaccesslayer.dao;

import com.ayanami.businesslogiclayer.model.Blueberry;

import java.util.List;

public interface BlueberryDAO {
    void save(Blueberry blueberry);
    void update(Blueberry blueberry);
    void delete(Blueberry blueberry);
    Blueberry findById(int id);
    List<Blueberry> findAll();
}
