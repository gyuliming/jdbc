package jdbcEx01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionEx {

    public static void main(String[] args) {
        Connection con = null;
        try {
            // 1. DB의 드라이버를 찾아서 로드, MySQL JDBC 드라이버 등록
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");

            // 2. 드라이버 로드가 OK 라면, 연결 Connection 객체 생성
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul", "root", "Songkl123!");
            System.out.println("Connection established" + con);

            // 3. Connection 객체가 생성되었다면, 쿼리문을 Statements 객체에 담아 DBMS 한테 전송

            // 4. 전송한 결과를 받아서 처리
        } catch (Exception e) {
            System.out.println("Driver loaded failed");
        } finally {
            // 5. 다 사용한 Statements 와 Connection 객체를 닫는다.
            if (con != null) {
                try {
                    con.close();
                    System.out.println("Connection closed");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
