package com.example;

/**
 * The pool class represents the actual pool where clients swim in.
 * One method represents swim (enter the pool), the other one represents to leave the pool (exit).
 * Besides people swimming inside, a cleaner (Jean Luc) randomly arrives to clean the pool from time to time, but only if there is no swimmer inside.
 * @author adrianackva
 * @author mahmoudelzorkany
 * @version 3.0
 */
public class Pool {

    // pool capacity and number of swimmers
    private int capacity;
    private int swimmers;

    /**
     * Constructor that sets the capacity according to the passed parameter.
     * The inital number of swimmers is zero.
     * @param capacity The total capacity of simultaneously swimming clients.
     */
    public Pool(int capacity) {
        this.capacity = capacity;
        this.swimmers = 0;
    }

    /**
     * Synchronized method to allow enter the pool if there it is not full. Otherwise, the client waits.
     * @param client Client Id to identify client
     * @throws InterruptedException In case of exceptions regarding multithreading
     */
    public synchronized void enterPool(int client) throws InterruptedException {

        // As long as the pool is full, client thread will wait.
        while (swimmers >= capacity) {
            System.out.println("Client " + client + " is waiting to go into the waiter. " + swimmers + " are in the water.");
            wait();
        }
        // Once space, clients enters and increases number of swimmers.
        swimmers += 1;
        //notifyAll();
    }

    /**
     * Synchronized method to leave the pool that includes to free up capacity by one.
     * @param client Client Id to identify client
     * @throws InterruptedException In case of exceptions regarding multithreading
     */
    public synchronized void exitPool (int client) throws InterruptedException {

        // Swimmer leaving pool, decrease swimmers by one, notify all waiting threads.
        System.out.println("Client " + client + " leaves pool. Swimming over.");
        swimmers -= 1;
        notifyAll();
    }

    /**
     * Synchronized method to clean the pool (Jean Luc is the cleaner). He only enters, if
     * there is no swimmer inside. While he cleans, no person can enter. This is the reason why
     * the whole cleaning process is synchronized.
     * @param jeanluc Jean Luc Object
     * @throws InterruptedException In case of exceptions regarding multithreading
     */
    public synchronized void cleanPool(JeanLuc jeanluc)throws InterruptedException
    {
        // Wait until no swimmer is in the pool anymore.
        while (swimmers!=0)
        {
            wait();
        }

        // Print out to show that cleaning is over.
        // Notification to all waiting threads that the pool can be used again.
        System.out.println("Jean Luc finished cleaning the pool. 'Avec plaisir' ");
        notifyAll();
    }


}
