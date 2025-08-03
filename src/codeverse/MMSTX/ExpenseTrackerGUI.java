package codeverse.MMSTX;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ExpenseTrackerGUI extends JFrame {

    private JTextField nameField = new JTextField(15);
    private JTextField numberField = new JTextField(15);
    private JTextField typeField = new JTextField(15);
    private JTextField amountField = new JTextField(10);

    private JTextArea displayArea = new JTextArea(15, 50);
    private HashMap<String, StudentExpenseTracker> studentMap = new HashMap<>();

    private final String FILE_NAME = "all_expenses.txt";

    public ExpenseTrackerGUI() {
        super("Multi-Student Expense Tracker");
        setLayout(new BorderLayout(10, 10)); // Add spacing between components

        // === INPUT PANEL ===
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Margin between components

        int y = 0;

        // Row 1 - Student Name
        gbc.gridx = 0; gbc.gridy = y;
        inputPanel.add(new JLabel("Student Name:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);
        y++;

        // Row 2 - Student Number
        gbc.gridx = 0; gbc.gridy = y;
        inputPanel.add(new JLabel("Student Number:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(numberField, gbc);
        y++;

        // Row 3 - Expense Type
        gbc.gridx = 0; gbc.gridy = y;
        inputPanel.add(new JLabel("Expense Type:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(typeField, gbc);
        y++;

        // Row 4 - Expense Amount
        gbc.gridx = 0; gbc.gridy = y;
        inputPanel.add(new JLabel("Expense Amount:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(amountField, gbc);
        y++;

        // === BUTTON PANEL ===
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        JButton addBtn = new JButton("Add Expense");
        JButton showBtn = new JButton("Monthly Summary");
        JButton saveBtn = new JButton("Save All");
        JButton loadBtn = new JButton("Load All");
        JButton fxViewBtn = new JButton("JavaFX View");

        buttonPanel.add(addBtn);
        buttonPanel.add(showBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(loadBtn);
        buttonPanel.add(fxViewBtn);

        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);

        // === DISPLAY AREA ===
        displayArea.setEditable(false);
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // === BUTTON ACTIONS ===
        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String number = numberField.getText().trim();
                String type = typeField.getText().trim();
                double amount = Double.parseDouble(amountField.getText().trim());

                if (!studentMap.containsKey(number)) {
                    studentMap.put(number, new StudentExpenseTracker(name, number));
                }

                studentMap.get(number).addExpense(type, amount);
                displayArea.append("Added for " + name + ": " + type + " - R" + amount + "\n");
            } catch (Exception ex) {
                displayArea.append("Invalid input. Please check the amount.\n");
            }
        });

        showBtn.addActionListener(e -> {
            String number = numberField.getText().trim();
            if (studentMap.containsKey(number)) {
                studentMap.get(number).displayMonthlySummary(); // Assuming it prints to console
            } else {
                displayArea.append("Student not found.\n");
            }
        });

        saveBtn.addActionListener(e -> {
            try (PrintWriter writer = new PrintWriter(new FileOutputStream(FILE_NAME))) {
                for (StudentExpenseTracker tracker : studentMap.values()) {
                    tracker.saveToFile(FILE_NAME);
                }
                displayArea.append("Data saved for all students.\n");
            } catch (Exception ex) {
                displayArea.append("Could not save file.\n");
            }
        });

        loadBtn.addActionListener(e -> {
            try (Scanner sc = new Scanner(new File(FILE_NAME))) {
                while (sc.hasNextLine()) {
                    StudentExpenseTracker t = StudentExpenseTracker.loadFromScanner(sc);
                    studentMap.put(t.studentNumber, t);
                }
                displayArea.append("Data loaded.\n");
            } catch (Exception ex) {
                displayArea.append("Could not load file.\n");
            }
        });

        fxViewBtn.addActionListener(e -> {
            String number = numberField.getText().trim();
            if (studentMap.containsKey(number)) {
                FXSummaryView.selectedTracker = studentMap.get(number);
                Platform.runLater(() -> {
                    try {
                        new FXSummaryView().start(new Stage());
                    } catch (Exception ex) {
                        displayArea.append("FX view failed to start.\n");
                    }
                });
            } else {
                displayArea.append("Student not found for FX view.\n");
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center window
        setVisible(true);
    }

    public static void main(String[] args) {
        new ExpenseTrackerGUI();
    }
}
