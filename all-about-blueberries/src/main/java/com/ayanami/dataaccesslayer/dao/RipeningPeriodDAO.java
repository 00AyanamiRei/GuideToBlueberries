package com.ayanami.dataaccesslayer.dao;

import com.ayanami.businesslogiclayer.model.RipeningPeriod;

import java.util.List;

/**
 * RipeningPeriod DAO interface
 */
public interface RipeningPeriodDAO {
    void save(RipeningPeriod ripeningPeriod);
    void update(RipeningPeriod ripeningPeriod);
    void delete(RipeningPeriod ripeningPeriod);
    RipeningPeriod findById(int id);
    List<RipeningPeriod> findAll();
}
