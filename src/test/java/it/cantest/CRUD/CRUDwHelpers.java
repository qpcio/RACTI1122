package it.cantest.CRUD;

import it.cantest.Helpers.TrelloBoards;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CRUDwHelpers {
    TrelloBoards trelloBoards;

    @BeforeEach
    public void setUP() {
        this.trelloBoards = new TrelloBoards();
    }


    @Test
    public void CRUDwHelpersTest() {
        String name = "Zupełnie nowa tablica";
        this.trelloBoards.createBoard(name);
        String boardId = this.trelloBoards.getLastBoardId();
        this.trelloBoards.getBoard(boardId);
        Assertions.assertEquals(HttpStatus.SC_OK, this.trelloBoards.getLastStatusCode());
        Assertions.assertEquals(name,this.trelloBoards.getLastBoardName());
        String newDescription = "Trach";
        String newName = "Zmienione";
        this.trelloBoards.updateBoardDesc(boardId,newDescription);
        this.trelloBoards.updateBoardName(boardId,newName);
        this.trelloBoards.getBoard(boardId);
        Assertions.assertEquals(newDescription,this.trelloBoards.getLastBoardDesc());
        Assertions.assertEquals(newName,this.trelloBoards.getLastBoardName());
        this.trelloBoards.deleteBoard(boardId);
        this.trelloBoards.getBoard(boardId);
        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND,this.trelloBoards.getLastStatusCode());
    }

    @Test
    public void shouldCleanAllBoards(){
        this.trelloBoards.deleteAllBoards();
        this.trelloBoards.getAllBoards();
        System.out.println(this.trelloBoards.getAllboardsIDs());
    }
}
