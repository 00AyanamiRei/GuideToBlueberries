package com.ayanami.dataaccesslayer.dao;

import com.ayanami.businesslogiclayer.model.SizeBlueberry;

import java.util.List;

/**
 * SizeBlueberry DAO interface
 */
public interface SizeBlueberryDAO {
    void save(SizeBlueberry sizeBlueberry);
    void update(SizeBlueberry sizeBlueberry);
    void delete(SizeBlueberry sizeBlueberry);
    SizeBlueberry findById(int id);
    List<SizeBlueberry> findAll();
}
