package callableStatementEx.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {
    private int m_seq;
    private String m_userid;
    private String m_pwd;
    private String m_email;
    private String m_hp;
    private java.sql.Date m_registdate;
    private int m_point;
}


