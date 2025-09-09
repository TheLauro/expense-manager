package com.studies.logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author lauro.ouverney
 */

public class Transaction {

    protected String description;
    protected double valor;
    protected LocalDate date;
    public static final DateTimeFormatter BRAZILIAN_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Transaction(String description, double valor, String date) {
        setDescription(description);
        setValor(valor);
        setDate(date);
    }

    public Transaction(String description, double valor) {
        this(description, valor, getTodayDate());
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValor(double valor) {
        if (valor > 0) {
            this.valor = valor;
        } else {
            throw new IllegalArgumentException("Valor Inválido! O valor não pode ser nulo ou menor que 0!");
        }
    }

    public static String getTodayDate() {
        LocalDate today = LocalDate.now();
        return today.format(BRAZILIAN_FORMAT);
    }

    public void setDate(String date) {
        try {
            LocalDate formattedDate = LocalDate.parse(date, BRAZILIAN_FORMAT);
            this.date = formattedDate;

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data inválido!", e);
        }
    }

    public String getDescription() {
        return this.description;
    }

    public double getValor() {
        return this.valor;
    }

    public LocalDate getDate() {
        return this.date;
    }
}
