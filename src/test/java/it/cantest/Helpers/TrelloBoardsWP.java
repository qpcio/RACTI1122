package it.cantest.Helpers;

import it.cantest.POJOs.Board;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class TrelloBoardsWP extends TrelloBase {
    private String boardsURL = "boards/";
    private String meURL = "members/me/";
    private Board board;

    public Board getBoard(String id) {
        super.lastRespose = get(this.boardsURL + id);
        if (super.lastRespose.statusCode() == 200) {
            this.board = lastRespose.as(Board.class);
            return this.board;
        }
        this.board = null;
        return null;
    }

    public Board createBoard(Board board) {
        super.lastRespose = given()
                .body(board)
                .when()
                .post(this.boardsURL);
        if (super.lastRespose.statusCode() == 200) {
            this.board = super.lastRespose.as(Board.class);
            return this.board;
        }
        this.board = null;
        return null;
    }

    public Board updateBoard(Board board) {
        super.lastRespose = given()
                .body(board)
                .when()
                .put(this.boardsURL + board.getId());
        if (super.lastRespose.statusCode() == 200) {
            this.board = super.lastRespose.as(Board.class);
            return this.board;
        }
        this.board = null;
        return null;
    }

    public void deleteBoard(Board board) {
        super.lastRespose = delete(this.boardsURL + board.getId());
    }

    public List<Board> getAllBoards(){
        super.lastRespose = get(this.meURL+this.boardsURL);
        return Arrays.stream(super.lastRespose.as(Board[].class)).toList();
    }


}
