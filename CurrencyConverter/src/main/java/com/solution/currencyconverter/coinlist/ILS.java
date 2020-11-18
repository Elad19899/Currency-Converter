package com.solution.currencyconverter.coinlist;

import com.solution.currencyconverter.datatypes.Coin;

import java.io.Serializable;
import java.math.BigDecimal;

public class ILS extends Coin implements Serializable {
    static private double value = 0.284;

    @Override
    public double getValue() {
        return value;
    }

    public ILS() {
    }

    @Override
    public void setValue(double amount) {
        value = amount;
    }

    @Override
    public double calculate(double input) {
        return input * getValue();
    }

}
