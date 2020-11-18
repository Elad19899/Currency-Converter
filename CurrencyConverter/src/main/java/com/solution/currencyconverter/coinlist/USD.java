package com.solution.currencyconverter.coinlist;


import com.solution.currencyconverter.datatypes.Coin;

import java.io.Serializable;
import java.lang.annotation.Annotation;

public class USD extends Coin implements Serializable {
    static private double value = 3.52;

    @Override
    public double getValue() {
        return value;
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
