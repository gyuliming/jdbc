CREATE TABLE TB_MEMBER (
                           m_seq INT AUTO_INCREMENT PRIMARY KEY,  -- 자동 증가 시퀀스
                           m_userid VARCHAR(20) NOT NULL,
                           m_pwd VARCHAR(20) NOT NULL,
                           m_email VARCHAR(50) NULL,
                           m_hp VARCHAR(20) NULL,
                           m_registdate DATETIME DEFAULT NOW(),  -- 기본값: 현재 날짜와 시간
                           m_point INT DEFAULT 0
);

desc TB_MEMBER;

-- 반드시  중복 사용자 예외 처리  (이미 존재하는 사용자 검사 시행)
-- 만약 성공적이면  숫자 200 리턴 , 이미 가입된 회원이라면 숫자 100 리턴
DROP PROCEDURE IF EXISTS SP_MEMBER_INSERT;
DELIMITER $$
CREATE PROCEDURE SP_MEMBER_INSERT(
    IN V_USERID VARCHAR(20),
    IN V_PWD VARCHAR(20),
    IN V_EMAIL VARCHAR(50),
    IN V_HP VARCHAR(20),
    OUT RTN_CODE INT
)
BEGIN
    DECLARE v_count int;

SELECT COUNT(m_seq) into v_count FROM TB_MEMBER WHERE M_USERID = V_USERID;

-- 중복 확인
IF v_count > 0 THEN
        SET RTN_CODE = 100;
ELSE
        INSERT INTO TB_MEMBER (M_USERID, M_pwd, M_email, M_hp)
            VALUES(V_USERID, V_PWD, V_EMAIL, V_HP);
        SET RTN_CODE = 200;
END IF;
COMMIT;
end $$
DELIMITER ;

CALL SP_MEMBER_INSERT('apple', '1111', 'apple@sample.com', '010-8888-9999', @result);
SELECT @result;

SELECT * FROM TB_MEMBER;
truncate TB_MEMBER;
SHOW CREATE PROCEDURE SP_MEMBER_INSERT;

-- SP_MEMBER_LIST() 프로시저를 생성   :  전체 회원들의 정보를 출력하는 기능입니다.
-- MemberList 클래스에서 callableStatement 방식으로 회원들의 리스트를 출력하는 기능 구현하세요
use bookmarketdb;

-- 회원전체리스트 확인
drop procedure if exists SP_MEMBER_LIST;
delimiter $$
create procedure SP_MEMBER_LIST()
begin
select * from TB_MEMBER;
end $$
delimiter ;

call SP_MEMBER_LIST();

-- 회원 m_userid 로 회원 정보 확인
drop procedure if exists SP_MEMBER_LIST_ONE;
delimiter $$
create procedure SP_MEMBER_LIST_ONE(IN id VARCHAR(20))
begin
select * from TB_MEMBER WHERE m_userid = id;
end $$
delimiter ;

call SP_MEMBER_LIST_ONE('user');

-- 회원 수정 ( 비밀번호)를 수정할지  이메일을 수정할지  연락처를 수정할지를 선택해서 다중분기로 처리하기
drop procedure if exists SP_MEMBER_UPDATE;
delimiter $$
create procedure SP_MEMBER_UPDATE(IN userid VARCHAR(20), IN str1 VARCHAR(20), IN str2 VARCHAR(20))
begin
case
        when str1 = 'm_pwd' then set @sqlQuery = concat('update tb_member set ', str1, ' = ', str2 , 'where m_userid = ', userid);
when str1 = 'm_email' then set @sqlQuery = 'm_email';
when str1 = 'm_hp' then set @sqlQuery = 'm_hp';
else rollback;
end case;

update tb_member set @check = str2 where m_userid = userid;

end $$
delimiter ;

call SP_MEMBER_UPDATE()