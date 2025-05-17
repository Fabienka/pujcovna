package cz.bojdova;

import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import cz.bojdova.dao.BookDao;
import cz.bojdova.dao.impl.BookDaoImpl;
import cz.bojdova.model.Book;
import cz.bojdova.model.User;
import cz.bojdova.util.IdGenerator;
import cz.bojdova.view.MainGUI;
import cz.bojdova.dao.UserDao;
import cz.bojdova.dao.impl.UserDaoImpl;
import cz.bojdova.util.IdGenerator;

public class Main {
    public static void main(String[] args) {
        // Set the look and feel to the system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        BookDao bookDao = new BookDaoImpl();
        UserDao userDao = new UserDaoImpl();

        List<Book> bookList = bookDao.getAllBooks();
        List<User> userList = userDao.getAllUsers();

        IdGenerator idGenerator = new IdGenerator(bookList, userList);


        // Create and display the main application window
        SwingUtilities.invokeLater(() -> { MainGUI mainGUI = new MainGUI(); });
    }
}