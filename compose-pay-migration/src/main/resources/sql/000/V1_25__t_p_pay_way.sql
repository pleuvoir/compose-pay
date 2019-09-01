
CREATE TABLE p_pay_way(
  id VARCHAR(32) primary key,
  pay_way_code VARCHAR(4),
  pay_way_name VARCHAR(32),
  remark VARCHAR(255)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

ALTER TABLE `p_pay_way`   
  ADD  UNIQUE INDEX `unique_pay_way_code_idx` (`pay_way_code`);
