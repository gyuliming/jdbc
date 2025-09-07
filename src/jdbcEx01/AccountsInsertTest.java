package jdbcEx01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountsInsertTest {
    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul";
        String username = "root";
        String password = "mysql1234";

        try {
            Class.forName(driver);
            System.out.println("Driver loaded");
        } catch (Exception e) {
            System.out.println("Driver loaded failed");
        }

        try (Connection con = DriverManager.getConnection(url, username, password)) {
            System.out.println("Autocommit 상태 : " + con.getAutoCommit());
            con.setAutoCommit(true);

            String sql = "insert into accounts(ano, owner, balance) values(?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, "555-555-5555");
            pstmt.setString(2, "신길동");
            pstmt.setInt(3, 1500000);

            int result = pstmt.executeUpdate();
            System.out.println("저장된 행의 수 : " + result);

            if (result != -1) {
                String selectsql = "select ano, owner, balance from accounts where ano = ?";

                try (PreparedStatement selectpstmt = con.prepareStatement(selectsql)){
                    selectpstmt.setString(1, "555-555-5555");
                    try(ResultSet rs = selectpstmt.executeQuery()) {
                        while (rs.next()) {
                            System.out.println("계좌번호 : " + rs.getString(1));
                            System.out.println("계좌주 : " +  rs.getString(2));
                            System.out.println("계좌총액 : " +  rs.getInt(3));

                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }
}