package com.solution.currencyconverter;

import com.solution.currencyconverter.datatypes.Result;
import com.solution.currencyconverter.enums.Coins;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.List;

/**
 * This class holds all the printing to the Console.
 * Verbose flag configurable [not implemented] is injected to this class
 * from the caller of user of the class.
 */
public class ConsoleOutputUtils {
    private static boolean mVerbose = false;
    private final static String VERBOSE_STARTING = ">>>>  ";

    static public final String CHOICE_ERROR = "invalid choice";
    static public final String AMOUNT_ERROR = "invalid amount";

    public ConsoleOutputUtils(boolean verbose) {
        mVerbose = verbose;
    }

    public void callToRestApiExistWithError(String errMessage) {
        System.out.println("The retriving of the conversion amount between {" + Coins.ILS.getReqCoin() + "} and {" + Coins.USD.getReqCoin() + "} does not work now.\nThe reason was " + errMessage);
    }

    void printEndScreen() {
        System.out.println("Thanks for using our currency converter");
        System.out.println("Ending of execution");
    }

    void printIntroductionScreen() {
        System.out.println("Welcome to the currenct converter.");

    }

    void dispChoiceScreen() {
        System.out.println("Please choose an option(1/2/3)");
        System.out.println("1. Dollars to Shekels");
        System.out.println("2. Shekels to Dollars");
        System.out.println("3. Shekels to Euro");
    }

    void showErrorForWrongInput(String errorType) {

        System.out.println("Invalid " + errorType + ", please try again.");
    }

    void echoInput(String selectedChoice) {
        System.out.println(VERBOSE_STARTING.concat(selectedChoice));
    }

    void showSelectedCoin(Coins selectedCoin) {
        if (mVerbose) {
            System.out.println(VERBOSE_STARTING.concat(selectedCoin.toString()));
        }
    }

    void echoInput(BigDecimal currencyAmount) {
        System.out.println(VERBOSE_STARTING.concat(currencyAmount.toString()));
    }


    void echoWrongInput(String currencyAmount, InputMismatchException currE) {
        System.out.println("Invalid choice, please try again.");
        if (mVerbose) {
            System.out.println("The wrong input was :[ " + currencyAmount + " ]");
        }
    }

    void showErrorForWrongSelection(String sErrorTypeMessage, String errorInput) {
        showErrorForWrongInput(sErrorTypeMessage);
        if (mVerbose) {
            String verboseError = VERBOSE_STARTING.concat("Input value was [".concat(errorInput).concat("]"));
            System.out.println(verboseError);
        }
    }

    public void echoCallingToRestAPI() {
        if (mVerbose) {
            System.out.println(VERBOSE_STARTING.concat("Started call to API."));
        }
    }

    public void achoFinishedCall() {
        System.out.println(VERBOSE_STARTING.concat("Received data from API"));
    }

    void printAmountRequest() {
        System.out.println("Please enter an amount to convert");
    }

    void printContinueQuestion() {
        System.out.println("Start over?");
        System.out.println("Y - will start cycle again.");
        System.out.println("N - will end the converter.");
    }


    void printWrongContinuationMessage(String ynRes) {

        if (mVerbose) {
            System.out.println(VERBOSE_STARTING.concat("The wrong input was: [" + ynRes + "]"));
        } else {
            System.out.println("Wrong input.");
        }
    }

    void displayConversionAmount(Double conversionAmount) {
        if (mVerbose) {
            System.out.println(VERBOSE_STARTING.concat("Conversion amount UDS to ILS is [" + conversionAmount.toString() + "]"));
        }
    }

    /**
     * Display results list.
     * In case of verbose - it will display header. [Maybe it should exist in every case -
     * condition may be removed]
     *
     * @param resultsList List of the Result of conversion
     */
    void printResultsFromList(List<Result> resultsList) {
        if (mVerbose) {
            System.out.println("The folowing list contains the conversion occurred in the system:");
        }
        for (Result result : resultsList) {
            System.out.println(result);
        }
    }

}
