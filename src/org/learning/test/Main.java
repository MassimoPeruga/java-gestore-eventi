package org.learning.test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //nome
        String title = "";
        while (title.isEmpty()) {
            System.out.print("Inserisci il nome dell'evento: ");
            title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("Il nome dell'evento non può essere vuoto. Riprova.");
            }
        }

        //data
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

        //posti totali
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

        //istanzio l'evento
        Event event = new Event(title, date, totalSeats);
        System.out.println("Evento creato");

        //prenoto i posti
        String firstChoice = "";
        while (!firstChoice.equalsIgnoreCase("s") && !firstChoice.equalsIgnoreCase("n")) {
            System.out.print("Vuoi fare delle prenotazioni? (s/n) ");
            firstChoice = scanner.nextLine();
            switch (firstChoice.toLowerCase()) {
                case "s":
                    boolean validInput = false;
                    while (!validInput) {
                        System.out.print("Quanti posti vuoi prenotare? ");
                        try {
                            int seats = Integer.parseInt(scanner.nextLine());
                            event.book(seats);
                            validInput = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Input non valido. Inserisci un numero intero.");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case "n":
                    break;
                default:
                    System.out.println("Risposta non valida. Inserisci 's' per sì o 'n' per no.");
                    firstChoice = "";
                    break;
            }
        }

        //disdico le prenotazioni
        String secondChoice = "";
        while (!secondChoice.equalsIgnoreCase("s") && !secondChoice.equalsIgnoreCase("n")) {
            System.out.print("Vuoi disdire delle prenotazioni? (s/n) ");
            secondChoice = scanner.nextLine();
            switch (secondChoice.toLowerCase()) {
                case "s":
                    boolean validInput = false;
                    while (!validInput) {
                        System.out.print("Quanti posti vuoi disdire? ");
                        try {
                            int seats = Integer.parseInt(scanner.nextLine());
                            event.cancel(seats);
                            validInput = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Input non valido. Inserisci un numero intero.");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case "n":
                    break;
                default:
                    System.out.println("Risposta non valida. Inserisci 's' per sì o 'n' per no.");
                    secondChoice = "";
                    break;
            }
        }

        //stampo a schermo
        System.out.println("******************************************");
        System.out.println("Posti prenotati: " + event.getBookedSeats());
        System.out.println("Posti disponibili: " + (event.getTotalSeats() - event.getBookedSeats()));

        scanner.close();
    }
}