package com.studies;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import com.studies.logic.*;
import java.util.Map;

/**
 *
 * @author lauro.ouverney
 */

public class FinancialManagerTestWithData {

    Expense expenseOfMonth;
    Income incomeOfMonth;
    Expense expenseOfOtherMonth;

    @BeforeEach
    public void initializeTransactionsExamples() {
        // clear allTransactions List
        FinancialManager.clearAllTransactions();

        // initialize transactions examples for each test
        expenseOfMonth = FinancialManager.createExpense("Casa", 500);
        expenseOfMonth.setCategory("Aluguel");

        incomeOfMonth = FinancialManager.createIncome("Salario", 1500);

        expenseOfOtherMonth = FinancialManager.createExpense("Conserto do carro", 1000, "01/07/2025");
        expenseOfOtherMonth.setCategory("Transporte");

        FinancialManager.createIncome("Bico de motoboy", 200, "20/08/2025");

        Expense expense3 = FinancialManager.createExpense("Jantar fora", 20, "12/08/2025");
        expense3.setCategory("Alimentacao");

        FinancialManager.createIncome("Me deram de presente", 300, "30/06/2025");
    }

    @Test
    public void getGeralBalanceTest() {
        double expectedResult = 480;
        double result = FinancialManager.getGeralBalance();
        assertEquals(expectedResult, result, 0);
    }

    @Test
    public void getMonthBalanceTest() {
        double expectedResult = 1000;
        double result = FinancialManager.getMonthBalance();

        assertEquals(expectedResult, result, 0);
    }

    @Test
    public void getExpensesByCategoryTest() {
        Map<String, Double> expectedResult = Map.of(
                "Alimentacao", 20.0,
                "Aluguel", 500.0,
                "Entretenimento", 0.0,
                "Transporte", 1000.0);

        Map<String, Double> result = FinancialManager.getExpensesByCategory();

        assertEquals(expectedResult, result);
    }

    @Test
    public void getMonthlyTransactionsTest() {

        List<Transaction> result = FinancialManager.getMonthlyTransactions();

        assertEquals(2, result.size());

        assertTrue(result.contains(expenseOfMonth)&&result.contains(incomeOfMonth));

        assertFalse(result.contains(expenseOfOtherMonth));

    }

}
