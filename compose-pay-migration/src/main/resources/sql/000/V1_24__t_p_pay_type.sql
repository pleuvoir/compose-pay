CREATE TABLE p_pay_type(
  id VARCHAR(32) primary key,
  pay_type_code VARCHAR(4),
  pay_type_name VARCHAR(32),
  remark VARCHAR(255)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

ALTER TABLE `p_pay_type`   
  ADD  UNIQUE INDEX `unique_pay_type_code_idx` (`pay_type_code`);
