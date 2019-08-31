create table pub_operation_log (
  id                  VARCHAR(32) primary key,
  username            VARCHAR(30),
  menu_id             VARCHAR(32),
  permission_id       VARCHAR(30),
  controller          VARCHAR(200),
  method              VARCHAR(200),
  ip                  VARCHAR(40),
  status              VARCHAR(4),
  elapsed_time        decimal(10,2),
  create_time         datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  remark              mediumtext
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;