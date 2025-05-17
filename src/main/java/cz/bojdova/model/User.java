package cz.bojdova.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {

private final int id;
private String name;
private String email;
private ArrayList<Integer> borrowedBooksIds;

    // Constructor
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.borrowedBooksIds = new ArrayList<Integer>();
}
    // Getter for id
    public int getId() {
        return id;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public ArrayList<Integer> getBorrowedBooksIds() {
        return borrowedBooksIds;
    }
}
