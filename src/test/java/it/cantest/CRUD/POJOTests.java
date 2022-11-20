package it.cantest.CRUD;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import it.cantest.Helpers.TrelloBoardsWP;
import it.cantest.POJOs.Board;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;


public class POJOTests {
    private static String baseURL = "https://api.trello.com/1/";
    private static String key = "a78b13c8f404e45e1ff845eb60f96002";
    private static String token = "82a9641e694084a53d268ed530e87329058018dc530b65250432cd9e717f1896";
    private String boardsURL = "boards/";

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
    public void simpleTest() {
        Board board = given()
                .queryParam("name", "test POJO")
                .when()
                .post(this.boardsURL)
                .as(Board.class);
        System.out.println(board);
    }

    @Test
    public void betterCreationTest() {
        Board board = new Board("", "Nazwa z POJO", "descdesc desc");
        System.out.println(board);
        board = given()
                .body(board)
                .when()
                .post(this.boardsURL)
                .as(Board.class);
        System.out.println(board);
    }

    @Test
    public void shouldNpotCreateBoardWithEmptyName() {
        Board board = new Board("", "", "Description");
        System.out.println(board);
        TrelloBoardsWP trelloBoardsWP = new TrelloBoardsWP();
        board = trelloBoardsWP.createBoard(board);
        System.out.println(board);
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST, trelloBoardsWP.getLastStatusCode());
        Assertions.assertNull(board);
    }

    //stworzenie ok boarda, proba update'u nieudana, skasowanie
    @Test
    public void shouldNotUpdateBoardWithEmptyName() {
        Board board = new Board("", "Nazwa", "Description");
        System.out.println(board);
        TrelloBoardsWP trelloBoardsWP = new TrelloBoardsWP();
        board = trelloBoardsWP.createBoard(board);
        System.out.println(board);
        board.setName("");
        Board board2 = trelloBoardsWP.updateBoard(board);
        Assertions.assertEquals(HttpStatus.SC_BAD_REQUEST,trelloBoardsWP.getLastStatusCode());
        Assertions.assertNull(board2);
        trelloBoardsWP.deleteBoard(board);
        board = trelloBoardsWP.getBoard(board.getId());
        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND,trelloBoardsWP.getLastStatusCode());
        Assertions.assertNull(board);
    }

    @BeforeEach
    public void emptyBoards(){
        TrelloBoardsWP trelloBoardsWP = new TrelloBoardsWP();
        List<Board> boards = trelloBoardsWP.getAllBoards();
        System.out.println(boards);
        for(Board b : boards){
            trelloBoardsWP.deleteBoard(b);
        }
    }

}

