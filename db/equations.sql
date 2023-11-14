-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 14, 2023 at 04:36 AM
-- Server version: 8.0.19
-- PHP Version: 7.1.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `equations`
--

-- --------------------------------------------------------

--
-- Table structure for table `eqbase`
--

CREATE TABLE `eqbase` (
  `id` bigint NOT NULL,
  `eqSentence` text NOT NULL,
  `eqRoot` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `eqbase`
--

INSERT INTO `eqbase` (`id`, `eqSentence`, `eqRoot`) VALUES
(1, '2*x=10', 5),
(2, '-1.3*5/x=1.2', -5.4166666666667),
(3, '2*x+5+x+5=10', 0),
(4, '17=2*x+5', 6),
(7, '2+x=14', 12),
(10, '2+x=4', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `eqbase`
--
ALTER TABLE `eqbase`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `eqbase`
--
ALTER TABLE `eqbase`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
