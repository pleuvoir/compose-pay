CREATE TABLE t_mer_ip(
  id VARCHAR(32) primary key,
  mid VARCHAR(15) NOT NULL,
  ip VARCHAR(15) NOT NULL,
  remark VARCHAR(255)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

ALTER TABLE `t_mer_ip`
  ADD  UNIQUE INDEX `unique_mer_mid__ip_idx` (`mid`,`ip`);
