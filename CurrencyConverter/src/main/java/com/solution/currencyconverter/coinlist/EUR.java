package com.solution.currencyconverter.coinlist;


import com.solution.currencyconverter.datatypes.Coin;

import java.lang.annotation.Annotation;
import java.io.Serializable;

public class EUR extends Coin implements Serializable {
    private final double value = 4.23;

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double amount) {
        throw new RuntimeException("Cannot get here");
    }

    @Override
    public double calculate(double input) {
        return input / getValue();
    }

}
