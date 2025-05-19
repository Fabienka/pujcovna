package cz.bojdova.view.dialog;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ConfirmDeleteDialog extends AbstractDialog {

    private boolean confirmed = false;

    public ConfirmDeleteDialog(JFrame parentFrame, String itemName) {
        super(parentFrame, "Confirm Delete");

        JLabel messageLabel = new JLabel("Do you really want to delete " + itemName + "?");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(messageLabel);
        centerPanel.add(Box.createVerticalStrut(20));

        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel("Delete", () -> {
            confirmed = true;
            close();
        });

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}