package com.studies.logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 *
 * @author lauro.ouverney
 */

public abstract class Transaction {

    protected String description;
    protected double value;
    protected LocalDate date;
    public static final DateTimeFormatter BRAZILIAN_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Transaction(String description, double value, String date) {
        setDescription(description);
        setValue(value);
        setDate(date);
    }

    public Transaction(String description, double value) {
        this(description, value, getTodayDate());
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(double value) {
        if (value > 0) {
            this.value = value;
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

    public double getValue() {
        return this.value;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public abstract double getSignedAmount();

    public abstract String getDetails();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || this.getClass() != o.getClass())
            return false;

        Transaction that = (Transaction) o;
        return Double.compare(this.value, that.value) == 0 &&
                Objects.equals(this.description, that.description) &&
                Objects.equals(this.date, that.date);

    }

    @Override
    public int hashCode() {
        return Objects.hash(description, value, date);
    }

}
