package cz.bojdova.view.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;
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

public class LoanBookDialog extends AbstractDialog {

    public LoanBookDialog(JFrame parentFrame, BookacheController controller, Book selectedBook, Runnable onSuccess) {
        super(parentFrame, "Loan Book");

        if (!selectedBook.isAvailable()) {
            JOptionPane.showMessageDialog(this, "This book is already on loan.", "Error", JOptionPane.ERROR_MESSAGE);
            close();
            return;
        }

        List<User> users = controller.getUsers();
        Map<Integer, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, u -> u));

        JLabel label = new JLabel("Select user who will borrow the book:");
        JComboBox<String> userCombo = new JComboBox<>();

        for (Map.Entry<Integer, User> entry : userMap.entrySet()) {
            userCombo.addItem(entry.getKey() + ": " + entry.getValue().getName());
        }

        // Změna layoutu na svislý BoxLayout
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        userCombo.setAlignmentX(Component.CENTER_ALIGNMENT);

        verticalPanel.add(Box.createVerticalStrut(10));
        verticalPanel.add(label);
        verticalPanel.add(Box.createVerticalStrut(5));
        verticalPanel.add(userCombo);
        verticalPanel.add(Box.createVerticalStrut(10));

        add(verticalPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel("Loan", () -> {
            String selectedEntry = (String) userCombo.getSelectedItem();
            if (selectedEntry != null) {
                try {
                    int userId = Integer.parseInt(selectedEntry.split(":")[0].trim());
                    User selectedUser = userMap.get(userId);
                    if (selectedUser != null) {
                        controller.loanBookToUser(selectedBook.getId(), userId);
                        if (onSuccess != null) onSuccess.run();
                        JOptionPane.showMessageDialog(this, "Book loaned to " + selectedUser.getName() + ".");
                        close();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Could not parse user ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
