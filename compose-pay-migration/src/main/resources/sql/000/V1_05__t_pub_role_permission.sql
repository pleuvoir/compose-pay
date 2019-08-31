create table pub_role_permission (
  role_id       VARCHAR(32) not null,
  permission_id VARCHAR(32) not null
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;