package com.example;
import java.util.Random;

/**
 * This Jean Luc class represents the cleaner who is responsible to clean to pool from time to time.
 * Jean Luc represents a thread. From time to time (randomly) Jean Luc tries to clean the pool until the program ends.
 * @author adrianackva
 * @author mahmoudelzorkany
 * @version 3.0
 */
public class JeanLuc extends Thread {

    //static value how long Jean Luc can "sleep" before he tries to clean again (max value for random)
    private static final int waitTimeBoundaries = 600;
    // Attribute swimming pool object to clean
    SwimmingPool swimmingPool;

    /**
     * Constructor to initialize Jean Luc, passing the swimming pool to clean.
     * @param swimmingPool The swimming pool object to assign him to.
     */
    public  JeanLuc(SwimmingPool swimmingPool)
    {
        this.swimmingPool=swimmingPool;
    }

    /**
     * Override Run method from thread class:
     * As long as the pools (get hours) is open, Jean Luc cleans the pool
     */
    @Override
    public void run() {
        // While opened hours is not zero
        while (Main.getHours()!=0) {
            try {
                //Jean Luc "sleeps" for a given amount of time ...
                sleep(determineSleepTime());
                // Until he goes to the pool and tries to clean it
                System.out.println("Jean Luc has arrived. 'Je veux NETTOYER' ");
                 swimmingPool.getPool().cleanPool(this);
            }//try
            catch (InterruptedException e) {
            }//catch
        }//while
    }//run

    /**
     * Static method to determine time how long Jean Luc will wait to clean the pool again
     * @return sleepTime Time how long Jean Luc will sleep
     */
    public static int determineSleepTime() {
        // random value within boundaries of static final value "waitTimeBoundaries"
        Random r = new Random();
        int sleepTime = r.nextInt(waitTimeBoundaries);
        return sleepTime;
    }
}
