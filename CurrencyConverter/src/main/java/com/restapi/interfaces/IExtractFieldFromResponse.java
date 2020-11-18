package com.restapi.interfaces;

import io.restassured.response.Response;

/**
 * get the conversion field from Jthe response
 */
public interface IExtractFieldFromResponse {
    public String getConversionFieldFromJson(Response response) throws Exception;
}
