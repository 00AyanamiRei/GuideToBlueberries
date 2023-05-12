package com.ayanami.dao;

import com.ayanami.model.BlueberryTaste;

import java.util.List;

public interface BlueberryTasteDAO {
    void save(BlueberryTaste blueberryTaste);
    void update(BlueberryTaste blueberryTaste);
    void delete(BlueberryTaste blueberryTaste);
    BlueberryTaste findById(int id);
    List<BlueberryTaste> findAll();
}
