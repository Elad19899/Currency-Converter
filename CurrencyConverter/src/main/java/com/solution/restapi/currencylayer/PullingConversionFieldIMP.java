package com.solution.restapi.currencylayer;

import com.restapi.interfaces.IExtractFieldFromResponse;
import io.restassured.response.Response;

import java.util.Scanner;

public class PullingConversionFieldIMP implements IExtractFieldFromResponse {


    public String getConversionFieldFromJson(Response response) throws Exception {
        String conversionList = response.jsonPath().getString("quotes");
        conversionList = conversionList.replace("[", "");
        conversionList = conversionList.replace("]", "");

        Scanner currScanner = new Scanner(conversionList).useDelimiter(",");
        boolean foundCurecncy = false;
        String stExtractedCoversionValue = null;

        if (currScanner.hasNext()
                && (!foundCurecncy)) {
            String currConversion = currScanner.findInLine(CurrencylayerStatics.CONVERSION_FROMDOLAR_TOISL);
            System.out.println(currConversion);
            if (currConversion == null) {
                throw new Exception("Convertion for USDILS does not exist.");
            }
            stExtractedCoversionValue = currScanner.next().replace(":", "");

        }

        if (stExtractedCoversionValue == null) { // Incase the list of conversions is empty - it wull get here
            throw new Exception("There was error in the inner string which seems to be empty.");
        }
        return stExtractedCoversionValue;
    }
}
