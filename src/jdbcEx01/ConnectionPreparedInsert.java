package jdbcEx01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ConnectionPreparedInsert {

    public static void main(String[] args) {
        String driverName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul";
        String user = "root";
        String password = "mysql1234";

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
            String sql = "INSERT INTO person(id, name) values(?, ?)";

            // PreparedStatement 얻기
            PreparedStatement pstmt = con.prepareStatement(sql);

            // 값 지정
            pstmt.setInt(1, 100); // 1번째 ?에 100이 할당
            pstmt.setString(2, "신길동"); // 2번째 ?에 "신길동" 할당

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
