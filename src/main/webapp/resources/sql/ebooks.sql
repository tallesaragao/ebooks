CREATE DATABASE  IF NOT EXISTS `ebooks_les` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ebooks_les`;
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
  `id_livro` int(11) DEFAULT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_ativacao`),
  KEY `fk_ativacao_livro_idx` (`id_livro`),
  KEY `fk_ativacao_cliente_idx` (`id_cliente`),
  CONSTRAINT `fk_ativacao_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bandeira`
--

LOCK TABLES `bandeira` WRITE;
/*!40000 ALTER TABLE `bandeira` DISABLE KEYS */;
INSERT INTO `bandeira` VALUES (1,'Visa'),(2,'MasterCard');
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartao_credito`
--

LOCK TABLES `cartao_credito` WRITE;
/*!40000 ALTER TABLE `cartao_credito` DISABLE KEYS */;
INSERT INTO `cartao_credito` VALUES (3,'5268910398469748','João das Neves','2025-12-01','406',2,2,'2017-10-05 00:00:00'),(4,'5203083853111854','Tobias Toldo Leoso','2019-07-18','716',1,2,'2017-10-18 00:00:00'),(5,'4539799800186123','Tobias Toldo Leoso','2025-06-16','762',1,1,'2017-10-24 00:00:00'),(6,'4556933941512099','Samuel Nathan Vitor Almeida','2019-01-07','792',11,1,'2017-11-07 00:00:00'),(7,'5304897968349077','Samuel Nathan Vitor Almeida','2020-08-07','325',11,2,'2017-11-07 00:00:00'),(8,'3512250424426324','Samuel Nathan Vitor Almeida','2019-07-09','630',11,1,'2017-11-09 00:00:00'),(9,'4539694115943','Rodrigo Bruno Castro','2018-07-13','819',12,1,'2017-11-13 00:00:00'),(10,'5363763649851451','Rodrigo Bruno Castro','2019-03-13','443',12,2,'2017-11-13 00:00:00');
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (5,'Didático','2017-09-21 00:00:00'),(6,'Romance','2017-09-21 00:00:00'),(7,'Aventura','2017-09-21 00:00:00'),(8,'Terror','2017-09-21 11:18:42'),(9,'Ficção','2017-09-22 11:20:57'),(10,'Biografia','2017-09-22 11:21:13'),(11,'Autoajuda','2017-09-29 11:11:58');
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
  `id_telefone` int(11) NOT NULL,
  `id_login` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_cliente`),
  KEY `fk_cliente_pessoa_fisica_idx` (`id_pessoa_fisica`),
  KEY `fk_cliente_telefone_idx` (`id_telefone`),
  KEY `fk_cliente_login_idx` (`id_login`),
  CONSTRAINT `fk_cliente_login` FOREIGN KEY (`id_login`) REFERENCES `login` (`id_login`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cliente_pessoa_fisica` FOREIGN KEY (`id_pessoa_fisica`) REFERENCES `pessoa_fisica` (`id_pessoa_fisica`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cliente_telefone` FOREIGN KEY (`id_telefone`) REFERENCES `telefone` (`id_telefone`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'tobias.leoso@gmail.com','M',1,9,3,7),(2,'ebooks@gmail.com','M',0,10,4,8),(4,'maria@outlook.com','F',0,13,7,9),(11,'snvalmeida@viacorte.com.br','M',1,21,15,10),(12,'rodrigobc79@gmail.com','M',1,26,20,11);
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
INSERT INTO `cliente_endereco` VALUES (1,1),(1,17),(1,18),(2,4),(4,5),(11,12),(11,19),(11,20),(12,13),(12,21);
/*!40000 ALTER TABLE `cliente_endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cupom_promo`
--

DROP TABLE IF EXISTS `cupom_promo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cupom_promo` (
  `id_cupom_promo` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(45) NOT NULL,
  `porcent_desc` double NOT NULL,
  `validade` date NOT NULL,
  `ativo` tinyint(4) NOT NULL,
  PRIMARY KEY (`id_cupom_promo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cupom_promo`
--

LOCK TABLES `cupom_promo` WRITE;
/*!40000 ALTER TABLE `cupom_promo` DISABLE KEYS */;
INSERT INTO `cupom_promo` VALUES (1,'10OFF',10,'2018-01-25',1),(2,'5OFF',5,'2017-11-25',1),(3,'15OFF',15,'2017-12-25',1),(4,'20OFF',20,'2017-12-25',0);
/*!40000 ALTER TABLE `cupom_promo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cupom_troca`
--

DROP TABLE IF EXISTS `cupom_troca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cupom_troca` (
  `id_cupom_troca` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(45) NOT NULL,
  `valor` decimal(20,2) NOT NULL,
  `validade` date NOT NULL,
  `ativo` tinyint(4) NOT NULL,
  `id_cliente` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_cupom_troca`),
  KEY `fk_cupom_troca_cliente_idx` (`id_cliente`),
  CONSTRAINT `fk_cupom_troca_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cupom_troca`
--

LOCK TABLES `cupom_troca` WRITE;
/*!40000 ALTER TABLE `cupom_troca` DISABLE KEYS */;
INSERT INTO `cupom_troca` VALUES (1,'9PQZ0HDK3K',200.00,'2018-05-30',1,1),(2,'BLJLUFAC51',500.00,'2018-02-19',1,1),(3,'EZN0FYJ0OQ',100.00,'2018-07-06',1,1),(4,'Z2FB4APKGR',150.00,'2018-07-06',0,1),(5,'WHRUWBFOTD',280.60,'2099-01-01',1,11),(8,'XQGVEMSJAM',88.12,'2099-01-01',1,1),(9,'JXEVUMLSCU',56.12,'2099-01-01',1,12);
/*!40000 ALTER TABLE `cupom_troca` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
INSERT INTO `endereco` VALUES (1,'Minha casa','Servidão Quatro','1500','AP 1206','União das Vilas','97509-382','Uruguaiana','RS','Brasil',1,'2017-10-03 12:33:46',1),(4,'Trabalho','SQS 205 Bloco A','99','Sala 33','Asa Sul','70235-010','Brasília','DF','Brasil',1,'2017-10-05 00:00:00',2),(5,'Casa','Viela Peri','500','Casa 13','Jardim Veneza','08715-200','Mogi das Cruzes','SP','Brasil',1,'2017-10-05 00:00:00',1),(12,'Minha casa','Rua Maria Aprígia Vieira','719','Casa 12','Jardim Novo Horizonte','79822-417','Dourados','MS','Brasil',1,'2017-10-16 00:00:00',1),(13,'Apartamento','Rua Tomé de Almeida e Oliveira','191','Apartamento 502','Vila Zat','02976-190','São Paulo','SP','Brasil',1,'2017-10-17 00:00:00',1),(17,'Casa de férias','Avenida Paraná','155','TORRE 7 AP 1008','Centro','69945-970','Acrelândia','AC','Brasil',1,'2017-10-23 00:00:00',1),(18,'Casa de aluguel','Quadra 133','841','Casa 7','Morada das Garças','72883-355','Cidade Ocidental','GO','Brasil',1,'2017-10-24 00:00:00',1),(19,'Casa mãe','Rua Benedito Aparecido de Oliveira','213','','Jardim Galego','13971-262','Itapira','SP','Brasil',1,'2017-11-07 00:00:00',1),(20,'Casa de passeio','Rua Diário de Pernambuco','565','','Santo Antônio','50010-300','Recife','PE','Brasil',1,'2017-11-09 00:00:00',1),(21,'Casa SP','Praça Maria José Moreira','554','','Pinheiros','05421-065','São Paulo','SP','Brasil',1,'2017-11-13 00:00:00',1);
/*!40000 ALTER TABLE `endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estoque`
--

DROP TABLE IF EXISTS `estoque`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estoque` (
  `id_estoque` int(11) NOT NULL AUTO_INCREMENT,
  `quant_min` mediumtext NOT NULL,
  `quant_max` mediumtext NOT NULL,
  `quant_atual` mediumtext NOT NULL,
  `quant_reserva` mediumtext NOT NULL,
  PRIMARY KEY (`id_estoque`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estoque`
--

LOCK TABLES `estoque` WRITE;
/*!40000 ALTER TABLE `estoque` DISABLE KEYS */;
INSERT INTO `estoque` VALUES (1,'0','300','90','2'),(2,'0','30','9','0');
/*!40000 ALTER TABLE `estoque` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forma_pag`
--

DROP TABLE IF EXISTS `forma_pag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forma_pag` (
  `id_forma_pag` int(11) NOT NULL AUTO_INCREMENT,
  `parcelas` mediumtext NOT NULL,
  PRIMARY KEY (`id_forma_pag`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forma_pag`
--

LOCK TABLES `forma_pag` WRITE;
/*!40000 ALTER TABLE `forma_pag` DISABLE KEYS */;
INSERT INTO `forma_pag` VALUES (7,'1'),(8,'1'),(9,'1'),(10,'1'),(14,'1');
/*!40000 ALTER TABLE `forma_pag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `frete`
--

DROP TABLE IF EXISTS `frete`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `frete` (
  `id_frete` int(11) NOT NULL AUTO_INCREMENT,
  `valor` decimal(20,2) NOT NULL,
  `dias_entrega` mediumtext NOT NULL,
  `prazo_estimado` date NOT NULL,
  PRIMARY KEY (`id_frete`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `frete`
--

LOCK TABLES `frete` WRITE;
/*!40000 ALTER TABLE `frete` DISABLE KEYS */;
INSERT INTO `frete` VALUES (8,38.00,'10','2017-11-11'),(9,7.00,'2','2017-11-09'),(10,24.00,'8','2017-11-17'),(11,5.00,'2','2017-11-15'),(15,12.00,'5','2017-11-27');
/*!40000 ALTER TABLE `frete` ENABLE KEYS */;
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
  `id_livro` int(11) DEFAULT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_inativacao`),
  KEY `fk_inativacao_livro_idx` (`id_livro`),
  KEY `fk_inativacao_cliente_idx` (`id_cliente`),
  CONSTRAINT `fk_inativacao_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
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
-- Table structure for table `item_pedido`
--

DROP TABLE IF EXISTS `item_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_pedido` (
  `id_item_pedido` int(11) NOT NULL AUTO_INCREMENT,
  `quantidade` mediumtext NOT NULL,
  `subtotal` decimal(20,2) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `id_livro` int(11) NOT NULL,
  PRIMARY KEY (`id_item_pedido`),
  KEY `fk_item_pedido_pedido_idx` (`id_pedido`),
  KEY `fk_item_pedido_livro_idx` (`id_livro`),
  CONSTRAINT `fk_item_pedido_livro` FOREIGN KEY (`id_livro`) REFERENCES `livro` (`id_livro`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_item_pedido_pedido` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_pedido`
--

LOCK TABLES `item_pedido` WRITE;
/*!40000 ALTER TABLE `item_pedido` DISABLE KEYS */;
INSERT INTO `item_pedido` VALUES (1,'1',16.00,3,2),(2,'1',40.12,3,3),(3,'5',80.00,4,2),(4,'5',200.60,4,3),(5,'5',80.00,5,2),(6,'3',120.36,5,3),(7,'1',16.00,6,2),(8,'1',40.12,6,3),(9,'7',112.00,7,2),(10,'1',40.12,7,3);
/*!40000 ALTER TABLE `item_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_troca`
--

DROP TABLE IF EXISTS `item_troca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_troca` (
  `id_item_troca` int(11) NOT NULL AUTO_INCREMENT,
  `quant_trocada` mediumtext NOT NULL,
  `quant_retornavel` mediumtext NOT NULL,
  `id_item_pedido` int(11) NOT NULL,
  `id_troca` int(11) NOT NULL,
  PRIMARY KEY (`id_item_troca`),
  KEY `fk_item_troca_item_pedido_idx` (`id_item_pedido`),
  KEY `fk_item_troca_troca_idx` (`id_troca`),
  CONSTRAINT `fk_item_troca_item_pedido` FOREIGN KEY (`id_item_pedido`) REFERENCES `item_pedido` (`id_item_pedido`),
  CONSTRAINT `fk_item_troca_troca` FOREIGN KEY (`id_troca`) REFERENCES `troca` (`id_troca`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_troca`
--

LOCK TABLES `item_troca` WRITE;
/*!40000 ALTER TABLE `item_troca` DISABLE KEYS */;
INSERT INTO `item_troca` VALUES (9,'5','5',3,8),(10,'5','5',4,8),(11,'1','0',7,9),(12,'1','0',8,9),(13,'3','0',9,11),(14,'1','0',10,11);
/*!40000 ALTER TABLE `item_troca` ENABLE KEYS */;
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
  `id_estoque` int(11) DEFAULT NULL,
  `dt_cadastro` datetime NOT NULL,
  PRIMARY KEY (`id_livro`),
  KEY `fk_livro_dimensoes_idx` (`id_dimensoes`),
  KEY `fk_livro_grupo_precificacao1_idx` (`id_grupo_precificacao`),
  KEY `fk_livro_editora_idx` (`id_editora`),
  KEY `fk_livro_precificacao_idx` (`id_precificacao`),
  KEY `fk_livro_estoque_idx` (`id_estoque`),
  CONSTRAINT `fk_livro_dimensoes` FOREIGN KEY (`id_dimensoes`) REFERENCES `dimensoes` (`id_dimensoes`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_editora` FOREIGN KEY (`id_editora`) REFERENCES `editora` (`id_editora`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_estoque` FOREIGN KEY (`id_estoque`) REFERENCES `estoque` (`id_estoque`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_grupo_precificacao` FOREIGN KEY (`id_grupo_precificacao`) REFERENCES `grupo_precificacao` (`id_grupo_precificacao`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_livro_precificacao` FOREIGN KEY (`id_precificacao`) REFERENCES `precificacao` (`id_precificacao`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livro`
--

LOCK TABLES `livro` WRITE;
/*!40000 ALTER TABLE `livro` DISABLE KEYS */;
INSERT INTO `livro` VALUES (2,'1881','Memórias Póstumas de Brás Cubas','3ª','9788526280335','96',1,'15','4610-11597358','Publicado em 1881, este romance é o marco do realismo brasileiro. Brás Cubas, homem abastado que nunca precisou trabalhar, escreve uma autobiografia depois de morrer. Na forma de lembranças fragmentadas, o defunto relata passagens de uma vida cheia de mesquinharias e insucessos. Com sua ironia característica, Machado apresenta uma visão cruel da natureza humana.',4,2,4,4,1,'2017-09-25 00:00:00'),(3,'2009','Capitães da Areia','1ª','9788535914061','288',1,'10','56-32641964','sinopse teste',5,3,5,5,2,'2017-09-25 00:00:00');
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
INSERT INTO `livro_categoria` VALUES (2,6),(3,6);
/*!40000 ALTER TABLE `livro_categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log` (
  `id_log` int(11) NOT NULL AUTO_INCREMENT,
  `nome_entidade` varchar(45) NOT NULL,
  `usuario` varchar(45) NOT NULL,
  `operacao` varchar(45) NOT NULL,
  `dt_cadastro` datetime NOT NULL,
  `estado_anterior` text NOT NULL,
  PRIMARY KEY (`id_log`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
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
  `id_perfil_acesso` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_login`),
  KEY `fk_login_perfil_acesso_idx` (`id_perfil_acesso`),
  CONSTRAINT `fk_login_perfil_acesso` FOREIGN KEY (`id_perfil_acesso`) REFERENCES `perfil_acesso` (`id_perfil_acesso`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (6,'admin','Admin12#','2017-10-02 00:00:00',1),(7,'tobias','Saibot00&','2017-10-02 00:00:00',2),(8,'ebooks','Ebooks123$','2017-10-03 00:00:00',2),(9,'maria','Maria12#','2017-10-05 00:00:00',2),(10,'snvalmeida','Guu2z3C0aY!','2017-10-16 00:00:00',2),(11,'rbcastro','O8RRDWF6g5@','2017-10-17 00:00:00',2);
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pag_cartao`
--

DROP TABLE IF EXISTS `pag_cartao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pag_cartao` (
  `id_pagamento` int(11) NOT NULL,
  `id_cartao_credito` int(11) NOT NULL,
  PRIMARY KEY (`id_pagamento`),
  KEY `fk_pag_cartao_pagamento_idx` (`id_pagamento`),
  KEY `fk_pag_cartao_cartao_credito_idx` (`id_cartao_credito`),
  CONSTRAINT `fk_pag_cartao_cartao_credito` FOREIGN KEY (`id_cartao_credito`) REFERENCES `cartao_credito` (`id_cartao_credito`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pag_cartao_pagamento` FOREIGN KEY (`id_pagamento`) REFERENCES `pagamento` (`id_pagamento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pag_cartao`
--

LOCK TABLES `pag_cartao` WRITE;
/*!40000 ALTER TABLE `pag_cartao` DISABLE KEYS */;
INSERT INTO `pag_cartao` VALUES (10,4),(11,5),(22,5),(12,6),(14,6),(13,7),(15,7),(16,8),(17,9),(18,10);
/*!40000 ALTER TABLE `pag_cartao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pag_vale_compras`
--

DROP TABLE IF EXISTS `pag_vale_compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pag_vale_compras` (
  `id_pagamento` int(11) NOT NULL,
  `id_vale_compras` int(11) NOT NULL,
  PRIMARY KEY (`id_pagamento`),
  KEY `fk_pag_vale_compras_pagamento_idx` (`id_pagamento`),
  KEY `fk_pag_vale_compras_vale_compras_idx` (`id_vale_compras`),
  CONSTRAINT `fk_pag_vale_compras_pagamento` FOREIGN KEY (`id_pagamento`) REFERENCES `pagamento` (`id_pagamento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pag_vale_compras_vale_compras` FOREIGN KEY (`id_vale_compras`) REFERENCES `cupom_troca` (`id_cupom_troca`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pag_vale_compras`
--

LOCK TABLES `pag_vale_compras` WRITE;
/*!40000 ALTER TABLE `pag_vale_compras` DISABLE KEYS */;
/*!40000 ALTER TABLE `pag_vale_compras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagamento`
--

DROP TABLE IF EXISTS `pagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagamento` (
  `id_pagamento` int(11) NOT NULL AUTO_INCREMENT,
  `valor_pago` decimal(20,2) NOT NULL,
  `id_forma_pag` int(11) NOT NULL,
  PRIMARY KEY (`id_pagamento`),
  KEY `fk_pagamento_forma_pag_idx` (`id_forma_pag`),
  CONSTRAINT `fk_pagamento_forma_pag` FOREIGN KEY (`id_forma_pag`) REFERENCES `forma_pag` (`id_forma_pag`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagamento`
--

LOCK TABLES `pagamento` WRITE;
/*!40000 ALTER TABLE `pagamento` DISABLE KEYS */;
INSERT INTO `pagamento` VALUES (10,227.50,7),(11,200.01,7),(12,200.80,8),(13,58.04,8),(14,70.70,9),(15,100.00,9),(16,20.00,9),(17,27.50,10),(18,27.50,10),(22,164.12,14);
/*!40000 ALTER TABLE `pagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pedido` (
  `id_pedido` int(11) NOT NULL AUTO_INCREMENT,
  `valor_total` decimal(20,2) NOT NULL,
  `numero` varchar(45) NOT NULL,
  `id_endereco_entrega` int(11) NOT NULL,
  `id_endereco_cobranca` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `id_cupom_promo` int(11) DEFAULT NULL,
  `id_frete` int(11) NOT NULL,
  `id_forma_pag` int(11) NOT NULL,
  `dt_cadastro` datetime NOT NULL,
  PRIMARY KEY (`id_pedido`),
  KEY `fk_pedido_endereco_entrega_idx` (`id_endereco_entrega`),
  KEY `fk_pedido_endereco_cobranca_idx` (`id_endereco_cobranca`),
  KEY `fk_pedido_cliente_idx` (`id_cliente`),
  KEY `fk_pedido_cupom_promo_idx` (`id_cupom_promo`),
  KEY `fk_pedido_frete_idx` (`id_frete`),
  KEY `fk_pedido_forma_pag_idx` (`id_forma_pag`),
  CONSTRAINT `fk_pedido_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedido_cupom_promo` FOREIGN KEY (`id_cupom_promo`) REFERENCES `cupom_promo` (`id_cupom_promo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedido_endereco_cobranca` FOREIGN KEY (`id_endereco_cobranca`) REFERENCES `endereco` (`id_endereco`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedido_endereco_entrega` FOREIGN KEY (`id_endereco_entrega`) REFERENCES `endereco` (`id_endereco`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedido_forma_pag` FOREIGN KEY (`id_forma_pag`) REFERENCES `forma_pag` (`id_forma_pag`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedido_frete` FOREIGN KEY (`id_frete`) REFERENCES `frete` (`id_frete`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (3,427.51,'31840183347',17,17,1,3,8,7,'2017-11-01 00:00:00'),(4,258.84,'465191606753',19,19,11,1,9,8,'2017-11-07 00:00:00'),(5,190.70,'3161645033',20,20,11,3,10,9,'2017-11-09 00:00:00'),(6,55.00,'31065042643',21,21,12,1,11,10,'2017-11-13 00:00:00'),(7,164.12,'1315706621',1,1,1,NULL,15,14,'2017-11-22 00:00:00');
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil_acesso`
--

DROP TABLE IF EXISTS `perfil_acesso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfil_acesso` (
  `id_perfil_acesso` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`id_perfil_acesso`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil_acesso`
--

LOCK TABLES `perfil_acesso` WRITE;
/*!40000 ALTER TABLE `perfil_acesso` DISABLE KEYS */;
INSERT INTO `perfil_acesso` VALUES (1,'Administrador'),(2,'Cliente');
/*!40000 ALTER TABLE `perfil_acesso` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa`
--

LOCK TABLES `pessoa` WRITE;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO `pessoa` VALUES (5,'Scipione','2017-09-25 00:00:00'),(6,'Machado de Assis','2017-09-25 00:00:00'),(7,'Companhia de Bolso','2017-09-25 00:00:00'),(8,'Jorge Amado','2017-09-25 00:00:00'),(15,'Tobias Toldo Leoso','2017-10-03 00:00:00'),(16,'Ebooks Silvério da Cunha','2017-10-03 00:00:00'),(19,'Maria das Dores','2017-10-05 00:00:00'),(27,'Samuel Nathan Vitor Almeida','2017-10-16 00:00:00'),(32,'Rodrigo Bruno Castro','2017-10-17 00:00:00');
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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa_fisica`
--

LOCK TABLES `pessoa_fisica` WRITE;
/*!40000 ALTER TABLE `pessoa_fisica` DISABLE KEYS */;
INSERT INTO `pessoa_fisica` VALUES (2,'359.958.820-15','1839-06-21',6),(3,'303.010.470-22','1912-08-10',8),(9,'986.518.476-10','1954-08-04',15),(10,'717.722.414-90','1990-12-04',16),(13,'078.737.835-61','1998-11-16',19),(21,'447.011.053-17','1986-05-01',27),(26,'000.881.868-14','1987-02-17',32);
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
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `id_status` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `dt_cadastro` datetime NOT NULL,
  PRIMARY KEY (`id_status`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'Em processamento','2017-11-07 19:08:46'),(2,'Aprovada','2017-11-08 08:40:04'),(3,'Reprovada','2017-11-08 08:41:34'),(4,'Em transporte','2017-11-08 08:41:52'),(5,'Entregue','2017-11-08 08:41:56'),(6,'Em troca','2017-11-08 08:42:00'),(7,'Trocado','2017-11-22 08:46:03');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_pedido`
--

DROP TABLE IF EXISTS `status_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status_pedido` (
  `id_status_pedido` int(11) NOT NULL AUTO_INCREMENT,
  `atual` tinyint(4) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `id_status` int(11) NOT NULL,
  `dt_cadastro` datetime NOT NULL,
  PRIMARY KEY (`id_status_pedido`),
  KEY `fk_status_pedido_pedido_idx` (`id_pedido`),
  KEY `fk_status_pedido_status_idx` (`id_status`),
  CONSTRAINT `fk_status_pedido_pedido` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_status_pedido_status` FOREIGN KEY (`id_status`) REFERENCES `status` (`id_status`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_pedido`
--

LOCK TABLES `status_pedido` WRITE;
/*!40000 ALTER TABLE `status_pedido` DISABLE KEYS */;
INSERT INTO `status_pedido` VALUES (1,0,3,1,'2017-11-01 00:00:00'),(2,0,4,1,'2017-11-07 00:00:00'),(5,0,4,2,'2017-11-08 00:00:00'),(6,0,3,2,'2017-11-08 00:00:00'),(7,0,5,1,'2017-11-09 00:00:00'),(8,0,5,2,'2017-11-09 00:00:00'),(9,0,4,4,'2017-11-09 00:00:00'),(10,0,5,4,'2017-11-09 00:00:00'),(11,1,5,5,'2017-11-09 00:00:00'),(12,0,4,5,'2017-11-13 00:00:00'),(13,0,6,1,'2017-11-13 00:00:00'),(14,0,6,2,'2017-11-13 00:00:00'),(15,0,3,4,'2017-11-16 00:00:00'),(16,1,3,5,'2017-11-16 00:00:00'),(17,1,4,6,'2017-11-22 09:13:35'),(18,0,6,4,'2017-11-22 00:00:00'),(19,0,6,5,'2017-11-22 00:00:00'),(20,1,6,6,'2017-11-22 09:19:49'),(21,0,7,1,'2017-11-22 00:00:00'),(22,0,7,2,'2017-11-22 00:00:00'),(23,0,7,4,'2017-11-22 00:00:00'),(24,1,7,5,'2017-11-22 00:00:00');
/*!40000 ALTER TABLE `status_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_troca`
--

DROP TABLE IF EXISTS `status_troca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status_troca` (
  `id_status_troca` int(11) NOT NULL AUTO_INCREMENT,
  `atual` tinyint(1) NOT NULL,
  `id_troca` int(11) NOT NULL,
  `id_status` int(11) NOT NULL,
  `dt_cadastro` datetime NOT NULL,
  PRIMARY KEY (`id_status_troca`),
  KEY `fk_status_troca_troca_idx` (`id_troca`),
  KEY `fk_status_troca_status_idx` (`id_status`),
  CONSTRAINT `fk_status_troca_status` FOREIGN KEY (`id_status`) REFERENCES `status` (`id_status`),
  CONSTRAINT `fk_status_troca_troca` FOREIGN KEY (`id_troca`) REFERENCES `troca` (`id_troca`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_troca`
--

LOCK TABLES `status_troca` WRITE;
/*!40000 ALTER TABLE `status_troca` DISABLE KEYS */;
INSERT INTO `status_troca` VALUES (6,0,8,6,'2017-11-22 00:00:00'),(7,0,9,6,'2017-11-22 00:00:00'),(8,0,11,6,'2017-11-22 00:00:00'),(9,1,8,7,'2017-11-24 08:43:12'),(11,1,11,7,'2017-11-24 09:39:17'),(12,1,9,7,'2017-11-24 00:00:00');
/*!40000 ALTER TABLE `status_troca` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `telefone`
--

LOCK TABLES `telefone` WRITE;
/*!40000 ALTER TABLE `telefone` DISABLE KEYS */;
INSERT INTO `telefone` VALUES (3,'(11)','94555-4658',1),(4,'(11)','99445-1213',1),(7,'(11)','95574-2101',1),(15,'(67)','98454-2134',1),(20,'(11)','98791-9229',1);
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

--
-- Table structure for table `troca`
--

DROP TABLE IF EXISTS `troca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `troca` (
  `id_troca` int(11) NOT NULL AUTO_INCREMENT,
  `fl_compra_toda` tinyint(4) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `id_cupom_troca` int(11) DEFAULT NULL,
  `dt_cadastro` datetime NOT NULL,
  `id_cliente` int(11) NOT NULL,
  PRIMARY KEY (`id_troca`),
  KEY `fk_troca_pedido_idx` (`id_pedido`),
  KEY `fk_troca_cupom_troca_idx` (`id_cupom_troca`),
  KEY `fk_troca_cliente_idx` (`id_cliente`),
  CONSTRAINT `fk_troca_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_troca_cupom_troca` FOREIGN KEY (`id_cupom_troca`) REFERENCES `cupom_troca` (`id_cupom_troca`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_troca_pedido` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `troca`
--

LOCK TABLES `troca` WRITE;
/*!40000 ALTER TABLE `troca` DISABLE KEYS */;
INSERT INTO `troca` VALUES (8,1,4,5,'2017-11-22 00:00:00',11),(9,1,6,9,'2017-11-22 00:00:00',12),(11,0,7,8,'2017-11-22 00:00:00',1);
/*!40000 ALTER TABLE `troca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uri`
--

DROP TABLE IF EXISTS `uri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uri` (
  `id_uri` int(11) NOT NULL AUTO_INCREMENT,
  `caminho` varchar(255) NOT NULL,
  PRIMARY KEY (`id_uri`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uri`
--

LOCK TABLES `uri` WRITE;
/*!40000 ALTER TABLE `uri` DISABLE KEYS */;
INSERT INTO `uri` VALUES (18,'/categoriaForm'),(19,'/categoriaList'),(20,'/categoriaEdit'),(21,'/categoriaSalvar'),(22,'/categoriaConsultar'),(23,'/categoriaAlterar'),(24,'/categoriaExcluir'),(25,'/livroForm'),(26,'/livroFormCategorias'),(27,'/livroFormGruposPrecificacao'),(28,'/livroList'),(29,'/livroEdit'),(30,'/livroSalvar'),(31,'/livroConsultar'),(32,'/livroAlterar'),(34,'/livroExcluir'),(35,'/loginSite'),(36,'/logoutSite'),(37,'/loginForm'),(38,'/loginSalvar'),(39,'/loginConsultar'),(40,'/loginAlterar'),(41,'/clienteForm'),(42,'/clienteFormTiposEndereco'),(43,'/clienteFormTiposTelefone'),(44,'/clienteList'),(45,'/clienteEdit'),(46,'/clienteEditTiposEndereco'),(47,'/clienteEditTiposTelefone'),(48,'/clienteView'),(49,'/clienteSalvar'),(50,'/clienteAlterar'),(51,'/clienteExcluir'),(52,'/clienteConsultar'),(53,'/clienteAtivar'),(54,'/clienteInativar'),(55,'/cartaoCreditoForm'),(56,'/cartaoCreditoFormBandeiras'),(57,'/cartaoCreditoEditBandeiras'),(59,'/cartaoCreditoEdit'),(60,'/cartaoCreditoView'),(61,'/cartaoCreditoSalvar'),(62,'/cartaoCreditoAlterar'),(63,'/cartaoCreditoExcluir'),(64,'/cartaoCreditoConsultar'),(65,'/enderecoForm'),(67,'/enderecoEdit'),(69,'/enderecoSalvar'),(70,'/enderecoAlterar'),(71,'/enderecoExcluir'),(72,'/enderecoConsultar'),(73,'/carrinhoCliente'),(74,'/carrinhoAdicionar'),(75,'/carrinhoRemover'),(76,'/carrinhoAlterar'),(77,'/carrinhoConsultar'),(78,'/carrinhoPedidoRemover'),(79,'/freteCalcular'),(80,'/carrinhoPagamento'),(81,'/pagamentoSelecionarCartoes'),(82,'/pagamentoRemoverCartao'),(83,'/pagamentoAdicionarCupom'),(84,'/pagamentoRemoverCupom'),(85,'/pagamentoAdicionarValeCompras'),(86,'/pagamentoRemoverValeCompras'),(87,'/validarFormaPagamento'),(88,'/pedidoDetalhes'),(89,'/pedidoConfirmarCompra'),(90,'/pedidoView'),(91,'/statusSalvar'),(92,'/pedidoTroca'),(93,'/trocaForm'),(94,'/trocaSalvar'),(95,'/trocaConsultar'),(96,'/trocaView'),(97,'/trocaList'),(98,'/trocaAprovar'),(99,'/statusTrocaSalvar'),(100,'/pagamentoSelecionarCupons'),(101,'/pedidoList'),(102,'/pedidoConsultar');
/*!40000 ALTER TABLE `uri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uri_perfil`
--

DROP TABLE IF EXISTS `uri_perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uri_perfil` (
  `id_perfil_acesso` int(11) NOT NULL,
  `id_uri` int(11) NOT NULL,
  PRIMARY KEY (`id_perfil_acesso`,`id_uri`),
  KEY `fk_uri_perfil_uri_idx` (`id_uri`),
  KEY `fk_uri_perfil_perfil_acesso_idx` (`id_perfil_acesso`),
  CONSTRAINT `fk_uri_perfil_uri` FOREIGN KEY (`id_uri`) REFERENCES `uri` (`id_uri`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_uri_perfil_uri_perfil_acesso` FOREIGN KEY (`id_perfil_acesso`) REFERENCES `perfil_acesso` (`id_perfil_acesso`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uri_perfil`
--

LOCK TABLES `uri_perfil` WRITE;
/*!40000 ALTER TABLE `uri_perfil` DISABLE KEYS */;
INSERT INTO `uri_perfil` VALUES (1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(1,28),(2,28),(1,29),(1,30),(1,31),(2,31),(1,32),(1,34),(1,35),(2,35),(1,36),(2,36),(1,37),(2,37),(1,38),(2,38),(1,39),(2,39),(1,40),(2,40),(1,41),(2,41),(1,42),(2,42),(1,43),(2,43),(1,44),(1,45),(2,45),(1,46),(2,46),(1,47),(2,47),(1,48),(2,48),(1,49),(2,49),(1,50),(2,50),(1,51),(1,52),(2,52),(1,53),(1,54),(1,55),(2,55),(1,56),(2,56),(1,57),(2,57),(1,59),(2,59),(1,60),(2,60),(1,61),(2,61),(1,62),(2,62),(1,63),(2,63),(1,64),(2,64),(1,65),(2,65),(1,67),(2,67),(1,69),(2,69),(1,70),(2,70),(1,71),(2,71),(1,72),(2,72),(1,73),(2,73),(1,74),(2,74),(1,75),(2,75),(1,76),(2,76),(1,77),(2,77),(1,78),(2,78),(1,79),(2,79),(1,80),(2,80),(1,81),(2,81),(1,82),(2,82),(1,83),(2,83),(1,84),(2,84),(1,85),(2,85),(1,86),(2,86),(1,87),(2,87),(1,88),(2,88),(1,89),(2,89),(1,90),(2,90),(1,91),(1,92),(2,92),(1,93),(2,93),(1,94),(2,94),(1,95),(1,96),(2,96),(1,97),(1,98),(1,99),(1,100),(2,100),(1,101),(1,102);
/*!40000 ALTER TABLE `uri_perfil` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-29 12:18:47
