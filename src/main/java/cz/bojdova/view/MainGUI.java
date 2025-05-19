package cz.bojdova.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import static java.lang.Integer.parseInt;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private JTable userTable;
    private DefaultTableModel tableModelUser;
    private DefaultTableModel tableModelBook;
    private final BookacheController controller = new BookacheController();
    private final IdGenerator idGen = controller.initIdGenerator();
  
    List<Book> fetchedBooks = controller.getBooks();
    List<User> fetchedUsers = controller.getUsers();

    public MainGUI() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Welcome to Bookache");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout(10, 30));

        JTabbedPane tabbedPane = new JTabbedPane();
        // Karta knih
        tabbedPane.addTab("Books", createBookPanel());

        // Karta výpůjček
        tabbedPane.addTab("Loans", createLoanPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
    private JPanel createLoanPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel titleLabel = new JLabel("Loans");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

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
        buttonPanel.add(Box.createVerticalStrut(10));

        JButton updateButton = createSizedButton("Update user");
        updateButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = parseInt(userTable.getValueAt(selectedRow, 0).toString());
                showUpdateUserDialog(id);
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
        buttonPanel.add(Box.createVerticalStrut(10));

        JButton updateButton = createSizedButton("Update book");
        updateButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = parseInt(bookTable.getValueAt(selectedRow, 0).toString());
                showUpdateBookDialog(id);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a book to update.");
            }
        });
        buttonPanel.add(updateButton);

       /* JButton saveButton = createSizedButton("Save changes"); 
        saveButton.addActionListener(e -> saveChangesToFile());
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(saveButton);
        */
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
                refreshAllTables();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JLabel());
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
        // Předvyplnění polí
        Book book = controller.findBookById(id);
        if (book != null) {
            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            genreField.setText(book.getGenre());
        }

        JButton addBtn = new JButton("Update");
        addBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String genre = genreField.getText().trim();

            if (!title.isEmpty() && !author.isEmpty() && !genre.isEmpty()) {
                controller.updateBook(id, title, author, genre);
                refreshAllTables();
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
                int id = idGen.getNextUserId();
                User newUser = new User(id, name, email);
                controller.addUser(newUser);
                refreshAllTables();
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
        // Předvyplnění polí
        User user = controller.findUserById(id);
        if (user != null) {
            nameField.setText(user.getName());
            emailField.setText(user.getEmail());
        }

        JButton addBtn = new JButton("Update");
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();

            if (!name.isEmpty() && !email.isEmpty()) {
                controller.updateUser(id, name, email);
                refreshAllTables();
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
    
        // 1. Vytvoř mapu: id → User
        Map<Integer, User> userMap = fetchedUsers.stream()
            .collect(Collectors.toMap(User::getId, u -> u));
    
        // 2. Vytvoř pole popisků: „ID: Name“
        String[] userOptions = userMap.entrySet().stream()
            .map(entry -> entry.getKey() + ": " + entry.getValue().getName())
            .toArray(String[]::new);
    
        // 3. Zobraz výběrový dialog
        String selectedEntry = (String) JOptionPane.showInputDialog(
            frame,
            "Select user who will borrow the book:",
            "Loan Book",
            JOptionPane.PLAIN_MESSAGE,
            null,
            userOptions,
            userOptions.length > 0 ? userOptions[0] : null
        );
    
        // 4. Zpracuj výběr
        if (selectedEntry != null) {
            try {
                int userId = Integer.parseInt(selectedEntry.split(":")[0].trim());
                User selectedUser = userMap.get(userId);
    
                if (selectedUser != null) {
                    controller.loanBookToUser(selectedBook.getId(), userId);
                    refreshAllTables();
                    JOptionPane.showMessageDialog(frame, "Book loaned to " + selectedUser.getName() + ".");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Could not parse user ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    private void showLoanDialogForSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(frame, "Please select a user to retrieve a book.");
            return;
        }
        User selectedUser = fetchedUsers.get(selectedRow);
        if (selectedUser.getBorrowedBooks().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "This user has already retrieved all books.");
            return;
        }
        // Seznam uživatelem vypůjčených knih
        //String[] borrowedBooks = selectedUser.getBorrowedBooks().stream()
        //.map(Book::getTitle)
        //.toArray(String[]::new);
       // 1. Vytvoř mapu: id → Book
        Map<Integer, Book> borrowedMap = selectedUser.getBorrowedBooks().stream()
            .collect(Collectors.toMap(Book::getId, b -> b));

            // 2. Vytvoř pole textových popisů pro výběr, např. „6: The Hobbit“
        String[] borrowedBookOptions = borrowedMap.entrySet().stream()
            .map(entry -> entry.getKey() + ": " + entry.getValue().getTitle())
            .toArray(String[]::new);

            // 3. Zobraz výběrový dialog
        String selectedEntry = (String) JOptionPane.showInputDialog(
            frame,
            "Select book to retrieve:",
            "Retrieve Book",
            JOptionPane.PLAIN_MESSAGE,
            null,
            borrowedBookOptions,
            borrowedBookOptions.length > 0 ? borrowedBookOptions[0] : null
            );

            // 4. Získání ID a knihy
        if (selectedEntry != null) {
            try {
                int bookId = Integer.parseInt(selectedEntry.split(":")[0].trim());
                Book selectedBook = borrowedMap.get(bookId);

                if (selectedBook != null) {
                    controller.returnBookFromUser(bookId, selectedUser.getId());
                    refreshAllTables();
                    JOptionPane.showMessageDialog(frame, "Book retrieved from " + selectedUser.getName() + ".");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Unable to parse selected book ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(frame, "Please select a user to retrieve book from.");
            return;
        }
    }
  
    private void refreshBookTable() {
        List<Book> refreshBooks = controller.getBooks();
        tableModelBook.setRowCount(0); // smaž staré řádky
    
        for (Book book : refreshBooks) {
            tableModelBook.addRow(new Object[]{
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.isAvailable() ? "Yes" : "No"
            });
        }
    }

    private void refreshUserTable() {
        List<User> refreshUsers = controller.getUsers();
        tableModelUser.setRowCount(0); // smaž staré řádky
    
        for (User user : refreshUsers) {
            tableModelUser.addRow(new Object[]{
                user.getId(),
                user.getName(),
                user.getBorrowedBooksName(),
                user.getLastLoanDate()
            });
        }
    }
    private void refreshAllTables() {
        refreshBookTable();
        refreshUserTable();
    }
}