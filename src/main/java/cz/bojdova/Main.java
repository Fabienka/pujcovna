package cz.bojdova;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import cz.bojdova.view.MainGUI;

public class Main {
    public static void main(String[] args) {
        // Set the look and feel to the system default
        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and display the main application window
        SwingUtilities.invokeLater(() -> { MainGUI mainGUI = new MainGUI(); });
    }
}