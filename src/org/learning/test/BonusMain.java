package org.learning.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class BonusMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EventManager eventManager = new EventManager("Eventi Programmati");

        while (true) {
            System.out.println("\n-------------------------");
            System.out.println("1. Aggiungi un evento");
            System.out.println("2. Visualizza tutti gli eventi");
            System.out.println("3. Prenota un evento in una data");
            System.out.println("4. Disdici prenotazioni in una data");
            System.out.println("5. Cancella tutti gli eventi programmati");
            System.out.println("6. Esci");
            System.out.println("-------------------------");
            System.out.print("Scegli un'opzione: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":  // Aggiungi un evento
                    // Nome evento
                    System.out.print("Inserisci il nome dell'evento: ");
                    String title = scanner.nextLine().trim();
                    while (title.isEmpty()) {
                        System.out.println("Il nome dell'evento non può essere vuoto. Riprova.");
                        title = scanner.nextLine().trim();
                    }

                    // Data evento
                    LocalDate date = null;
                    while (date == null) {
                        System.out.print("Inserisci la data dell'evento (dd/MM/yyyy): ");
                        String dateString = scanner.nextLine().trim();
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            date = LocalDate.parse(dateString, formatter);
                            if (date.isBefore(LocalDate.now())) {
                                System.out.println("La data dell'evento non può essere nel passato.");
                                date = null;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Formato data non valido. Riprova.");
                        }
                    }

                    // Posti evento
                    int totalSeats = -1;
                    while (totalSeats <= 0) {
                        System.out.print("Inserisci il numero totale di posti: ");
                        try {
                            totalSeats = Integer.parseInt(scanner.nextLine().trim());
                            if (totalSeats <= 0) {
                                System.out.println("Il numero totale di posti deve essere positivo. Riprova.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Input non valido. Inserisci un numero intero.");
                        }
                    }

                    // Concerto?
                    System.out.print("L'evento è un concerto? (s/n): ");
                    String isConcert = scanner.nextLine().trim();
                    Event event;
                    if (isConcert.equalsIgnoreCase("s")) {
                        // Ora concerto
                        LocalTime time = null;
                        while (time == null) {
                            System.out.print("Inserisci l'ora del concerto (HH:mm): ");
                            String timeString = scanner.nextLine().trim();
                            try {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                                time = LocalTime.parse(timeString, formatter);
                            } catch (DateTimeParseException e) {
                                System.out.println("Formato ora non valido. Riprova.");
                            }
                        }

                        // Prezzo concerto
                        BigDecimal price = null;
                        while (price == null) {
                            System.out.print("Inserisci il prezzo del biglietto: ");
                            try {
                                price = new BigDecimal(scanner.nextLine().trim());
                            } catch (NumberFormatException e) {
                                System.out.println("Input non valido. Inserisci un numero.");
                            }
                        }

                        // Istanzio concerto
                        event = new Concert(title, date, totalSeats, time, price);
                    } else {
                        // Istanzio evento
                        event = new Event(title, date, totalSeats);
                    }

                    // Aggiungo evento alla lista
                    eventManager.addEvent(event);
                    System.out.println("Evento creato e aggiunto al programma.");
                    break;
                case "2":
                    // Visualizza tutti gli eventi
                    if(eventManager.getNumEvents() == 0){
                        System.out.println("Non ci sono eventi programmati.");
                    }else {
                        System.out.println(eventManager);
                    }
                    break;
                case "3":
                    // Prenota un evento in una data
                    System.out.print("Inserisci la data dell'evento che vuoi prenotare (dd/MM/yyyy): ");
                    String dateString = scanner.nextLine().trim();
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        date = LocalDate.parse(dateString, formatter);
                        List<Event> eventsInDate = eventManager.getEventsInDate(date);
                        if (eventsInDate.isEmpty()) {
                            System.out.println("Non ci sono eventi in questa data.");
                        } else {
                            for (int i = 0; i < eventsInDate.size(); i++) {
                                Event current = eventsInDate.get(i);
                                System.out.println((i + 1) + ". " + current.getTitle() + "Posti disponibili: " + (current.getTotalSeats() - current.getBookedSeats()));
                            }
                            System.out.print("Scegli un evento da prenotare: ");
                            int eventIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
                            Event eventToBook = eventsInDate.get(eventIndex);
                            System.out.print("Quanti posti vuoi prenotare? ");
                            int seats = Integer.parseInt(scanner.nextLine().trim());
                            eventToBook.book(seats);
                            System.out.println("Prenotazione effettuata.");
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato data non valido. Riprova.");
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        System.out.println("Input non valido. Riprova.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "4":
                    // Disdici prenotazioni in una data
                    System.out.print("Inserisci la data dell'evento per cui vuoi disdire le prenotazioni (dd/MM/yyyy): ");
                    dateString = scanner.nextLine().trim();
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        date = LocalDate.parse(dateString, formatter);
                        List<Event> eventsInDate = eventManager.getEventsInDate(date);
                        if (eventsInDate.isEmpty()) {
                            System.out.println("Non ci sono eventi in questa data.");
                        } else {
                            for (int i = 0; i < eventsInDate.size(); i++) {
                                System.out.println((i + 1) + ". " + eventsInDate.get(i).getTitle());
                            }
                            System.out.print("Scegli un evento per cui disdire le prenotazioni: ");
                            int eventIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
                            Event eventToCancel = eventsInDate.get(eventIndex);
                            System.out.print("Quanti posti vuoi disdire? ");
                            int seats = Integer.parseInt(scanner.nextLine().trim());
                            eventToCancel.cancel(seats);
                            System.out.println("Prenotazioni disdette.");
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato data non valido. Riprova.");
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        System.out.println("Input non valido. Riprova.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "5":
                    // Cancella tutti gli eventi programmati
                    eventManager.clearEvents();
                    System.out.println("Tutti gli eventi programmati sono stati cancellati.");
                    break;
                case "6":
                    // Esci
                    System.out.println("Arrivederci!");
                    return;
                default:
                    System.out.println("Opzione non valida. Riprova.");
                    break;
            }
        }
    }
}