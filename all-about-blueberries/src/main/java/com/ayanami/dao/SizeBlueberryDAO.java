package com.ayanami.dao;

import com.ayanami.model.SizeBlueberry;

import java.util.List;

public interface SizeBlueberryDAO {
    void save(SizeBlueberry sizeBlueberry);
    void update(SizeBlueberry sizeBlueberry);
    void delete(SizeBlueberry sizeBlueberry);
    SizeBlueberry findById(int id);
    List<SizeBlueberry> findAll();
}
