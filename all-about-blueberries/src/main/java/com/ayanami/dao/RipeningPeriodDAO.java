package com.ayanami.dao;

import com.ayanami.model.RipeningPeriod;

import java.util.List;

public interface RipeningPeriodDAO {
    void save(RipeningPeriod ripeningPeriod);
    void update(RipeningPeriod ripeningPeriod);
    void delete(RipeningPeriod ripeningPeriod);
    RipeningPeriod findById(int id);
    List<RipeningPeriod> findAll();
}
