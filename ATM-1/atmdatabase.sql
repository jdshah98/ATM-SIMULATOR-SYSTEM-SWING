-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 07, 2018 at 10:27 AM
-- Server version: 10.1.35-MariaDB
-- PHP Version: 7.2.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `atmdatabase`
--

-- --------------------------------------------------------

--
-- Table structure for table `register`
--

CREATE TABLE `register` (
  `card_no` bigint(20) NOT NULL,
  `lname` text,
  `fname` text,
  `mname` text,
  `address` text,
  `gender` text,
  `phone_no` text,
  `email_id` text,
  `pin` int(11) DEFAULT NULL,
  `cvv` int(11) DEFAULT NULL,
  `balance` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `register`
--

INSERT INTO `register` (`card_no`, `lname`, `fname`, `mname`, `address`, `gender`, `phone_no`, `email_id`, `pin`, `cvv`, `balance`) VALUES
(1, 'roy', 'roy', 'roy', 'roy', 'Male', '8596741230', 'roy@roy.roy', 1004, 161, 14000),
(2, 'Roy', 'Parth', 'D', 'Surat', 'Male', '9712448794', 'roy@roy.com', 328, 459, 20000);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `sCardno` bigint(20) DEFAULT NULL,
  `rCardno` bigint(20) DEFAULT NULL,
  `change_balance` bigint(20) DEFAULT NULL,
  `acc_balance` bigint(20) DEFAULT NULL,
  `type` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id`, `sCardno`, `rCardno`, `change_balance`, `acc_balance`, `type`) VALUES
(1, 1, NULL, 8963, 1000021617, 'Deposit'),
(2, 1, NULL, 100000, 999921617, 'Withdraw'),
(3, 1, NULL, 19999, 1, 'Withdraw'),
(4, 1, NULL, 19999, 20000, 'Deposit'),
(5, 1, NULL, 2000, 22000, 'Deposit'),
(6, 1, NULL, 2000, 24000, 'Deposit'),
(7, 1, NULL, 20000, 44000, 'Deposit'),
(8, 1, NULL, 20000, 24000, 'Withdraw'),
(9, 1, 2, 10000, 14000, 'Transfer'),
(10, 2, 1, 1000, 19000, 'Transfer'),
(11, 1, 2, 1000, 14000, 'Transfer');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL,
  `password` int(16) DEFAULT NULL,
  `cvv` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `password`, `cvv`) VALUES
(1, 1004, 161),
(2, 328, 459);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `register`
--
ALTER TABLE `register`
  ADD PRIMARY KEY (`card_no`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `register`
--
ALTER TABLE `register`
  MODIFY `card_no` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
