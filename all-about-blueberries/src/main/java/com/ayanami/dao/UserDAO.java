package com.ayanami.dao;

import com.ayanami.model.User;

import java.util.List;

public interface UserDAO {
    void save(User user);
    void update(User user);
    void delete(User user);
    User findById(int id);
    List<User> findAll();
}
