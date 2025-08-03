/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codeverse.MMSTX;

import java.time.LocalDate;

/**
 *
 * @author ADMIN
 */
public class Expense {
    
    private String expenseType;
    private double expenseAmount;
    private LocalDate date;

    public Expense(String expenseType, double expenseAmount, LocalDate date) {
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
        this.date = date;
    }

    public String getExpenseType() { return expenseType; }
    public double getExpenseAmount() { return expenseAmount; }
    public LocalDate getDate() { return date; }

    @Override
    public String toString() {
        return expenseType + ";" + expenseAmount + ";" + date;
    }

    public static Expense fromString(String line) {
        String[] parts = line.split(";");
        return new Expense(parts[0], Double.parseDouble(parts[1]), LocalDate.parse(parts[2]));
    }
}
