create table pub_user (
  id              VARCHAR(32) primary key,
  username        VARCHAR(30),
  password        VARCHAR(80),
  safe_password   VARCHAR(80),
  salt            VARCHAR(32),
  status          TINYINT(4),
  fail_count      INT(10) NOT NULL default 0,
  locked_time     datetime,
  create_by       VARCHAR(30),
  create_time     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  remark          VARCHAR(255)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;