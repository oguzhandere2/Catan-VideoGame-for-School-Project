package components;

/**
 * This class is created for trade operations.
 * @author = fatih
 * @date = 10.11.2019
 */
public class Trade {

    private int buyer;  // no of player
    private int seller; // no of player
    private Resource offers; //offered cards
    private Resource requests;   // requested cards

    public Trade(int buyer, int seller, Resource offers, Resource requests) {
        this.buyer = buyer;
        this.seller = seller;
        this.offers = offers;
        this.requests = requests;
    }

    public int getBuyer() {
        return buyer;
    }

    public int getSeller() {
        return seller;
    }

    public Resource getOffers() {
        return offers;
    }

    public Resource getRequests() {
        return requests;
    }

}
