-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 06, 2024 at 06:06 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `feynman`
--
CREATE DATABASE IF NOT EXISTS `feynman` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `feynman`;

-- --------------------------------------------------------

--
-- Table structure for table `assessment`
--

CREATE TABLE `assessment` (
  `AssID` int(11) NOT NULL,
  `AssPool` int(11) NOT NULL,
  `AssRandom` tinyint(1) DEFAULT NULL,
  `AssLength` int(11) DEFAULT NULL,
  `AssRetakes` int(11) NOT NULL,
  `AssType` text NOT NULL,
  `AssName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `assessment`
--

INSERT INTO `assessment` (`AssID`, `AssPool`, `AssRandom`, `AssLength`, `AssRetakes`, `AssType`, `AssName`) VALUES
(2, 2, NULL, NULL, 10000, 'DRILL', 'Simple Addition Drill'),
(3, 3, NULL, NULL, 0, 'EXAM', 'Simple Subtraction Exam');

-- --------------------------------------------------------

--
-- Table structure for table `assessmentattemptquestions`
--

CREATE TABLE `assessmentattemptquestions` (
  `attemptID` int(11) NOT NULL,
  `QID` int(11) NOT NULL,
  `correctAnswer` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `assessmentattempts`
--

CREATE TABLE `assessmentattempts` (
  `attemptID` int(11) NOT NULL,
  `AssID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `attemptTimestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `classroom`
--

CREATE TABLE `classroom` (
  `ClassroomID` int(11) NOT NULL,
  `ClassroomName` varchar(50) NOT NULL,
  `ClassroomDescription` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `classroomassessments`
--

CREATE TABLE `classroomassessments` (
  `ClassroomID` int(11) NOT NULL,
  `AssID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pool`
--

CREATE TABLE `pool` (
  `PoolID` int(11) NOT NULL,
  `PoolName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pool`
--

INSERT INTO `pool` (`PoolID`, `PoolName`) VALUES
(2, 'Simple Addition'),
(3, 'Simple Subtraction');

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

CREATE TABLE `question` (
  `QID` int(11) NOT NULL,
  `QFormula` text NOT NULL,
  `QAnswer` text NOT NULL,
  `QDifficulty` tinyint(4) NOT NULL,
  `QInclMult` tinyint(1) NOT NULL,
  `QInclDiv` tinyint(1) NOT NULL,
  `QInclAdd` tinyint(1) NOT NULL,
  `QInclSub` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `question`
--

INSERT INTO `question` (`QID`, `QFormula`, `QAnswer`, `QDifficulty`, `QInclMult`, `QInclDiv`, `QInclAdd`, `QInclSub`) VALUES
(3, '5 + 5', '10', 1, 0, 0, 1, 0),
(4, '2 + 2', '4', 1, 0, 0, 1, 0),
(5, '3 - 1', '2', 2, 0, 0, 0, 1),
(6, '250 - 130', '120', 3, 0, 0, 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `questionassignments`
--

CREATE TABLE `questionassignments` (
  `QID` int(11) NOT NULL,
  `AssID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `questionpools`
--

CREATE TABLE `questionpools` (
  `QID` int(11) NOT NULL,
  `PoolID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `questionpools`
--

INSERT INTO `questionpools` (`QID`, `PoolID`) VALUES
(3, 2),
(4, 2),
(5, 3),
(6, 3);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `RoleID` int(11) NOT NULL,
  `RoleName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`RoleID`, `RoleName`) VALUES
(1, 'admin'),
(2, 'teacher'),
(3, 'student'),
(4, 'parent');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `UserID` int(11) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserID`, `Username`, `Password`, `FirstName`, `LastName`) VALUES
(1, 'jwalrath21', 'password', 'Jonathan', 'Walrath'),
(2, 'admin', 'password', 'Admin', 'McAdmin'),
(3, 'student', 'password', 'Student', 'Studentson'),
(4, 'parent', 'password', 'Parent', 'Fitzparent')
(5, 'teacher', 'password', 'Teacher', 'MacTeacher');

-- --------------------------------------------------------

--
-- Table structure for table `userassessments`
--

CREATE TABLE `userassessments` (
  `UserID` int(11) NOT NULL,
  `AssID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `userchildren`
--

CREATE TABLE `userchildren` (
  `UserID` int(11) NOT NULL,
  `ChildUserID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `userclassrooms`
--

CREATE TABLE `userclassrooms` (
  `UserID` int(11) NOT NULL,
  `ClassroomID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `userpools`
--

CREATE TABLE `userpools` (
  `UserID` int(11) NOT NULL,
  `PoolID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `userpools`
--

INSERT INTO `userpools` (`UserID`, `PoolID`) VALUES
(1, 2),
(1, 3);

-- --------------------------------------------------------

--
-- Table structure for table `userroles`
--

CREATE TABLE `userroles` (
  `UserID` int(11) NOT NULL,
  `RoleID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `userroles`
--

INSERT INTO `userroles` (`UserID`, `RoleID`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 1),
(3, 3),
(4, 4),
(5, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `assessment`
--
ALTER TABLE `assessment`
  ADD PRIMARY KEY (`AssID`),
  ADD KEY `AssPool` (`AssPool`);

--
-- Indexes for table `assessmentattemptquestions`
--
ALTER TABLE `assessmentattemptquestions`
  ADD PRIMARY KEY (`attemptID`,`QID`),
  ADD KEY `fkQID_AssAttemptQuestions` (`QID`);

--
-- Indexes for table `assessmentattempts`
--
ALTER TABLE `assessmentattempts`
  ADD PRIMARY KEY (`attemptID`),
  ADD KEY `fkUserID_AssessmentAttempts` (`userID`),
  ADD KEY `fkAssID_AssessmentAttempts` (`AssID`);

--
-- Indexes for table `classroom`
--
ALTER TABLE `classroom`
  ADD PRIMARY KEY (`ClassroomID`);

--
-- Indexes for table `classroomassessments`
--
ALTER TABLE `classroomassessments`
  ADD PRIMARY KEY (`ClassroomID`,`AssID`),
  ADD KEY `fkAssID_ClassroomAssessments` (`AssID`);

--
-- Indexes for table `pool`
--
ALTER TABLE `pool`
  ADD PRIMARY KEY (`PoolID`);

--
-- Indexes for table `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`QID`);

--
-- Indexes for table `questionassignments`
--
ALTER TABLE `questionassignments`
  ADD PRIMARY KEY (`QID`,`AssID`),
  ADD KEY `fkAssID_QuestionAssignments` (`AssID`);

--
-- Indexes for table `questionpools`
--
ALTER TABLE `questionpools`
  ADD PRIMARY KEY (`QID`,`PoolID`),
  ADD KEY `fkPoolID_QuestionPools` (`PoolID`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`RoleID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserID`);

--
-- Indexes for table `userassessments`
--
ALTER TABLE `userassessments`
  ADD PRIMARY KEY (`UserID`,`AssID`),
  ADD KEY `fkAssID_UserAssessments` (`AssID`);

--
-- Indexes for table `userchildren`
--
ALTER TABLE `userchildren`
  ADD PRIMARY KEY (`UserID`,`ChildUserID`),
  ADD KEY `fkChildUserID_UserChildren` (`ChildUserID`);

--
-- Indexes for table `userclassrooms`
--
ALTER TABLE `userclassrooms`
  ADD PRIMARY KEY (`UserID`,`ClassroomID`),
  ADD KEY `fkClassroomID_UserClassrooms` (`ClassroomID`);

--
-- Indexes for table `userpools`
--
ALTER TABLE `userpools`
  ADD PRIMARY KEY (`UserID`,`PoolID`),
  ADD KEY `fkPoolID_UserPools` (`PoolID`);

--
-- Indexes for table `userroles`
--
ALTER TABLE `userroles`
  ADD PRIMARY KEY (`UserID`,`RoleID`),
  ADD KEY `fkRoleID_UserRoles` (`RoleID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `assessment`
--
ALTER TABLE `assessment`
  MODIFY `AssID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `assessmentattemptquestions`
--
ALTER TABLE `assessmentattemptquestions`
  MODIFY `attemptID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `assessmentattempts`
--
ALTER TABLE `assessmentattempts`
  MODIFY `attemptID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `classroom`
--
ALTER TABLE `classroom`
  MODIFY `ClassroomID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pool`
--
ALTER TABLE `pool`
  MODIFY `PoolID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `question`
--
ALTER TABLE `question`
  MODIFY `QID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `RoleID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `assessment`
--
ALTER TABLE `assessment`
  ADD CONSTRAINT `assessment_ibfk_1` FOREIGN KEY (`AssPool`) REFERENCES `pool` (`PoolID`);

--
-- Constraints for table `assessmentattemptquestions`
--
ALTER TABLE `assessmentattemptquestions`
  ADD CONSTRAINT `fkQID_AssAttemptQuestions` FOREIGN KEY (`QID`) REFERENCES `question` (`QID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fkattemptID_AssAttemptQuestions` FOREIGN KEY (`attemptID`) REFERENCES `assessmentattempts` (`attemptID`);

--
-- Constraints for table `assessmentattempts`
--
ALTER TABLE `assessmentattempts`
  ADD CONSTRAINT `fkAssID_AssessmentAttempts` FOREIGN KEY (`AssID`) REFERENCES `assessment` (`AssID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fkUserID_AssessmentAttempts` FOREIGN KEY (`userID`) REFERENCES `user` (`UserID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `classroomassessments`
--
ALTER TABLE `classroomassessments`
  ADD CONSTRAINT `fkAssID_ClassroomAssessments` FOREIGN KEY (`AssID`) REFERENCES `assessment` (`AssID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fkClassroomID_ClassroomAssessments` FOREIGN KEY (`ClassroomID`) REFERENCES `classroom` (`ClassroomID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `questionassignments`
--
ALTER TABLE `questionassignments`
  ADD CONSTRAINT `fkAssID_QuestionAssignments` FOREIGN KEY (`AssID`) REFERENCES `assessment` (`AssID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fkQID_QuestionAssignments` FOREIGN KEY (`QID`) REFERENCES `question` (`QID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `questionpools`
--
ALTER TABLE `questionpools`
  ADD CONSTRAINT `fkPoolID_QuestionPools` FOREIGN KEY (`PoolID`) REFERENCES `pool` (`PoolID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fkQID_QuestionPools` FOREIGN KEY (`QID`) REFERENCES `question` (`QID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `userassessments`
--
ALTER TABLE `userassessments`
  ADD CONSTRAINT `fkAssID_UserAssessments` FOREIGN KEY (`AssID`) REFERENCES `assessment` (`AssID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fkUserID_UserAssessments` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `userchildren`
--
ALTER TABLE `userchildren`
  ADD CONSTRAINT `fkChildUserID_UserChildren` FOREIGN KEY (`ChildUserID`) REFERENCES `user` (`UserID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fkUserID_UserChildren` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `userclassrooms`
--
ALTER TABLE `userclassrooms`
  ADD CONSTRAINT `fkClassroomID_UserClassrooms` FOREIGN KEY (`ClassroomID`) REFERENCES `classroom` (`ClassroomID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fkUserID_UserClassrooms` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `userpools`
--
ALTER TABLE `userpools`
  ADD CONSTRAINT `fkPoolID_UserPools` FOREIGN KEY (`PoolID`) REFERENCES `pool` (`PoolID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fkUserID_UserPools` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `userroles`
--
ALTER TABLE `userroles`
  ADD CONSTRAINT `fkRoleID_UserRoles` FOREIGN KEY (`RoleID`) REFERENCES `roles` (`RoleID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fkUserID_UserRoles` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
