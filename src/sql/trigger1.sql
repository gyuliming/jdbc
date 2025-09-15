-- 실습테이블 : 제품 로그 테이블 => 제품이 추가될 때마다 로그 테이블에 정보를 남기는 트리거 작성
CREATE TABLE 제품(
                   제품번호 INT AUTO_INCREMENT PRIMARY KEY,
                   제품명 VARCHAR(100),
                   포장단위 INT DEFAULT 0,
                   단가 INT DEFAULT 0,
                   재고 INT DEFAULT 0
);

CREATE TABLE 제품로그 (
                      로그번호 INT AUTO_INCREMENT PRIMARY KEY,
                      처리 VARCHAR(10),
                      내용 VARCHAR(100),
                      처리일 TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DELIMITER $$
CREATE TRIGGER TRIGGER_제품추가로그
    AFTER INSERT ON 제품
    FOR EACH ROW
BEGIN
    INSERT INTO 제품로그(처리, 내용) VALUES ('INSERT', CONCAT('제품번호 : ', NEW.제품번호, '제품명 : ', NEW.제품명));
END $$
DELIMITER ;

INSERT INTO 제품(제품명, 단가, 재고) VALUES ('애플캔디', 2000, 10);
SELECT * FROM 제품 WHERE 제품번호 = 1;

SELECT * FROM 제품로그;

-- 제품 테이블에서 단가나 재고가 변경되면 변경된 사항을 제품로그 테이블에 저장하는 트리거 생성
DELIMITER $$
CREATE TRIGGER TRIGGER_제품변경로그
    AFTER UPDATE ON 제품
    FOR EACH ROW
BEGIN
    IF(NEW.단가 <> OLD.단가) THEN -- <> : NOT
        INSERT INTO 제품로그(처리, 내용) VALUES ('UPDATE', CONCAT('제품번호 : ', OLD.제품번호, '단가 : ', OLD.단가, ' -> ', NEW.단가));
END IF;

IF(NEW.재고 <> OLD.재고) THEN -- <> : NOT
        INSERT INTO 제품로그(처리, 내용) VALUES ('UPDATE', CONCAT('제품번호 : ', OLD.제품번호, '재고 : ', OLD.재고, ' -> ', NEW.재고));
END IF;
END $$
DELIMITER ;

UPDATE 제품 SET 단가 = 2500 WHERE 제품번호 = 1;

SELECT * FROM 제품;
SELECT * FROM 제품로그;

-- 제품 테이블에서 제품 정보를 삭제하면 삭제된 레코드의 정보를 제품 로그 테이블에 저장하는 트리거 생성
DELIMITER $$
CREATE TRIGGER TRIGGER_제품삭제로그
    AFTER DELETE ON 제품
    FOR EACH ROW
BEGIN
    INSERT INTO 제품로그(처리, 내용) VALUES ('DELETE', CONCAT('제품번호 : ', OLD.제품번호, '제품명 : ', OLD.제품명));
END $$
DELIMITER ;

DELETE FROM 제품 WHERE 제품번호 = 1;
SELECT * FROM 제품;
SELECT * FROM 제품로그;

-- 트리거에서는 이벤트가 발생한 값을 확인하기 위해서 사용되는 키워드 OLD, NEW
-- OLD : UPDATE, DELETE
-- NEW : UPDATE, INSERT