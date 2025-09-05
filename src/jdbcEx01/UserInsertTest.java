package jdbcEx01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UserInsertTest {

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

            // 매개 변수화 된 SQL 문
            String sql = "INSERT INTO users(userid, username, userpassword, userage, useremail)" +
                    " values (?, ?, ?, ?, ?)";

            // PreparedStatement 얻기
            PreparedStatement pstmt = con.prepareStatement(sql);

            // 값 지정
            pstmt.setString(1, "8");
            pstmt.setString(2, "손흥민");
            pstmt.setString(3, "12345");
            pstmt.setInt(4, 34);
            pstmt.setString(5, "son@gmail.com");

            // SQL 문 실행
            int result = pstmt.executeUpdate();
            System.out.println("저장된 행의 수 : " + result);

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
