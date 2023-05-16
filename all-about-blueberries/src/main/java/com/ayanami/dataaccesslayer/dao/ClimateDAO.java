package com.ayanami.dataaccesslayer.dao;

import com.ayanami.businesslogiclayer.model.Climate;

import java.util.List;

public interface ClimateDAO {
    void save(Climate climate);

    void update(Climate climate);

    void delete(Climate climate);

    Climate findById(int id);

    List<Climate> findAll();
}
