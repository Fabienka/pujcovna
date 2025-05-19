package cz.bojdova.view.dialog;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cz.bojdova.controller.BookacheController;
import cz.bojdova.model.User;

public class UpdateUserDialog extends AbstractDialog {

    public UpdateUserDialog(JFrame parentFrame, BookacheController controller, int userId, Runnable onSuccess) {
        super(parentFrame, "Update User");

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();

        contentPanel.add(new JLabel("Name:"));
        contentPanel.add(nameField);
        contentPanel.add(new JLabel("Email:"));
        contentPanel.add(emailField);

        User user = controller.findUserById(userId);
        if (user != null) {
            nameField.setText(user.getName());
            emailField.setText(user.getEmail());
        }

        JPanel buttonPanel = createButtonPanel("Update", () -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();

            if (!name.isEmpty() && !email.isEmpty()) {
                controller.updateUser(userId, name, email);
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
