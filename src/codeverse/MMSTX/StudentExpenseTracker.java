/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeverse.MMSTX;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ADMIN
 */
public class StudentExpenseTracker {
    public String studentName;
    public String studentNumber;
    public LocalDate today;
    public ArrayList<Expense> expenses;

    // Constructor sets name, number, today's date, and creates empty list
    public StudentExpenseTracker(String studentName, String studentNumber) {
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.today = LocalDate.now();
        this.expenses = new ArrayList<Expense>();
    }

    // Add an expense to the list
    public void addExpense(String type, double amount) {
        Expense exp = new Expense(type, amount, LocalDate.now());
        expenses.add(exp);
    }

    // Show all expenses for the current month in console
    public void displayMonthlySummary() {
        double total = 0;
        System.out.println("Monthly Summary for student: " + studentName + " (" + studentNumber + ")");
        for (int i = 0; i < expenses.size(); i++) {
            Expense e = expenses.get(i);
            if (e.getDate().getMonth() == today.getMonth() && e.getDate().getYear() == today.getYear()) {
                System.out.println(" - " + e.getExpenseType() + ": R" + e.getExpenseAmount());
                total = total + e.getExpenseAmount();
            }
        }
        System.out.println("Total expenses are: R" + total);
    }
    
    // Get the total expense amount for the current month
    public double getTotalForMonth() {
        double total = 0;
        for (int i = 0; i < expenses.size(); i++) {
            Expense e = expenses.get(i);
            if (e.getDate().getMonth() == today.getMonth() && e.getDate().getYear() == today.getYear()) {
                total += e.getExpenseAmount();
            }
        }
        return total;
    }

    // Save student data and expenses to a file
    public void saveToFile(String filename) {
        try {
            FileOutputStream out = new FileOutputStream(filename, true);
            PrintWriter writer = new PrintWriter(out);
            writer.println(studentName);
            writer.println(studentNumber);
            writer.println(expenses.size());
            for (int i = 0; i < expenses.size(); i++) {
                Expense e = expenses.get(i);
                writer.println(e.toString());
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving to file");
        }
    }

    // Read one student's data from the scanner
    public static StudentExpenseTracker loadFromScanner(Scanner sc) {
        String name = sc.nextLine();
        String number = sc.nextLine();
        int count = Integer.parseInt(sc.nextLine());
        StudentExpenseTracker s = new StudentExpenseTracker(name, number);
        for (int i = 0; i < count; i++) {
            Expense exp = Expense.fromString(sc.nextLine());
            s.expenses.add(exp);
        }
        return s;
    }
}
