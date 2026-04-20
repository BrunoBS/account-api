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
DROP DATABASE IF EXISTS  app_db;
CREATE DATABASE `app_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'BBS','2026-03-29 23:22:04.082103',NULL,'Conta central para gestão de serviços de backend e infraestrutura core.','bbs@empresa.com','bc13ef8c-34b7-43a7-beda-60d70ee61b2a','Conta Corporativa Principal',_binary '\0','{\"max_applications\":50,\"environment_limit\":5,\"allow_custom_infra\":true}','bruno.barbosa','2026-04-05 16:47:43.763632',2,NULL,'BBS'),(2,'','2026-03-30 23:16:31.867247',NULL,'Conta secundaria para processamento de dados.','bbs@empresa.com','4605b78e-98d5-4329-8bb8-92ded3286fb7','Conta Corporativa Secundaria',_binary '\0','{\"max_applications\":50,\"environment_limit\":5,\"allow_custom_infra\":true}','bruno.barbosa','2026-03-30 23:16:31.867247',2,NULL,'SIG');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `accounts_approvers`
--

LOCK TABLES `accounts_approvers` WRITE;
/*!40000 ALTER TABLE `accounts_approvers` DISABLE KEYS */;
INSERT INTO `accounts_approvers` VALUES ('116f7cb5-10ba-415d-94c7-67b609a8ed60','bruno@empresa.com','999999999',1),('28e8e65b-5fa3-406a-9099-650dd49c9513','bruno@empresa.com','999999999',2),('325e0767-0911-485d-8916-eedff84eab2e','sistemas@empresa.com','111111111',1),('aeee7fce-9b5d-4cb9-9378-f5396e8d6045','sistemas@empresa.com','111111111',2);
/*!40000 ALTER TABLE `accounts_approvers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `accounts_environment_publishers`
--

