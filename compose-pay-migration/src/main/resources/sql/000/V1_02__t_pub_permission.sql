create table pub_permission (
  id      VARCHAR(32) primary key,
  name    VARCHAR(50),
  code    VARCHAR(255),
  menu_id VARCHAR(32),
  remark  VARCHAR(255)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;