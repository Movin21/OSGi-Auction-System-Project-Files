package auctiontimer;

public interface TimerService {
	int getTimeRemaining();  // Returns remaining time in seconds
    boolean isAuctionClosed();  // Returns true if auction has ended
}
