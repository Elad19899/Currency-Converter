package com.solution.currencyconverter;

import com.solution.currencyconverter.datatypes.Coin;
import com.solution.currencyconverter.coinlist.*;
import com.solution.currencyconverter.enums.Coins;

/**
 * Factory for creation a new Coin according to the Coins input.
 *
 * <p> It also put the coins inside the Coin.
 * For later using in
 * {@link com.solution.currencyconverter#changeConversionValue(Double[], Coin) changeConversionValue}</p>
 */
public class CoinFactory {

    public Coin getCon(Coins currCoins) throws Exception {
        Coin selectedCoin;
        switch (currCoins) {
            case USD:
                selectedCoin = new USD();
                break;
            case ILS:
                selectedCoin = new ILS();
                break;
            case EUR:
                selectedCoin = new EUR();
                break;
            default: // However if for any case it get's here - there is issue in the system.
                throw new Exception("Cannot get here");

        }
        selectedCoin.setCurrCoins(currCoins);
        return selectedCoin;
    }
}
