package jdbc_boards.view;

import jdbc_boards.Controller.BoardMenu;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        BoardMenu boardMenu = new BoardMenu(); // BoardMenu와 연결(강한 결합)
        boardMenu.boardMenu();
    }
}
