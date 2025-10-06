package com.studies.logic;

/**
 *
 * @author lauro.ouverney
 */

public class Income extends Transaction{

    public Income(String description, double value, String date) {
        super(description, value, date);
    }

    public Income(String description, double value) {
        super(description, value);
    }

    @Override
    public double getSignedAmount(){
        return this.value;
    }

    @Override
    public String getDetails(){
        return "Tipo: Entrada";
    }

}