DROP TABLE IF EXISTS `accounts_environment_publishers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts_environment_publishers` (
  `account_id` bigint NOT NULL,
  `environment_id` bigint NOT NULL,
  `publisher_id` bigint NOT NULL,
  UNIQUE KEY `UKc17isq9d4l1vccu78jp0p461p` (`publisher_id`),
  KEY `FK4o2g2dephr0ls9ar7sluui04b` (`account_id`,`environment_id`),
  CONSTRAINT `FK4o2g2dephr0ls9ar7sluui04b` FOREIGN KEY (`account_id`, `environment_id`) REFERENCES `accounts_environments` (`account_id`, `environment_id`),
  CONSTRAINT `FKqcdmodvsy5vfl9e0winl6r5wl` FOREIGN KEY (`publisher_id`) REFERENCES `publisher_configs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts_environment_publishers`
--

LOCK TABLES `accounts_environment_publishers` WRITE;
/*!40000 ALTER TABLE `accounts_environment_publishers` DISABLE KEYS */;
INSERT INTO `accounts_environment_publishers` VALUES (1,1,62),(1,4,58),(1,4,59),(1,5,63);
/*!40000 ALTER TABLE `accounts_environment_publishers` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `accounts_environments`
--

LOCK TABLES `accounts_environments` WRITE;
/*!40000 ALTER TABLE `accounts_environments` DISABLE KEYS */;
INSERT INTO `accounts_environments` VALUES (1,1,'{\"account_id\":\"conta1\",\"env_id\":\"ambiente1\"}'),(1,4,'{\"account_id\":\"conta2\",\"env_id\":\"ambiente2\"}'),(1,5,'{\"account_id\":\"conta1\",\"env_id\":\"ambiente1\"}');
/*!40000 ALTER TABLE `accounts_environments` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts_onboarding_completions`
--

LOCK TABLES `accounts_onboarding_completions` WRITE;
/*!40000 ALTER TABLE `accounts_onboarding_completions` DISABLE KEYS */;
INSERT INTO `accounts_onboarding_completions` VALUES (1,1,'2026-03-29 23:22:04.115540','USER',1),(2,2,'2026-03-30 23:16:31.901200','USER',1),(3,1,'2026-03-31 20:22:33.338827','USER',2);
/*!40000 ALTER TABLE `accounts_onboarding_completions` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `accounts_tags`
--

LOCK TABLES `accounts_tags` WRITE;
/*!40000 ALTER TABLE `accounts_tags` DISABLE KEYS */;
INSERT INTO `accounts_tags` VALUES ('00174dd9-cb64-4573-91f4-26077cdbf37f','4605b78e-98d5-4329-8bb8-92ded3286fb7',2),('15c5e54c-44e5-4bba-a1c3-feac5a87a19e','corporativa',2),('17671cff-9d30-4dd5-a770-38d20e467b47','conta-corporativa-principal',1),('27176584-5b98-4128-bce2-ddff8d94fd13','conta-corporativa-secundaria',2),('66b7acaa-ef9e-44bb-a5b0-e44ea696bd97','bbs',1),('a4adbabd-25df-4ca1-8ee0-e68306733462','secundaria',2),('b1a494e9-b589-4105-a716-5a59e0c9e00c','bruno',1),('cf851ac6-6d1f-48e2-b37e-5f5b9eb3df2e','bc13ef8c-34b7-43a7-beda-60d70ee61b2a',1),('e28611bd-52b5-4efc-9ac7-2a2dbc108316','sig',2),('eefec07d-7a91-4d7e-ba09-2c3128974127','conta-secundaria',2);
/*!40000 ALTER TABLE `accounts_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_audit_events`
--

DROP TABLE IF EXISTS `api_audit_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `api_audit_events` (
  `cod_idef_event` bigint NOT NULL,
  `cod_idef_ambi` bigint NOT NULL,
  `cod_idef_enti` bigint NOT NULL,
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
-- Dumping data for table `api_audit_events`
--

LOCK TABLES `api_audit_events` WRITE;
/*!40000 ALTER TABLE `api_audit_events` DISABLE KEYS */;
/*!40000 ALTER TABLE `api_audit_events` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applications`
--

LOCK TABLES `applications` WRITE;
/*!40000 ALTER TABLE `applications` DISABLE KEYS */;
INSERT INTO `applications` VALUES (1,'cust-api','BBS-01','2026-04-04 17:30:12.411837',NULL,_binary 'c+rD\�Cr�ӳ,�u�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0',_binary '\0','context-api','{}','2026-04-04 17:30:12.411837',1,3,1,1,'pm5-01');
/*!40000 ALTER TABLE `applications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `applications_environment_publishers`
--

DROP TABLE IF EXISTS `applications_environment_publishers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `applications_environment_publishers` (
  `application_id` bigint NOT NULL,
  `environment_id` bigint NOT NULL,
  `publisher_configuration_id` bigint NOT NULL,
  UNIQUE KEY `UKhh06mbtw4ialht9fhcyvdwe4u` (`publisher_configuration_id`),
  KEY `FKea8brwfsu9xmdw1tk611o3k2c` (`application_id`,`environment_id`),
  CONSTRAINT `FKea8brwfsu9xmdw1tk611o3k2c` FOREIGN KEY (`application_id`, `environment_id`) REFERENCES `applications_environments` (`application_id`, `environment_id`),
  CONSTRAINT `FKre7cb2578yno3u8vtix1x27ap` FOREIGN KEY (`publisher_configuration_id`) REFERENCES `publisher_configs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applications_environment_publishers`
--

LOCK TABLES `applications_environment_publishers` WRITE;
/*!40000 ALTER TABLE `applications_environment_publishers` DISABLE KEYS */;
/*!40000 ALTER TABLE `applications_environment_publishers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `applications_environments`
--

DROP TABLE IF EXISTS `applications_environments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `applications_environments` (
  `application_id` bigint NOT NULL,
  `environment_id` bigint NOT NULL,
  `authorizer_group` varchar(255) NOT NULL,
  `settings` text,
  PRIMARY KEY (`application_id`,`environment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applications_environments`
--

LOCK TABLES `applications_environments` WRITE;
/*!40000 ALTER TABLE `applications_environments` DISABLE KEYS */;
/*!40000 ALTER TABLE `applications_environments` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `applications_tag`
--

LOCK TABLES `applications_tag` WRITE;
/*!40000 ALTER TABLE `applications_tag` DISABLE KEYS */;
INSERT INTO `applications_tag` VALUES ('16caa801-eacf-42eb-aaa6-832f7ba16acd','critical',1),('2a22f484-3c65-4ce7-8438-e332e5e7740a','customer',1),('7113cbfd-13f2-4927-b1ad-5b35bc94513f','production',1),('ce78db1f-2eaa-46d9-bba0-d1aa1c3f425a','context-api',1),('f0f187eb-f1ba-4def-86b2-379fd84e6462','pm5-01',1),('f755a937-40a5-4f1b-8cf9-b01d7058ff20','backend',1);
/*!40000 ALTER TABLE `applications_tag` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `environments`
--

LOCK TABLES `environments` WRITE;
/*!40000 ALTER TABLE `environments` DISABLE KEYS */;
INSERT INTO `environments` VALUES (1,_binary '','Ambiente usado para criar e testar novas funcionalidades do sistema.','Desenvolvimento',1,NULL,1,1,'','{}'),(2,_binary '','Ambiente de validação onde as funcionalidades são testadas antes de serem liberadas para os usuários.','Homologação',2,NULL,2,1,'',NULL),(3,_binary '','Ambiente oficial do sistema, utilizado no dia a dia pelos usuários.','Produção',3,NULL,3,1,'',NULL),(4,_binary '','Configuração customizado da conta.','Ambiente Customizado',4,1,2,2,'','{}'),(5,_binary '','Configuração customizado da conta.','CUSTOM',5,1,1,2,'','{}');
/*!40000 ALTER TABLE `environments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kafka_cluster_property`
--

DROP TABLE IF EXISTS `kafka_cluster_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kafka_cluster_property` (
  `id` varchar(36) NOT NULL,
  `date_update` datetime(6) DEFAULT NULL,
  `environment` varchar(255) DEFAULT NULL,
  `properties` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kafka_cluster_property`
--

LOCK TABLES `kafka_cluster_property` WRITE;
/*!40000 ALTER TABLE `kafka_cluster_property` DISABLE KEYS */;
INSERT INTO `kafka_cluster_property` VALUES ('06d7c4f3-d654-4956-8ab3-b0345a035cdd','2026-04-03 18:30:21.994262','PROD','{\"bootstrap.servers\":\"localhost:49092\",\"key.serializer\":\"org.apache.kafka.common.serialization.StringSerializer\",\"value.serializer\":\"org.apache.kafka.common.serialization.StringSerializer\",\"acks\":\"all\",\"retries\":10,\"retry.backoff.ms\":1000,\"enable.idempotence\":true,\"delivery.timeout.ms\":50000,\"request.timeout.ms\":30000,\"max.block.ms\":3000,\"connections.max.idle.ms\":540000,\"reconnect.backoff.ms\":1000,\"reconnect.backoff.max.ms\":10000,\"linger.ms\":5,\"batch.size\":32768,\"buffer.memory\":67108864,\"max.in.flight.requests.per.connection\":5,\"compression.type\":\"snappy\",\"client.id\":\"prod-producer\",\"metadata.max.age.ms\":60000,\"spring.kafka.producer.retries\":0,\"spring.kafka.producer.delivery-timeout-ms\":3000,\"spring.kafka.producer.max-block-ms\":2000}'),('1c53626d-cb15-4322-b213-93879f7a1cf4','2026-04-03 18:30:15.767530','DEV','{\"bootstrap.servers\":\"localhost:29092\",\"key.serializer\":\"org.apache.kafka.common.serialization.StringSerializer\",\"value.serializer\":\"org.apache.kafka.common.serialization.StringSerializer\",\"acks\":\"all\",\"retries\":10,\"retry.backoff.ms\":1000,\"enable.idempotence\":true,\"request.timeout.ms\":30000,\"delivery.timeout.ms\":60000,\"max.block.ms\":3000,\"linger.ms\":5,\"batch.size\":16384,\"compression.type\":\"snappy\",\"client.id\":\"dev-producer-bruno\",\"metadata.max.age.ms\":60000,\"max.in.flight.requests.per.connection\":\"1\"}'),('973f2569-99a0-4e60-9e2d-c84eec934787','2026-04-03 18:30:18.966686','HOM','{\"bootstrap.servers\":\"localhost:39092\",\"key.serializer\":\"org.apache.kafka.common.serialization.StringSerializer\",\"value.serializer\":\"org.apache.kafka.common.serialization.StringSerializer\",\"acks\":\"all\",\"retries\":10,\"retry.backoff.ms\":1000,\"enable.idempotence\":true,\"delivery.timeout.ms\":120000,\"request.timeout.ms\":30000,\"max.block.ms\":3000,\"connections.max.idle.ms\":60000,\"reconnect.backoff.ms\":1000,\"reconnect.backoff.max.ms\":10000,\"linger.ms\":5,\"batch.size\":32768,\"buffer.memory\":67108864,\"max.in.flight.requests.per.connection\":5,\"compression.type\":\"snappy\",\"client.id\":\"dev-producer-bruno\",\"metadata.max.age.ms\":60000,\"spring.kafka.producer.retries\":0,\"spring.kafka.producer.delivery-timeout-ms\":3000,\"spring.kafka.producer.max-block-ms\":2000}');
/*!40000 ALTER TABLE `kafka_cluster_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kafka_topic_config`
--

DROP TABLE IF EXISTS `kafka_topic_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kafka_topic_config` (
  `id` varchar(36) NOT NULL,
  `environment` varchar(255) DEFAULT NULL,
  `parametros` text,
  `topic` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kafka_topic_config`
--

LOCK TABLES `kafka_topic_config` WRITE;
/*!40000 ALTER TABLE `kafka_topic_config` DISABLE KEYS */;
INSERT INTO `kafka_topic_config` VALUES ('19cab660-07db-4835-9765-5ca671eec645','PROD','{\"max.retry.attempts\":25,\"max.block.ms\":5000,\"priority\":\"high\",\"validation.enabled\":true,\"log.payload\":false,\"alert.on.failure\":true}','topic.message.accounts.update'),('7f3006f5-adbc-42b0-80d7-55824ec0a136','HOM','{\"max.retry.attempts\":10,\"max.block.ms\":3000,\"priority\":\"medium\",\"validation.enabled\":true,\"log.payload\":true}','topic.message.accounts.update'),('9b676320-9ebd-4192-a4cd-d7ac784f629e','DEV','{\"max.retry.attempts\":5,\"max.block.ms\":2000,\"priority\":\"low\"}','topic.message.accounts.update');
/*!40000 ALTER TABLE `kafka_topic_config` ENABLE KEYS */;
UNLOCK TABLES;

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
  `last_error` longtext,
  `message` text,
  `next_retry` datetime(6) DEFAULT NULL,
  `retry_count` int NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `topic` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_buffer_lookup` (`environment`,`status`,`next_retry`,`created_at`)
) ENGINE=InnoDB AUTO_INCREMENT=33010 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `local_buffer`
--

LOCK TABLES `local_buffer` WRITE;
/*!40000 ALTER TABLE `local_buffer` DISABLE KEYS */;
/*!40000 ALTER TABLE `local_buffer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publisher_configs`
--

DROP TABLE IF EXISTS `publisher_configs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publisher_configs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_index` int NOT NULL,
  `parameters` text,
  `publisher_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKolqd7h8rw12ohifemkv1xevnx` (`publisher_id`),
  CONSTRAINT `FKolqd7h8rw12ohifemkv1xevnx` FOREIGN KEY (`publisher_id`) REFERENCES `publishers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publisher_configs`
--

LOCK TABLES `publisher_configs` WRITE;
/*!40000 ALTER TABLE `publisher_configs` DISABLE KEYS */;
INSERT INTO `publisher_configs` VALUES (58,1,'{\"clientId\":\"1234-1234-1234\"}',1),(59,2,'{\"topic\":\"TOPICO X\"}',2),(62,1,'{\"topic\":\"TOPICO X\"}',2),(63,1,'{\"topic\":\"TOPICO X\"}',2);
/*!40000 ALTER TABLE `publisher_configs` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `publishers`
--

LOCK TABLES `publishers` WRITE;
/*!40000 ALTER TABLE `publishers` DISABLE KEYS */;
INSERT INTO `publishers` VALUES (1,_binary '',_binary '\0','Publicador para comunicação bidirecional em tempo real via protocolo WS/WSS.','{\"type\":\"object\",\"properties\":{\"clientId\":{\"type\":\"string\",\"format\":\"uuid\"}},\"required\":[\"clientId\"]}','Web Socket','WEB_SOCKET',1),(2,_binary '',_binary '','Publicador gerenciado para integração com clusters Kafka (Kafka as a Service).','{\"type\":\"object\",\"properties\":{\"topic\":{\"type\":\"string\"}},\"required\":[\"topic\"]}','KaaS (Kafka)','KAAS',1),(3,_binary '',_binary '\0','Publicador para gestão e distribuição de configurações dinâmicas de aplicação.','{\"type\":\"object\",\"properties\":{\"application_id\":{\"type\":\"string\"},\"environment_id\":{\"type\":\"string\"},\"arn\":{\"type\":\"string\"}},\"required\":[\"application_id\",\"environment_id\",\"arn\"]}','AppConfig','APPCONFIG',2);
/*!40000 ALTER TABLE `publishers` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sharing_origins`
--

LOCK TABLES `sharing_origins` WRITE;
/*!40000 ALTER TABLE `sharing_origins` DISABLE KEYS */;
/*!40000 ALTER TABLE `sharing_origins` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `sharing_target_features`
--

LOCK TABLES `sharing_target_features` WRITE;
/*!40000 ALTER TABLE `sharing_target_features` DISABLE KEYS */;
/*!40000 ALTER TABLE `sharing_target_features` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKremht23gx2ib6e1is02nvc46c` (`identifier`),
  KEY `FKr9e7youug783huit0l1hiygrg` (`application_id`),
  CONSTRAINT `FKr9e7youug783huit0l1hiygrg` FOREIGN KEY (`application_id`) REFERENCES `applications` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sharing_targets`
--

LOCK TABLES `sharing_targets` WRITE;
/*!40000 ALTER TABLE `sharing_targets` DISABLE KEYS */;
/*!40000 ALTER TABLE `sharing_targets` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `type_accounts`
--

LOCK TABLES `type_accounts` WRITE;
/*!40000 ALTER TABLE `type_accounts` DISABLE KEYS */;
INSERT INTO `type_accounts` VALUES (1,_binary '','Perfil com acesso total às configurações e administração de configurações do NF2 (Ambiente Legado)','Admin','ADMIN',1,'{\"isLegado\":true}'),(2,_binary '','Perfil com acesso total às configurações e administração de configurações do Ambiente Modernizado','Manager','MANAGER',2,'{}'),(3,_binary '','Perfil responsável pela gestão e manutenção do catálogo de MFEs','Catalog','CATALOG',3,'{}');
/*!40000 ALTER TABLE `type_accounts` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `type_application_scopes`
--

LOCK TABLES `type_application_scopes` WRITE;
/*!40000 ALTER TABLE `type_application_scopes` DISABLE KEYS */;
INSERT INTO `type_application_scopes` VALUES (1,_binary '','Serviços de processamento, APIs e integração com banco de dados.','Backend','BACKEND',1,''),(2,_binary '','Interfaces de usuário, aplicações web e componentes visuais.','Frontend','FRONTEND',2,''),(3,_binary '','Recursos compartilhados.','Shared','SHARED',3,'');
/*!40000 ALTER TABLE `type_application_scopes` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `type_authorizations`
--

LOCK TABLES `type_authorizations` WRITE;
/*!40000 ALTER TABLE `type_authorizations` DISABLE KEYS */;
INSERT INTO `type_authorizations` VALUES (1,_binary '','Ambiente de desenvolvimento para construção e testes locais.','Development','DEV',1,'{\"group_name\":\"DEV\",\"group_scope\":\"1\"}'),(2,_binary '','Ambiente de testes integrados e homologação de funcionalidades.','Test','TST',2,'{}'),(3,_binary '','Ambiente administrativo para gestão de recursos e configurações.','Administration','ADM',3,'{}');
/*!40000 ALTER TABLE `type_authorizations` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `type_environments`
--

LOCK TABLES `type_environments` WRITE;
/*!40000 ALTER TABLE `type_environments` DISABLE KEYS */;
INSERT INTO `type_environments` VALUES (1,_binary '','Configuração padrão do sistema, aplicada automaticamente.','Default','DEFAULT',1,''),(2,_binary '','Configuração personalizada definida pelo usuário ou conta.','Custom','CUSTOM',2,'');
/*!40000 ALTER TABLE `type_environments` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `type_feature_scopes`
--

LOCK TABLES `type_feature_scopes` WRITE;
/*!40000 ALTER TABLE `type_feature_scopes` DISABLE KEYS */;
INSERT INTO `type_feature_scopes` VALUES (1,_binary '','Escopo global de conta para gestões estruturais.','Account','ACCOUNT',1,'{\"type_code\":\"SCOPE\"}'),(2,_binary '','Escopo focado em motor de promoções e pacotes.','Promotion Engine','PROMOTION_ENGINE',2,'{\"type_code\":\"SCOPE\"}'),(3,_binary '','Escopo para aplicações compartilhadas e utilitários core.','Shared Application','SHARED_APPLICATION',3,'{\"type_code\":\"SCOPE\"}'),(4,_binary '','Escopo para gestão de Micro Front-Ends e catálogos.','MFE Application','MFE_APPLICATION',4,'{\"type_code\":\"SCOPE\"}'),(5,_binary '','Escopo para serviços de backend e regras de negócio.','Backend Application','BACKEND_APPLICATION',5,'{\"type_code\":\"SCOPE\"}');
/*!40000 ALTER TABLE `type_feature_scopes` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `type_features`
--

LOCK TABLES `type_features` WRITE;
/*!40000 ALTER TABLE `type_features` DISABLE KEYS */;
INSERT INTO `type_features` VALUES (1,_binary '','Gestão de aplicações compartilhadas no nível de conta.','Shared Applications','SHARED_APPLICATIONS',1,_binary '\0','{\"feature_key\":\"FEATURE\"}',1),(2,_binary '','Gestão de serviços de backend no nível de conta.','Backend Applications','BACKEND_APPLICATIONS',2,_binary '\0','{\"feature_key\":\"FEATURE\"}',1),(3,_binary '','Gestão de Micro Front-Ends no nível de conta.','MFE Applications','MFE_APPLICATIONS',3,_binary '\0','{\"feature_key\":\"FEATURE\"}',1),(4,_binary '','Acesso ao motor de promoções no nível de conta.','Promotion Engine','PROMOTION_ENGINE',4,_binary '\0','{\"feature_key\":\"FEATURE\"}',1),(5,_binary '','Configurações globais no nível de conta.','Configuration','CONFIGURATION',5,_binary '\0','{\"feature_key\":\"FEATURE\"}',1),(6,_binary '','Criação de pacotes de emergência.','Emergency Package','EMERGENCY_PACKAGE',6,_binary '\0','{\"feature_key\":\"FEATURE\"}',2),(7,_binary '','Funcionalidade de criação de pacotes.','Create Package','CREATE_PACKAGE',7,_binary '\0','{\"feature_key\":\"FEATURE\"}',2),(8,_binary '','Gestão do ciclo de vida de pacotes.','Manage Package','MANAGE_PACKAGE',8,_binary '\0','{\"feature_key\":\"FEATURE\"}',2),(9,_binary '','Gestão de chaves para aplicações compartilhadas.','Key Management','KEY_MANAGEMENT',9,_binary '\0','{\"feature_key\":\"FEATURE\"}',3),(10,_binary '','Motor de regras para aplicações compartilhadas.','Rule Engine','RULE_ENGINE',10,_binary '\0','{\"feature_key\":\"FEATURE\"}',3),(11,_binary '','Menus dinâmicos para aplicações compartilhadas.','Flexible Menu','FLEXIBLE_MENU',11,_binary '\0','{\"feature_key\":\"FEATURE\"}',3),(12,_binary '','Roteamento dinâmico para aplicações compartilhadas.','Flexible Route','FLEXIBLE_ROUTE',12,_binary '\0','{\"feature_key\":\"FEATURE\"}',3),(13,_binary '','Lista de permissões para aplicações compartilhadas.','Allow List','ALLOW_LIST',13,_binary '\0','{\"feature_key\":\"FEATURE\"}',3),(14,_binary '','Gestão e orquestração de MFEs.','MFE Management','MFE_MANAGEMENT',14,_binary '\0','{\"feature_key\":\"FEATURE\"}',4),(15,_binary '','Catálogo de componentes MFE.','MFE Catalog','MFE_CATALOG',15,_binary '\0','{\"feature_key\":\"FEATURE\"}',4),(16,_binary '','Gestão de chaves para aplicações backend.','Key Management','KEY_MANAGEMENT',16,_binary '\0','{\"feature_key\":\"FEATURE\"}',5),(17,_binary '','Motor de regras para aplicações backend.','Rule Engine','RULE_ENGINE',17,_binary '\0','{\"feature_key\":\"FEATURE\"}',5),(18,_binary '','Menus dinâmicos para aplicações backend.','Flexible Menu','FLEXIBLE_MENU',18,_binary '\0','{\"feature_key\":\"FEATURE\"}',5),(19,_binary '','Roteamento dinâmico para aplicações backend.','Flexible Route','FLEXIBLE_ROUTE',19,_binary '\0','{\"feature_key\":\"FEATURE\"}',5),(20,_binary '','Lista de permissões para aplicações backend.','Allow List','ALLOW_LIST',20,_binary '\0','{\"feature_key\":\"FEATURE\"}',5);
/*!40000 ALTER TABLE `type_features` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `type_infrastructures`
--

LOCK TABLES `type_infrastructures` WRITE;
/*!40000 ALTER TABLE `type_infrastructures` DISABLE KEYS */;
INSERT INTO `type_infrastructures` VALUES (1,_binary '','Infraestrutura baseada em máquinas virtuais tradicionais','Virtual Machine','VM',1,''),(2,_binary '','Ambiente isolado para execução de aplicações via Docker ou similares.','Container','CONTAINER',2,''),(3,_binary '','Orquestração de containers em larga escala com alta disponibilidade.','Kubernetes','KUBERNETES',3,''),(4,_binary '','Execução baseada em eventos sem gerenciamento direto de servidores.','Serverless','SERVERLESS',4,'');
/*!40000 ALTER TABLE `type_infrastructures` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `type_languages`
--

LOCK TABLES `type_languages` WRITE;
/*!40000 ALTER TABLE `type_languages` DISABLE KEYS */;
INSERT INTO `type_languages` VALUES (1,_binary '','Linguagem robusta para sistemas corporativos e backend.','Java','JAVA',1,''),(2,_binary '','Framework versátil para aplicações web e serviços Microsoft.','.NET','DOTNET',2,''),(3,_binary '','Linguagem focada em performance e alta concorrência.','Go','GO',3,''),(4,_binary '','Linguagem dinâmica voltada para dados, scripts e web.','Python','PYTHON',4,''),(5,_binary '','Outras linguagens ou tecnologias não listadas anteriormente.','Other','OTHER',5,'');
/*!40000 ALTER TABLE `type_languages` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `type_onboardings`
--

LOCK TABLES `type_onboardings` WRITE;
/*!40000 ALTER TABLE `type_onboardings` DISABLE KEYS */;
INSERT INTO `type_onboardings` VALUES (1,_binary '','Registro inicial dos dados da conta no sistema.','Account Registration','ACCOUNT_REGISTRATION',1,'{\"type_code\":\"ONBOARDING_STEP\"}','Realize o cadastro inicial da sua conta e valide os dados de acesso para habilitar a configuração dos ambientes.'),(2,_binary '','Configuração do primeiro ambiente da conta.','Account First Environment','ACCOUNT_FIRST_ENVIRONMENT',2,'{\"type_code\":\"ONBOARDING_STEP\"}','Configure os ambientes da sua conta (Desenvolvimento, Homologação e Produção). É obrigatório configurar ao menos o ambiente de Desenvolvimento para prosseguir.'),(3,_binary '','Criação da primeira aplicação associada à conta.','First Application Registration','FIRST_APPLICATION_REGISTRATION',3,'{\"type_code\":\"ONBOARDING_STEP\"}','Crie sua primeira aplicação para liberar o acesso às features do Portal Manager  e personalizar suas configurações.'),(4,_binary '','Vinculação da aplicação ao seu primeiro ambiente de execução.','Application First Environment','APPLICATION_FIRST_ENVIRONMENT',4,'{\"type_code\":\"ONBOARDING_STEP\"}','Finalize o vínculo da sua aplicação com o ambiente de Desenvolvimento. Você poderá replicar as configurações para Homologação e Produção posteriormente.');
/*!40000 ALTER TABLE `type_onboardings` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `type_publisher_scopes`
--

LOCK TABLES `type_publisher_scopes` WRITE;
/*!40000 ALTER TABLE `type_publisher_scopes` DISABLE KEYS */;
INSERT INTO `type_publisher_scopes` VALUES (1,_binary '','Recursos e configurações aplicados ao nível global da conta.','Account','ACCOUNT',1,'{}'),(2,_binary '','Recursos e configurações restritos ao contexto da aplicação.','Application','APPLICATION',2,'');
/*!40000 ALTER TABLE `type_publisher_scopes` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `type_schemas`
--

LOCK TABLES `type_schemas` WRITE;
/*!40000 ALTER TABLE `type_schemas` DISABLE KEYS */;
INSERT INTO `type_schemas` VALUES (1,_binary '','Configurações globais do nível de conta.','Account','ACCOUNT',1,'{\"type\":\"object\",\"properties\":{\"account_id\":{\"type\":\"string\"}},\"required\":[\"account_id\"],\"additionalProperties\":false}','{\"type_code\":\"ACCOUNT\"}'),(2,_binary '','Configurações específicas de uma aplicação.','Application','APPLICATION',2,'{\"type\":\"object\",\"properties\":{\"gitRepositoryName\":{\"type\":\"string\",\"description\":\"Nome do repositório da aplicação no sistema de controle de versão (ex: GitHub, GitLab, Bitbucket).\",\"maxLength\":255},\"gitRepositoryUrl\":{\"type\":\"string\",\"format\":\"uri\",\"description\":\"URL do repositório Git onde está armazenado o código-fonte da aplicação.\",\"maxLength\":500},\"applicationUrl\":{\"type\":\"string\",\"format\":\"uri\",\"description\":\"URL principal de acesso à aplicação ou API em execução.\",\"maxLength\":500},\"documentationUrl\":{\"type\":\"string\",\"format\":\"uri\",\"description\":\"URL da documentação técnica da aplicação (ex: Swagger, Confluence, README, portal de documentação).\",\"maxLength\":500},\"observabilityUrl\":{\"type\":\"string\",\"format\":\"uri\",\"description\":\"URL das ferramentas de observabilidade da aplicação, como dashboards de monitoramento, logs ou métricas (ex: Grafana, Kibana, Datadog).\",\"maxLength\":500},\"ownerTeam\":{\"type\":\"string\",\"description\":\"Nome do time responsável pela manutenção e evolução da aplicação.\",\"maxLength\":150},\"ownerTeamEmail\":{\"type\":\"string\",\"format\":\"email\",\"description\":\"Endereço de e-mail do time responsável pela aplicação para contato, incidentes ou comunicação operacional.\",\"maxLength\":255}},\"required\":[\"gitRepositoryName\",\"gitRepositoryUrl\",\"applicationUrl\",\"documentationUrl\",\"observabilityUrl\",\"ownerTeam\",\"ownerTeamEmail\"],\"additionalProperties\":false}','{\"type_code\":\"APPLICATION\"}'),(3,_binary '','Configurações de ambiente (DEV, TST, PRD).','Environment','ENVIRONMENT',3,'{\"type\":\"object\",\"properties\":{\"env_name\":{\"type\":\"string\"}},\"required\":[\"env_name\"],\"additionalProperties\":false}','{\"type_code\":\"ENVIRONMENT\"}'),(4,_binary '','Relação entre conta e ambiente específico.','Account Environment','ACCOUNT_ENVIRONMENT',4,'{\"type\":\"object\",\"properties\":{\"account_id\":{\"type\":\"string\"},\"env_id\":{\"type\":\"string\"}},\"required\":[\"account_id\",\"env_id\"],\"additionalProperties\":false}','{\"type_code\":\"ACCOUNT_ENVIRONMENT\"}'),(5,_binary '','Relação entre aplicação e ambiente específico.','Application Environment','APPLICATION_ENVIRONMENT',5,'{\"type\":\"object\",\"properties\":{\"app_id\":{\"type\":\"string\"},\"env_id\":{\"type\":\"string\"}},\"required\":[\"app_id\",\"env_id\"],\"additionalProperties\":false}','{\"type_code\":\"APPLICATION_ENVIRONMENT\"}'),(6,_binary '','Definições de grupos de autorização.','Authorization Group','AUTHORIZATION_GROUP',6,'{\"type\":\"object\",\"properties\":{\"group_name\":{\"type\":\"string\"},\"group_scope\":{\"type\":\"string\"}},\"required\":[\"group_name\",\"group_scope\"],\"additionalProperties\":false}','{\"type_code\":\"AUTHORIZATION_GROUP\"}'),(7,_binary '','Definições de tipos e dicionários do sistema.','Type','TYPE',7,'{\"type\":\"object\",\"properties\":{\"type_code\":{\"type\":\"string\"}},\"required\":[\"type_code\"],\"additionalProperties\":false}','{\"type_code\":\"TYPE\"}'),(8,_binary '','Funcionalidades e flags de sistema.','Feature','FEATURE',8,'{\"type\":\"object\",\"properties\":{\"feature_key\":{\"type\":\"string\"}},\"required\":[\"feature_key\"],\"additionalProperties\":false}','{\"type_code\":\"FEATURE\"}');
/*!40000 ALTER TABLE `type_schemas` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_sharing_statuses`
--

LOCK TABLES `type_sharing_statuses` WRITE;
/*!40000 ALTER TABLE `type_sharing_statuses` DISABLE KEYS */;
INSERT INTO `type_sharing_statuses` VALUES (1,_binary '','Aguardando aprovação da conta de destino do compartilhamento.','Waiting Destination Approval','WAITING_DESTINATION_APPROVAL',1,'{\"type_code\":\"SHARING_STATUS\"}'),(2,_binary '','Compartilhamento aprovado na etapa atual.','Approved','APPROVED',2,'{\"type_code\":\"SHARING_STATUS\"}'),(3,_binary '','Compartilhamento rejeitado na etapa atual.','Rejected','REJECTED',3,'{\"type_code\":\"SHARING_STATUS\"}'),(4,_binary '','Aguardando aprovação da origem (dono) do compartilhamento.','Waiting Source Approval','WAITING_SOURCE_APPROVAL',4,'{\"type_code\":\"SHARING_STATUS\"}');
/*!40000 ALTER TABLE `type_sharing_statuses` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-17  9:19:24
