package com.example;
import java.util.Random;

/**
 * This client class represents a client (customer) for the swimming pool. Each customer will act as one thread.
 * The basic concept is as follows:<br>
 * - Client enters swimming pool building, buys a ticket a the counter <br>
 * - Changes clothes in changing room <br>
 * - (optional) Rent Swim Fins<br>
 * - Enters swimming Pool for swimming (and leaves it later ony) <br>
 * - (optional) Return Swim Fins<br>
 * - Change clothes back<br>
 * - Leaves swimming pool building (no particular method)<br>
 *
 * @author adrianackva
 * @author mahmoudelzorkany
 * @version 3.0
 */
public class Client extends Thread {

    // Atrributes to visit all objects (reception, weekend_reception if applicable, pool, etc.
    // Boolean whether swim fins wanted or not.
    // Final attributes to determine duration of activities
    private int clientId;
    //private SwimmingPool swimmingPool;
    private Reception reception;
    private Reception receptionWeekend;
    private Pool pool;
    private ChangingRoom changingRoom;
    private SwimFinCounter swimFinCounter;
    private boolean sfRent;
    private final int changeTimeSleep = 100;
    private final int receptionSleep = 10;
    private static final int swimTimeBound = 600;
    private static int totalNumberOfClients;

    /**
     * Constructor to create custumer incl. parameter "id".
     * Determine all attributes, retrieving from parameter swimming poool.
     * In case it's weekend, the weekend counter is determined, too.
     * @param clientId The client ID for the new client
     * @param swimmingPool The swimming pool object which the client "visits"
     */
    public Client (int clientId, SwimmingPool swimmingPool) {
        // Set Client Id
        this.clientId = clientId;
        this.reception = swimmingPool.getReception();
        this.changingRoom = swimmingPool.getChangingRoom();
        this.pool = swimmingPool.getPool();
        this.swimFinCounter =swimmingPool.getSwimFinCounter();

        //If weekend --> create weekendReception
        if (swimmingPool.getReceptionWeekend() != null) {
            this.receptionWeekend = swimmingPool.getReceptionWeekend();
        }

        // Randomly deciding if client wants to use swim fins or not.
        Random rand= new Random();
        sfRent =rand.nextBoolean();
    }

    /**
     * Override Run method from Thread class:
     * Go to ticket counter (if weekend: decide which counter has shorter queue) <br>
     *
     *
     */
    public void run() {

        try {

            buyTicket();
            change();

            // ** Rent fins (if desired)
            if (sfRent) { swimFinCounter.rentFin(clientId); }//if

            swim();
            // ** Return fins (if borrowed)
            if (sfRent) { swimFinCounter.returnFin(clientId); }//if

            change();
        }//try
        catch (InterruptedException e) {
            e.printStackTrace();
        }//catch
    }//run

    /**
     * Static method to retrieve a random swim time within boundaries of final value
     * @return The swim time for the client
     */
    public static int determineSwimTime() {
        Random r = new Random();
        int swimtime = r.nextInt(swimTimeBound);
        return swimtime;
    }

    /**
     * Getter method to get the client Id
     * @return Client Id
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * /* buyTicket method where the client Go to ticket counter (if weekend: decide which counter has shorter queue)
     * @throws InterruptedException
     */
    private void buyTicket() throws InterruptedException
    {
        // ** Buy Ticket
        // If it is weekend, client decides to use the counter with the shorter queue

        Reception chosenReception;
        int receptionCounter;
        if (this.receptionWeekend != null) {
            // If regular counter's queue shorter-equal to weekend counter, go there
            if (reception.getQueue() <= receptionWeekend.getQueue()) {
                chosenReception = reception;
                receptionCounter = 1;
            }
            // If weekend counter has a shorter queue, go there
            else {
                chosenReception = receptionWeekend;
                receptionCounter = 2;
            }
        } //if
        // If no weekend
        else {
            chosenReception = reception;
            receptionCounter = 1;
        }
        // If weekend: Queue up
        if(this.receptionWeekend != null) {
            chosenReception.inQueue();
            System.out.println("Client " + this.getClientId() + " queues at counter " + receptionCounter);
        }

        // Buy Ticket
        chosenReception.sellTicket(this);

        //sleep(receptionSleep);
        // If weekend: go away from counter (decrease queue)
        if(this.receptionWeekend != null) { chosenReception.deQueue();}
    }

    /**
     * change method where the clients enter and exit the changing room before and after swimming
     * @throws InterruptedException
     */
    private void change() throws InterruptedException
    {
        // ** Change Clothes
        changingRoom.startChanging(clientId);
        // change (--> thread sleeps)
        sleep(changeTimeSleep);
        changingRoom.stopChanging(clientId);
    }

    /**
     * swim method where the clients enter the swimming poo, swim for some random time then leave the pool
     * @throws InterruptedException
     */
    private void swim() throws InterruptedException
    {
        // ** Go for the swim
        pool.enterPool(clientId);
        // Determine swim time and swim (--> thread sleeps)
        int swimTime = determineSwimTime();
        System.out.println("Client "+ clientId + " swimming now for the next " + swimTime/10 + " minutes.");
        sleep(swimTime);
        // Leave pool
        pool.exitPool(clientId);
    }

}


