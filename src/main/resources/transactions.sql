CREATE TABLE `transactions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `amount` decimal(10,0) NOT NULL,
  `payee` varchar(100) NOT NULL,
  `particulars` varchar(100) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `reference` varchar(100) DEFAULT NULL,
  `transaction_type` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=461 DEFAULT CHARSET=utf8
