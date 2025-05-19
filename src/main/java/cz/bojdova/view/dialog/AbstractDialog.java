package cz.bojdova.view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class AbstractDialog extends JDialog {

    protected JPanel contentPanel = new JPanel(new GridLayout(0, 2, 5, 5));

    public AbstractDialog(JFrame parentFrame, String title) {
        super(parentFrame, title, true);
        setSize(400, 200);
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout(10, 10));
        add(contentPanel, BorderLayout.CENTER);
    }

    protected void close() {
        setVisible(false);
        dispose();
    }

    protected JPanel createButtonPanel(String actionLabel, Runnable onAction) {
        JButton actionButton = new JButton(actionLabel);
        actionButton.setPreferredSize(new Dimension(100, 30));
        actionButton.addActionListener(e -> {
            if (onAction != null) onAction.run();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addActionListener(e -> close());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(actionButton);
        panel.add(cancelButton);
        return panel;
    }
} 
