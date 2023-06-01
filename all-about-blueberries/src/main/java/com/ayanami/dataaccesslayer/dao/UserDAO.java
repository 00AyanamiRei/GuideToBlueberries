package com.ayanami.dataaccesslayer.dao;

import com.ayanami.businesslogiclayer.model.User;

import java.util.List;

/**
 * User DAO interface
 */
public interface UserDAO {
    boolean save(User user);
    void update(User user);
    void delete(User user);
    User findById(int id);
    List<User> findAll();
    boolean findUserByUserNameAndPassword(String userNameInput, String passwordInput);
    String getUserStatus(String username);
}
