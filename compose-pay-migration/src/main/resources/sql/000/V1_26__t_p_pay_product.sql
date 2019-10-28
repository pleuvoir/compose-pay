
CREATE TABLE p_pay_product(
  id VARCHAR(32) primary key,
  pay_type_code VARCHAR(4)  NOT NULL DEFAULT '',
  pay_way_code VARCHAR(3)  NOT NULL DEFAULT '',
  pay_product_code VARCHAR(4) NOT NULL DEFAULT '',
  name VARCHAR(32)  NOT NULL DEFAULT '',
  status VARCHAR(2)  NOT NULL DEFAULT '',
  remark VARCHAR(255)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

ALTER TABLE `p_pay_product`   
  ADD  INDEX `unique_pay_type_way_code_idx` (`pay_type_code`, `pay_way_code`);
