package jdbcEx01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectionTest {

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");
        } catch (Exception e){
            System.out.println("Driver loaded failed");
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul", "root", "mysql1234")) {
            System.out.println("AutoCommit 상태: " + con.getAutoCommit());
            con.setAutoCommit(true);
            Statement stmt = con.createStatement();

            int result = stmt.executeUpdate("insert into person(id, name) values (100, '홍길동')");
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
