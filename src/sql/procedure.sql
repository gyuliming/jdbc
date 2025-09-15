-- 샘플테이블 생성
create table CODE1(
                      CID int,
                      cName VARCHAR(50)
);

desc CODE1;

-- 데이터 백업할 때 많이 사용하는 방식
INSERT INTO CODE1(CID, cNAME)
select ifnull(max(CID), 0) + 1 as CID2, 'TEST' as cName2
FROM CODE1;

select * from CODE1;

truncate CODE1; -- 데이터만 삭제

-- 프로시저 생성 : P_INSERTCODES()
DROP PROCEDURE IF EXISTS P_INSERTCODES;
DELIMITER $$
CREATE PROCEDURE P_INSERTCODES(IN cData VARCHAR(255), IN cTname VARCHAR(255), OUT resultMsg VARCHAR(255))
BEGIN
    SET @strsql = CONCAT(
                  'INSERT INTO ', cTname, '(CID, cName)',
                  'SELECT COALESCE(MAX(CID), 0) + 1, ? FROM ', cTname
                  );

    -- 바인딩할 변수 설정
    SET @cData = cData;
    SET resultMsg = 'Insert Success';
prepare stmt FROM @strsql;
EXECUTE stmt USING @cData;
DEALLOCATE PREPARE stmt;
COMMIT;

END $$
DELIMITER ;

SET @result = '';
CALL P_INSERTCODES('프론트디자이너', 'CODE1', @result);
CALL P_INSERTCODES('개발자', 'CODE1', @result);
select @result;
select * from CODE1;

