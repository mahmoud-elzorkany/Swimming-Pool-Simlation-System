package com.example;

/**
 * The changing room class represents the changing room used when entering and leaving the swimming pool.
 * Two methods exist: "startChanging" and "stopChanging". The capacity of the changing room is limited.
 * @author adrianackva
 * @author mahmoudelzorkany
 * @version 3.0
 */
public class ChangingRoom  {

    // Attributes: capacity (total) and clientsChanging ("currently")
    private int capacity;
    private int clientsChanging;

    /**
     * Constructor to initialize Changing room object with given capacity and initially zero clients inside.
     * @param capacity Total number of people who can change simultaneously.
     */
    public ChangingRoom(int capacity) {
        this.capacity = capacity;
        this.clientsChanging = 0;
    }

    /**
     * Synchronized method to enter the changing room. As long as there is space, people can enter.
     * Otherwise, they will wait (thread sleep).
     * @param client the ID of the entering client
     * @throws InterruptedException in case of multithreading exceptions
     */
    public synchronized void startChanging(int client) throws InterruptedException {

        // As long as changing room is full, client thread waits
        while (clientsChanging>=capacity) {
            System.out.println("Client " + client + " waiting to enter changing room.");
            wait();
        }
        // Once free, clientsChanging is increased and client changes.
        clientsChanging+=1;
        System.out.println("Client " + client + " starts changing. In total, " + clientsChanging + " persons in changing room.");
        //notifyAll();
    }

    /**
     * Synchronized method to leave the changing room.
     * As soon as client has changed, he/she can leave (within "synchronization" boundaries) and free up space in the room.
     * @param client Client ID of leaving client
     * @throws InterruptedException Exception in case of multithreading issues
     */
        public synchronized void stopChanging(int client) throws InterruptedException
    {
        // Decrease number of people changing
        clientsChanging-=1;
        System.out.println("Client " + client + " leaves changing room. In total, " + clientsChanging + " persons in changing room.");
        // Notify all other waiting threads
        notifyAll();
    }

}
