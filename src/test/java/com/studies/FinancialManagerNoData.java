package com.studies;

import com.studies.logic.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;

/**
 *
 * @author lauro.ouverney
 */

public class FinancialManagerNoData {

    FinancialManager manager;

    @BeforeEach
    public void setUp(){

        this.manager = new FinancialManager();

    }

    @Test
    public void createExpenseTest() {
        Expense expense1 = manager.createExpense("teste", 200, "01/10/2010");

        Expense expense2 = new Expense("teste", 200, "01/10/2010");

        boolean contains = manager.getAllTransactions().contains(expense1);

        assertEquals(expense1, expense2);
        assertTrue(contains);

    }

    @Test
    public void createIncomeTest() {
        Income income1 = manager.createIncome("teste", 200, "01/10/2010");

        Income income2 = new Income("teste", 200, "01/10/2010");

        boolean contains = manager.getAllTransactions().contains(income1);

        assertEquals(income1, income2);
        assertTrue(contains);
    }

    @Test
    public void deleteTransactionTest() {
        Expense expense1 = manager.createExpense("Casa", 500);

        manager.deleteTransaction(expense1);

        boolean contains = manager.getAllTransactions().contains(expense1);

        assertFalse(contains);
    }

    @Test
    public void updateTransactionDescriptionTest(){
        Expense expense = manager.createExpense("Casa", 500);

        manager.updateTransaction(expense, "Apartamento");
        
        String expenseDescription = expense.getDescription();

        assertEquals("Apartamento", expenseDescription);

        Income income = manager.createIncome("Salario", 1500);

        manager.updateTransaction(income, "Bonus");
        
        String incomeDescription = income.getDescription();

        assertEquals("Bonus", incomeDescription);
    }

    @Test
    public void updateTransactionValueTest(){
        Expense expense = manager.createExpense("Casa", 500);

        manager.updateTransaction(expense, 100);
        
        double expenseValue = expense.getValue();

        assertEquals(100, expenseValue);

        Income income = manager.createIncome("Salario", 1500);

        manager.updateTransaction(income, 1000);
        
        double incomeValue = income.getValue();

        assertEquals(1000, incomeValue);
    }

    @Test
    public void updateTransactionDateTest(){
        Expense expense = manager.createExpense("Casa", 500, "12/10/2006");

        LocalDate expenseDate = LocalDate.parse("01/09/1999",Transaction.BRAZILIAN_FORMAT);

        manager.updateTransaction(expense,expenseDate);
        
        LocalDate expenseRealDate = expense.getDate();

        String expenseStringDate = expenseRealDate.format(Transaction.BRAZILIAN_FORMAT);

        assertEquals("01/09/1999", expenseStringDate);

        Income income = manager.createIncome("Casa", 500, "12/10/2006");

        LocalDate incomeDate = LocalDate.parse("02/09/1999",Transaction.BRAZILIAN_FORMAT);

        manager.updateTransaction(income,incomeDate);
        
        LocalDate incomeRealDate = income.getDate();

        String incomeStringDate = incomeRealDate.format(Transaction.BRAZILIAN_FORMAT);

        assertEquals("02/09/1999", incomeStringDate);
       
    }
    
    @Test
    public void updateTransactionCategoryTest(){
        Expense expense = manager.createExpense("Casa", 500);

        expense.setCategory("Alimentacao");

        manager.updateCategory(expense, "Aluguel");
        
        String expenseCategory = expense.getCategory();

        assertEquals("Aluguel", expenseCategory);
    }


}
