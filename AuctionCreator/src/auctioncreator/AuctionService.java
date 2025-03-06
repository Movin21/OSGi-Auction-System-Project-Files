package auctioncreator;

public interface AuctionService {
	String getAuctionDetails();  // Returns auction info (item, price, bid)
    boolean updateHighestBid(double bid);  // Updates the highest bid if valid
}
