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

    // Konstruktor pro Gson
    public User() {
    }

    // Konstruktor s výpůjčkami
    public User(int id, String name, String email, ArrayList<Book> borrowedBooks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.borrowedBooks = borrowedBooks;
        this.lastLoanDate = new Date();
    }

    // Konstruktor bez výpůjček
    public User(int id, String name, String email) {
        this(id, name, email, new ArrayList<>());
    }

    // Konstruktor bez ID a výpůjček
    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
    }

    // ID uživatele
    public int getId() {
        return id;
    }

    // Jméno uživatele
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // E-mail
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Poslední datum výpůjčky (formátované)
    public String getLastLoanDate() {
        if (lastLoanDate == null) return "No loan date available";
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
        return sdf.format(lastLoanDate);
    }

    public void setLastLoanDate(Date lastLoanDate) {
        this.lastLoanDate = lastLoanDate;
    }

    // Aktualizace na aktuální datum
    public void updateLastLoanDate() {
        this.lastLoanDate = new Date();
    }

    // Výpůjčky
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
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

    // Vrací seznam názvů vypůjčených knih jako text
    public String getBorrowedBooksName() {
        if (borrowedBooks == null || borrowedBooks.isEmpty()) {
            return "No borrowed books";
        }
        StringBuilder sb = new StringBuilder();
        for (Book book : borrowedBooks) {
            sb.append(book.getTitle()).append(", ");
        }
        sb.setLength(sb.length() - 2); // Odstraň poslední čárku a mezeru
        return sb.toString();
    }

    // Textová reprezentace uživatele
    @Override
    public String toString() {
        return String.format("User[id=%d, name='%s', email='%s', borrowedBooks=%d]",
                id, name, email, borrowedBooks != null ? borrowedBooks.size() : 0);
    }
}
