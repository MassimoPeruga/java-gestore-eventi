package org.learning.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventManager {
    //attributi
    private final String title;
    private List<Event> events;

    //costruttore
    public EventManager(String title) throws IllegalArgumentException {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Il titolo non può essere vuoto.");
        }
        this.title = title;
        this.events = new ArrayList<>();
    }

    //metodi
    public void addEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("L'evento non può essere null.");
        }
        this.events.add(event);
    }

    public List<Event> getEventsInDate(LocalDate date) {
        return this.events.stream()
                .filter(event -> event.getDate().equals(date))
                .collect(Collectors.toList());
    }

    public int getNumEvents() {
        return this.events.size();
    }

    public void clearEvents() {
        this.events.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("\n");
        this.events.stream()
                .sorted((e1, e2) -> e1.getDate().compareTo(e2.getDate()))
                .forEach(event -> sb.append(event.getDate()).append(" - ").append(event.getTitle()).append("\n"));
        return sb.toString();
    }
}