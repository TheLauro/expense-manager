package com.studies;

/**
 *
 * @author lauro.ouverney
 */

public class Income extends Transaction{

    public Income(String description, double valor, String date) {
        super(description, valor, date);
    }

    public Income(String description, double valor) {
        super(description, valor);
    }

}
