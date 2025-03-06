package auctioncreator;

public class AuctionImpl implements AuctionService {
	private String itemName;
    private double startingPrice;
    private int duration;  // Duration in seconds
    private double highestBid;

    public AuctionImpl(String itemName, double startingPrice, int duration) {
        this.itemName = itemName;
        this.startingPrice = startingPrice;
        this.duration = duration;
        this.highestBid = startingPrice;  // Initially, highest bid is starting price
    }

    @Override
    public String getAuctionDetails() {
        return String.format("Item: %s, Starting Price: %.2f, Highest Bid: %.2f, Duration: %d sec",
                itemName, startingPrice, highestBid, duration);
    }

    @Override
    public boolean updateHighestBid(double bid) {
        if (bid > highestBid) {
            highestBid = bid;
            System.out.println("New highest bid: " + bid);
            return true;
        }
        System.out.println("Bid rejected: Must be higher than " + highestBid);
        return false;
    }
}
