package cz.bojdova.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import cz.bojdova.controller.BookacheController;


public class MainGUI {
    private JFrame frame;
    private JTable bookTable;
    private JTable loanTable;
    private DefaultTableModel tableModelUser;
    private DefaultTableModel tableModelBook;
    private BookacheController controller;
  
    private JTable userTable;

    public MainGUI() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Welcome to Bookache");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        //frame.setLayout(new BorderLayout(10, 10));

        JTabbedPane tabbedPane = new JTabbedPane();
        frame.add(tabbedPane);

        // Karta knih
        tabbedPane.addTab("Books", createBookPanel());

        // Karta výpůjček
        tabbedPane.addTab("Loans", createLoanPanel());

        frame.setVisible(true);


       
    }
    private JPanel createLoanPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel("Loans");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        tableModelUser = new DefaultTableModel(new String[]{"User inicials", "Books loaned", "Last loan date"}, 0);
        userTable = new JTable(tableModelUser);
        JScrollPane scrollPaneUser = new JScrollPane(userTable);
        panel.add(scrollPaneUser, BorderLayout.CENTER);
        List<User> users = controller.getUsers();
        for (User user : users) {
            tableModelUser.addRow(new Object[]{user.getName(), user.getEmail(), user.getBorrowedBooksName()});
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return panel;
    }
    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel titleLabel = new JLabel("Books for loan");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        tableModelBook = new DefaultTableModel(new String[]{"Title", "Author", "Genre", "Available"}, 0);
        bookTable = new JTable(tableModelBook);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        for (Book book : bookList) {
            tableModelBook.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getGenre(), book.isAvailable() ? "Yes" : "No"});
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addButton = createSizedButton("Add book");
        
        //List<Integer> bookIds = bookList.stream().map(Book::getId).toList();
        //List<Integer> userIds = userList.stream().map(User::getId).toList();
        //IdGenerator idGenerator = new IdGenerator(bookIds, userIds);

        addButton.addActionListener(e -> showAddBookDialog());
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        JButton loanButton = createSizedButton("Loan book");
        loanButton.addActionListener(e -> loanSelectedBook());
        buttonPanel.add(loanButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        JButton deleteButton = createSizedButton("Delete book");
        deleteButton.addActionListener(e -> deleteSelectedBook());
        buttonPanel.add(deleteButton);

        JButton saveButton = createSizedButton("Save changes"); 
        saveButton.addActionListener(e -> saveChangesToFile());
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(saveButton);

        panel.add(buttonPanel, BorderLayout.EAST);
        panel.setVisible(true);
        return panel;
    }    

    private JButton createSizedButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(150, 30));
        return button;
    }

    private void showAddBookDialog() {

        JDialog dialog = new JDialog(frame, "Add New Book", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 2, 5, 5));
        dialog.setLocationRelativeTo(frame);

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField genreField = new JTextField();

        dialog.add(new JLabel("Title:"));
        dialog.add(titleField);
        dialog.add(new JLabel("Author:"));
        dialog.add(authorField);
        dialog.add(new JLabel("Genre:"));
        dialog.add(genreField);

        JButton addBtn = new JButton("Add");
        addBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String genre = genreField.getText().trim();

            if (!title.isEmpty() && !author.isEmpty() && !genre.isEmpty()) {
                Book newBook = new Book(title, author, genre, true);
                controller.addBook(newBook);
                tableModelBook.addRow(new Object[]{title, author, genre, "Yes"});
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JLabel()); // filler
        dialog.add(addBtn);
        dialog.setVisible(true);
    }

    private void deleteSelectedBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                controller.removeBook(parseInt(bookTable.getValueAt(selectedRow,0).toString()));
                tableModelBook.removeRow(selectedRow);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(frame, "Please select a book to delete.");
        }
    }

    private void loanSelectedBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow >= 0) {
            Book book = bookList.get(selectedRow);
            if (!book.isAvailable()) {
                JOptionPane.showMessageDialog(frame, "This book is already on loan.");
            } else {
                book.setAvailable(false);
                tableModelBook.setValueAt("No", selectedRow, 3);
                bookDao.saveAllBooks(bookList);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a book to loan.");
        }
    }
    private void saveChangesToFile() {
        List<Book> updatedList = new ArrayList<>();
        // TODO přidat save pro users
        for (int i = 0; i < tableModelBook.getRowCount(); i++) {
            String title = tableModelBook.getValueAt(i, 0).toString();
            String author = tableModelBook.getValueAt(i, 1).toString();
            String genre = tableModelBook.getValueAt(i, 2).toString();
    
            updatedList.add(new Book(title, author, genre));
        }
    
        bookList = updatedList; // aktualizuj interní seznam
        bookDao.saveAllBooks(bookList);
        JOptionPane.showMessageDialog(frame, "Changes saved to books.json.");
    }
}