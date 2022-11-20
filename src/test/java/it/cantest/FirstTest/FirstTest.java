package it.cantest.FirstTest;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class FirstTest {
    private static String baseURL = "https://api.trello.com/1/";
    private static String key = "a78b13c8f404e45e1ff845eb60f96002";
    private static String token = "82a9641e694084a53d268ed530e87329058018dc530b65250432cd9e717f1896";
    private String meURL = "members/me";

    @BeforeAll
    public static void setUP() {
        baseURI = baseURL;
        RestAssured.requestSpecification = given()
                .queryParam("key", key)
                .queryParam("token", token)
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON);
    }

    @Test
    public void shouldCreateNewBoard() {
        Response response = given()
                .queryParam("name", "Zrodzony z kodu")
                .when()
                .post("boards");
        String idTablicy = response.getBody().jsonPath().get("id");
        given()
                .body("{\"name\":\"New name from JSON\",\"desc\":\"To jest opis tablicy\"}")
                .when()
                .put("boards/"+idTablicy);
        response = get("boards/"+idTablicy);
        Assertions.assertEquals(200,response.getStatusCode());
        Assertions.assertEquals("New name from JSON",response.getBody().jsonPath().get("name"));
        Assertions.assertEquals("To jest opis tablicy",response.getBody().jsonPath().get("desc"));
        delete("boards/"+idTablicy);
        response = get("boards/"+idTablicy);
        Assertions.assertEquals(404,response.getStatusCode());
    }


    @Test
    public void secondTestBetter() {
        Response response = get(meURL);
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals("Kuba Rosiński", response.getBody().jsonPath().get("fullName"));
    }

    @Test
    public void veryFirstTest() {
        given()
                .baseUri("https://api.trello.com/1/")
                .queryParam("key", "a78b13c8f404e45e1ff845eb60f96002")
                .queryParam("token", "82a9641e694084a53d268ed530e87329058018dc530b65250432cd9e717f1896")
                .when()
                .get("members/me")
                .then()
                .statusCode(200)
                .body("fullName", equalTo("Kuba Rosiński"));
    }

    @Test
    public void nextTest() {
        Response response = given()
                .baseUri("https://api.trello.com/1/")
                .queryParam("key", "a78b13c8f404e45e1ff845eb60f96002")
                .queryParam("token", "82a9641e694084a53d268ed530e87329058018dc530b65250432cd9e717f1896")
                .when()
                .get("members/me");
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals("Kuba Rosiński", response.getBody().jsonPath().get("fullName"));
    }


}
