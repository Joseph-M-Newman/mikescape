-- phpMyAdmin SQL Dump
-- version 3.5.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 01, 2014 at 03:59 AM
-- Server version: 5.5.37-35.1
-- PHP Version: 5.4.23

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `poanizer_highscores`
--

-- --------------------------------------------------------

--
-- Table structure for table `skills`
--

CREATE TABLE IF NOT EXISTS `skills` (
  `playerName` varchar(15) NOT NULL DEFAULT '',
  `Attacklvl` double DEFAULT NULL,
  `Attackxp` double DEFAULT NULL,
  `Defencelvl` double DEFAULT NULL,
  `Defencexp` double DEFAULT NULL,
  `Strengthlvl` double DEFAULT NULL,
  `Strengthxp` double DEFAULT NULL,
  `Hitpointslvl` double DEFAULT NULL,
  `Hitpointsxp` double DEFAULT NULL,
  `Rangelvl` double DEFAULT NULL,
  `Rangedxp` double DEFAULT NULL,
  `Prayerlvl` double DEFAULT NULL,
  `Prayerxp` double DEFAULT NULL,
  `Magiclvl` double DEFAULT NULL,
  `Magicxp` double DEFAULT NULL,
  `Cookinglvl` double DEFAULT NULL,
  `Cookingxp` double DEFAULT NULL,
  `Woodcuttinglvl` double DEFAULT NULL,
  `Woodcuttingxp` double DEFAULT NULL,
  `Fletchinglvl` double DEFAULT NULL,
  `Fletchingxp` double DEFAULT NULL,
  `Fishinglvl` double DEFAULT NULL,
  `Fishingxp` double DEFAULT NULL,
  `Firemakinglvl` double DEFAULT NULL,
  `Firemakingxp` double DEFAULT NULL,
  `Craftinglvl` double DEFAULT NULL,
  `Craftingxp` double DEFAULT NULL,
  `Smithinglvl` double DEFAULT NULL,
  `Smithingxp` double DEFAULT NULL,
  `Mininglvl` double DEFAULT NULL,
  `Miningxp` double DEFAULT NULL,
  `Herblorelvl` double DEFAULT NULL,
  `Herblorexp` double DEFAULT NULL,
  `Agilitylvl` double DEFAULT NULL,
  `Agilityxp` double DEFAULT NULL,
  `Thievinglvl` double DEFAULT NULL,
  `Thievingxp` double DEFAULT NULL,
  `Slayerlvl` double DEFAULT NULL,
  `Slayerxp` double DEFAULT NULL,
  `Farminglvl` double DEFAULT NULL,
  `Farmingxp` double DEFAULT NULL,
  `Runecraftlvl` double DEFAULT NULL,
  `Runecraftxp` double DEFAULT NULL,
  `Hunterlvl` double DEFAULT NULL,
  `Hunterxp` double DEFAULT NULL,
  `Constructionlvl` double DEFAULT NULL,
  `Constructionxp` double DEFAULT NULL,
  `Summoninglvl` double DEFAULT NULL,
  `Summoningxp` double DEFAULT NULL,
  `Dungeoneeringlvl` double DEFAULT NULL,
  `Dungeoneeringxp` double DEFAULT NULL,
  PRIMARY KEY (`playerName`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `skillsoverall`
--

CREATE TABLE IF NOT EXISTS `skillsoverall` (
  `playerName` varchar(15) NOT NULL DEFAULT '',
  `lvl` int(11) DEFAULT NULL,
  `xp` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`playerName`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
