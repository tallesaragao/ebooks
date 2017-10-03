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
  `id_pessoa_fisica` int(11) NOT NULL,
  PRIMARY KEY (`id_autor`),
  KEY `fk_pf_autor_idx` (`id_pessoa_fisica`),
  CONSTRAINT `fk_pf_autor` FOREIGN KEY (`id_pessoa_fisica`) REFERENCES `pessoa_fisica` (`id_pessoa_fisica`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autor`
--

LOCK TABLES `autor` WRITE;
/*!40000 ALTER TABLE `autor` DISABLE KEYS */;
INSERT INTO `autor` VALUES (2,2),(3,3);
/*!40000 ALTER TABLE `autor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bandeira`
--

DROP TABLE IF EXISTS `bandeira`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bandeira` (
  `id_bandeira` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`id_bandeira`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bandeira`
--

LOCK TABLES `bandeira` WRITE;
/*!40000 ALTER TABLE `bandeira` DISABLE KEYS */;
/*!40000 ALTER TABLE `bandeira` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartao_credito`
--

DROP TABLE IF EXISTS `cartao_credito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cartao_credito` (
  `id_cartao_credito` int(11) NOT NULL AUTO_INCREMENT,
  `numero` varchar(45) NOT NULL,
  `nome_titular` varchar(45) NOT NULL,
  `dt_vencimento` date NOT NULL,
  `codigo_seguranca` varchar(45) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `id_bandeira` int(11) NOT NULL,
  `dt_cadastro` datetime NOT NULL,
  PRIMARY KEY (`id_cartao_credito`),
  KEY `fk_cartao_credito_cliente_idx` (`id_cliente`),
  KEY `fk_cartao_credito_bandeira_idx` (`id_bandeira`),
  CONSTRAINT `fk_cartao_credito_bandeira` FOREIGN KEY (`id_bandeira`) REFERENCES `bandeira` (`id_bandeira`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cartao_credito_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartao_credito`
--

LOCK TABLES `cartao_credito` WRITE;
/*!40000 ALTER TABLE `cartao_credito` DISABLE KEYS */;
/*!40000 ALTER TABLE `cartao_credito` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (5,'Didático','2017-09-21 00:00:00'),(6,'Romance','2017-09-21 00:00:00'),(7,'Aventura','2017-09-21 00:00:00'),(8,'Terror','2017-09-21 11:18:42'),(9,'Ficção','2017-09-22 11:20:57'),(10,'Biografia','2017-09-22 11:21:13'),(11,'Autoajuda','2017-09-29 11:11:58'),(12,'Teste','2017-10-02 08:42:01');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `genero` char(1) NOT NULL,
  `fl_ativo` tinyint(1) NOT NULL,
  `id_pessoa_fisica` int(11) NOT NULL,
  `id_login` int(11) NOT NULL,
  `id_telefone` int(11) NOT NULL,
  PRIMARY KEY (`id_cliente`),
  KEY `fk_cliente_pessoa_fisica_idx` (`id_pessoa_fisica`),
  KEY `fk_cliente_login_idx` (`id_login`),
  KEY `fk_cliente_telefone_idx` (`id_telefone`),
  CONSTRAINT `fk_cliente_login` FOREIGN KEY (`id_login`) REFERENCES `login` (`id_login`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cliente_pessoa_fisica` FOREIGN KEY (`id_pessoa_fisica`) REFERENCES `pessoa_fisica` (`id_pessoa_fisica`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cliente_telefone` FOREIGN KEY (`id_telefone`) REFERENCES `telefone` (`id_telefone`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'tobias.leoso@gmail.com','M',1,9,7,3),(2,'ebooks@gmail.com','M',1,10,8,4);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente_endereco`
--

DROP TABLE IF EXISTS `cliente_endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente_endereco` (
  `id_cliente` int(11) NOT NULL,
  `id_endereco` int(11) NOT NULL,
  PRIMARY KEY (`id_cliente`,`id_endereco`),
  KEY `fk_cliente_endereco_cliente_idx` (`id_cliente`),
  KEY `fk_cliente_endereco_endereco_idx` (`id_endereco`),
  CONSTRAINT `fk_cliente_endereco_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cliente_endereco_endereco` FOREIGN KEY (`id_endereco`) REFERENCES `endereco` (`id_endereco`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente_endereco`
--

LOCK TABLES `cliente_endereco` WRITE;
/*!40000 ALTER TABLE `cliente_endereco` DISABLE KEYS */;
INSERT INTO `cliente_endereco` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `cliente_endereco` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dimensoes`
--

LOCK TABLES `dimensoes` WRITE;
/*!40000 ALTER TABLE `dimensoes` DISABLE KEYS */;
INSERT INTO `dimensoes` VALUES (4,20.6,13.4,118,0.8),(5,12.2,6.6,150,5.2);
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `editora`
--

LOCK TABLES `editora` WRITE;
/*!40000 ALTER TABLE `editora` DISABLE KEYS */;
INSERT INTO `editora` VALUES (4,4),(5,5);
/*!40000 ALTER TABLE `editora` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endereco`
--

DROP TABLE IF EXISTS `endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endereco` (
  `id_endereco` int(11) NOT NULL AUTO_INCREMENT,
  `identificacao` varchar(45) NOT NULL,
  `logradouro` varchar(45) NOT NULL,
  `numero` varchar(45) NOT NULL,
  `complemento` varchar(45) DEFAULT NULL,
  `bairro` varchar(45) NOT NULL,
  `cep` varchar(45) NOT NULL,
  `cidade` varchar(45) NOT NULL,
  `estado` varchar(45) NOT NULL,
  `pais` varchar(45) NOT NULL,
  `fl_principal` tinyint(1) NOT NULL,
  `dt_cadastro` datetime NOT NULL,
  `id_tipo_endereco` int(11) NOT NULL,
  PRIMARY KEY (`id_endereco`),
  KEY `fk_endereco_tipo_endereco_idx` (`id_tipo_endereco`),
  CONSTRAINT `fk_endereco_tipo_endereco` FOREIGN KEY (`id_tipo_endereco`) REFERENCES `tipo_endereco` (`id_tipo_endereco`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
INSERT INTO `endereco` VALUES (1,'Minha casa','Rua teste','151',NULL,'Bairro teste','08720-040','Poá','SP','Brasil',1,'2017-10-03 12:33:46',1),(2,'Meu trabalho','Rua testinha','1741',NULL,'Bairro teste','08720-045','Poá','SP','Brasil',0,'2017-10-03 12:37:45',2);
/*!40000 ALTER TABLE `endereco` ENABLE KEYS */;
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
  `nome` varchar(45) NOT NULL,
  `dt_cadastro` datetime DEFAULT NULL,
  PRIMARY KEY (`id_grupo_precificacao`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupo_precificacao`
--

LOCK TABLES `grupo_precificacao` WRITE;
/*!40000 ALTER TABLE `grupo_precificacao` DISABLE KEYS */;
INSERT INTO `grupo_precificacao` VALUES (1,15.5,'E','2017-09-25 08:35:22'),(2,30,'D','2017-09-25 08:35:24'),(3,45,'C','2017-09-25 08:35:25'),(4,60,'B','2017-09-25 08:35:26'),(5,80,'A','2017-09-25 08:35:27'),(6,100,'S','2017-09-25 08:35:28');
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
  `codigo` varchar(45) NOT NULL,
  `sinopse` varchar(500) NOT NULL DEFAULT 'sinopse teste',
  `id_dimensoes` int(11) NOT NULL,
  `id_precificacao` int(11) NOT NULL,
  `id_grupo_precificacao` int(11) NOT NULL,
  `id_editora` int(11) NOT NULL,
  `dt_cadastro` datetime NOT NULL,
  PRIMARY KEY (`id_livro`),
  KEY `fk_livro_dimensoes_idx` (`id_dimensoes`),
  KEY `fk_livro_grupo_precificacao1_idx` (`id_grupo_precificacao`),
  KEY `fk_livro_editora_idx` (`id_editora`),
  KEY `fk_livro_precificacao_idx` (`id_precificacao`),
  CONSTRAINT `fk_livro_dimensoes` FOREIGN KEY (`id_dimensoes`) REFERENCES `dimensoes` (`id_dimensoes`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_editora` FOREIGN KEY (`id_editora`) REFERENCES `editora` (`id_editora`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_grupo_precificacao` FOREIGN KEY (`id_grupo_precificacao`) REFERENCES `grupo_precificacao` (`id_grupo_precificacao`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_precificacao` FOREIGN KEY (`id_precificacao`) REFERENCES `precificacao` (`id_precificacao`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livro`
--

LOCK TABLES `livro` WRITE;
/*!40000 ALTER TABLE `livro` DISABLE KEYS */;
INSERT INTO `livro` VALUES (2,'1881','Memórias Póstumas de Brás Cubas','3ª','9788526280335','96',1,'15','4610-11597358','Publicado em 1881, este romance é o marco do realismo brasileiro. Brás Cubas, homem abastado que nunca precisou trabalhar, escreve uma autobiografia depois de morrer. Na forma de lembranças fragmentadas, o defunto relata passagens de uma vida cheia de mesquinharias e insucessos. Com sua ironia característica, Machado apresenta uma visão cruel da natureza humana.',4,2,4,4,'2017-09-25 00:00:00'),(3,'2009','Capitães da Areia','1ª','9788535914061','288',1,'10','56-32641964','sinopse teste',5,3,5,5,'2017-09-25 00:00:00');
/*!40000 ALTER TABLE `livro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `livro_autor`
--

DROP TABLE IF EXISTS `livro_autor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `livro_autor` (
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
-- Dumping data for table `livro_autor`
--

LOCK TABLES `livro_autor` WRITE;
/*!40000 ALTER TABLE `livro_autor` DISABLE KEYS */;
INSERT INTO `livro_autor` VALUES (2,2),(3,3);
/*!40000 ALTER TABLE `livro_autor` ENABLE KEYS */;
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
INSERT INTO `livro_categoria` VALUES (2,6),(3,6),(2,10);
/*!40000 ALTER TABLE `livro_categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `id_login` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(45) NOT NULL,
  `senha` varchar(45) NOT NULL,
  `dt_cadastro` datetime NOT NULL,
  PRIMARY KEY (`id_login`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (4,'teste','Abcd1234%','2017-10-02 08:39:32'),(6,'admin','Admin12#','2017-10-02 00:00:00'),(7,'tobias','Saibot00&','2017-10-02 00:00:00'),(8,'ebooks','Ebooks123$','2017-10-03 00:00:00');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa`
--

LOCK TABLES `pessoa` WRITE;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO `pessoa` VALUES (5,'Scipione','2017-09-25 00:00:00'),(6,'Machado de Assis','2017-09-25 00:00:00'),(7,'Companhia de Bolso','2017-09-25 00:00:00'),(8,'Jorge Amado','2017-09-25 00:00:00'),(15,'Tobias Toldo Leoso','2017-10-03 00:00:00'),(16,'Ebooks Silvério da Cunha','2017-10-03 00:00:00');
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa_fisica`
--

LOCK TABLES `pessoa_fisica` WRITE;
/*!40000 ALTER TABLE `pessoa_fisica` DISABLE KEYS */;
INSERT INTO `pessoa_fisica` VALUES (2,'359.958.820-15','1839-06-21',6),(3,'303.010.470-22','1912-08-10',8),(9,'986.518.476-10','1954-08-04',15),(10,'717.722.414-90','1990-12-04',16);
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa_juridica`
--

LOCK TABLES `pessoa_juridica` WRITE;
/*!40000 ALTER TABLE `pessoa_juridica` DISABLE KEYS */;
INSERT INTO `pessoa_juridica` VALUES (4,'44.127.355/0004-64','Editora Scipione S.A.',5),(5,'55.702.451/0001-62','Companhia de Bolso LTDA.',7);
/*!40000 ALTER TABLE `pessoa_juridica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `precificacao`
--

DROP TABLE IF EXISTS `precificacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `precificacao` (
  `id_precificacao` int(11) NOT NULL AUTO_INCREMENT,
  `preco_custo` decimal(20,2) NOT NULL,
  `preco_venda` decimal(20,2) NOT NULL,
  PRIMARY KEY (`id_precificacao`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `precificacao`
--

LOCK TABLES `precificacao` WRITE;
/*!40000 ALTER TABLE `precificacao` DISABLE KEYS */;
INSERT INTO `precificacao` VALUES (2,10.00,16.00),(3,22.29,40.12);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subcategoria`
--

LOCK TABLES `subcategoria` WRITE;
/*!40000 ALTER TABLE `subcategoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `subcategoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `telefone`
--

DROP TABLE IF EXISTS `telefone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `telefone` (
  `id_telefone` int(11) NOT NULL AUTO_INCREMENT,
  `ddd` varchar(10) NOT NULL,
  `numero` varchar(15) NOT NULL,
  `id_tipo_telefone` int(11) NOT NULL,
  PRIMARY KEY (`id_telefone`),
  KEY `fk_telefone_tipo_telefone_idx` (`id_tipo_telefone`),
  CONSTRAINT `fk_telefone_tipo_telefone` FOREIGN KEY (`id_tipo_telefone`) REFERENCES `tipo_telefone` (`id_tipo_telefone`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `telefone`
--

LOCK TABLES `telefone` WRITE;
/*!40000 ALTER TABLE `telefone` DISABLE KEYS */;
INSERT INTO `telefone` VALUES (3,'(11)','94555-4656',1),(4,'(11)','99445-1212',1);
/*!40000 ALTER TABLE `telefone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_endereco`
--

DROP TABLE IF EXISTS `tipo_endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_endereco` (
  `id_tipo_endereco` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `dt_cadastro` datetime NOT NULL,
  PRIMARY KEY (`id_tipo_endereco`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_endereco`
--

LOCK TABLES `tipo_endereco` WRITE;
/*!40000 ALTER TABLE `tipo_endereco` DISABLE KEYS */;
INSERT INTO `tipo_endereco` VALUES (1,'Residencial','2017-10-02 11:40:25'),(2,'Comercial','2017-10-02 11:40:26');
/*!40000 ALTER TABLE `tipo_endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_telefone`
--

DROP TABLE IF EXISTS `tipo_telefone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_telefone` (
  `id_tipo_telefone` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`id_tipo_telefone`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_telefone`
--

LOCK TABLES `tipo_telefone` WRITE;
/*!40000 ALTER TABLE `tipo_telefone` DISABLE KEYS */;
INSERT INTO `tipo_telefone` VALUES (1,'Celular'),(2,'Fixo');
/*!40000 ALTER TABLE `tipo_telefone` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-03 12:47:00
