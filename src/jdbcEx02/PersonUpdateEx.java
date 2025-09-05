package jdbcEx02;

// UPDATE 테이블명 SET (필드 = '수정값') WHERE num = 1;
// String sql = "UPDATE person SET id = ?, name = ? WHERE num = ?";
// String sql = new StringBuilder()
//              .append("UPDATE person ")
//              .append("SET id = ?, ")
//              .append("name = ? ")
//              .append("WHERE num = ?").toString();

import Util.DBUtil;
import jdbcEx01.vo.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonUpdateEx {

    public static void main(String[] args) {
        Connection connection = DBUtil.getConnection();

        // 매개 변수화 된 UPDATE SQL 문 작성
        String sql = new StringBuilder()
                .append("UPDATE person SET ")
                .append("id = ?, ")
                .append("name = ?")
                .append("WHERE num = ?").toString();

        // PreparedStatement 객체를 생성하고 값 지정
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, 150);
            pstmt.setString(2, "고길동");
            pstmt.setInt(3, 1);

            // SQL 문 실행
            int rows = pstmt.executeUpdate();
            System.out.println(rows + " rows updated");

            String selectSql = "select id, name from person";
            ResultSet rs = pstmt.executeQuery(selectSql);
            while(rs.next()) {
                jdbcEx01.vo.Person person = new Person();
                person.setId(rs.getInt(1));
                person.setName(rs.getString(2));
                System.out.println(person.getId() + " " + person.getName());
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
