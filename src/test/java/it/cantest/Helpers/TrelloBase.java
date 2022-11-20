package it.cantest.Helpers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public abstract class TrelloBase {
    private static String baseURL = "https://api.trello.com/1/";
    private static String key = "a78b13c8f404e45e1ff845eb60f96002";
    private static String token = "82a9641e694084a53d268ed530e87329058018dc530b65250432cd9e717f1896";
    protected Response lastRespose;

    protected String getLastResponseValue(String key){
        if(this.lastRespose==null)throw new RuntimeException("lastResponse is null");
        return lastRespose.body().jsonPath().get(key);
    }

    public TrelloBase(){
        baseURI = baseURL;
        RestAssured.requestSpecification = given()
                .queryParam("key", key)
                .queryParam("token", token)
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON);
    }

    public int getLastStatusCode() {
        return this.lastRespose.statusCode();
    }

}
