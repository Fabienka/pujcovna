package cz.bojdova.view.util;

import java.awt.Dimension;

import javax.swing.JButton;

public class ButtonFactory {

    public static JButton createButton(String text, Runnable onClick) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(150, 30));
        button.addActionListener(e -> {
            if (onClick != null) onClick.run();
        });
        return button;
    }

    public static JButton createButton(String text, Dimension size, Runnable onClick) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.addActionListener(e -> {
            if (onClick != null) onClick.run();
        });
        return button;
    }
}
