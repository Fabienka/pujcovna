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
import cz.bojdova.model.User;
import cz.bojdova.util.IdGenerator;
import cz.bojdova.view.dialog.AddUserDialog;
import cz.bojdova.view.dialog.ConfirmDeleteDialog;
import cz.bojdova.view.dialog.RetrieveBookDialog;
import cz.bojdova.view.dialog.UpdateUserDialog;
import cz.bojdova.view.util.ButtonFactory;

public class LoanTabPanel extends JPanel {

    private JTable userTable;
    private DefaultTableModel tableModel;
    private final BookacheController controller;
    private final IdGenerator idGen;

    public LoanTabPanel(BookacheController controller, IdGenerator idGen, Runnable onDataChange) {
        this.controller = controller;
        this.idGen = idGen;

        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Loans");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "User initials", "Books loaned", "Last loan date"}, 0);
        userTable = new JTable(tableModel);
        add(new JScrollPane(userTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buttonPanel.add(ButtonFactory.createButton("Add user", () -> new AddUserDialog(getFrame(), controller, idGen, onDataChange)));
        buttonPanel.add(Box.createVerticalStrut(10));

        buttonPanel.add(ButtonFactory.createButton("Retrieve book", () -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                User selectedUser = controller.getUsers().get(selectedRow);
                new RetrieveBookDialog(getFrame(), controller, selectedUser, onDataChange);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user to retrieve a book.");
            }
        }));
        buttonPanel.add(Box.createVerticalStrut(10));

        buttonPanel.add(ButtonFactory.createButton("Delete user", () -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                String name = tableModel.getValueAt(selectedRow, 1).toString();
                ConfirmDeleteDialog confirm = new ConfirmDeleteDialog(getFrame(), "name \"" + name + "\"");
                if (confirm.isConfirmed()) {
                    controller.removeUser(id);
                    onDataChange.run();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user to delete.");
            }
        }));
        buttonPanel.add(Box.createVerticalStrut(10));

        buttonPanel.add(ButtonFactory.createButton("Update user", () -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                new UpdateUserDialog(getFrame(), controller, id, onDataChange);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user to update.");
            }
        }));

        add(buttonPanel, BorderLayout.EAST);

        refreshTable();
    }

    public void refreshTable() {
        List<User> users = controller.getUsers();
        tableModel.setRowCount(0);
        for (User user : users) {
            tableModel.addRow(new Object[]{
                user.getId(),
                user.getName(),
                user.getBorrowedBooksName(),
                user.getLastLoanDate()
            });
        }
    }

    private JFrame getFrame() {
        return (JFrame) SwingUtilities.getWindowAncestor(this);
    }
} 
