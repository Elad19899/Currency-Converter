package com.solution.currencyconverter;

import com.solution.currencyconverter.datatypes.Coin;
import com.solution.currencyconverter.datatypes.Result;
import com.solution.currencyconverter.enums.Coins;
import com.restapi.RestAPIWrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;


/**
 * <p>Calling point. </p>
 * Main will retrieve conversion details with the Rest API
 * and will call to the conversion machine till the user will stop it with 'Y' as the input
 * <p>
 * The printing commands to the Console is done by the class ConsoleOutputUtils.
 */
public class ConversionMain {
    static boolean verbose = true;
    static private boolean ignoreLowerCase = true;

    /**
     * Do Figh level of the work:
     * <ul>
     * <li> call to the Rest API for retrieving the conversion amount from USD to ILS </li>
     * <li> call to the conversion machine</li>
     *
     * @param args - not used
     */
    public static void main(String[] args) {
        boolean continueFlag = false;
        Double convertionForILS = null;
        Double convertionForUSD = null;
        Double[] apiConversion = {convertionForUSD, convertionForILS};
        try {
            ConsoleOutputUtils echoUtils = new ConsoleOutputUtils(verbose);
            Scanner inputFromConsole = new Scanner(System.in);

            Double conversionValue;
            conversionValue = callToRestAPI(echoUtils);


            fillConversionAmountFromRestAPI(convertionForILS, conversionValue);

            List<Result> resultsList = new ArrayList<Result>();

            do {
                try {
                    currencyConvert(apiConversion, conversionValue, echoUtils, inputFromConsole, resultsList);

                } catch (Exception ex) {
                    System.out.println("Error occured in the system.");
                    Logger.getLogger(ConversionMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                continueFlag = getCntinueAnswer(echoUtils, inputFromConsole);
            } while (continueFlag);


            resultsHandling(echoUtils, resultsList);
            echoUtils.printEndScreen();
        } catch (IOException ex) {
            Logger.getLogger(ConversionMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void fillConversionAmountFromRestAPI(Double convertionForILS, Double conversionValue) {
        Double convertionForUSD;
        if (conversionValue != null) {
            BigDecimal bd = BigDecimal.valueOf(1F / conversionValue);
            try {

                bd = bd.setScale(3, RoundingMode.FLOOR);
                convertionForILS = bd.doubleValue();
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }

            convertionForUSD = conversionValue;
            System.out.println("Selected conversion: ");
            System.out.println("ILS " + convertionForILS);
            System.out.println("USD: " + convertionForUSD);
        }
    }

    /**
     * Print to the echo the resultsList from the input.Save them to the file result.txt.
     *
     * @param echoUtils   Echo string to the console
     * @param resultsList List of the results of the conversion operations
     * @throws IOException in case of exception as result of file handling
     */
    public static void resultsHandling(ConsoleOutputUtils echoUtils, List<Result> resultsList) throws IOException {
        printResultsFromList(echoUtils, resultsList);

        File resultFile = createNewFile("result.txt");
        FileUtils.writeLines(resultFile, resultsList);

    }

    /**
     * If the file exist - remove it and use a new one.
     * Otherwise - create a new file.
     *
     * @param sFileName
     * @return A new file
     */
    public static File createNewFile(String sFileName) {
        File resultFile = new File(sFileName);
        if (resultFile.exists()) {
            resultFile.delete();
            resultFile = new File("result.txt");
        }
        System.out.println("Helper - the location of the file is:\n===>" + resultFile.getAbsolutePath());
        return resultFile;
    }

    public static void printResultsFromList(ConsoleOutputUtils echoUtils, List<Result> resultsList) {
        echoUtils.printResultsFromList(resultsList);

    }

    public static double callToRestAPI(ConsoleOutputUtils echoUtils) {
        Double conversionAmount = null;
        RestAPIWrapper aPIReader = new RestAPIWrapper(echoUtils);
        try {
            conversionAmount = aPIReader.callToRestAPI();
            echoUtils.displayConversionAmount(conversionAmount);
        } catch (Exception e) {
            echoUtils.callToRestApiExistWithError(e.getMessage());
        }
        return conversionAmount;
    }

    public static void currencyConvert(Double[] apiConversion, double conversionValue, ConsoleOutputUtils echoUtils, Scanner inputFromConsole, List<Result> resultsList) throws Exception {
        CoinFactory coinFactory = new CoinFactory();

        echoUtils.printIntroductionScreen();
        echoUtils.dispChoiceScreen();


        Coins itsValue = pullSelectedChoice(inputFromConsole, echoUtils);
        itsValue = Coins.fromPlaceInList(1);

        echoUtils.showSelectedCoin(itsValue);

        BigDecimal itsAmount = pullCurrencyAmount(inputFromConsole, echoUtils);
        echoUtils.echoInput(itsAmount);
        Coin selectedCoin = coinFactory.getCon(itsValue);

        changeConversionValue(apiConversion, selectedCoin);

        double result = selectedCoin.calculate(itsAmount.doubleValue());
        System.out.println(result);

        Result r = new Result(result, itsValue.getCoinName());
        resultsList.add(r);
    }

    public static void changeConversionValue(Double[] apiConversion, Coin selectedCoin) {
        if (apiConversion[0] != null) {
            switch (selectedCoin.getCurrCoins()) {
                case USD:
                    selectedCoin.setValue(apiConversion[0]);
                    break;
                case ILS:
                    selectedCoin.setValue(apiConversion[1]);
                    break;
                case EUR: // Do nothing - implementation for retriving the conversion wasn't done
                    break;
                default:
                    throw new RuntimeException("Cannot get here. Review code");
            }
        }
    }

    /**
     * Wait for input from the user.
     * In case the input is 'Y' or 'N' - it return true/false according the result.
     * Otherwise - show error message and wait for correct answer from the user.
     *
     * @param echoUtils        - Output to the console class
     * @param inputFromConsole Input from the consoe
     * @return true/false according to the input from the user.
     * @Note - y/n will also worked the same as Y/N [according to the flag ignoreLowerCase]
     */
    public static boolean getCntinueAnswer(ConsoleOutputUtils echoUtils, Scanner inputFromConsole) {
        boolean receivedNYAnswer = false;
        boolean continueFlag = false; // For ignore error - no really use
        String ynRes = null;
        do {
            echoUtils.printContinueQuestion();
            if (inputFromConsole.hasNext()) {
                ynRes = inputFromConsole.next();

                if (ynRes.length() == 1) {
                    if (ignoreLowerCase) {
                        ynRes = ynRes.toUpperCase();
                    }
                    if (ynRes.equals("Y")) {
                        continueFlag = true;
                        receivedNYAnswer = true;
                    }
                    if (ynRes.equals("N")) {
                        continueFlag = false;
                        receivedNYAnswer = true;
                    }
                }
            }
            if (!receivedNYAnswer) {
                echoUtils.printWrongContinuationMessage(ynRes);
            }
        } while (!receivedNYAnswer);

        return continueFlag;
    }

    public static Coins pullSelectedChoice(Scanner mInpFromCon, ConsoleOutputUtils echoUtils) {

        Coins selectedCoin = null;
        do
        { // will trey receiving input from the user. If it will be wrong - it will be inserted to loop till user will end correct desicion.

            // Get the next selection from the user
            Integer selectedValue = -1;
            String errorValue;
            if (mInpFromCon.hasNextInt()) {
                selectedValue = mInpFromCon.nextInt();
            } else {
                errorValue = mInpFromCon.nextLine();
            }

            if ((selectedValue > Coins.values().length) || (selectedValue < 1)) {// no such option - move it to wrong option
                errorValue = selectedValue.toString();
                echoUtils.showErrorForWrongSelection(ConsoleOutputUtils.CHOICE_ERROR, errorValue);
            }
            selectedCoin = Coins.fromPlaceInList(selectedValue);
            if (selectedCoin == null) {

            }
        } while (selectedCoin == null);

        return selectedCoin;

    }

    private static BigDecimal pullCurrencyAmount(Scanner m1InpFromCon, ConsoleOutputUtils echoUtils) {
        BigDecimal currencyAmount = null;
        echoUtils.printAmountRequest();
        do {

            boolean bigDecimalInput = m1InpFromCon.hasNextBigDecimal();

            if (!bigDecimalInput) {
                String errorInput = m1InpFromCon.next();
                echoUtils.showErrorForWrongSelection(ConsoleOutputUtils.AMOUNT_ERROR, errorInput);
            } else {
                currencyAmount = m1InpFromCon.nextBigDecimal();
            }
        } while (currencyAmount == null);
        return currencyAmount;
    }


}
