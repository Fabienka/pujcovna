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
import cz.bojdova.model.Book;
import cz.bojdova.model.User;
import cz.bojdova.util.IdGenerator;


public class MainGUI {
    private JFrame frame;
    private JTable bookTable;
    private JTable loanTable;
    private DefaultTableModel tableModelUser;
    private DefaultTableModel tableModelBook;
    private final BookacheController controller = new BookacheController();
    private final IdGenerator idGen = controller.initIdGenerator();
  
    private JTable userTable;

    List<Book> fetchedBooks = controller.getBooks();
    List<User> fetchedUsers = controller.getUsers();

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
        tableModelUser = new DefaultTableModel(new String[]{"ID", "User inicials", "Books loaned", "Last loan date"}, 0);
        userTable = new JTable(tableModelUser);
        JScrollPane scrollPaneUser = new JScrollPane(userTable);
        panel.add(scrollPaneUser, BorderLayout.CENTER);
        List<User> users = controller.getUsers();
        for (User user : users) {
            tableModelUser.addRow(new Object[]{user.getId(),user.getName(), user.getBorrowedBooksName(), user.getLastLoanDate()});
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addButton = createSizedButton("Add user");
        addButton.addActionListener(e -> showAddUserDialog());
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        JButton loanButton = createSizedButton("Retrieve book");
        loanButton.addActionListener(e-> showLoanDialogForSelectedUser());
        buttonPanel.add(loanButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        JButton deleteButton = createSizedButton("Delete user");
        deleteButton.addActionListener(e -> deleteSelectedUser());
        buttonPanel.add(deleteButton);

        JButton updateButton = createSizedButton("Update user");
        updateButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                User user = fetchedUsers.get(selectedRow);
                int id = parseInt(bookTable.getValueAt(selectedRow, 0).toString());
                showUpdateUserDialog(id);
                tableModelBook.setValueAt(user.getName(), selectedRow, 1);
                tableModelBook.setValueAt(user.getBorrowedBooks(), selectedRow, 2);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a user to update.");
            }
        });
        buttonPanel.add(updateButton);

       // JButton saveButton = createSizedButton("Save changes"); 
       // saveButton.addActionListener(e -> saveChangesToFile());
       // buttonPanel.add(Box.createVerticalStrut(10));
       // buttonPanel.add(saveButton);

        panel.add(buttonPanel, BorderLayout.EAST);

        JPanel searchPanel = createShowPanel();
        panel.add(searchPanel, BorderLayout.SOUTH); // nebo NORTH podle potřeby
        return panel;
    }

    private JPanel createShowPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        JLabel searchLabel = new JLabel("Search:");
        JTextField searchField = new JTextField();
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        return searchPanel;
    }
    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel titleLabel = new JLabel("Books for loan");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        tableModelBook = new DefaultTableModel(new String[]{"ID", "Title", "Author", "Genre", "Available"}, 0);
        bookTable = new JTable(tableModelBook);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        JPanel searchPanel = createShowPanel();
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.SOUTH); // nebo NORTH podle potřeby

       
        for (Book book : fetchedBooks) {
            tableModelBook.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.isAvailable() ? "Yes" : "No"});
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addButton = createSizedButton("Add book");
        addButton.addActionListener(e -> showAddBookDialog());
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        JButton loanButton = createSizedButton("Loan book");
        loanButton.addActionListener(e-> showLoanDialogForSelectedBook());
        buttonPanel.add(loanButton);
        buttonPanel.add(Box.createVerticalStrut(10));

        JButton deleteButton = createSizedButton("Delete book");
        deleteButton.addActionListener(e -> deleteSelectedBook());
        buttonPanel.add(deleteButton);

        JButton updateButton = createSizedButton("Update book");
        updateButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                Book book = fetchedBooks.get(selectedRow);
                int id = parseInt(bookTable.getValueAt(selectedRow, 0).toString());
                showUpdateBookDialog(id);
                tableModelBook.setValueAt(book.getTitle(), selectedRow, 1);
                tableModelBook.setValueAt(book.getAuthor(), selectedRow, 2);
                tableModelBook.setValueAt(book.getGenre(), selectedRow, 3);
                tableModelBook.setValueAt(book.isAvailable() ? "Yes" : "No", selectedRow, 4);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a book to update.");
            }
        });
        buttonPanel.add(updateButton);

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
                int newId = idGen.getNextBookId();
                Book newBook = new Book(newId,title, author, genre, true);
                controller.addBook(newBook);
                tableModelBook.addRow(new Object[]{newId,title, author, genre, "Yes"});
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JLabel()); // filler
        dialog.add(addBtn);
        dialog.setVisible(true);
    }

    private void showUpdateBookDialog(int id) {

        JDialog dialog = new JDialog(frame, "Update Book", true);
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

        JButton addBtn = new JButton("Update");
        addBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String genre = genreField.getText().trim();

            if (!title.isEmpty() && !author.isEmpty() && !genre.isEmpty()) {
                controller.updateBook(id, title, author, genre);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JLabel()); // filler
        dialog.add(addBtn);
        dialog.setVisible(true);
    }

    private void showAddUserDialog() {

        JDialog dialog = new JDialog(frame, "Add New User", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 2, 5, 5));
        dialog.setLocationRelativeTo(frame);

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();

        dialog.add(new JLabel("Name:"));
        dialog.add(titleField);
        dialog.add(new JLabel("Email:"));
        dialog.add(authorField);

        JButton addBtn = new JButton("Add");
        addBtn.addActionListener(e -> {
            String name = titleField.getText().trim();
            String email = authorField.getText().trim();

            if (!name.isEmpty() && !email.isEmpty()) {
                User newUser = new User(name, email);
                controller.addUser(newUser);
                tableModelBook.addRow(new Object[]{name, email});
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JLabel()); // filler
        dialog.add(addBtn);
        dialog.setVisible(true);
    }

    private void showUpdateUserDialog(int id) {

        JDialog dialog = new JDialog(frame, "Update User", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 2, 5, 5));
        dialog.setLocationRelativeTo(frame);

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();

        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("E-mail:"));
        dialog.add(emailField);

        JButton addBtn = new JButton("Update");
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();

            if (!name.isEmpty() && !email.isEmpty()) {
                controller.updateUser(id, name, email);
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

    private void deleteSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                controller.removeUser(parseInt(userTable.getValueAt(selectedRow,0).toString()));
                tableModelBook.removeRow(selectedRow);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(frame, "Please select a user to delete.");
        }
    }

    private void showLoanDialogForSelectedBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(frame, "Please select a book to loan.");
            return;
        }
    
        Book selectedBook = fetchedBooks.get(selectedRow);
        if (!selectedBook.isAvailable()) {
            JOptionPane.showMessageDialog(frame, "This book is already on loan.");
            return;
        }
    
        // Seznam jmen uživatelů pro výběr
        String[] userNames = fetchedUsers.stream()
            .map(User::getName)
            .toArray(String[]::new);
    
        String selectedUserName = (String) JOptionPane.showInputDialog(
            frame,
            "Select user who will borrow the book:",
            "Loan Book",
            JOptionPane.PLAIN_MESSAGE,
            null,
            userNames,
            userNames.length > 0 ? userNames[0] : null
        );
    
        if (selectedUserName != null) {
            // Najdi odpovídajícího uživatele
            User selectedUser = fetchedUsers.stream()
                .filter(u -> u.getName().equals(selectedUserName))
                .findFirst()
                .orElse(null);
    
            if (selectedUser != null) {
                // Půjč knihu
                controller.loanBookToUser(selectedBook.getId(), selectedUser.getId());
                
                // Aktualizuj stav v tabulce
                tableModelBook.setValueAt("No", selectedRow, 3);
                JOptionPane.showMessageDialog(frame, "Book loaned to " + selectedUser.getName() + ".");
            }
        }
    }

    private void showLoanDialogForSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(frame, "Please select a user to loan a book.");
            return;
        }
    }
    private void loanSelectedBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow >= 0) {
            Book book = fetchedBooks.get(selectedRow);
            if (!book.isAvailable()) {
                JOptionPane.showMessageDialog(frame, "This book is already on loan.");
            } else {
                book.setAvailable(false);
                tableModelBook.setValueAt("No", selectedRow, 3);
                controller.saveAllBooks(fetchedBooks);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a book to loan.");
        }
    }
    private void saveChangesToFile() {
        List<Book> updatedBookList = new ArrayList<>();
        //List<User> updatedUserList = new ArrayList<>();
        // TODO přidat save pro users
        for (int i = 0; i < tableModelBook.getRowCount(); i++) {
            int id = parseInt(tableModelBook.getValueAt(i, 0).toString());
            String title = tableModelBook.getValueAt(i, 1).toString();
            String author = tableModelBook.getValueAt(i, 2).toString();
            String genre = tableModelBook.getValueAt(i, 3).toString();
            boolean available = tableModelBook.getValueAt(i, 4).toString().equals("Yes");
    
            updatedBookList.add(new Book(id, title, author, genre, available));
        }
      
        fetchedBooks = updatedBookList; // aktualizuj interní seznam
        controller.saveAllBooks(fetchedBooks);
        JOptionPane.showMessageDialog(frame, "Changes saved to books.json.");
    }
}