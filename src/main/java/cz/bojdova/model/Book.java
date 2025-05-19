package cz.bojdova.model;


public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private boolean available;

    // Konstruktor pro Gson
    public Book() {
    }

    // Konstruktor s ID a dostupností
    public Book(int id, String title, String author, String genre, boolean available) {
        this(title, author, genre, available);
        this.id = id;
    }

    // Konstruktor s ID, implicitně dostupný
    public Book(int id, String title, String author, String genre) {
        this(id, title, author, genre, true);
    }

    // Konstruktor bez ID, dostupnost volitelná
    public Book(String title, String author, String genre, boolean available) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.available = available;
    }

    // Konstruktor bez ID, implicitně dostupný
    public Book(String title, String author, String genre) {
        this(title, author, genre, true);
    }

    // Gettery a settery

    // ID knihy
    public int getId() {
        return id;
    }

    // Název knihy
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Autor knihy
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // Žánr knihy
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // Dostupnost knihy 
    public boolean isAvailable() {
        return available;
    }


    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Vrací textovou reprezentaci knihy 
    @Override
    public String toString() {
        return String.format("Book[id=%d, title='%s', author='%s', genre='%s', available=%b]",
                id, title, author, genre, available);
    }
}
