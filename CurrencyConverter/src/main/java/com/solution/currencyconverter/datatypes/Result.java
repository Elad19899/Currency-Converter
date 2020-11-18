package com.solution.currencyconverter.datatypes;

public class Result {
    private double amount;
    private String activity;

    @Override
    public String toString() {
        return "Result{" + "amount=" + amount + ", activity=" + activity + '}';
    }

    public Result(double amount, String activity) {
        this.amount = amount;
        this.activity = activity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }


}
