package com.studies.logic;

/**
 *
 * @author lauro.ouverney
 */

public class Expense extends Transaction {

    private String category;

    public Expense(String description, double valor, String date) {
        super(description, valor, date);
    }

    public Expense(String description, double valor) {
        super(description, valor);
    }

    public void setCategory(String category) {
        if (!Category.exists(category)) {
            throw new IllegalArgumentException("Categoria não existente!");
        }
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    @Override
    public double getSignedAmount() {
        return -this.value;
    }

    @Override
    public String getDetails() {
        return "Tipo: Despesa" +
                "\n" + "Categoria: " + this.getCategory();
    }

}
