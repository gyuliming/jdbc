package jdbcEx01;

import java.io.FileInputStream;
import java.sql.*;

public class BoardInsertTest {

    public static void main(String[] args) {
        String driverName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul";
        String user = "root";
        String password = "Songkl123!";

        try {
            Class.forName(driverName);
            System.out.println("Driver loaded successfully");
        } catch (Exception e){
            System.out.println("Driver loaded failed");
        }

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("AutoCommit 상태: " + con.getAutoCommit());
            con.setAutoCommit(true);

            // 매개 변수화 된 SQL 문 (auto increment 생략)
            String sql = "INSERT INTO boards(btitle, bcontent, bwriter, bdate, bfilename, bfiledata)" +
                    " values (?, ?, ?, now(), ?, ?)";

            // PreparedStatement 얻기, RETURN_GENERATED_KEYS : auto increment로 인해 자동으로 생성된 키 가져옴
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // 값 지정
            pstmt.setString(1, "봄");
            pstmt.setString(2, "꽃이 피니 행복하다");
            pstmt.setString(3, "신세계");
            pstmt.setString(4, "spring.jpg"); // bfilename
            pstmt.setBlob(5, new FileInputStream("C:/Temp/spring.jpg")); // blob : 대용량 첨부데이터 타입

            // SQL 문 실행
            int result = pstmt.executeUpdate();
            System.out.println("저장된 행의 수 : " + result);

            int bno = -1;
            // bno 값 얻기
            if (result == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()) {
                    bno = rs.getInt(1);
                    System.out.println("bno = " + bno);
                }
            }

            if (bno != -1) {
                String selectsql = "SELECT bno, btitle, bcontent, bwriter, bdate, bfilename " +
                        "FROM boards WHERE bno = ?";
                try (PreparedStatement selectpstmt = con.prepareStatement(selectsql)) {
                    selectpstmt.setInt(1, bno);
                    try (ResultSet rs = selectpstmt.executeQuery()) {
                        while (rs.next()) {
                            bno = rs.getInt(1);
                            System.out.println("bno = " + bno);
                            System.out.println("btitle = " + rs.getString(2));
                            System.out.println("bcontent = " + rs.getString(3));
                            System.out.println("bwriter = " + rs.getString(4));
                            System.out.println("bdate = " + rs.getTimestamp(5));
                            System.out.println("bfilename = " + rs.getString(6));
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }



            if (result == 1) {
                System.out.println("Insert successful");
            } else {
                System.out.println("Insert failed");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
