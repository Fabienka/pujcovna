package cz.bojdova.controller;

import java.util.ArrayList;
import java.util.List;

import cz.bojdova.dao.BookDao;
import cz.bojdova.dao.UserDao;
import cz.bojdova.dao.impl.BookDaoImpl;
import cz.bojdova.dao.impl.UserDaoImpl;
import cz.bojdova.model.Book;
import cz.bojdova.model.User;
import cz.bojdova.util.IdGenerator;

public class BookacheController {
    private final BookDao bookDao;
    private final UserDao userDao;
    private final List<Book> books;
    private final List<User> users;

    // Default constructor
    public BookacheController() {
        this.bookDao = new BookDaoImpl();
        this.userDao = new UserDaoImpl();
        this.books = new ArrayList<>(bookDao.getAllBooks());
        this.users = new ArrayList<>(userDao.getAllUsers());
    }

    public BookacheController(BookDao bookDao, UserDao userDao) {
        this.bookDao = bookDao;
        this.userDao = userDao;
        this.books = new ArrayList<>(bookDao.getAllBooks());
        this.users = new ArrayList<>(userDao.getAllUsers());
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addBook(Book book) {
        books.add(book);
        bookDao.saveAllBooks(books);
    }

    public void removeBook(Book book) {
        books.remove(book);
        bookDao.saveAllBooks(books);
    }

    public void removeBook(int id) {
        System.out.println("Removing book with ID: " + id);
        Book bookToRemove = findBookById(id);
        if (bookToRemove == null) {
            System.out.println("Book with ID " + id + " not found.");
            return;
        }
        books.remove(bookToRemove);
        bookDao.saveAllBooks(books);
    }

    public void addUser(User user) {
        users.add(user);
        userDao.saveAllUsers(users);
    }

    public void removeUser(int id) {
        users.remove(id);
        userDao.saveAllUsers(users);
    }

    public void loanBookToUser(int bookId, int userId) {
        Book book = findBookById(bookId);
        User user = findUserById(userId);


        if (book != null && user != null && book.isAvailable()) {
            book.setAvailable(false);
            user.getBorrowedBooks().add(book);
            user.updateLastLoanDate();
            bookDao.saveAllBooks(books);
            userDao.saveAllUsers(users);
        }
    }

    public void returnBookFromUser(int bookId, int userId) {
        Book book = findBookById(bookId);
        User user = findUserById(userId);

        if (book != null && user != null) {
            user.getBorrowedBooks().removeIf(b -> b.getId() == bookId);
            book.setAvailable(true);
            bookDao.saveAllBooks(books);
            userDao.saveAllUsers(users);
        }
    }

    public Book findBookById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }
    public Book updateBook(int id, String title, String author, String genre) {
        Book book = findBookById(id);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setGenre(genre);
            bookDao.saveAllBooks(books);
        }
        return book;
    }

    public User updateUser(int id, String name, String email) {
        User user = findUserById(id);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            userDao.saveAllUsers(users);
        }
        return user;
    }
    public User findUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public void saveAllBooks(List<Book> books) {
        bookDao.saveAllBooks(books);
    }
    public IdGenerator initIdGenerator() {
        List<Integer> bookIds = books.stream().map(Book::getId).toList();
        List<Integer> userIds = users.stream().map(User::getId).toList();

        return new IdGenerator(bookIds, userIds);
    }
}

