package it.cantest.CRUD;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class CRUD1 {
    private static String baseURL = "https://api.trello.com/1/";
    private static String key = "a78b13c8f404e45e1ff845eb60f96002";
    private static String token = "82a9641e694084a53d268ed530e87329058018dc530b65250432cd9e717f1896";
    private String boardsURL = "boards/";
    private String idTablicy;
    private Response lastResponse;


    @BeforeAll
    public static void setUP() {
        baseURI = baseURL;
        RestAssured.requestSpecification = given()
                .queryParam("key", key)
                .queryParam("token", token)
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON);
    }

    private void createBoard(String name){
        this.lastResponse = given()
                .queryParam("name", name)
                .when()
                .post(this.boardsURL);
        this.idTablicy = this.lastResponse.body().jsonPath().get("id");
    }

    private void getBoard(String id){
        this.lastResponse = get(this.boardsURL+id);
    }

    private void updateBoard(String id, String body){
        this.lastResponse = given()
                .body(body)
                .when()
                .put(this.boardsURL+id);
    }

    private void deleteBoard(String id){
        this.lastResponse = delete(this.boardsURL+id);
    }

    @Test
    public void shouldImplementCRUD(){
        //given
        String nazwa1 = "Całkiem nowy name";
        String newDesc = "Tralalala";
        String newBody = "{\"desc\":\""+newDesc+"\"}";
        String nameField = "name";
        String descField = "desc";

        //when + then
        this.createBoard(nazwa1);
        Assertions.assertEquals(HttpStatus.SC_OK,this.lastResponse.getStatusCode());
        this.getBoard(this.idTablicy);
        Assertions.assertEquals(nazwa1,this.lastResponse.getBody().jsonPath().get(nameField));
        this.updateBoard(this.idTablicy,newBody);
        this.getBoard(this.idTablicy);
        Assertions.assertEquals(newDesc,this.lastResponse.getBody().jsonPath().get(descField));
        this.deleteBoard(this.idTablicy);
        this.getBoard(this.idTablicy);
        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND,this.lastResponse.getStatusCode());
    }


}
