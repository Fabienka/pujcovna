package cz.bojdova.model;

import java.util.ArrayList;
import java.util.List;

public class User {

private int id;
private String name;
private String email;
private List<Book> borrowedBooks;

    // Default constructor for Gson
    public User() {
    }
    // Constructor
    public User(int id, String name, String email, ArrayList<Book> borrowedBooks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.borrowedBooks = borrowedBooks;
}
    // Constructor without borrowedBooks
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
    }
    // Constructor without borrowedBooks
    public User(String name, String email) {
        this.id = 0; // Default id, can be set later
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
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

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    public void setBorrowedBooks(ArrayList<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void addBorrowedBook(Book book) {
        this.borrowedBooks.add(book);
    }
    public void removeBorrowedBook(Book book) {
        this.borrowedBooks.remove(book);
    }
    public void clearBorrowedBooks() {
        this.borrowedBooks.clear();
    }
    public boolean hasBorrowedBooks() {
        return !this.borrowedBooks.isEmpty();
    }
    public String getBorrowedBooksName() {
        if (borrowedBooks == null || borrowedBooks.isEmpty()) {
            return "No borrowed books";
        }
        StringBuilder sb = new StringBuilder();
        for (Book book : borrowedBooks) {
            sb.append(book.getTitle()).append(", ");
        }
        sb.setLength(sb.length() - 2); // Remove trailing ", "
        return sb.toString();
    }
}
