package cz.bojdova.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import cz.bojdova.controller.BookacheController;
import cz.bojdova.util.IdGenerator;
import cz.bojdova.view.panel.BookTabPanel;
import cz.bojdova.view.panel.LoanTabPanel;


public class MainGUI {
    private JFrame frame;
    private BookTabPanel bookTab;
    private LoanTabPanel loanTab;

    private final BookacheController controller = new BookacheController();
    private final IdGenerator idGen = controller.initIdGenerator();
  

    public MainGUI() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Welcome to Bookache");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout(10, 30));

        JTabbedPane tabbedPane = new JTabbedPane();
        // Karta knih
        bookTab = new BookTabPanel(controller, idGen, this::refreshAllTables);
        tabbedPane.addTab("Books", bookTab);

        // Karta výpůjček
        loanTab = new LoanTabPanel(controller, idGen, this::refreshAllTables);
        tabbedPane.addTab("Loans", loanTab);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }  

    private void refreshAllTables() {
        if (bookTab != null) bookTab.refreshTable();
        if (loanTab != null) loanTab.refreshTable();
    }
}