package cz.bojdova.view.dialog;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cz.bojdova.controller.BookacheController;
import cz.bojdova.model.User;
import cz.bojdova.util.IdGenerator;

public class AddUserDialog extends AbstractDialog {

    public AddUserDialog(JFrame parentFrame, BookacheController controller, IdGenerator idGen, Runnable onSuccess) {
        super(parentFrame, "Add New User");

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();

        contentPanel.add(new JLabel("Name:"));
        contentPanel.add(nameField);
        contentPanel.add(new JLabel("Email:"));
        contentPanel.add(emailField);

        JPanel buttonPanel = createButtonPanel("Add", () -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();

            if (!name.isEmpty() && !email.isEmpty()) {
                int id = idGen.getNextUserId();
                User newUser = new User(id, name, email);
                controller.addUser(newUser);
                if (onSuccess != null) onSuccess.run();
                close();
            } else {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
} 
