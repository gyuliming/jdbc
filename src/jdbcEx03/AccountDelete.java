package jdbcEx03;

import Util.DBUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDelete {

    public static void main(String[] args) throws IOException {
        Connection connection = DBUtil.getConnection();
        String sql = "DELETE FROM accounts WHERE ano = ?";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            System.out.print("제거할 ano를 입력하세요 : ");
            String input = br.readLine();
            pstmt.setString(1, input);

            int rows = pstmt.executeUpdate();
            if (rows == 1) {
                System.out.println(rows + " rows deleted");
            } else {
                System.out.println("Cannot rows delete");
            }

        } catch (SQLException e) {
            System.out.println("에러 발생 : " + e.getMessage());
        }
    }
}
