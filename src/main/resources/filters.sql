CREATE TABLE `filters` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pattern` varchar(100) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8
