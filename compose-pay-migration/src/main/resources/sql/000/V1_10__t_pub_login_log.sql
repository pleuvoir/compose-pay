create table pub_login_log (
  id          VARCHAR(32) primary key,
  username    VARCHAR(30),
  agent       VARCHAR(255),
  ip          VARCHAR(40),
  login_date  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status      VARCHAR(2),
  remark      VARCHAR(255)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;