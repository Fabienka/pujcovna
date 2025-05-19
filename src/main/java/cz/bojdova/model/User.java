package cz.bojdova.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

private int id;
private String name;
private String email;
private List<Book> borrowedBooks;
private Date lastLoanDate;


    // Default constructor for Gson
    public User() {
    }
    // Constructor
    public User(int id, String name, String email, ArrayList<Book> borrowedBooks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.borrowedBooks = borrowedBooks;
        this.lastLoanDate = new Date(); // Set to current date
}
    // Constructor without borrowedBooks
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
        this.lastLoanDate = new Date(); // Set to current date
    }
    // Constructor without id and borrowedBooks
    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
        this.lastLoanDate = new Date(); // Set to current date
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

    public String getLastLoanDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
        if (lastLoanDate == null) {
            return "No loan date available";
        }
        return sdf.format(lastLoanDate);
    }
    public void setLastLoanDate(Date lastLoanDate) {
        this.lastLoanDate = lastLoanDate;
    }
    public void updateLastLoanDate() {
        this.lastLoanDate = new Date(); // Set to current date
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    public void setBorrowedBooks(ArrayList<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
        updateLastLoanDate();
    }

    public void addBorrowedBook(Book book) {
        this.borrowedBooks.add(book);
        updateLastLoanDate();
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
