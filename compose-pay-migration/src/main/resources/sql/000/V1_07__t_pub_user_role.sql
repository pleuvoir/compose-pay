create table pub_user_role (
  role_id VARCHAR(32) not null,
  user_id VARCHAR(32) not null
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;