package jdbcEx03;

import Util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountUpdate {

    public static void main(String[] args) {
        Connection connection = DBUtil.getConnection();
        String sql = new StringBuilder()
                .append("UPDATE accounts SET ")
                .append("ano = ?, ")
                .append("owner = ?, ")
                .append("balance = ? ")
                .append("WHERE ano = ?").toString();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "123-456-789");
            pstmt.setString(2, "감길동");
            pstmt.setInt(3, 500000);
            pstmt.setString(4, "333-333-3333");

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " rows updated");;

        } catch (SQLException e) {
            System.out.println("에러 발생 : " + e.getMessage());
        }

    }
}
