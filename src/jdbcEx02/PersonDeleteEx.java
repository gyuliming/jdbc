package jdbcEx02;

// JDBC 를 이용해서 데이터 삭제를 하는 DELETE 문을 실행하는 방법
// DELETE FROM 테이블명; => 해당 테이블에 있는 모든 행을 제거
// DELETE FROM 테이블명 WHERE id = 식별값; => 해당 식별값의 행만 제거
// DELETE FROM person WHERE num = 1;// person 테이블의 pk num = 1 행 제거
// String sql = "DELETE FROM person WHERE num = ?";

import Util.DBUtil;
import jdbcEx01.vo.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDeleteEx {

    public static void main(String[] args) {
        Connection connection = DBUtil.getConnection();

        // SQL 문 작성
        String sql = "DELETE FROM person WHERE num = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, 1);
            int rows = pstmt.executeUpdate();
            System.out.println("삭제된 행의 수 : " + rows);

            String selectSql = "select id, name from person";
            ResultSet rs = pstmt.executeQuery(selectSql); // select 일경우는 executeQuery
            while(rs.next()) {
                jdbcEx01.vo.Person person = new Person();
                person.setId(rs.getInt(1));
                person.setName(rs.getString(2));
                System.out.println(person.getId() + " " + person.getName());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
