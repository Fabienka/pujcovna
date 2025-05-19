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

    public void removeBook(int id) {
        System.out.println("[INFO] Removing book with ID: " + id);
        Book bookToRemove = findBookById(id);
        if (bookToRemove == null) {
            System.out.println("[WARN] Book with ID " + id + " not found.");
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
        System.out.println("[INFO] Removing user with ID: " + id);
        User userToRemove = findUserById(id);
        if (userToRemove == null) {
            System.out.println("[WARN] User with ID " + id + " not found.");
            return;
        }
        users.remove(userToRemove);
        userDao.saveAllUsers(users);
    }

    public void loanBookToUser(int bookId, int userId) {
        Book book = findBookById(bookId);
        User user = findUserById(userId);

        if (book == null || user == null) {
            System.out.println("[ERROR] Book or User not found.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("[WARN] Book is already on loan.");
            return;
        }

        book.setAvailable(false);
        user.getBorrowedBooks().add(book);
        user.updateLastLoanDate();
        bookDao.saveAllBooks(books);
        userDao.saveAllUsers(users);
    }

    public void returnBookFromUser(int bookId, int userId) {
        Book book = findBookById(bookId);
        User user = findUserById(userId);

        if (book != null && user != null) {
            boolean removed = user.getBorrowedBooks().removeIf(b -> b.getId() == bookId);
            if (removed) {
                book.setAvailable(true);
                bookDao.saveAllBooks(books);
                userDao.saveAllUsers(users);
            } else {
                System.out.println("[WARN] Book not found in user's borrowed list.");
            }
        }
    }

    public Book updateBook(int id, String title, String author, String genre) {
        Book book = findBookById(id);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setGenre(genre);

            // Synchronizace vypůjčených kopií u uživatelů
            for (User user : users) {
                user.getBorrowedBooks().stream()
                        .filter(b -> b.getId() == id)
                        .forEach(b -> {
                            b.setTitle(title);
                            b.setAuthor(author);
                            b.setGenre(genre);
                        });
            }

            bookDao.saveAllBooks(books);
            userDao.saveAllUsers(users);
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

    public Book findBookById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public User findUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    public void saveAllBooks(List<Book> books) {
        bookDao.saveAllBooks(books);
    }

    public void saveAllUsers(List<User> users) {
        userDao.saveAllUsers(users);
    }

    public IdGenerator initIdGenerator() {
        List<Integer> bookIds = books.stream().map(Book::getId).toList();
        List<Integer> userIds = users.stream().map(User::getId).toList();
        return new IdGenerator(bookIds, userIds);
    }
}
