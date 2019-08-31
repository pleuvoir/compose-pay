CREATE TABLE pub_menu(
  id VARCHAR(32) primary key,
  has_child VARCHAR(1),
  icon VARCHAR(128),
  node VARCHAR(1),
  parent_id VARCHAR(32),
  path VARCHAR(100) ,
  sort decimal(10) NOT NULL DEFAULT 0,
  title VARCHAR(32) NOT NULL DEFAULT '',
  is_show VARCHAR(2),
  remark VARCHAR(255)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;
