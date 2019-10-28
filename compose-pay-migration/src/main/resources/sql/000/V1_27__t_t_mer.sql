CREATE TABLE t_mer(
  id VARCHAR(32) primary key,
  mid VARCHAR(15) NOT NULL,
  mer_name VARCHAR(80),
  encrypt_key VARCHAR(255),
  status smallint(5)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

ALTER TABLE `t_mer`
  ADD  UNIQUE INDEX `unique_mer_mid_idx` (`mid`);
