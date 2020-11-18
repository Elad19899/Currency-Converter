package com.solution.currencyconverter.datatypes;

import com.solution.currencyconverter.enums.Coins;
import com.solution.currencyconverter.interfacess.ICalculate;

public abstract class Coin implements ICalculate {

    /**
     * @return the conversion amount
     */
    public abstract double getValue();

    public abstract void setValue(double amount);

    public Coins getCurrCoins() {
        return currCoins;
    }

    public void setCurrCoins(Coins currCoins) {
        this.currCoins = currCoins;
    }


    public Coins currCoins;


}
