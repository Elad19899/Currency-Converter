package com.solution.currencyconverter.enums;


/**
 * Enum which contain list of coins. As the current required has 3 options to convert:
 * 1. From USD to Shekel  - USD is used for this
 * 2. From Shekel to USD  - ILS is used for this
 * 1. From Shekel to Euro - EUR is used for this
 * <p>
 * every one of them contains the name of the activity for construction of the Result data type.
 */
public enum Coins {
    // List of Enums, order is critical for the correct selection in the input
    USD("USD to ILS", "USD"), ILS("ILS TO USD", "ILS"), EUR("ILS TO EUR", "EUR");

    private String reqCoin; //  Contain the name of the coin. give the ability to load Coins according his name
    private String coinName; // Contains convertion from/to related to this coin.

    private Coins(String sCoinName, String reqCoin) {
        coinName = sCoinName;
        this.reqCoin = reqCoin;
    }


    @Override
    public String toString() { // Will be used for verbose display
        return "Coins{" + "reqCoin=" + reqCoin + ", coinName=" + coinName + '}';
    }

    /**
     * Look for the Coins which is in the index desicionValue.
     * The options and Coins in this Enum are ordered in the same order.
     *
     * @param desicionValue - the option which has been selected for the conversion.
     * @return The selected Coins. null - in case of not founding Coins in the place in the list
     */
    public static Coins fromPlaceInList(int desicionValue) {
        int index = 0;
        for (Coins currCoin : values()) {
            ++index;
            if (index == desicionValue) return currCoin;
        }
        return null;
    }

    public String getReqCoin() {
        return reqCoin;
    }

    public void setReqCoin(String reqCoin) {
        this.reqCoin = reqCoin;
    }


    public String getCoinName() {
        return coinName;
    }
}
