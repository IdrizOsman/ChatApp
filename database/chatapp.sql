-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 30, 2025 at 08:30 PM
-- Server version: 9.1.0
-- PHP Version: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `chatapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `channel`
--

DROP TABLE IF EXISTS `channel`;
CREATE TABLE IF NOT EXISTS `channel` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `channel`
--

INSERT INTO `channel` (`id`, `name`) VALUES
(37, 'test234'),
(39, 'testchannel'),
(40, 'test3');

-- --------------------------------------------------------

--
-- Table structure for table `channels`
--

DROP TABLE IF EXISTS `channels`;
CREATE TABLE IF NOT EXISTS `channels` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `owner_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `owner_id` (`owner_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `channel_users`
--

DROP TABLE IF EXISTS `channel_users`;
CREATE TABLE IF NOT EXISTS `channel_users` (
  `channel_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  KEY `FKc40tx8vt4y74hblewb3oj81of` (`user_id`),
  KEY `FK5gwjfra6mfck7rgmmamb6cghu` (`channel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `channel_users`
--

INSERT INTO `channel_users` (`channel_id`, `user_id`) VALUES
(37, 1),
(37, 2),
(37, 3),
(39, 5),
(39, 6),
(40, 6),
(40, 5),
(40, 4);

-- --------------------------------------------------------

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
CREATE TABLE IF NOT EXISTS `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `description` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `script` varchar(1000) COLLATE utf8mb4_general_ci NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `flyway_schema_history`
--

INSERT INTO `flyway_schema_history` (`installed_rank`, `version`, `description`, `type`, `script`, `checksum`, `installed_by`, `installed_on`, `execution_time`, `success`) VALUES
(1, '1', '<< Flyway Baseline >>', 'BASELINE', '<< Flyway Baseline >>', NULL, 'chatuser', '2025-01-28 20:58:11', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `channelId` bigint DEFAULT NULL,
  `content` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `receiverId` bigint DEFAULT NULL,
  `senderId` bigint DEFAULT NULL,
  `channel_id` bigint DEFAULT NULL,
  `receiver_id` bigint DEFAULT NULL,
  `sender_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjh48ccg1cpjb9x77jdv5mbpqh` (`channel_id`),
  KEY `FK2hnqtmi68j7g8y1bwhxq4apd5` (`receiver_id`),
  KEY `FKcrnfc5k5lxdfurq3b97o52dnv` (`sender_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`id`, `channelId`, `content`, `receiverId`, `senderId`, `channel_id`, `receiver_id`, `sender_id`) VALUES
(1, NULL, 'Тестово съобщение', NULL, NULL, NULL, 3, 2),
(2, NULL, 'test', NULL, NULL, NULL, 3, 2),
(3, NULL, 'test', NULL, NULL, NULL, 3, 2),
(4, NULL, 'hey', NULL, NULL, NULL, 6, 1),
(5, NULL, 'hey', NULL, NULL, NULL, 6, 1),
(6, NULL, 'test', NULL, NULL, NULL, 6, 1);

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
CREATE TABLE IF NOT EXISTS `messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `author_id` bigint NOT NULL,
  `channel_id` bigint DEFAULT NULL,
  `recipient_id` bigint DEFAULT NULL,
  `timestamp` datetime DEFAULT CURRENT_TIMESTAMP,
  `sender_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `author_id` (`author_id`),
  KEY `FK3u3ckbhwq9se1cmopk2pq05b2` (`channel_id`),
  KEY `FKhdkwfnspwb3s60j27vpg0rpg6` (`recipient_id`),
  KEY `FK4ui4nnwntodh6wjvck53dbk9m` (`sender_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `username`) VALUES
(1, 'testuser@example.com', 'password123', 'testuser'),
(2, 'user1@example.com', 'user1password123', 'user1'),
(3, 'user2@example.com', 'user2password123', 'user2'),
(4, 'user3@example.com', 'user3password123', 'user3'),
(5, NULL, '$2a$10$rp7qgXQ9/deKogj7E8XMBOuHr.Pyox2OzNHYYn6X1VVxB4s/hLnT2', 'test1'),
(6, NULL, '$2a$10$TP1/PsMqXC5xiba.Qv/VzuNNqGYgw2Xu0vuDm3MIDKAB/4uOv3xZq', 'test2');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(191) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `email`, `password`) VALUES
(1, 'testuser', 'testuser@example.com', 'password123');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `channel_users`
--
ALTER TABLE `channel_users`
  ADD CONSTRAINT `FK5gwjfra6mfck7rgmmamb6cghu` FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`),
  ADD CONSTRAINT `FKc40tx8vt4y74hblewb3oj81of` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `FK2hnqtmi68j7g8y1bwhxq4apd5` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKcrnfc5k5lxdfurq3b97o52dnv` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKjh48ccg1cpjb9x77jdv5mbpqh` FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
