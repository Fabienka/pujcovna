package cz.bojdova.view.dialog;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cz.bojdova.controller.BookacheController;
import cz.bojdova.model.Book;

public class UpdateBookDialog extends AbstractDialog {

    public UpdateBookDialog(JFrame parentFrame, BookacheController controller, int bookId, Runnable onSuccess) {
        super(parentFrame, "Update Book");

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField genreField = new JTextField();

        contentPanel.add(new JLabel("Title:"));
        contentPanel.add(titleField);
        contentPanel.add(new JLabel("Author:"));
        contentPanel.add(authorField);
        contentPanel.add(new JLabel("Genre:"));
        contentPanel.add(genreField);

        // Předvyplnění polí
        Book book = controller.findBookById(bookId);
        if (book != null) {
            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            genreField.setText(book.getGenre());
        }

        JPanel btnPanel = createButtonPanel("Update", () -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String genre = genreField.getText().trim();

            if (!title.isEmpty() && !author.isEmpty() && !genre.isEmpty()) {
                controller.updateBook(bookId, title, author, genre);
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
