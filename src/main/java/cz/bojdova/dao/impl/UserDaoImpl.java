package cz.bojdova.dao.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cz.bojdova.dao.UserDao;
import cz.bojdova.model.User;

public class UserDaoImpl implements UserDao {

    private static final String FILE_PATH = "users.json";
    private final Gson gson = new Gson();

    @Override
    public List<User> getAllUsers() {
        try {
            String json = Files.readString(Path.of(FILE_PATH));
            User[] usersArray = gson.fromJson(json, User[].class);
            return new ArrayList<>(Arrays.asList(usersArray));
        } catch (IOException e) {
            System.err.println("Error: Cannot read or find the file '" + FILE_PATH + "'.");
        } catch (JsonSyntaxException e) {
            System.err.println("Error: Invalid JSON syntax in file '" + FILE_PATH + "'. " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error while reading users: " + e.getMessage());
        }

        return new ArrayList<>(); // fallback in case of error
    }

    @Override
    public void saveAllUsers(List<User> users) {
        try {
            String json = gson.toJson(users);
            Files.write(Path.of(FILE_PATH), json.getBytes());
        } catch (IOException e) {
            System.err.println("Error: Failed to save data to '" + FILE_PATH + "'. " + e.getMessage());
        }
    }
}
