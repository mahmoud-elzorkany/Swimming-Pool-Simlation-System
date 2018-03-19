package com.example;

/**
 * The reception counter class represents a ticket counter where clients buy tickets for the swimming pool.
 * It has a simple queueing system (increase/decrease queue) and a method to sell tickets to clients.
 * @author adrianackva
 * @author mahmoudelzorkany
 * @version 3.0
 */
public class Reception {

    // Only one attribute to represent the queue length.
    private int queue;

    /**
     * Constructor that sets the initial queue to zero.
     */
    public Reception () {
        this.queue = 0;
    }

    /**
     * Getter method to retrieve the current queue length.
     * @return the length of the queue
     */
    public int getQueue() {
        return queue;
    }

    /**
     * Increase queue by one (client).
     */
    public void inQueue()
    {
        queue+=1;
    }

    /**
     * Decrease queue by one (client).
     */
    public void deQueue() {queue-=1;}

    /**
     * Synchronized method to sell tickets to clients. Only one client can be served at a time.
     * @param client client id to identify the customer.
     */
    public synchronized void sellTicket(Client client) throws InterruptedException {

        System.out.println("Client " + client.getClientId()+ " bought his ticket");
        client.sleep(10);
    }


}
