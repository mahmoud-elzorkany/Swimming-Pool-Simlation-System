package com.example;

import java.nio.channels.SelectableChannel;
import java.util.Random;

/**
 * The main class obviously contains the main method of the Swimming Pool simulation tool.
 * The main method description explains the actual procedure.
 * @author adrianackva
 * @author mahmoudelzorkany
 * @version 3.0
 */
public class Main {

    // Static values: totalHoursOpened; hourInMilliSeconds
    public static int hoursOpened = 12;
    public static int hourInMilliSeconds = 600;

    /**
     * Static method to get the hours opened today
     * @return number of hours opened ("today")
     */
    public static int getHours()
     {return hoursOpened;}

    /**
     * Main method with the following procedure: <br>
     *     - Set different varables (e.g. number of clients, capacity of the different facilities,
     *     client array, numer of swim fins, weekend y/n <br>
     *     - Initialize Swimming Pool with given parameters<br>
     *     - Initialize Jean Luc (cleaner) with a higher priority<br>
     *     - Every hour (while loop), a random number of clients is created (currently up to 25 per hour)<br>
     *     - These clients (threads) are started<br>
     *     - Repeated until this swimming pool closes (hoursOpened == 0)<br>
     */
    public static void main(String[] args) {

        // Set parameters
        int maxCustomersPerHour = 30;
        int capacityChangingRoom = 15;
        int capacityPool = 20;
        int sfNumber = 8;
        boolean isWeekend = determineWeekend();

        // Initialize Pool
        SwimmingPool swimmingPool = new SwimmingPool(capacityChangingRoom, capacityPool,sfNumber, isWeekend);

        // Initialize Jean Luc and give him (thread) higher priority
        JeanLuc jeanLuc = new JeanLuc(swimmingPool);
        jeanLuc.setPriority(7);

        // Provide welcome message
        System.out.println(
                "******************************************\n" +
                "Swimming Pool [v3.0] opened for today - Have fun and don't drink too much water\n" +
                "******************************************"    );

        // If weekend: tell about second counter. Otherwise, no specific message.
        if (isWeekend) {
            System.out.println("It's " + "the weekend "+ "! We have opened a second ticket counter for you\n" +
                    "******************************************"    );
            }
        else {
            System.out.println("It's " +"a week day " + "! Enjoy your stay.\n" +
                    "\"******************************************\"    ");
        }

        // Start Jean Luc
        jeanLuc.start();

        // Section to initialize and start client threads
        int clientNumber = 0;

        // As long as swimming pool is opened (hours opened <> 0)
        while(hoursOpened!=0) {
            // Determine random number of client objects (up to "25" per hour)
            Random random = new Random();
            int newClients = random.nextInt(maxCustomersPerHour);
            System.out.println(newClients + " new customers have arrived.");
            Client clients[] = new Client[newClients];
            // Initialize each client object with clientNumber and our swimming pool.
            for (int j = 0; j < newClients; j++) {
                clients[j] = new Client(clientNumber, swimmingPool);
                clientNumber++;
            }

            // Start (run) client threads
            for (int j = 0; j < newClients; j++) {
                clients[j].start();
            }

            // Let the loop sleep for one hour
            try {
                Thread.sleep(hourInMilliSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //reduce number of hours opened
            hoursOpened--;
        }
    }

    /**
     * Static method to determine if it is weekend or not (for the second counter)
     * @return isWeekend boolean value
     */
    public static boolean determineWeekend() {

        // Generate random number between 0 and 6
        int weekday;
        Random rand = new Random();
        weekday = rand.nextInt(6);
        boolean isWeekend;

        // if weekday = 0-4: no weekend, if 5 or 6: weekend
        switch (weekday) {
            case 0: case 1: case 2: case 3: case 4:
                isWeekend = false;
                break;
            case 5: case 6:
                isWeekend = true;
                break;
            default:
                isWeekend = false;
        }

        return isWeekend;
    }
}