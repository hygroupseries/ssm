-- Oracle XE 11g 图书模块建表脚本
-- 请以具有建表权限的用户登录后执行（建议使用独立的 SSM 用户，见 README.md）

-- 删除旧对象（如已存在）
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE book CASCADE CONSTRAINTS';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/
BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE seq_book_id';
EXCEPTION WHEN OTHERS THEN NULL;
END;
/

-- 创建图书表（5 个字段）
CREATE TABLE book (
    id           NUMBER(10)     NOT NULL,
    name         VARCHAR2(200)  NOT NULL,
    author       VARCHAR2(100),
    price        NUMBER(10, 2),
    publish_date DATE           DEFAULT SYSDATE,
    CONSTRAINT pk_book PRIMARY KEY (id)
);

-- 创建主键序列
CREATE SEQUENCE seq_book_id
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- 初始化测试数据
INSERT INTO book (id, name, author, price, publish_date)
VALUES (seq_book_id.NEXTVAL, 'Java程序设计', '张三', 59.90, TO_DATE('2022-01-01', 'YYYY-MM-DD'));

INSERT INTO book (id, name, author, price, publish_date)
VALUES (seq_book_id.NEXTVAL, '数据结构与算法', '李四', 49.00, TO_DATE('2021-06-15', 'YYYY-MM-DD'));

INSERT INTO book (id, name, author, price, publish_date)
VALUES (seq_book_id.NEXTVAL, '设计模式', '王五', 79.00, TO_DATE('2020-09-10', 'YYYY-MM-DD'));

INSERT INTO book (id, name, author, price, publish_date)
VALUES (seq_book_id.NEXTVAL, '编译原理', '赵六', 68.50, TO_DATE('2019-03-20', 'YYYY-MM-DD'));

COMMIT;
