package com.studies;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {

    protected String description;
    protected double valor;
    protected LocalDate date;
    protected static final DateTimeFormatter BRAZILIAN_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Transaction(String description, double valor, String date) {
        setDescription(description);
        setValor(valor);
        setDate(date);
    }

    public Transaction(String description, double valor) {
        this(description, valor, getTodayDate());
    }

    public Transaction(double valor) {
        this("", valor);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean setValor(double valor) {
        if (valor > 0) {
            this.valor = valor;
            return true;
        } else {
            return false;
        }
    }

    public static String getTodayDate() {
        LocalDate today = LocalDate.now();
        return today.format(BRAZILIAN_FORMAT);
    }

    public void setDate(String date) {
        LocalDate formattedDate = LocalDate.parse(date, BRAZILIAN_FORMAT);
        this.date = formattedDate;
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
