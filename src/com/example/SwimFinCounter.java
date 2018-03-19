package com.example;

/**
 * The swim fin counter class represents a counter where clients can borrow swim fins in paris of two
 * in case they want to swim with them.
 * @author adrianackva
 * @author mahmoudelzorkany
 * @version 3.0
 */
public class SwimFinCounter {

    // number of Swim Fins (available)
    private int sfins;

    /**
     * Constructor with a single parameter: number of swim fins available at this counter "today"
     * @param sfins Number of Swin Fins at the beginning.
     */
    public SwimFinCounter(int sfins)
    {
        this.sfins = sfins;
    }

    /**
     * Synchronized method to rent fins by clients.
     * As long as there are at least two sim fins available, a client can rent a pair.
     * If not, he/she will wait (Thread wait method) until two fins are available
     * Once there are enough fins, two are given to the client
     * @param client client Id to identify customer
     * @throws InterruptedException in case multithreading issues occur
     */
    public synchronized void rentFin(int client)throws  InterruptedException
    {
        // As long as swim fins available is less than 2, client thread will wait.
        while (sfins < 2)
        {
            System.out.println("Client " + client + " waiting to get swim fins.");
            wait();
        }
        // Decrease swim fins by two.
        sfins -= 2;
        System.out.println("Client " + client + " is renting a pair of swimming fins. " + sfins + " swim fins left.");
        //notifyAll();
    }

    /**
     * Synchronized method to return rented fins by clients.
     * Customers can always return fins (within boundaries of "synchronized" methods).
     * The number of fins is increased by two and every waiting thread is notified.
     * @param client client Id to identify customer
     * @throws InterruptedException in case multithreading issues occur
     */
    public synchronized void returnFin(int client) throws InterruptedException
    {
        // Increase swim fins by two
        sfins += 2;
        System.out.println("Client " + client + " is returning the borrowed swimming fins. " + sfins + " swim finds are still available");
        // Notify all waiting threads
        notifyAll();
    }
}
