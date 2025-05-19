package cz.bojdova.view.panel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import cz.bojdova.controller.BookacheController;
import cz.bojdova.model.Book;
import cz.bojdova.util.IdGenerator;
import cz.bojdova.view.dialog.AddBookDialog;
import cz.bojdova.view.dialog.ConfirmDeleteDialog;
import cz.bojdova.view.dialog.LoanBookDialog;
import cz.bojdova.view.dialog.UpdateBookDialog;
import cz.bojdova.view.util.ButtonFactory;

public class BookTabPanel extends JPanel {

    private JTable bookTable;
    private DefaultTableModel tableModel;
    private final BookacheController controller;
    private final IdGenerator idGen;

    public BookTabPanel(BookacheController controller, IdGenerator idGen, Runnable onDataChange) {
        this.controller = controller;
        this.idGen = idGen;

        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Books for loan");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Author", "Genre", "Available"}, 0);
        bookTable = new JTable(tableModel);
        add(new JScrollPane(bookTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buttonPanel.add(ButtonFactory.createButton("Add book", () -> new AddBookDialog(getFrame(), controller, idGen, onDataChange)));
        buttonPanel.add(Box.createVerticalStrut(10));

        buttonPanel.add(ButtonFactory.createButton("Loan book", () -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                Book selectedBook = controller.getBooks().get(selectedRow);
                new LoanBookDialog(getFrame(), controller, selectedBook, onDataChange);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to loan.");
            }
        }));
        buttonPanel.add(Box.createVerticalStrut(10));

        buttonPanel.add(ButtonFactory.createButton("Delete book", () -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                String title = tableModel.getValueAt(selectedRow, 1).toString();
                ConfirmDeleteDialog confirm = new ConfirmDeleteDialog(getFrame(), "book \"" + title + "\"");
                if (confirm.isConfirmed()) {
                    controller.removeBook(id);
                    onDataChange.run();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to delete.");
            }
        }));
        buttonPanel.add(Box.createVerticalStrut(10));

        buttonPanel.add(ButtonFactory.createButton("Update book", () -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                new UpdateBookDialog(getFrame(), controller, id, onDataChange);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a book to update.");
            }
        }));

        add(buttonPanel, BorderLayout.EAST);

        refreshTable();
    }

    public void refreshTable() {
        List<Book> books = controller.getBooks();
        tableModel.setRowCount(0);
        for (Book book : books) {
            tableModel.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.isAvailable() ? "Yes" : "No"});
        }
    }

    private JFrame getFrame() {
        return (JFrame) SwingUtilities.getWindowAncestor(this);
    }
} 
