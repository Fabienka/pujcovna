package cz.bojdova.view.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cz.bojdova.controller.BookacheController;
import cz.bojdova.model.Book;
import cz.bojdova.model.User;

public class RetrieveBookDialog extends AbstractDialog {

    public RetrieveBookDialog(JFrame parentFrame, BookacheController controller, User selectedUser, Runnable onSuccess) {
        super(parentFrame, "Retrieve Book");

        if (selectedUser.getBorrowedBooks().isEmpty()) {
            JOptionPane.showMessageDialog(this, "This user has no borrowed books.", "Info", JOptionPane.INFORMATION_MESSAGE);
            close();
            return;
        }

        Map<Integer, Book> borrowedMap = selectedUser.getBorrowedBooks().stream()
                .collect(Collectors.toMap(Book::getId, b -> b));

        JLabel label = new JLabel("Select book to retrieve:");
        JComboBox<String> bookCombo = new JComboBox<>();

        for (Map.Entry<Integer, Book> entry : borrowedMap.entrySet()) {
            bookCombo.addItem(entry.getKey() + ": " + entry.getValue().getTitle());
        }

        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookCombo.setAlignmentX(Component.CENTER_ALIGNMENT);

        verticalPanel.add(Box.createVerticalStrut(10));
        verticalPanel.add(label);
        verticalPanel.add(Box.createVerticalStrut(5));
        verticalPanel.add(bookCombo);
        verticalPanel.add(Box.createVerticalStrut(10));

        add(verticalPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel("Retrieve", () -> {
            String selectedEntry = (String) bookCombo.getSelectedItem();
            if (selectedEntry != null) {
                try {
                    int bookId = Integer.parseInt(selectedEntry.split(":")[0].trim());
                    Book selectedBook = borrowedMap.get(bookId);
                    if (selectedBook != null) {
                        controller.returnBookFromUser(bookId, selectedUser.getId());
                        if (onSuccess != null) onSuccess.run();
                        JOptionPane.showMessageDialog(this, "Book retrieved from " + selectedUser.getName() + ".");
                        close();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Could not parse book ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
