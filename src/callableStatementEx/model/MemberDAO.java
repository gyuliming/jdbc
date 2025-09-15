package callableStatementEx.model;

import Util.DBUtil;
import callableStatementEx.vo.Member;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    private Connection conn;
    List<Member> memberList = new ArrayList<>();

    public void MemberInsert() {
        conn = DBUtil.getConnection();
        String sql = "{CALL SP_MEMBER_INSERT(?, ?, ?, ?, ?)}"; // IN 4개, OUT 1개 => ? 플레이스 홀더 5개

        try (CallableStatement call = conn.prepareCall(sql)) {
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
            try {
                conn.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
    }

    // 회원 전체 리스트 확인
    public List<Member> MemberList() {
        conn = DBUtil.getConnection();
        String sql = "{call SP_MEMBER_LIST()}";

        try (CallableStatement call = conn.prepareCall(sql)) {
            ResultSet rs = call.executeQuery(sql);
            while (rs.next()) {
                Member member = new Member();
                member.setM_seq(rs.getInt(1));
                member.setM_userid(rs.getString(2));
                member.setM_pwd(rs.getString(3));
                member.setM_email(rs.getString(4));
                member.setM_hp(rs.getString(5));
                member.setM_registdate(rs.getDate(6));
                member.setM_point(rs.getInt(7));

                memberList.add(member);
            }
            return memberList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 회원 m_userid 로 회원 정보 확인
    public Member MemberListOne() {
        conn = DBUtil.getConnection();
        String sql = "{call SP_MEMBER_LIST_ONE()}";

        try(CallableStatement call = conn.prepareCall(sql)) {
            call.setString(2, m_userid);
            try (ResultSet rs = call.executeQuery(sql)) {
                if (rs.next()) {
                    Member member = new Member();
                    member.setM_seq(rs.getInt(1));
                    member.setM_userid(rs.getString(2));
                    member.setM_pwd(rs.getString(3));
                    member.setM_email(rs.getString(4));
                    member.setM_hp(rs.getString(5));
                    member.setM_registdate(rs.getDate(6));
                    member.setM_point(rs.getInt(7));

                    return member;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void MemberUpdate() {

    }
}
