CREATE TABLE t_mer_sign_fee(
  id VARCHAR(32) primary key,
  mid VARCHAR(15) NOT NULL,
  pay_type VARCHAR(4),
  pay_way VARCHAR(4),
  pay_product VARCHAR(4),
  rate decimal(10,2),
  must_amt decimal(10,2),
  hight_amt decimal(10,2),
  low_amt decimal(10,2),
  trans_hight_amt decimal(10,2),
  trans_low_amt decimal(10,2),
  fee_algorithm VARCHAR(2),
  refund_fee_flag VARCHAR(2),
  remark VARCHAR(255),
  day_hight_amt decimal(10,2)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

ALTER TABLE `t_mer_sign_fee`
  ADD  UNIQUE INDEX `unique_mer_mid_product__idx` (`mid`,`pay_type`,`pay_way`,'pay_product');
