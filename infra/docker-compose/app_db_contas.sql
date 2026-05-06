-- MySQL dump 10.13  Distrib 8.0.45, for macos15 (arm64)
--
-- Host: 127.0.0.1    Database: app_db
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `authorizer_group` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `email_group` varchar(255) NOT NULL,
  `identifier` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `onbording` bit(1) NOT NULL,
  `parameters` text,
  `request` varchar(255) NOT NULL,
  `update_at` datetime(6) NOT NULL,
  `type_accounts_id` bigint NOT NULL,
  `settings` text,
  `acronym` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKq8730aou96d750g511j8hqhag` (`identifier`),
  UNIQUE KEY `uk_account_name_active` (`name`,`deleted_at`),
  KEY `FKjkevkc21ooj6xc3mrgt8us7kn` (`type_accounts_id`),
  CONSTRAINT `FKjkevkc21ooj6xc3mrgt8us7kn` FOREIGN KEY (`type_accounts_id`) REFERENCES `type_accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `accounts_approvers`
--

DROP TABLE IF EXISTS `accounts_approvers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts_approvers` (
  `id` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `funcional` varchar(255) DEFAULT NULL,
  `account_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsrbjat6u1ikljwiixhp8jo4os` (`account_id`),
  CONSTRAINT `FKsrbjat6u1ikljwiixhp8jo4os` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `accounts_environment_publishers`
--

DROP TABLE IF EXISTS `accounts_environment_publishers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts_environment_publishers` (
  `account_id` bigint NOT NULL,
  `environment_id` bigint NOT NULL,
  `publisher_id` varchar(36) NOT NULL,
  UNIQUE KEY `UKc17isq9d4l1vccu78jp0p461p` (`publisher_id`),
  KEY `FK4o2g2dephr0ls9ar7sluui04b` (`account_id`,`environment_id`),
  CONSTRAINT `FK4o2g2dephr0ls9ar7sluui04b` FOREIGN KEY (`account_id`, `environment_id`) REFERENCES `accounts_environments` (`account_id`, `environment_id`),
  CONSTRAINT `FKqcdmodvsy5vfl9e0winl6r5wl` FOREIGN KEY (`publisher_id`) REFERENCES `publisher_configs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `accounts_environments`
--

DROP TABLE IF EXISTS `accounts_environments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts_environments` (
  `account_id` bigint NOT NULL,
  `environment_id` bigint NOT NULL,
  `settings` text,
  PRIMARY KEY (`account_id`,`environment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `accounts_onboarding_completions`
--

DROP TABLE IF EXISTS `accounts_onboarding_completions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts_onboarding_completions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_id` bigint NOT NULL,
  `completion_date` datetime(6) NOT NULL,
  `user_identifier` varchar(255) NOT NULL,
  `type_onboardings_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK35c5xerdww1t2eurdue45xuk7` (`type_onboardings_id`),
  CONSTRAINT `FK35c5xerdww1t2eurdue45xuk7` FOREIGN KEY (`type_onboardings_id`) REFERENCES `type_onboardings` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `accounts_tags`
--

DROP TABLE IF EXISTS `accounts_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts_tags` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `account_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKohgd7dfu2fkyi5kscutdop2ot` (`account_id`),
  CONSTRAINT `FKohgd7dfu2fkyi5kscutdop2ot` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `api_audit_events`
--

DROP TABLE IF EXISTS `api_audit_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `api_audit_events` (
  `cod_idef_event` varchar(36) NOT NULL,
  `cod_idef_ambi` varchar(255) NOT NULL,
  `cod_idef_enti` varchar(255) NOT NULL,
  `dat_hor_even` datetime(6) NOT NULL,
  `entity_event` varchar(255) NOT NULL,
  `metadata_event` text,
  `origem_event` varchar(255) NOT NULL,
  `product_event` varchar(255) NOT NULL,
  `type_event` varchar(255) NOT NULL,
  `user_event` varchar(255) NOT NULL,
  PRIMARY KEY (`cod_idef_event`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `applications`
--

DROP TABLE IF EXISTS `applications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `applications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) NOT NULL,
  `authorizer_group` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `identifier` binary(36) NOT NULL,
  `is_default` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parameter` text,
  `update_at` datetime(6) NOT NULL,
  `account_id` bigint DEFAULT NULL,
  `type_application_scopes_id` bigint DEFAULT NULL,
  `type_infrastructures_id` bigint DEFAULT NULL,
  `type_languages_id` bigint DEFAULT NULL,
  `acronym_application` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK62ohbnnylywgq6in4lnmf1r78` (`identifier`),
  UNIQUE KEY `uk_application_name_active_account` (`name`,`deleted_at`,`account_id`),
  KEY `FKdk4tg0wut29pnsgryfcy2mhet` (`account_id`),
  KEY `FKi98ygei9mkgoysyy6vp3e7urk` (`type_application_scopes_id`),
  KEY `FK3quaqnnt36nqb0xduaki61lg` (`type_infrastructures_id`),
  KEY `FKanuelxe2al0mg6i5q8ka2tjgq` (`type_languages_id`),
  CONSTRAINT `FK3quaqnnt36nqb0xduaki61lg` FOREIGN KEY (`type_infrastructures_id`) REFERENCES `type_infrastructures` (`id`),
  CONSTRAINT `FKanuelxe2al0mg6i5q8ka2tjgq` FOREIGN KEY (`type_languages_id`) REFERENCES `type_languages` (`id`),
  CONSTRAINT `FKdk4tg0wut29pnsgryfcy2mhet` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`),
  CONSTRAINT `FKi98ygei9mkgoysyy6vp3e7urk` FOREIGN KEY (`type_application_scopes_id`) REFERENCES `type_application_scopes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `applications_environment_publishers`
--

DROP TABLE IF EXISTS `applications_environment_publishers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `applications_environment_publishers` (
  `application_id` bigint NOT NULL,
  `environment_id` bigint NOT NULL,
  `publisher_id` varchar(36) NOT NULL,
  UNIQUE KEY `UKdfkvmoiwry6t4nuanqd10fgf5` (`publisher_id`),
  KEY `FKea8brwfsu9xmdw1tk611o3k2c` (`application_id`,`environment_id`),
  CONSTRAINT `FKea8brwfsu9xmdw1tk611o3k2c` FOREIGN KEY (`application_id`, `environment_id`) REFERENCES `applications_environments` (`application_id`, `environment_id`),
  CONSTRAINT `FKm5hcgk0v98bfm5cuylnyu07gt` FOREIGN KEY (`publisher_id`) REFERENCES `publisher_configs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `applications_environments`
--

DROP TABLE IF EXISTS `applications_environments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `applications_environments` (
  `application_id` bigint NOT NULL,
  `environment_id` bigint NOT NULL,
  `settings` text,
  PRIMARY KEY (`application_id`,`environment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `applications_tag`
--

DROP TABLE IF EXISTS `applications_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `applications_tag` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `application_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKerjaxt4i9jrigjhnpq6a444yc` (`application_id`),
  CONSTRAINT `FKerjaxt4i9jrigjhnpq6a444yc` FOREIGN KEY (`application_id`) REFERENCES `applications` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `environments`
--

DROP TABLE IF EXISTS `environments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `environments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `description` text NOT NULL,
  `name` varchar(100) NOT NULL,
  `sort_order` int NOT NULL,
  `account_id` bigint DEFAULT NULL,
  `type_authorizations_id` bigint NOT NULL,
  `type_environments_id` bigint NOT NULL,
  `authorizer_group` varchar(255) DEFAULT NULL,
  `settings` text,
  PRIMARY KEY (`id`),
  KEY `FK4h33ahj3vj339333rro16oub0` (`account_id`),
  KEY `FKaxg3627t2fw6ln5jg1re7hdnm` (`type_authorizations_id`),
  KEY `FK5uybivubf9yrcjp1nccutf8b4` (`type_environments_id`),
  CONSTRAINT `FK4h33ahj3vj339333rro16oub0` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`),
  CONSTRAINT `FK5uybivubf9yrcjp1nccutf8b4` FOREIGN KEY (`type_environments_id`) REFERENCES `type_environments` (`id`),
  CONSTRAINT `FKaxg3627t2fw6ln5jg1re7hdnm` FOREIGN KEY (`type_authorizations_id`) REFERENCES `type_authorizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kafka_cluster_property`
--

DROP TABLE IF EXISTS `kafka_cluster_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kafka_cluster_property` (
  `date_update` datetime(6) DEFAULT NULL,
  `environment` varchar(255) NOT NULL,
  `id` varchar(36) NOT NULL,
  `properties` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kafka_topic_config`
--

DROP TABLE IF EXISTS `kafka_topic_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kafka_topic_config` (
  `environment` varchar(255) NOT NULL,
  `id` varchar(36) NOT NULL,
  `parametros` text NOT NULL,
  `topic` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `local_buffer`
--

DROP TABLE IF EXISTS `local_buffer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `local_buffer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `environment` varchar(255) DEFAULT NULL,
  `kafka_key` varchar(255) DEFAULT NULL,
  `last_error` longtext,
  `kafka_message` text,
  `next_retry` datetime(6) DEFAULT NULL,
  `retry_count` int NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `topic` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_buffer_lookup` (`environment`,`status`,`next_retry`,`created_at`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `publisher_configs`
--

DROP TABLE IF EXISTS `publisher_configs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publisher_configs` (
  `id` varchar(36) NOT NULL,
  `order_index` int NOT NULL,
  `parameters` text,
  `publisher_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKolqd7h8rw12ohifemkv1xevnx` (`publisher_id`),
  CONSTRAINT `FKolqd7h8rw12ohifemkv1xevnx` FOREIGN KEY (`publisher_id`) REFERENCES `publishers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `publishers`
--

DROP TABLE IF EXISTS `publishers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publishers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `deprecated` bit(1) NOT NULL,
  `description` varchar(500) NOT NULL,
  `json_schema` text NOT NULL,
  `label` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `publisher_scope_type_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKan1ucpx8sw2qm194mlok8e5us` (`name`),
  KEY `FKt3mb83yqd5y7qbj2jhwtdjtw4` (`publisher_scope_type_id`),
  CONSTRAINT `FKt3mb83yqd5y7qbj2jhwtdjtw4` FOREIGN KEY (`publisher_scope_type_id`) REFERENCES `type_publisher_scopes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sharing_origins`
--

DROP TABLE IF EXISTS `sharing_origins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sharing_origins` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `application_id` bigint NOT NULL,
  `type_sharing_statuses_id` bigint NOT NULL,
  `sharing_targets_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf7dqtxl1wlk766l1nodk47as7` (`application_id`),
  KEY `FKgalu0yprni5x3xtkk2d3n02i7` (`type_sharing_statuses_id`),
  KEY `FK9p5y34peq221f8kswb3xo95qa` (`sharing_targets_id`),
  CONSTRAINT `FK9p5y34peq221f8kswb3xo95qa` FOREIGN KEY (`sharing_targets_id`) REFERENCES `sharing_targets` (`id`),
  CONSTRAINT `FKf7dqtxl1wlk766l1nodk47as7` FOREIGN KEY (`application_id`) REFERENCES `applications` (`id`),
  CONSTRAINT `FKgalu0yprni5x3xtkk2d3n02i7` FOREIGN KEY (`type_sharing_statuses_id`) REFERENCES `type_sharing_statuses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sharing_target_features`
--

DROP TABLE IF EXISTS `sharing_target_features`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sharing_target_features` (
  `sharing_targets_id` bigint NOT NULL,
  `type_features_id` bigint NOT NULL,
  KEY `FK76um1xsvg21k0gb344pcadwfk` (`type_features_id`),
  KEY `FK6a9nn20vpwe0nwc877v321hjf` (`sharing_targets_id`),
  CONSTRAINT `FK6a9nn20vpwe0nwc877v321hjf` FOREIGN KEY (`sharing_targets_id`) REFERENCES `sharing_targets` (`id`),
  CONSTRAINT `FK76um1xsvg21k0gb344pcadwfk` FOREIGN KEY (`type_features_id`) REFERENCES `type_features` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sharing_targets`
--

DROP TABLE IF EXISTS `sharing_targets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sharing_targets` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(500) NOT NULL,
  `identifier` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `application_id` bigint NOT NULL,
  `hash_features` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKremht23gx2ib6e1is02nvc46c` (`identifier`),
  UNIQUE KEY `uk_app_name` (`application_id`,`name`),
  UNIQUE KEY `uk_app_hash` (`application_id`,`hash_features`),
  KEY `idx_application` (`application_id`),
  CONSTRAINT `FKr9e7youug783huit0l1hiygrg` FOREIGN KEY (`application_id`) REFERENCES `applications` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_accounts`
--

DROP TABLE IF EXISTS `type_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_accounts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `description` text,
  `label` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sort_order` int NOT NULL,
  `settings` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKb0g925cp2d66lk487cqgo503f` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_application_scopes`
--

DROP TABLE IF EXISTS `type_application_scopes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_application_scopes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `description` text,
  `label` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sort_order` int NOT NULL,
  `settings` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK2t7riwxq8d6pfbad03j5goftt` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_authorizations`
--

DROP TABLE IF EXISTS `type_authorizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_authorizations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `description` text,
  `label` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sort_order` int NOT NULL,
  `settings` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKtn5lk17k58w76b5oq1sddc6k2` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_environments`
--

DROP TABLE IF EXISTS `type_environments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_environments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `description` text,
  `label` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sort_order` int NOT NULL,
  `settings` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKj0gublmoek13snlmt3di3pjre` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_feature_scopes`
--

DROP TABLE IF EXISTS `type_feature_scopes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_feature_scopes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `description` text,
  `label` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sort_order` int NOT NULL,
  `settings` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKg813qp09vwbb4rolorlnq8es9` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_features`
--

DROP TABLE IF EXISTS `type_features`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_features` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `description` text,
  `label` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sort_order` int NOT NULL,
  `available` bit(1) NOT NULL,
  `settings` text,
  `type_feature_scopes_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_scope_name` (`type_feature_scopes_id`,`name`),
  CONSTRAINT `FK6b7q30v1x0lofeetkmq2juv4i` FOREIGN KEY (`type_feature_scopes_id`) REFERENCES `type_feature_scopes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_infrastructures`
--

DROP TABLE IF EXISTS `type_infrastructures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_infrastructures` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `description` text,
  `label` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sort_order` int NOT NULL,
  `settings` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK4spt3o213lxc99ork9sw11pjo` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_languages`
--

DROP TABLE IF EXISTS `type_languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_languages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `description` text,
  `label` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sort_order` int NOT NULL,
  `settings` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKow09cbjlmw6vv77yctob9nfsb` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_onboardings`
--

DROP TABLE IF EXISTS `type_onboardings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_onboardings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `description` text,
  `label` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sort_order` int NOT NULL,
  `settings` text NOT NULL,
  `orientation` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKesf3nfn6m0eeg9ms8ucrdwawv` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_publisher_scopes`
--

DROP TABLE IF EXISTS `type_publisher_scopes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_publisher_scopes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `description` text,
  `label` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sort_order` int NOT NULL,
  `settings` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK3jss15sbvtabr1w5dhukxctt6` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_schemas`
--

DROP TABLE IF EXISTS `type_schemas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_schemas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `description` text,
  `label` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sort_order` int NOT NULL,
  `json_schema` text NOT NULL,
  `settings` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKey5wc99iw74da7hsg3nsteh8w` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `type_sharing_statuses`
--

DROP TABLE IF EXISTS `type_sharing_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_sharing_statuses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `description` text,
  `label` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `sort_order` int NOT NULL,
  `settings` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKeaoicjh57oo8l5vmokbk2lrg` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-06 12:16:16
