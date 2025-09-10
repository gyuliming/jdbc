package jdbc_boards.Controller;

import jdbc_boards.model.BoardDAO;
import jdbc_boards.vo.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class BoardMenu {
    BoardDAO dao;
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    List<Board> boardList;

    public BoardMenu() {
        dao = new BoardDAO();
    }

    public void boardMenu() throws IOException {
        System.out.println("메인 메뉴: 1.Create | 2.ReadAll | 3.ReadOne | 4.Update | 5.Delete | 0.Exit");
        System.out.println("메뉴 선택:");
        int choice = 0;
        try{
            choice = Integer.parseInt(input.readLine());
        }catch (IOException e){
            System.out.println("입력도중 에러 발생");
        }catch (NumberFormatException e1){
            System.out.println("숫자만 입력하라니까");
        }catch (Exception e2){
            System.out.println("꿰엑 에라 모르겠다.");
        }
        switch (choice){
            // 글 생성
            case 1:
                //사용자에게 title,content를 입력받아서 Board 구성하여 createBoard()넘겨주자
                Board row = boardDataInput();
                boolean ack = dao.createBoard(row);
                if(ack == true) System.out.println("글이 성공적으로 입력되었습니다.");
                else {
                    System.out.println("입력 실패, 다시 시도 부탁드립니다. ");
                    //원하는 위치로 이동
                }
                break;

            // 전체 글 읽어오기
            case 2:
                boardList = dao.selectAll();
                if (boardList.isEmpty()) System.out.println("불러올 글이 존재하지 않습니다.");
                else {
                    Iterator<Board> iterator = boardList.iterator();
                    while (iterator.hasNext()) {
                        System.out.println(iterator.next() + " ");
                    }
                }
                break;

            // 글 번호를 지정하여 읽어오기
            case 3:
                int searchBno = selectBnoNum();
                Board selectBoard = dao.selectOne(searchBno);
                System.out.println(selectBoard);
                break;

            // 글 수정
            case 4:
                Board updateRow = boardDataUpdate();
                boolean updateResult = dao.updateBoard(updateRow);
                if(updateResult == true) System.out.println("글이 성공적으로 수정되었습니다.");
                else {
                    System.out.println("수정 실패, 다시 시도 부탁드립니다. ");
                }
                break;

                // 글 삭제
            case 5:
                int deleteRow = selectBnoNum();
                boolean deleteResult = dao.deleteBoard(deleteRow);
                if (deleteResult) System.out.println("삭제 완료");
                else System.out.println("삭제 실패");
                break;

            // 종료
            case 0:
                System.out.println("종료합니다.");
                return;

            default:
                System.out.println("올바른 숫자를 입력해주세요.");
                break;
        }
        boardMenu();

    }

    public Board boardDataInput() throws IOException{
        Board board = new Board();
        System.out.println("새로운 글 입력");
        System.out.println("제목 입력");
        String title =input.readLine();
        board.setBtitle(title);
        System.out.println("내용 입력");
        String content = input.readLine();
        board.setBcontent(content);
        System.out.println("작성자 입력");
        String writer = input.readLine();
        board.setBwriter(writer);
        return board;
    }

    public int selectBnoNum() throws IOException {
        System.out.println("bno 값");
        int bno = Integer.parseInt(input.readLine());
        return bno;
    }

    public Board boardDataUpdate() throws IOException {
        Board board = new Board();
        System.out.println("수정할 글 입력");
        System.out.println("수정할 bno");
        int bno = Integer.parseInt(input.readLine());
        board.setBno(bno);
        System.out.println("제목 입력");
        String title = input.readLine();
        board.setBtitle(title);
        System.out.println("내용 입력");
        String content = input.readLine();
        board.setBcontent(content);
        return board;
    }
}
