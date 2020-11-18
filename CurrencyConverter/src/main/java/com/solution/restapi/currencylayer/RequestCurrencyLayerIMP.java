package com.solution.restapi.currencylayer;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.restapi.interfaces.ResponseFromRestApiINT;

/*
 * Implementaion of calling to http://api.currencylayer.com/
 */
public class RequestCurrencyLayerIMP implements ResponseFromRestApiINT {

    @Override
    public Response internalCallToRestApi() {
        RestAssured.baseURI = CurrencylayerStatics.BASE_HOST;
        RequestSpecification httpRequest = RestAssured.given();

        Response jSonResponse = httpRequest.request(Method.GET, "/historical?access_key=" + CurrencylayerStatics.ACCESS_KEY + "&date=2020-11-11");

        return jSonResponse;
    }

}
