package it.cantest.CRUD;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;

public class CRUD2 {
    private static String baseURL = "https://api.trello.com/1/";
    private static String key = "a78b13c8f404e45e1ff845eb60f96002";
    private static String token = "82a9641e694084a53d268ed530e87329058018dc530b65250432cd9e717f1896";
    private String boardsURL = "boards/";
    private String idTablicy;

    @BeforeAll
    public static void setUP() {
        baseURI = baseURL;
        RestAssured.requestSpecification = given()
                .queryParam("key", key)
                .queryParam("token", token)
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON);
    }

    @BeforeEach
    public void createTable(){
        Response response = given()
                .queryParam("name","niewazne jaka")
                .post(this.boardsURL);
        this.idTablicy = response.body().jsonPath().get("id");
        Assertions.assertEquals(HttpStatus.SC_OK,response.statusCode());
    }

    @AfterEach
    public void deleteTable(){
        Response response = delete(this.boardsURL+this.idTablicy);
        Assertions.assertEquals(HttpStatus.SC_OK,response.statusCode());
        //czy tu dodac, ze zwraca 404?
    }

    //test na zmiane desc i sprawdzenie tej zmiany

    @Test
    public void shouldChangeTableDesc(){
        //given
        String newDesc = "Umpa umpa";
        String newBody = "{\"desc\":\""+newDesc+"\"}";
        Response response = given()
                .body(newBody)
                .when()
                .put(this.boardsURL+this.idTablicy);
        Assertions.assertEquals(HttpStatus.SC_OK,response.statusCode());
        response = get(this.boardsURL+this.idTablicy);
        //response.prettyPrint();
        response.then().log().all();
        Assertions.assertEquals(newDesc,response.getBody().jsonPath().get("desc"));
    }


}
