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

    private static final List<Transaction> allTransactions = new ArrayList<>();

    private FinancialManager() {

    }

    // Expense Create Methods
    public static Expense createExpense(String description, double valor, String date) {

        Expense newExpense = new Expense(description, valor, date);
        allTransactions.add(newExpense);

        return newExpense;
    }

    public static Expense createExpense(String description, double valor) {

        Expense newExpense = new Expense(description, valor);
        allTransactions.add(newExpense);

        return newExpense;
    }

    // Income Create Methods
    public static Income createIncome(String description, double valor, String date) {

        Income newIncome = new Income(description, valor, date);
        allTransactions.add(newIncome);

        return newIncome;
    }

    public static Income createIncome(String description, double valor) {

        Income newIncome = new Income(description, valor);
        allTransactions.add(newIncome);

        return newIncome;
    }

    // Transactions Delete Method
    public static void deleteTransaction(Transaction transaction) {
        allTransactions.remove(transaction);
    }

    // Begin of Update Methods for Transactions
    
    //Update description
    public static void updateTransaction(Transaction transaction, String description){
        transaction.setDescription(description);
    }

    // Update valor
    public static void updateTransaction(Transaction transaction, double valor) {
        transaction.setValor(valor);
    }

    // Update date
    public static void updateTransaction(Transaction transaction,LocalDate date) {
        try{
            String dateString = date.format(Transaction.BRAZILIAN_FORMAT);
            transaction.setDate(dateString);;
        } catch (IllegalArgumentException e){
            throw e;
        }
    }

    //Update Category
    public static void updateCategory(Expense expense,String category){
        if(Category.exists(category)){
            expense.setCategory(category);
        } else {
            throw new IllegalArgumentException("Categoria não existente! Adicione a primeiro e depois edite sua despesa");
        }
    }

    // End of Update Methods for Transaction

    // Calculate Geral Balance of All Transactions
    public static double getGeralBalance() {

        double balance = 0;

        for (Transaction transaction : allTransactions) {
            balance+=transaction.getSignedAmount();
        }

        return balance;
    }

    // Calculate Current Month Balance
    public static double getMonthBalance() {

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
    public static Map<String, Double> getExpensesByCategory() {
        Map<String, Double> expensesByCategory = new HashMap<>();

        for (Transaction transaction : allTransactions) {

            if (transaction.getClass() == Expense.class) {
                Expense expense = (Expense) transaction;

                String category = expense.getCategory();
                double valor = expense.getValor();

                expensesByCategory.merge(category, valor, Double::sum);
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
    public static List<Transaction> getAllTransactions() {
        return Collections.unmodifiableList(allTransactions);
    }

    // Calculate the number of transactions
    public static Integer getNumberOfTransactions() {
        return allTransactions.size();
    }

    // Return a unmodifiable list whith monthly transactions
    public static List<Transaction> getMonthlyTransactions(){
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
}
