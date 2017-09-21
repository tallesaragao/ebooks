-- MySQL dump 10.13  Distrib 5.7.12, for Win32 (AMD64)
--
-- Host: localhost    Database: ebooks_les
-- ------------------------------------------------------
-- Server version	5.7.16-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ativacao`
--

DROP TABLE IF EXISTS `ativacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ativacao` (
  `id_ativacao` int(11) NOT NULL AUTO_INCREMENT,
  `justificativa` varchar(200) NOT NULL,
  `id_livro` int(11) NOT NULL,
  PRIMARY KEY (`id_ativacao`),
  KEY `fk_ativacao_livro_idx` (`id_livro`),
  CONSTRAINT `fk_ativacao_livro` FOREIGN KEY (`id_livro`) REFERENCES `livro` (`id_livro`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ativacao`
--

LOCK TABLES `ativacao` WRITE;
/*!40000 ALTER TABLE `ativacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `ativacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `autor`
--

DROP TABLE IF EXISTS `autor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `autor` (
  `id_autor` int(11) NOT NULL AUTO_INCREMENT,
  `dt_cadastro` datetime NOT NULL,
  `id_pessoa_fisica` int(11) NOT NULL,
  PRIMARY KEY (`id_autor`),
  KEY `fk_pf_autor_idx` (`id_pessoa_fisica`),
  CONSTRAINT `fk_pf_autor` FOREIGN KEY (`id_pessoa_fisica`) REFERENCES `pessoa_fisica` (`id_pessoa_fisica`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autor`
--

LOCK TABLES `autor` WRITE;
/*!40000 ALTER TABLE `autor` DISABLE KEYS */;
/*!40000 ALTER TABLE `autor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `autor_livro`
--

DROP TABLE IF EXISTS `autor_livro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `autor_livro` (
  `id_livro` int(11) NOT NULL,
  `id_autor` int(11) NOT NULL,
  PRIMARY KEY (`id_livro`,`id_autor`),
  KEY `fk_autor_livro_livro_idx` (`id_livro`),
  KEY `fk_autor_livro_autor_idx` (`id_autor`),
  CONSTRAINT `fk_autor_livro_autor` FOREIGN KEY (`id_autor`) REFERENCES `autor` (`id_autor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_autor_livro_livro` FOREIGN KEY (`id_livro`) REFERENCES `livro` (`id_livro`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autor_livro`
--

LOCK TABLES `autor_livro` WRITE;
/*!40000 ALTER TABLE `autor_livro` DISABLE KEYS */;
/*!40000 ALTER TABLE `autor_livro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoria` (
  `id_categoria` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `dt_cadastro` datetime NOT NULL,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (5,'Didático','2017-09-21 00:00:00'),(6,'Romance','2017-09-21 00:00:00'),(7,'Aventura','2017-09-21 00:00:00'),(8,'Terror','2017-09-21 11:18:42');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dimensoes`
--

DROP TABLE IF EXISTS `dimensoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dimensoes` (
  `id_dimensoes` int(11) NOT NULL AUTO_INCREMENT,
  `altura` double NOT NULL,
  `largura` double NOT NULL,
  `peso` double NOT NULL,
  `profundidade` double NOT NULL,
  PRIMARY KEY (`id_dimensoes`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dimensoes`
--

LOCK TABLES `dimensoes` WRITE;
/*!40000 ALTER TABLE `dimensoes` DISABLE KEYS */;
/*!40000 ALTER TABLE `dimensoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `editora`
--

DROP TABLE IF EXISTS `editora`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `editora` (
  `id_editora` int(11) NOT NULL AUTO_INCREMENT,
  `id_pessoa_juridica` int(11) NOT NULL,
  PRIMARY KEY (`id_editora`),
  KEY `fk_pj_editora_idx` (`id_pessoa_juridica`),
  CONSTRAINT `fk_pj_editora` FOREIGN KEY (`id_pessoa_juridica`) REFERENCES `pessoa_juridica` (`id_pessoa_juridica`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `editora`
--

LOCK TABLES `editora` WRITE;
/*!40000 ALTER TABLE `editora` DISABLE KEYS */;
/*!40000 ALTER TABLE `editora` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupo_precificacao`
--

DROP TABLE IF EXISTS `grupo_precificacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupo_precificacao` (
  `id_grupo_precificacao` int(11) NOT NULL AUTO_INCREMENT,
  `margem_lucro` double NOT NULL,
  PRIMARY KEY (`id_grupo_precificacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupo_precificacao`
--

LOCK TABLES `grupo_precificacao` WRITE;
/*!40000 ALTER TABLE `grupo_precificacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `grupo_precificacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inativacao`
--

DROP TABLE IF EXISTS `inativacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inativacao` (
  `id_inativacao` int(11) NOT NULL AUTO_INCREMENT,
  `justificativa` varchar(200) NOT NULL,
  `id_livro` int(11) NOT NULL,
  PRIMARY KEY (`id_inativacao`),
  KEY `fk_inativacao_livro_idx` (`id_livro`),
  CONSTRAINT `fk_inativacao_livro` FOREIGN KEY (`id_livro`) REFERENCES `livro` (`id_livro`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inativacao`
--

LOCK TABLES `inativacao` WRITE;
/*!40000 ALTER TABLE `inativacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `inativacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `livro`
--

DROP TABLE IF EXISTS `livro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `livro` (
  `id_livro` int(11) NOT NULL AUTO_INCREMENT,
  `ano` varchar(4) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `edicao` varchar(45) NOT NULL,
  `isbn` varchar(13) NOT NULL,
  `num_paginas` varchar(45) NOT NULL,
  `fl_ativo` tinyint(1) NOT NULL,
  `quantidade` mediumtext NOT NULL,
  `id_dimensoes` int(11) NOT NULL,
  `id_grupo_precificacao` int(11) NOT NULL,
  `id_precificacao` int(11) NOT NULL,
  `id_editora` int(11) NOT NULL,
  PRIMARY KEY (`id_livro`),
  KEY `fk_livro_dimensoes_idx` (`id_dimensoes`),
  KEY `fk_livro_grupo_precificacao1_idx` (`id_grupo_precificacao`),
  KEY `fk_livro_precificacao_idx` (`id_precificacao`),
  KEY `fk_livro_editora_idx` (`id_editora`),
  CONSTRAINT `fk_livro_dimensoes` FOREIGN KEY (`id_dimensoes`) REFERENCES `dimensoes` (`id_dimensoes`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_editora` FOREIGN KEY (`id_editora`) REFERENCES `editora` (`id_editora`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_grupo_precificacao` FOREIGN KEY (`id_grupo_precificacao`) REFERENCES `grupo_precificacao` (`id_grupo_precificacao`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_precificacao` FOREIGN KEY (`id_precificacao`) REFERENCES `precificacao` (`id_precificacao`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livro`
--

LOCK TABLES `livro` WRITE;
/*!40000 ALTER TABLE `livro` DISABLE KEYS */;
/*!40000 ALTER TABLE `livro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `livro_categoria`
--

DROP TABLE IF EXISTS `livro_categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `livro_categoria` (
  `id_livro` int(11) NOT NULL,
  `id_categoria` int(11) NOT NULL,
  PRIMARY KEY (`id_livro`,`id_categoria`),
  KEY `fk_livro_categoria_categoria_idx` (`id_categoria`),
  CONSTRAINT `fk_livro_categoria_categoria` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_categoria_livro` FOREIGN KEY (`id_livro`) REFERENCES `livro` (`id_livro`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livro_categoria`
--

LOCK TABLES `livro_categoria` WRITE;
/*!40000 ALTER TABLE `livro_categoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `livro_categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pessoa`
--

DROP TABLE IF EXISTS `pessoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pessoa` (
  `id_pessoa` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `dt_cadastro` datetime DEFAULT NULL,
  PRIMARY KEY (`id_pessoa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa`
--

LOCK TABLES `pessoa` WRITE;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
/*!40000 ALTER TABLE `pessoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pessoa_fisica`
--

DROP TABLE IF EXISTS `pessoa_fisica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pessoa_fisica` (
  `id_pessoa_fisica` int(11) NOT NULL AUTO_INCREMENT,
  `cpf` varchar(45) NOT NULL,
  `dt_nascimento` date NOT NULL,
  `id_pessoa` int(11) NOT NULL,
  PRIMARY KEY (`id_pessoa_fisica`),
  KEY `fk_pessoa_pf_idx` (`id_pessoa`),
  CONSTRAINT `fk_pessoa_pf` FOREIGN KEY (`id_pessoa`) REFERENCES `pessoa` (`id_pessoa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa_fisica`
--

LOCK TABLES `pessoa_fisica` WRITE;
/*!40000 ALTER TABLE `pessoa_fisica` DISABLE KEYS */;
/*!40000 ALTER TABLE `pessoa_fisica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pessoa_juridica`
--

DROP TABLE IF EXISTS `pessoa_juridica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pessoa_juridica` (
  `id_pessoa_juridica` int(11) NOT NULL AUTO_INCREMENT,
  `cnpj` varchar(45) NOT NULL,
  `razao_social` varchar(45) NOT NULL,
  `id_pessoa` int(11) NOT NULL,
  PRIMARY KEY (`id_pessoa_juridica`),
  KEY `fk_pessoa_pj_idx` (`id_pessoa`),
  CONSTRAINT `fk_pessoa_pj` FOREIGN KEY (`id_pessoa`) REFERENCES `pessoa` (`id_pessoa`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa_juridica`
--

LOCK TABLES `pessoa_juridica` WRITE;
/*!40000 ALTER TABLE `pessoa_juridica` DISABLE KEYS */;
/*!40000 ALTER TABLE `pessoa_juridica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `precificacao`
--

DROP TABLE IF EXISTS `precificacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `precificacao` (
  `id_precificacao` int(11) NOT NULL,
  `preco_custo` decimal(20,2) NOT NULL,
  `preco_venda` decimal(20,2) NOT NULL,
  PRIMARY KEY (`id_precificacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `precificacao`
--

LOCK TABLES `precificacao` WRITE;
/*!40000 ALTER TABLE `precificacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `precificacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subcategoria`
--

DROP TABLE IF EXISTS `subcategoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subcategoria` (
  `id_subcategoria` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `dt_cadastro` datetime NOT NULL,
  `id_categoria` int(11) NOT NULL,
  PRIMARY KEY (`id_subcategoria`),
  KEY `fk_subcategoria_categoria_idx` (`id_categoria`),
  CONSTRAINT `fk_subcategoria_categoria` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subcategoria`
--

LOCK TABLES `subcategoria` WRITE;
/*!40000 ALTER TABLE `subcategoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `subcategoria` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-21 12:25:38
