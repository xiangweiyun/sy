CREATE TABLE USERS (
  id NUMBER(20) primary key,
  username VARCHAR2(32) DEFAULT NULL,
  password VARCHAR2(32)  DEFAULT NULL,
  create_time DATE DEFAULT NULL
);
COMMENT ON TABLE USERS
  is '用户表';
COMMENT ON COLUMN USERS.id
  is '主键';
COMMENT ON COLUMN USERS.username
  is '账号';
COMMENT ON COLUMN USERS.password
  is '密码';
COMMENT ON COLUMN USERS.create_time
  is '创建时间';

INSERT INTO USERS(id, username, password, create_time) VALUES(0, 'yudaoyuanma', 'password', sysdate);
commit;