-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Sep 10, 2021 at 12:41 AM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `school`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `Name` varchar(20) NOT NULL,
  `password` varchar(6) NOT NULL,
  PRIMARY KEY (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`Name`, `password`) VALUES
('admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
CREATE TABLE IF NOT EXISTS `book` (
  `ISBN` varchar(13) COLLATE utf16_unicode_ci NOT NULL,
  `Title` varchar(35) COLLATE utf16_unicode_ci NOT NULL,
  PRIMARY KEY (`ISBN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`ISBN`, `Title`) VALUES
('1001', 'English G10 Text Bookkkkk'),
('1002', 'Math G10 Text Book'),
('1003', 'Biology G10 Text Book'),
('1004', 'Chemistry G10 Text Book'),
('1005', 'Physics G10 Text Book '),
('1006', 'Civics G10 Text Book'),
('1007', 'Amharic G10 Text Book'),
('1101', 'English G11 Text Book'),
('1102', 'Math G11 Text Book'),
('1103', 'Biology G11 Text Book'),
('1104', 'Chemistry G11 Text Book'),
('1105', 'Physics G11 Text Book '),
('1106', 'Civics G11 Text Book'),
('1107', 'SAT G11 Text Book'),
('1201', 'English G12'),
('1202', 'Math G12 Text Book'),
('1203', 'Biology G12 Text Book'),
('1204', 'Chemistry G12 Text Book'),
('1205', 'Physics G12 Text Book'),
('1206', 'Civics G12 Text Book'),
('1207', 'SAT G12 Text Book'),
('901', 'English G9 Text Book'),
('902', 'Math G9 Text Book'),
('903', 'Biology G9 Text Book'),
('904', 'Chemistry G9 Text Book'),
('905', 'Physics G9 Text Book '),
('906', 'Civics G9 Text Book'),
('907', 'Amharic G9 Text Book');

-- --------------------------------------------------------

--
-- Table structure for table `class_room`
--

DROP TABLE IF EXISTS `class_room`;
CREATE TABLE IF NOT EXISTS `class_room` (
  `GradeLvl` int(2) NOT NULL,
  PRIMARY KEY (`GradeLvl`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `class_room`
--

INSERT INTO `class_room` (`GradeLvl`) VALUES
(9),
(10),
(11),
(12);

-- --------------------------------------------------------

--
-- Table structure for table `extra_curricular`
--

DROP TABLE IF EXISTS `extra_curricular`;
CREATE TABLE IF NOT EXISTS `extra_curricular` (
  `Name` varchar(20) COLLATE utf16_unicode_ci NOT NULL,
  `Description` varchar(100) COLLATE utf16_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `extra_curricular`
--

INSERT INTO `extra_curricular` (`Name`, `Description`) VALUES
('Book Club', 'For students Interested in meeting and discussing about books.......'),
('HikingClub', 'Student will hike accross long trips on mountains');

-- --------------------------------------------------------

--
-- Table structure for table `gardian`
--

DROP TABLE IF EXISTS `gardian`;
CREATE TABLE IF NOT EXISTS `gardian` (
  `Fname` varchar(20) COLLATE utf16_unicode_ci NOT NULL,
  `SID` int(5) NOT NULL,
  `Pno` varchar(11) COLLATE utf16_unicode_ci DEFAULT NULL,
  `Relationship` varchar(20) COLLATE utf16_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`Fname`,`SID`),
  KEY `SID` (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `gardian`
--

INSERT INTO `gardian` (`Fname`, `SID`, `Pno`, `Relationship`) VALUES
('fisum', 125, NULL, 'Mother'),
('Hana', 122, NULL, 'Mother');

-- --------------------------------------------------------

--
-- Table structure for table `instructor`
--

DROP TABLE IF EXISTS `instructor`;
CREATE TABLE IF NOT EXISTS `instructor` (
  `Title` varchar(5) COLLATE utf16_unicode_ci DEFAULT NULL,
  `FName` varchar(11) COLLATE utf16_unicode_ci NOT NULL,
  `LName` varchar(11) COLLATE utf16_unicode_ci NOT NULL,
  `TID` int(5) NOT NULL AUTO_INCREMENT,
  `Address` varchar(20) COLLATE utf16_unicode_ci DEFAULT NULL,
  `Pno` varchar(11) COLLATE utf16_unicode_ci DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `Salary` int(7) NOT NULL,
  `Cno` varchar(6) COLLATE utf16_unicode_ci NOT NULL,
  `Sex` char(1) COLLATE utf16_unicode_ci NOT NULL,
  `PassWord` varchar(4) COLLATE utf16_unicode_ci NOT NULL DEFAULT '0000',
  PRIMARY KEY (`TID`),
  UNIQUE KEY `Cno_2` (`Cno`),
  UNIQUE KEY `Cno_3` (`Cno`),
  KEY `Cno` (`Cno`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `instructor`
--

INSERT INTO `instructor` (`Title`, `FName`, `LName`, `TID`, `Address`, `Pno`, `DOB`, `Salary`, `Cno`, `Sex`, `PassWord`) VALUES
(NULL, 'PHY G12', 'Teacher', 7, NULL, NULL, NULL, 4000, '12PHY', 'M', '0000'),
(NULL, 'CIV G12', 'Teacher', 8, NULL, NULL, NULL, 7000, '12CIV', 'F', '0000'),
(NULL, 'SAT G12', 'Teacher', 9, NULL, NULL, NULL, 7000, '12SAT', 'F', '0000'),
('Mr.', 'Eng G11', 'Teacher', 17, NULL, NULL, NULL, 7000, '11ENG', 'M', '0000'),
(NULL, 'Math G11', 'Teacher', 18, NULL, NULL, NULL, 4000, '11MATH', 'F', '0000'),
(NULL, 'Bio G11', 'Teacher', 19, NULL, NULL, NULL, 7000, '11BIO', 'F', '0000'),
('Mr.', 'CHE G11', 'Teacher', 20, NULL, NULL, NULL, 7000, '11CHE', 'F', '0000'),
(NULL, 'PHY G11', 'Teacher', 21, NULL, NULL, NULL, 4000, '11PHY', 'M', '0000'),
(NULL, 'CIV G11', 'Teacher', 22, NULL, NULL, NULL, 7000, '11CIV', 'F', '0000'),
(NULL, 'SAT G11', 'Teacher', 23, NULL, NULL, NULL, 7000, '11SAT', 'F', '0000'),
('Mr.', 'Eng G10', 'Teacher', 38, NULL, NULL, NULL, 7000, '10ENG', 'M', '0000'),
(NULL, 'Math G10', 'Teacher', 39, NULL, NULL, NULL, 4000, '10MATH', 'F', '0000'),
(NULL, 'Bio G10', 'Teacher', 40, NULL, NULL, NULL, 7000, '10BIO', 'F', '0000'),
('Mr.', 'CHE G10', 'Teacher', 41, NULL, NULL, NULL, 7000, '10CHE', 'F', '0000'),
(NULL, 'PHY G10', 'Teacher', 42, NULL, NULL, NULL, 4000, '10PHY', 'M', '0000'),
(NULL, 'CIV G10', 'Teacher', 43, NULL, NULL, NULL, 7000, '10CIV', 'F', '0000'),
(NULL, 'AMH G10', 'Teacher', 44, NULL, NULL, NULL, 7000, '10AMH', 'F', '0000'),
('Mr.', 'Eng G9', 'Teacher', 45, NULL, NULL, NULL, 7000, '9ENG', 'M', '0000'),
(NULL, 'Math G9', 'Teacher', 46, NULL, NULL, NULL, 4000, '9MATH', 'F', '0000'),
(NULL, 'Bio G9', 'Teacher', 47, NULL, NULL, NULL, 7000, '9BIO', 'F', '0000'),
('Mr.', 'CHE G9', 'Teacher', 48, NULL, NULL, NULL, 7000, '9CHE', 'F', '0000'),
(NULL, 'PHY G9', 'Teacher', 49, NULL, NULL, NULL, 4000, '9PHY', 'M', '0000'),
(NULL, 'CIV G9', 'Teacher', 50, NULL, NULL, NULL, 7000, '9CIV', 'F', '0000'),
(NULL, 'AMH G9', 'Teacher', 51, NULL, NULL, NULL, 7000, '9AMH', 'F', '0000'),
('Mr.', 'BIO G12', 'Teacher', 54, NULL, NULL, NULL, 7000, '12BIO', 'M', '0000'),
('Mr.', 'EngTeacher', 'Teacher', 55, NULL, NULL, NULL, 7000, '12ENG', 'M', '0000'),
('Mr', 'Che', 'GTelwv', 57, '', '', '0002-02-02', 3444, '12CHE', 'M', '0000');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
CREATE TABLE IF NOT EXISTS `student` (
  `Fname` varchar(10) COLLATE utf16_unicode_ci NOT NULL,
  `Lname` varchar(10) COLLATE utf16_unicode_ci NOT NULL,
  `SID` int(5) NOT NULL AUTO_INCREMENT,
  `Sex` varchar(1) COLLATE utf16_unicode_ci NOT NULL,
  `Address` varchar(20) COLLATE utf16_unicode_ci DEFAULT NULL,
  `Pno` varchar(10) COLLATE utf16_unicode_ci DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `GradeLvl` int(2) NOT NULL,
  `EName` varchar(20) COLLATE utf16_unicode_ci DEFAULT NULL,
  `PassWord` varchar(4) COLLATE utf16_unicode_ci NOT NULL DEFAULT '0000',
  PRIMARY KEY (`SID`),
  KEY `GradeLvl` (`GradeLvl`),
  KEY `EName` (`EName`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`Fname`, `Lname`, `SID`, `Sex`, `Address`, `Pno`, `DOB`, `GradeLvl`, `EName`, `PassWord`) VALUES
('Nati', 'Tes', 122, 'M', '1223 bethel', '09747131', '1903-12-02', 12, 'Book Club', '0000'),
('Leo', 'Mes', 123, 'M', 'bethel', '0965341213', '1943-12-11', 12, NULL, '0000'),
('leul', 'mola', 124, 'M', 'torhialoch', '09447821', '1932-12-12', 11, 'Book Club', '0000'),
('Abebe', 'Kebede', 125, 'M', '4Kilo', '09776633', '1933-12-01', 12, 'HikingClub', '0000');

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
CREATE TABLE IF NOT EXISTS `subject` (
  `Cno` varchar(6) COLLATE utf16_unicode_ci NOT NULL,
  `CName` varchar(20) COLLATE utf16_unicode_ci NOT NULL,
  `GradeLvl` int(2) DEFAULT NULL,
  `ISBN` varchar(13) COLLATE utf16_unicode_ci NOT NULL,
  PRIMARY KEY (`Cno`),
  KEY `ISBN` (`ISBN`),
  KEY `GradeLvl` (`GradeLvl`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `subject`
--

INSERT INTO `subject` (`Cno`, `CName`, `GradeLvl`, `ISBN`) VALUES
('10AMH', 'Amharic', 10, '1007'),
('10BIO', 'Biology', 10, '1003'),
('10CHE', 'Chemistry', 10, '1004'),
('10CIV', 'Civics', 10, '1006'),
('10ENG', 'English', 10, '1001'),
('10MATH', 'Math', 10, '1002'),
('10PHY', 'Physics', 10, '1005'),
('11BIO', 'Biology', 11, '1103'),
('11CHE', 'Chemistry', 11, '1104'),
('11CIV', 'Civics', 11, '1106'),
('11ENG', 'English', 11, '1101'),
('11MATH', 'Math', 11, '1102'),
('11PHY', 'Physics', 11, '1105'),
('11SAT', 'Aptuide', 11, '1107'),
('12BIO', 'Biology', 12, '1203'),
('12CHE', 'Chemistry', 12, '1204'),
('12CIV', 'Civics', 12, '1206'),
('12ENG', 'English', 12, '1201'),
('12MATH', 'Math ', 12, '1202'),
('12PHY', 'Physics', 12, '1205'),
('12SAT', 'Aptitude ', 12, '1207'),
('9AMH', 'Amharic', 9, '907'),
('9BIO', 'Biology', 9, '903'),
('9CHE', 'Chemistry', 9, '904'),
('9CIV', 'Civics', 9, '906'),
('9ENG', 'English', 9, '901'),
('9MATH', 'Math', 9, '902'),
('9PHY', 'Physics', 9, '905');

-- --------------------------------------------------------

--
-- Table structure for table `takes`
--

DROP TABLE IF EXISTS `takes`;
CREATE TABLE IF NOT EXISTS `takes` (
  `SID` int(5) NOT NULL,
  `Cno` varchar(6) COLLATE utf16_unicode_ci NOT NULL,
  `FinalMark` int(3) DEFAULT NULL,
  PRIMARY KEY (`SID`,`Cno`),
  KEY `Cno` (`Cno`),
  KEY `SID` (`SID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `takes`
--

INSERT INTO `takes` (`SID`, `Cno`, `FinalMark`) VALUES
(122, '12BIO', NULL),
(122, '12CHE', NULL),
(122, '12CIV', NULL),
(122, '12ENG', NULL),
(122, '12MATH', NULL),
(122, '12PHY', 100),
(122, '12SAT', NULL),
(123, '12BIO', NULL),
(123, '12CHE', NULL),
(123, '12CIV', NULL),
(123, '12ENG', NULL),
(123, '12MATH', NULL),
(123, '12PHY', 98),
(123, '12SAT', NULL),
(124, '11BIO', NULL),
(124, '11CHE', NULL),
(124, '11CIV', NULL),
(124, '11ENG', NULL),
(124, '11MATH', NULL),
(124, '11PHY', NULL),
(124, '11SAT', NULL),
(125, '12BIO', 66),
(125, '12CHE', 88),
(125, '12CIV', 76),
(125, '12ENG', 77),
(125, '12MATH', 77),
(125, '12PHY', 85),
(125, '12SAT', NULL);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `gardian`
--
ALTER TABLE `gardian`
  ADD CONSTRAINT `gardian_ibfk_1` FOREIGN KEY (`SID`) REFERENCES `student` (`SID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `instructor`
--
ALTER TABLE `instructor`
  ADD CONSTRAINT `instructor_ibfk_1` FOREIGN KEY (`Cno`) REFERENCES `subject` (`Cno`) ON UPDATE CASCADE;

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `ATTENDS` FOREIGN KEY (`GradeLvl`) REFERENCES `class_room` (`GradeLvl`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `PARTICIPATES_IN` FOREIGN KEY (`EName`) REFERENCES `extra_curricular` (`Name`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `subject`
--
ALTER TABLE `subject`
  ADD CONSTRAINT `subject_ibfk_1` FOREIGN KEY (`GradeLvl`) REFERENCES `class_room` (`GradeLvl`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `subject_ibfk_2` FOREIGN KEY (`ISBN`) REFERENCES `book` (`ISBN`) ON UPDATE CASCADE;

--
-- Constraints for table `takes`
--
ALTER TABLE `takes`
  ADD CONSTRAINT `takes_ibfk_2` FOREIGN KEY (`Cno`) REFERENCES `subject` (`Cno`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `takes_ibfk_3` FOREIGN KEY (`SID`) REFERENCES `student` (`SID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
