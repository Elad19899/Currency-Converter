package com.restapi;

import com.solution.currencyconverter.ConsoleOutputUtils;
import com.solution.restapi.currencylayer.RequestCurrencyLayerIMP;
import io.restassured.response.Response;

import java.math.BigDecimal;

import com.restapi.interfaces.IExtractFieldFromResponse;
import com.restapi.interfaces.ResponseFromRestApiINT;
import com.solution.restapi.currencylayer.PullingConversionFieldIMP;

/**
 * This class is an wrapper for calling to the Rest API.
 * As every rest API use another method, host, paramters - it should be handled as specific implementation.
 * <p>
 * For giving this ability - the wrapper use some interfaces for loading the data and extract the specific field
 * as it's exist in the specific Rest APi.
 * The retrieved value is handled internally in thie wrapper, do required verification and return it back to the caller.
 * <p>
 * <p>
 * It used two interfaces:
 * <ul>
 * <li> Calling to the RST API and receive JSON response from it
 * <li> calling to he extraction of the conversion amount
 * </ul>
 * The two interfaces are based on specific implementation of Rest API.
 * <p>
 * Therefore there are two Constructor:
 * one that received input and assign default implementations.
 * Second [mainly for enabling JUnit] - enable to change the implementation for the extraction of the field from the result.
 * <p>
 * The result of the second interface will extract the field that relate to the required conversion.
 * <p>
 * The wrapper call to receive the answer from the REST API. Then call to the second interface for retrieve the field.
 * In the end  - it verify the result(if it's a number), and will insert it to the coin object as well.
 */
public class RestAPIWrapper {

    static private ConsoleOutputUtils printUtils;
    private IExtractFieldFromResponse effjintIMP;
    private final ResponseFromRestApiINT apiINP;


    /**
     * Constructor with mandatory field.
     * All the interfaces will receive their default implementation.
     *
     * @param echoUtils
     */
    public RestAPIWrapper(ConsoleOutputUtils echoUtils) {
        printUtils = echoUtils;
        apiINP = new RequestCurrencyLayerIMP();
        effjintIMP = new PullingConversionFieldIMP();
    }

    public RestAPIWrapper(ConsoleOutputUtils echoUtils, IExtractFieldFromResponse effjintIMP) {
        printUtils = echoUtils;
        this.effjintIMP = effjintIMP;
        apiINP = new RequestCurrencyLayerIMP();
        effjintIMP = new PullingConversionFieldIMP();
    }


    public double callToRestAPI() throws Exception {
        printUtils.echoCallingToRestAPI();
        Response response = apiINP.internalCallToRestApi();
        printUtils.achoFinishedCall();
        return pullConversionAmount(response);
    }

    public double pullConversionAmount(Response response) throws Exception {

        String stExtractedCoversionValue = effjintIMP.getConversionFieldFromJson(response);

        BigDecimal bd = null;
        try {
            bd = new BigDecimal(stExtractedCoversionValue);
        } catch (java.lang.NumberFormatException e) {

            throw new Exception("Error in the format of the extracted conversion  [" + stExtractedCoversionValue + "]  " + e.getClass().getCanonicalName() + " was the exception.", e);
        }
        return bd.doubleValue();
    }


}
