package cz.bojdova.dao;

import java.util.List;

import cz.bojdova.model.User;

public interface UserDao {
    List<User> getAllUsers();
    void saveAllUsers(List<User> users);
}