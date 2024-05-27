package org.learning.test;

import java.time.LocalDate;

public class Event {
    //attributi
    private String title;
    private LocalDate date;
    private final int totalSeats;
    private int bookedSeats = 0;

    //costruttore
    public Event(String title, LocalDate date, int totalSeats) throws IllegalArgumentException {
        if (totalSeats <= 0) {
            throw new IllegalArgumentException("Il numero totale di posti deve essere positivo.");
        }
        this.title = title;
        setDate(date);
        this.totalSeats = totalSeats;
    }

    //setter
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDate date) throws IllegalArgumentException {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La data dell'evento non può essere nel passato.");
        }
        this.date = date;
    }

    //getter
    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    //metodi
    public void book(int seats) throws IllegalArgumentException {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("L'evento è già passato.");
        }
        if (bookedSeats + seats > totalSeats) {
            throw new IllegalArgumentException("Non ci sono abbastanza posti disponibili.");
        }
        bookedSeats += seats;
    }

    public void cancel(int seats) throws IllegalArgumentException {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("L'evento è già passato.");
        }
        if (bookedSeats - seats < 0) {
            throw new IllegalArgumentException("Non ci sono abbastanza prenotazioni.");
        }
        bookedSeats -= seats;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title= '" + title + '\'' +
                ", date= " + date +
                ", totalSeats= " + totalSeats +
                ", bookedSeats= " + bookedSeats +
                '}';
    }
}