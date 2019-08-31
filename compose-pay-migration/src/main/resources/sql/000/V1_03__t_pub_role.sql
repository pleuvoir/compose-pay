create table pub_role (
  id          VARCHAR(32) primary key,
  name        VARCHAR(30),
  is_tmp      VARCHAR(1),
  create_by   VARCHAR(30),
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  remark      VARCHAR(255)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;