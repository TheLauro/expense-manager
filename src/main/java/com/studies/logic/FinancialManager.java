package com.studies.logic;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author lauro.ouverney
 */

public class FinancialManager {

    private final List<Transaction> allTransactions = new ArrayList<>();

    // Expense Create Methods
    public Expense createExpense(String description, double value, String date) {

        Expense newExpense = new Expense(description, value, date);
        allTransactions.add(newExpense);

        return newExpense;
    }

    public Expense createExpense(String description, double value) {

        Expense newExpense = new Expense(description, value);
        allTransactions.add(newExpense);

        return newExpense;
    }

    // Income Create Methods
    public Income createIncome(String description, double value, String date) {

        Income newIncome = new Income(description, value, date);
        allTransactions.add(newIncome);

        return newIncome;
    }

    public Income createIncome(String description, double value) {

        Income newIncome = new Income(description, value);
        allTransactions.add(newIncome);

        return newIncome;
    }

    // Transactions Delete Method
    public void deleteTransaction(Transaction transaction) {
        allTransactions.remove(transaction);
    }

    // Begin of Update Methods for Transactions
    
    //Update description
    public void updateTransaction(Transaction transaction, String description){
        transaction.setDescription(description);
    }

    // Update value
    public void updateTransaction(Transaction transaction, double value) {
        transaction.setValue(value);
    }

    // Update date
    public void updateTransaction(Transaction transaction,LocalDate date) {
        try{
            String dateString = date.format(Transaction.BRAZILIAN_FORMAT);
            transaction.setDate(dateString);;
        } catch (IllegalArgumentException e){
            throw e;
        }
    }

    //Update Category
    public void updateCategory(Expense expense,String category){
        if(Category.exists(category)){
            expense.setCategory(category);
        } else {
            throw new IllegalArgumentException("Categoria não existente! Adicione a primeiro e depois edite sua despesa");
        }
    }

    // End of Update Methods for Transaction

    // Calculate Geral Balance of All Transactions
    public double getGeralBalance() {

        double balance = 0;

        for (Transaction transaction : allTransactions) {
            balance+=transaction.getSignedAmount();
        }

        return balance;
    }

    // Calculate Current Month Balance
    public double getMonthBalance() {

        double balance = 0;

        YearMonth currentMonth = YearMonth.now();

        LocalDate firstDayOfMonth = currentMonth.atDay(1);
        LocalDate lastDayOfMonth = currentMonth.atEndOfMonth();

        for (Transaction transaction : allTransactions) {
            if (!transaction.getDate().isBefore(firstDayOfMonth) && !transaction.getDate().isAfter(lastDayOfMonth)) {
                balance+=transaction.getSignedAmount();
            }
        }
        return balance;
    }

    // Calculate total expenses per category
    public Map<String, Double> getExpensesByCategory() {
        Map<String, Double> expensesByCategory = new HashMap<>();

        for (Transaction transaction : allTransactions) {

            if (transaction.getClass() == Expense.class) {
                Expense expense = (Expense) transaction;

                String category = expense.getCategory();
                double value = expense.getValue();

                expensesByCategory.merge(category, value, Double::sum);
            }
        }
        
        for(String category : Category.getCategories()){
            if(!expensesByCategory.containsKey(category)){
                expensesByCategory.put(category, 0.0);
            }
        }

        return expensesByCategory;
    }

    // Return a unmodifiable list whith all transactions
    public List<Transaction> getAllTransactions() {
        return Collections.unmodifiableList(allTransactions);
    }

    // Calculate the number of transactions
    public Integer getNumberOfTransactions() {
        return allTransactions.size();
    }

    // Return a unmodifiable list whith monthly transactions
    public List<Transaction> getMonthlyTransactions(){
        List<Transaction> monthlyTransactions = new ArrayList<>();
        YearMonth currentMonth = YearMonth.now();

        LocalDate firstDayOfMonth = currentMonth.atDay(1);
        LocalDate lastDayOfMonth = currentMonth.atEndOfMonth();

        for (Transaction transaction : allTransactions) {
            if (!transaction.getDate().isBefore(firstDayOfMonth) && !transaction.getDate().isAfter(lastDayOfMonth)) {
                monthlyTransactions.add(transaction);
            }
        }

        return Collections.unmodifiableList(monthlyTransactions);
    }

    public void clearAllTransactions(){
        allTransactions.clear();
    }

}
