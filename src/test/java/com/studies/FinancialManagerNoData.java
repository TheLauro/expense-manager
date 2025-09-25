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

    @BeforeEach
    public void clearAllTransactionsForTest(){

        FinancialManager.clearAllTransactions();

    }

    @Test
    public void createExpenseTest() {
        Expense expense1 = FinancialManager.createExpense("teste", 200, "01/10/2010");

        Expense expense2 = new Expense("teste", 200, "01/10/2010");

        boolean contains = FinancialManager.getAllTransactions().contains(expense1);

        assertEquals(expense1, expense2);
        assertTrue(contains);

    }

    @Test
    public void createIncomeTest() {
        Income income1 = FinancialManager.createIncome("teste", 200, "01/10/2010");

        Income income2 = new Income("teste", 200, "01/10/2010");

        boolean contains = FinancialManager.getAllTransactions().contains(income1);

        assertEquals(income1, income2);
        assertTrue(contains);
    }

    @Test
    public void deleteTransactionTest() {
        Expense expense1 = FinancialManager.createExpense("Casa", 500);

        FinancialManager.deleteTransaction(expense1);

        boolean contains = FinancialManager.getAllTransactions().contains(expense1);

        assertFalse(contains);
    }

    @Test
    public void updateTransactionDescriptionTest(){
        Expense expense = FinancialManager.createExpense("Casa", 500);

        FinancialManager.updateTransaction(expense, "Apartamento");
        
        String expenseDescription = expense.getDescription();

        assertEquals("Apartamento", expenseDescription);

        Income income = FinancialManager.createIncome("Salario", 1500);

        FinancialManager.updateTransaction(income, "Bonus");
        
        String incomeDescription = income.getDescription();

        assertEquals("Bonus", incomeDescription);
    }

    @Test
    public void updateTransactionValorTest(){
        Expense expense = FinancialManager.createExpense("Casa", 500);

        FinancialManager.updateTransaction(expense, 100);
        
        double expenseValor = expense.getValor();

        assertEquals(100, expenseValor);

        Income income = FinancialManager.createIncome("Salario", 1500);

        FinancialManager.updateTransaction(income, 1000);
        
        double incomeValor = income.getValor();

        assertEquals(1000, incomeValor);
    }

    @Test
    public void updateTransactionDateTest(){
        Expense expense = FinancialManager.createExpense("Casa", 500, "12/10/2006");

        LocalDate expenseDate = LocalDate.parse("01/09/1999",Transaction.BRAZILIAN_FORMAT);

        FinancialManager.updateTransaction(expense,expenseDate);
        
        LocalDate expenseRealDate = expense.getDate();

        String expenseStringDate = expenseRealDate.format(Transaction.BRAZILIAN_FORMAT);

        assertEquals("01/09/1999", expenseStringDate);

        Income income = FinancialManager.createIncome("Casa", 500, "12/10/2006");

        LocalDate incomeDate = LocalDate.parse("02/09/1999",Transaction.BRAZILIAN_FORMAT);

        FinancialManager.updateTransaction(income,incomeDate);
        
        LocalDate incomeRealDate = income.getDate();

        String incomeStringDate = incomeRealDate.format(Transaction.BRAZILIAN_FORMAT);

        assertEquals("02/09/1999", incomeStringDate);
       
    }
    
    @Test
    public void updateTransactionCategoryTest(){
        Expense expense = FinancialManager.createExpense("Casa", 500);

        expense.setCategory("Alimentacao");

        FinancialManager.updateCategory(expense, "Aluguel");
        
        String expenseCategory = expense.getCategory();

        assertEquals("Aluguel", expenseCategory);
    }


}
