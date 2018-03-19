package com.example;

/**
 * The Swimming Pool class provides the general overview for the "Natatoria".
 * It has a reception (ticket counter), an optional 2nd weekend counter as well
 * as a changing room and a pool.
 * @author adrianackva
 * @author mahmoudelzorkany
 * @version 3.0
 */
public class SwimmingPool {

    private Reception reception;
    private Reception receptionWeekend;
    private ChangingRoom changingRoom;
    private SwimFinCounter swimFinCounter;
    private Pool pool;

    /**
     * The swimming pool constructor creates the object for Rennes' swimming pool.
     * It requires different parameters to be initialized, mainly for "capacities" and to identify
     * the necessity to open a second counter
     * @param capacityChangingRoom how many people can use the changing room simultaneously
     * @param capacityPool how many people can swim in the pool at the same time
     * @param sfNumber Number of swim fins available at the counter
     * @param weekday true or false if it is a weekday - if weekend: open second counter
     */
    public SwimmingPool(int capacityChangingRoom, int capacityPool, int sfNumber, boolean weekday) {
        this.reception = new Reception();
        this.changingRoom = new ChangingRoom(capacityChangingRoom);
        this.pool = new Pool(capacityPool);
        this.swimFinCounter =new SwimFinCounter(sfNumber);
        if (weekday) {
            this.receptionWeekend = new Reception();
        }
    }

    /**
     * * Simple getter method to ge the reception
     * @return the reception object
     */
    public Reception getReception() {
        return reception;
    }

    /**
     * Simple getter method to ge the reception opened on weekends
     * @return the receptionWeekend object
     */
    public Reception getReceptionWeekend() {
        return receptionWeekend;
    }

    /**
     * Simple getter method to ge the changing room
     * @return the changing room object
     */
    public ChangingRoom getChangingRoom() {
        return changingRoom;
    }

    /**
     * Simple getter method to ge the pool
     * @return the pool object
     */
    public  Pool getPool() {
        return pool;
    }

    /**
     * Simple getter method to ge the reception
     * @return the swim fin counter object
     */
    public SwimFinCounter getSwimFinCounter()
    {
        return swimFinCounter;
    }
    }

