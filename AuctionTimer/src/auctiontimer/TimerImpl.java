package auctiontimer;

import auctioncreator.AuctionService;

public class TimerImpl implements TimerService {
    private volatile int timeRemaining;  // Volatile for thread safety
    private volatile boolean auctionClosed;
    private Thread timerThread;

    public TimerImpl(AuctionService auctionService, int initialDuration) {
        this.timeRemaining = initialDuration;
        this.auctionClosed = false;

        // Start countdown thread
        timerThread = new Thread(() -> {
            try {
                while (timeRemaining > 0 && !Thread.interrupted()) {
                    Thread.sleep(1000);  // Decrease every second
                    timeRemaining--;
                }
                auctionClosed = true;
                System.out.println("Auction has ended!");
            } catch (InterruptedException e) {
                System.out.println("Timer interrupted.");
            }
        });
        timerThread.start();
    }

    public void extendDuration(int additionalSeconds) {
        if (!auctionClosed) {
            timeRemaining += additionalSeconds;
            System.out.println("Auction extended by " + additionalSeconds + " seconds. New time remaining: " + timeRemaining);
        } else {
            System.out.println("Cannot extend: Auction already closed.");
        }
    }

    @Override
    public int getTimeRemaining() {
        return timeRemaining;
    }

    @Override
    public boolean isAuctionClosed() {
        return auctionClosed;
    }

    public void stopTimer() {
        if (timerThread != null) {
            timerThread.interrupt();
        }
    }
}
