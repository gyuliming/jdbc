package jdbc_boards.model;

import Util.DBUtil;
import jdbc_boards.vo.Board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    private Connection conn;
    List<Board> boardList = new ArrayList<>();
//    private final Connection conn;
//
//    public BoardDAO(Connection conn) {
//        this.conn = DBUtil.getConnection();
//    }

    // 글 등록 기능
    public boolean createBoard(Board board) {
        // 1. Connection 필요
        conn = DBUtil.getConnection();

        // 2. 쿼리 생성
        String sql = "INSERT INTO boardtable(btitle, bcontent, bwriter, bdate) VALUES (?, ?, ?, now())";

        // 3. Connection 쿼리를 담아 DB 서버로 request 할 객체 PrepareStatement 생성
        try(PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // 4. 값 셋팅
            pstmt.setString(1, board.getBtitle());
            pstmt.setString(2, board.getBcontent());
            pstmt.setString(3, board.getBwriter());

            // 5. 서버로 전송 후 결과값 (int 성공 1  실패 0)
            int affected = pstmt.executeUpdate();
            boolean result = affected > 0;

            // 생성된 PK를 Board 객체에 반영
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int newBno = rs.getInt(1);
                    board.setBno(newBno);
                    boardList.add(board);
                }
            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // 전체 검색(최신순 조회)
    public List<Board> selectAll() {
        conn = DBUtil.getConnection();
        String sql = "SELECT * FROM boardtable ORDER BY bno DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Board board = new Board();
                board.setBno(rs.getInt(1));
                board.setBtitle(rs.getString(2));
                board.setBcontent(rs.getString(3));
                board.setBwriter(rs.getString(4));
                board.setBdate(rs.getDate(5));

                boardList.add(board);
            }
            return boardList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 1개 검색
    public Board selectOne(int bno) {
        conn = DBUtil.getConnection();
        String sql = "SELECT * FROM boardtable WHERE bno = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bno);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Board board = new Board();
                    board.setBno(rs.getInt(1));
                    board.setBtitle(rs.getString(2));
                    board.setBcontent(rs.getString(3));
                    board.setBwriter(rs.getString(4));
                    board.setBdate(rs.getDate(5));
                    return board;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // UPDATE
    public boolean updateBoard(Board board) {
        conn = DBUtil.getConnection();
        String sql = "UPDATE boardtable SET btitle = ?, bcontent = ? WHERE bno = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, board.getBtitle());
            pstmt.setString(2, board.getBcontent());
            pstmt.setInt(3, board.getBno());

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE
    public boolean deleteBoard(int bno) {
        conn = DBUtil.getConnection();
        String sql = "DELETE FROM boardtable WHERE bno = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bno);

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
