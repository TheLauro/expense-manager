package com.studies;

import com.studies.logic.FinancialManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import java.util.List;
import com.studies.logic.Transaction;
import com.studies.logic.Expense;
import java.util.Map;

/**
 *
 * @author lauro.ouverney
 */

public class FinancialManagerTest {


    @BeforeAll
    public static void initializeTransactionsExamples(){
        //initialize transactions examples for each test
        Expense expense1 = FinancialManager.createExpense("Casa", 500);
        expense1.setCategory("Aluguel");

        FinancialManager.createIncome("Salario", 1500,"01/09/2025");

        Expense expense2 = FinancialManager.createExpense("Conserto do carro", 1000, "01/07/2025");
        expense2.setCategory("Transporte");
        
        FinancialManager.createIncome("Bico de motoboy", 200,"20/08/2025");

        Expense expense3 = FinancialManager.createExpense("Jantar fora", 20,"12/08/2025");
        expense3.setCategory("Alimentacao");

        FinancialManager.createIncome("Me deram de presente", 300, "30/06/2025");
    }

    @Test
    public void getGeralBalanceTest(){
        double expectedResult = 480;
        double result = FinancialManager.getGeralBalance();
        assertEquals(expectedResult, result,0);
    }

    @Test
    public void getExpensesByCategoryTest(){
        Map<String, Double> expectedResult = Map.of(
            "Alimentacao",20.0,
            "Aluguel", 500.0,
            "Entretenimento", 0.0,
            "Transporte",1000.0
        );

        Map<String, Double> result = FinancialManager.getExpensesByCategory();

        assertEquals(expectedResult, result);
    }

    @Test
    public void getMonthBalanceTest(){
        double expectedResult = 1000;
        double result = FinancialManager.getMonthBalance();

        assertEquals(expectedResult, result, 0);
    }

    @Test
    public void getMonthlyTransactionsTest(){
        List<Transaction> expectedResult = List.of(
            FinancialManager.getAllTransactions().get(0),
            FinancialManager.getAllTransactions().get(1)
            );

            List<Transaction> result = FinancialManager.getMonthlyTransactions();

            assertEquals(expectedResult, result);
    }
}
