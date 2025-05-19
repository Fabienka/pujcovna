package cz.bojdova.view.dialog;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cz.bojdova.controller.BookacheController;
import cz.bojdova.model.Book;
import cz.bojdova.util.IdGenerator;

public class AddBookDialog extends AbstractDialog {

    public AddBookDialog(JFrame parentFrame, BookacheController controller, IdGenerator idGen, Runnable onSuccess) {
        super(parentFrame, "Add New Book");

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField genreField = new JTextField();

        contentPanel.add(new JLabel("Title:"));
        contentPanel.add(titleField);
        contentPanel.add(new JLabel("Author:"));
        contentPanel.add(authorField);
        contentPanel.add(new JLabel("Genre:"));
        contentPanel.add(genreField);

        JPanel btnPanel = createButtonPanel("Add", () -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String genre = genreField.getText().trim();

            if (!title.isEmpty() && !author.isEmpty() && !genre.isEmpty()) {
                int newId = idGen.getNextBookId();
                Book newBook = new Book(newId, title, author, genre, true);
                controller.addBook(newBook);
                if (onSuccess != null) onSuccess.run();
                close();
            } else {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(btnPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
