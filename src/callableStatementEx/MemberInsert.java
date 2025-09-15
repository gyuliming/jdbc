package callableStatementEx;

import Util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class MemberInsert {
    static Connection conn = DBUtil.getConnection();

    public static void main(String[] args) {
        String m_userid = "user";
        String m_pwd = "userpwd";
        String m_email = "user@gmail.com";
        String m_hp = "010-0000-1111";

        String sql = "{CALL SP_MEMBER_INSERT(?, ?, ?, ?, ?)}"; // IN 4개, OUT 1개 => ? 플레이스 홀더 5개

        try(CallableStatement call = conn.prepareCall(sql)) {
            conn.setAutoCommit(false);

            // IN 파라미터 세팅
            call.setString(1, m_userid);
            call.setString(2, m_pwd);
            call.setString(3, m_email);
            call.setString(4, m_hp);

            // OUT 파라미터 세팅
            call.registerOutParameter(5, java.sql.Types.INTEGER);

            //실행
            call.execute();

            int rtn = call.getInt(5);
            if (rtn == 100) {
                conn.rollback();
                System.out.println("이미 가입된 사용자입니다.");
            } else {
                conn.commit();
                System.out.println("회원 가입이 완료되었습니다.");
            }
        } catch (SQLException e) {
            try{ conn.rollback(); } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
    }
}
