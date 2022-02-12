DROP TABLE IF EXISTS `reviews`;
DROP TABLE IF EXISTS `reservation`;
DROP TABLE IF EXISTS `issuance`;
DROP TABLE IF EXISTS `instances`;
DROP TABLE IF EXISTS `books_genres`;
DROP TABLE IF EXISTS `books_authors`;
DROP TABLE IF EXISTS `books`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `config`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `halls`;
DROP TABLE IF EXISTS `types`;
DROP TABLE IF EXISTS `publishers`;
DROP TABLE IF EXISTS `genres`;
DROP TABLE IF EXISTS `authors`;

--
-- Table structure for table `authors`
--
CREATE TABLE `authors` (
  `id` int NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `father_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_authors_last_name_first_name` (`last_name`,`first_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `genres`
--
CREATE TABLE `genres` (
  `id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `publishers`
--
CREATE TABLE `publishers` (
  `id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `city` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `types`
--
CREATE TABLE `types` (
  `id` int NOT NULL,
  `name` varchar(25) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `halls`
--
CREATE TABLE `halls` (
  `id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `short_name` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `roles`
--
CREATE TABLE `roles` (
  `id` int NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `config`
--
CREATE TABLE `config` (
  `param_name` varchar(25) NOT NULL,
  `param_value` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `users`
--
CREATE TABLE `users` (
  `id` int NOT NULL,
  `roles_id` int NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `password` varchar(60) NOT NULL,
  `email` varchar(45) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `father_name` varchar(30) DEFAULT NULL,
  `birthday` date NOT NULL,
  `gender` varchar(1) NOT NULL,
  `passport` varchar(20) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `image` varchar(100) NOT NULL,
  `lock` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_users_roles1_idx` (`roles_id`),
  CONSTRAINT `fk_users_roles1` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `books`
--
CREATE TABLE `books` (
  `id` int NOT NULL,
  `name` varchar(100) NOT NULL,
  `publishers_id` int NOT NULL,
  `types_id` int NOT NULL,
  `publication_year` int NOT NULL,
  `pages` int NOT NULL,
  `part` int DEFAULT NULL,
  `isbn` varchar(17) DEFAULT NULL,
  `issn` varchar(9) DEFAULT NULL,
  `annotation` varchar(1500) DEFAULT NULL,
  `price` decimal(7,2) NOT NULL,
  `image` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_books_isbn_issn` (`isbn`,`issn`),
  KEY `fk_books_types1_idx` (`types_id`),
  KEY `fk_books_publishers1_idx` (`publishers_id`),
  CONSTRAINT `fk_books_publishers1` FOREIGN KEY (`publishers_id`) REFERENCES `publishers` (`id`),
  CONSTRAINT `fk_books_types1` FOREIGN KEY (`types_id`) REFERENCES `types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `books_authors`
--
CREATE TABLE `books_authors` (
  `books_id` int NOT NULL,
  `authors_id` int NOT NULL,
  PRIMARY KEY (`books_id`,`authors_id`),
  KEY `fk_books_has_authors_authors1_idx` (`authors_id`),
  KEY `fk_books_has_authors_books1_idx` (`books_id`),
  CONSTRAINT `fk_books_has_authors_authors1` FOREIGN KEY (`authors_id`) REFERENCES `authors` (`id`),
  CONSTRAINT `fk_books_has_authors_books1` FOREIGN KEY (`books_id`) REFERENCES `books` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `books_genres`
--
CREATE TABLE `books_genres` (
  `books_id` int NOT NULL,
  `genres_id` int NOT NULL,
  PRIMARY KEY (`books_id`,`genres_id`),
  KEY `fk_books_has_genres_genres1_idx` (`genres_id`),
  KEY `fk_books_has_genres_books1_idx` (`books_id`),
  CONSTRAINT `fk_books_has_genres_books1` FOREIGN KEY (`books_id`) REFERENCES `books` (`id`),
  CONSTRAINT `fk_books_has_genres_genres1` FOREIGN KEY (`genres_id`) REFERENCES `genres` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `instances`
--
CREATE TABLE `instances` (
  `id` int NOT NULL,
  `books_id` int NOT NULL,
  `number` varchar(10) NOT NULL,
  `halls_id` int NOT NULL,
  `date_received` date NOT NULL,
  `date_write_off` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `number_UNIQUE` (`number`),
  KEY `fk_instances_books1_idx` (`books_id`),
  KEY `fk_instances_halls1_idx` (`halls_id`),
  CONSTRAINT `fk_instances_books1` FOREIGN KEY (`books_id`) REFERENCES `books` (`id`),
  CONSTRAINT `fk_instances_halls1` FOREIGN KEY (`halls_id`) REFERENCES `halls` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `issuance`
--
CREATE TABLE `issuance` (
  `id` int NOT NULL,
  `instances_id` int NOT NULL,
  `reader_id` int NOT NULL,
  `date_issue` date NOT NULL,
  `date_return` date DEFAULT NULL,
  `date_return_planned` date NOT NULL,
  `lost` tinyint(1) DEFAULT NULL,
  `price` decimal(7,2) DEFAULT NULL,
  `rental_price` decimal(7,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_books_issuing_instances1_idx` (`instances_id`),
  KEY `fk_books_issuing_users1_idx` (`reader_id`),
  CONSTRAINT `fk_books_issuing_instances1` FOREIGN KEY (`instances_id`) REFERENCES `instances` (`id`),
  CONSTRAINT `fk_books_issuing_users1` FOREIGN KEY (`reader_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `reservation`
--
CREATE TABLE `reservation` (
  `id` int NOT NULL,
  `instances_id` int NOT NULL,
  `reader_id` int NOT NULL,
  `date` date NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_instances_has_books_booking_instances1_idx` (`instances_id`),
  KEY `fk_instances_reservation_users1_idx` (`reader_id`),
  CONSTRAINT `fk_instances_has_books_booking_instances1` FOREIGN KEY (`instances_id`) REFERENCES `instances` (`id`),
  CONSTRAINT `fk_instances_reservation_users1` FOREIGN KEY (`reader_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `reviews`
--
CREATE TABLE `reviews` (
  `id` int NOT NULL,
  `books_id` int NOT NULL,
  `reader_id` int NOT NULL,
  `rating` int NOT NULL,
  `comment` varchar(1000) DEFAULT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_books_ratings_books1_idx` (`books_id`),
  KEY `fk_books_ratings_users1_idx` (`reader_id`),
  CONSTRAINT `fk_books_ratings_books1` FOREIGN KEY (`books_id`) REFERENCES `books` (`id`),
  CONSTRAINT `fk_books_ratings_users1` FOREIGN KEY (`reader_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
