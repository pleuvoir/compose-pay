create table pub_param (
  code        VARCHAR(30) primary key,
  name        VARCHAR(50),
  group_code  VARCHAR(30),
  decimal_val decimal(18,6) default 0,
  int_val     decimal(10) default 0,
  str_val     VARCHAR(255),
  boolean_val VARCHAR(10),
  type        VARCHAR(2),
  modify_flag VARCHAR(1),
  remark      VARCHAR(255)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;