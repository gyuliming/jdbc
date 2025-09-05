package jdbcEx03;

import Util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountInsert {

    public static void main(String[] args) {
        Connection connection = DBUtil.getConnection();

        String sql = "INSERT INTO accounts (ano, owner, balance) VALUES ('333-333-3333', '고길동', 30000)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int rows = pstmt.executeUpdate();
            System.out.println(rows + " rows Inserted");

        } catch (SQLException e) {
            System.out.println("에러 발생 : " + e.getMessage());
        }
    }
}
