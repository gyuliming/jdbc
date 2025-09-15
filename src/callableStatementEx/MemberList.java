package callableStatementEx;

import Util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MemberList {
    static Connection conn = DBUtil.getConnection();

    public static void main(String[] args) {
        String sql = "{call SP_MEMBER_LIST()}";

        try(CallableStatement call = conn.prepareCall(sql)) {
            ResultSet rs = call.executeQuery(sql);
            while (rs.next()) {
                int seq = rs.getInt(1);
                String userid = rs.getString(2);
                String pwd = rs.getString(3);
                String email = rs.getString(4);
                String hp = rs.getString(5);
                java.sql.Date registDate = rs.getDate(6);
                int point = rs.getInt(7);

                System.out.printf("%d | %s | %s | %s | %s | %s | %d\n",
                        seq, userid, pwd, email, hp, registDate, point);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
