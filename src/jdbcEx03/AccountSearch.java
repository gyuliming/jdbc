package jdbcEx03;

import Util.DBUtil;
import jdbcEx03.vo.Accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountSearch {

    public static void main(String[] args) {
        Connection connection = DBUtil.getConnection();
        String selectSql = "select ano, owner, balance from accounts";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSql)) {
            ResultSet rs = pstmt.executeQuery(selectSql); // select 일경우는 executeQuery
            while(rs.next()) {
                jdbcEx03.vo.Accounts accounts = new Accounts();
                accounts.setAno(rs.getString(1));
                accounts.setOwner(rs.getString(2));
                accounts.setBalance(rs.getInt(3));
                System.out.println(accounts.getAno() + " " + accounts.getOwner() + " " + accounts.getBalance());
            }
        } catch (SQLException e) {
            System.out.println("에러 발생 : " + e.getMessage());
        }



    }

}
