package org.learning.test;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;

public class Concert extends Event {
    //attributi
    private LocalTime time;
    private BigDecimal price;

    //costruttore
    public Concert(String title, LocalDate date, int totalSeats, LocalTime time, BigDecimal price) throws IllegalArgumentException {
        super(title, date, totalSeats);
        setTime(time);
        setPrice(price);
    }

    //setter
    public void setTime(LocalTime time) throws IllegalArgumentException {
        if (time.isBefore(LocalTime.now()) && getDate().equals(LocalDate.now())) {
            throw new IllegalArgumentException("L'ora dell'evento non può essere nel passato.");
        }
        this.time = time;
    }

    public void setPrice(BigDecimal price) throws IllegalArgumentException {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Il prezzo non può essere negativo.");
        }
        this.price = price;
    }

    //getter
    public LocalTime getTime() {
        return time;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getFormattedDateTime() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return getDate().format(dateFormatter) + " " + getTime().format(timeFormatter);
    }

    public String getFormattedPrice() {
        return getPrice().setScale(2, RoundingMode.HALF_EVEN).toString() + '€';
    }

    @Override
    public String toString() {
        return getFormattedDateTime() + " - " + getTitle() + " - " + getFormattedPrice();
    }
}