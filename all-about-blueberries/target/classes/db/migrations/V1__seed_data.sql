SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `blueberry_data_hub`
--

-- --------------------------------------------------------

--
-- Структура таблицы `blueberry`
--

DROP TABLE IF EXISTS `blueberry`;
CREATE TABLE IF NOT EXISTS `blueberry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  `size_blueberry_id` int(11) NOT NULL,
  `period_id` int(11) NOT NULL,
  `taste_id` int(11) NOT NULL,
  `climate_id` int(11) NOT NULL,
  `landing_distance` varchar(255) NOT NULL,
  `pollination` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `photo` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `size_blueberry_id` (`size_blueberry_id`),
  KEY `period_id` (`period_id`),
  KEY `taste_id` (`taste_id`),
  KEY `climate_id` (`climate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `blueberry`
--

INSERT INTO `blueberry` (`id`, `name`, `size_blueberry_id`, `period_id`, `taste_id`, `climate_id`, `landing_distance`, `pollination`, `description`, `photo`) VALUES
(37, 'Blueberry 1', 4, 1, 1, 1, '10 cm', 'Wind', 'Description 1', 'photo1.jpg'),
(38, 'Blueberry 2', 6, 2, 2, 2, '15 cm', 'Insects', 'Description 2', 'photo2.jpg'),
(39, 'Blueberry 3', 8, 3, 3, 3, '20 cm', 'Bees', 'Description 3', 'photo3.jpg'),
(40, 'Blueberry 4', 7, 1, 2, 1, '12 cm', 'Insects', 'Description 4', 'photo4.jpg'),
(41, 'Blueberry 5', 22, 2, 3, 2, '18 cm', 'Wind', 'Description 5', 'photo5.jpg'),
(42, 'Blueberry 6', 23, 3, 1, 3, '22 cm', 'Bees', 'Description 6', 'photo6.jpg'),
(43, 'Blueberry 7', 25, 2, 2, 1, '14 cm', 'Wind', 'Description 7', 'photo7.jpg'),
(44, 'Blueberry 8', 26, 3, 3, 2, '17 cm', 'Insects', 'Description 8', 'photo8.jpg'),
(45, 'Blueberry 9', 12, 1, 1, 3, '19 cm', 'Bees', 'Description 9', 'photo9.jpg'),
(46, 'Blueberry 10', 15, 2, 3, 1, '11 cm', 'Insects', 'Description 10', 'photo10.jpg'),
(47, 'Blueberry 11', 5, 3, 1, 2, '16 cm', 'Bees', 'Description 11', 'photo11.jpg'),
(48, 'Blueberry 12', 13, 1, 2, 3, '21 cm', 'Wind', 'Description 12', 'photo12.jpg'),
(49, 'Blueberry 13', 12, 3, 1, 1, '13 cm', 'Bees', 'Description 13', 'photo13.jpg'),
(50, 'Blueberry 14', 21, 1, 2, 2, '24 cm', 'Wind', 'Description 14', 'photo14.jpg'),
(51, 'Blueberry 15', 14, 2, 3, 3, '26 cm', 'Insects', 'Description 15', 'photo15.jpg'),
(52, 'Blueberry 16', 15, 3, 2, 1, '28 cm', 'Bees', 'Description 16', 'photo16.jpg'),
(53, 'Blueberry 17', 19, 1, 3, 2, '30 cm', 'Wind', 'Description 17', 'photo17.jpg'),
(54, 'Blueberry 18', 18, 2, 1, 3, '32 cm', 'Insects', 'Description 18', 'photo18.jpg');

-- --------------------------------------------------------

--
-- Структура таблицы `blueberry_review`
--

DROP TABLE IF EXISTS `blueberry_review`;
CREATE TABLE IF NOT EXISTS `blueberry_review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `blueberry_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `Review` varchar(255) NOT NULL,
  `Rating` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `lochna_id` (`blueberry_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `blueberry_review`
--

INSERT INTO `blueberry_review` (`id`, `blueberry_id`, `user_id`, `Review`, `Rating`) VALUES
(77, 37, 4, 'Review 4', 2),
(78, 38, 5, 'Review 5', 3),
(79, 39, 6, 'Review 6', 4),
(80, 40, 7, 'Review 7', 5),
(81, 41, 8, 'Review 8', 4),
(82, 42, 9, 'Review 9', 3),
(83, 43, 10, 'Review 10', 2),
(84, 44, 11, 'Review 11', 5),
(85, 45, 12, 'Review 12', 4),
(86, 46, 13, 'Review 13', 3),
(87, 47, 14, 'Review 14', 2),
(88, 48, 15, 'Review 15', 4),
(89, 49, 16, 'Review 16', 5),
(90, 50, 17, 'Review 17', 3),
(91, 51, 18, 'Review 18', 4),
(92, 52, 19, 'Review 19', 5),
(93, 53, 20, 'Review 20', 2),
(94, 54, 1, 'Review 21', 3),
(95, 37, 2, 'Review 22', 4);

-- --------------------------------------------------------

--
-- Структура таблицы `blueberry_taste`
--

DROP TABLE IF EXISTS `blueberry_taste`;
CREATE TABLE IF NOT EXISTS `blueberry_taste` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taste` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `blueberry_taste`
--

INSERT INTO `blueberry_taste` (`id`, `taste`) VALUES
(1, 'Sweet'),
(2, 'Sour'),
(3, 'Tangy'),
(4, 'Bitter'),
(5, 'Juicy'),
(6, 'Refreshing'),
(7, 'Aromatic'),
(8, 'Mild'),
(9, 'Tart'),
(10, 'Delicate'),
(11, 'Rich'),
(12, 'Flavorful'),
(13, 'Subtle'),
(14, 'Citrusy'),
(15, 'Creamy'),
(16, 'Exotic'),
(17, 'Spicy'),
(18, 'Earthy'),
(19, 'Herbaceous'),
(20, 'Nutty'),
(21, 'Sweet'),
(22, 'Sour'),
(23, 'Tangy'),
(24, 'Bitter'),
(25, 'Juicy'),
(26, 'Refreshing'),
(27, 'Aromatic'),
(28, 'Mild'),
(29, 'Tart'),
(30, 'Delicate'),
(31, 'Rich'),
(32, 'Flavorful'),
(33, 'Subtle'),
(34, 'Citrusy'),
(35, 'Creamy'),
(36, 'Exotic'),
(37, 'Spicy'),
(38, 'Earthy'),
(39, 'Herbaceous'),
(40, 'Nutty');

-- --------------------------------------------------------

--
-- Структура таблицы `ripening_period`
--

DROP TABLE IF EXISTS `ripening_period`;
CREATE TABLE IF NOT EXISTS `ripening_period` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deadline` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `ripening_period`
--

INSERT INTO `ripening_period` (`id`, `deadline`) VALUES
(1, '2023-05-04'),
(2, '2023-05-05'),
(3, '2023-05-06'),
(4, '2023-05-07'),
(5, '2023-05-08'),
(6, '2023-05-09'),
(7, '2023-05-10'),
(8, '2023-05-11'),
(9, '2023-05-12'),
(10, '2023-05-13'),
(11, '2023-05-14'),
(12, '2023-05-15'),
(13, '2023-05-16'),
(14, '2023-05-17'),
(15, '2023-05-18'),
(16, '2023-05-19'),
(17, '2023-05-20'),
(18, '2023-05-21'),
(19, '2023-05-22'),
(20, '2023-05-23'),
(21, '2023-05-04'),
(22, '2023-05-05'),
(23, '2023-05-06'),
(24, '2023-05-07'),
(25, '2023-05-08'),
(26, '2023-05-09'),
(27, '2023-05-10'),
(28, '2023-05-11'),
(29, '2023-05-12'),
(30, '2023-05-13'),
(31, '2023-05-14'),
(32, '2023-05-15'),
(33, '2023-05-16'),
(34, '2023-05-17'),
(35, '2023-05-18'),
(36, '2023-05-19'),
(37, '2023-05-20'),
(38, '2023-05-21'),
(39, '2023-05-22'),
(40, '2023-05-23');

-- --------------------------------------------------------

--
-- Структура таблицы `size_blueberry`
--

DROP TABLE IF EXISTS `size_blueberry`;
CREATE TABLE IF NOT EXISTS `size_blueberry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `size` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `size_blueberry`
--

INSERT INTO `size_blueberry` (`id`, `size`) VALUES
(4, 'Small'),
(5, 'Medium'),
(6, 'Large'),
(7, 'Extra Large'),
(8, 'Tiny'),
(9, 'Huge'),
(10, 'Giant'),
(11, 'Miniature'),
(12, 'Regular'),
(13, 'Big'),
(14, 'Small'),
(15, 'Medium'),
(16, 'Large'),
(17, 'Extra Large'),
(18, 'Tiny'),
(19, 'Huge'),
(20, 'Giant'),
(21, 'Miniature'),
(22, 'Regular'),
(23, 'Big'),
(24, 'Small'),
(25, 'Medium'),
(26, 'Large'),
(27, 'Extra Large'),
(28, 'Tiny'),
(29, 'Huge'),
(30, 'Giant'),
(31, 'Miniature'),
(32, 'Regular'),
(33, 'Big'),
(34, 'Small'),
(35, 'Medium'),
(36, 'Large'),
(37, 'Extra Large'),
(38, 'Tiny'),
(39, 'Huge'),
(40, 'Giant'),
(41, 'Miniature'),
(42, 'Regular'),
(43, 'Big');

-- --------------------------------------------------------

--
-- Структура таблицы `suitable_climate`
--

DROP TABLE IF EXISTS `suitable_climate`;
CREATE TABLE IF NOT EXISTS `suitable_climate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `climate` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `suitable_climate`
--

INSERT INTO `suitable_climate` (`id`, `climate`) VALUES
(1, 'Temperate'),
(2, 'Subtropical'),
(3, 'Mediterranean'),
(4, 'Continental'),
(5, 'Oceanic'),
(6, 'Desert'),
(7, 'Tropical'),
(8, 'Arctic'),
(9, 'Steppe'),
(10, 'Humid subtropical'),
(11, 'Highland'),
(12, 'Mediterranean'),
(13, 'Savanna'),
(14, 'Taiga'),
(15, 'Grassland'),
(16, 'Boreal'),
(17, 'Polar'),
(18, 'Alpine'),
(19, 'Subarctic'),
(20, 'Monsoon'),
(21, 'Temperate'),
(22, 'Subtropical'),
(23, 'Mediterranean'),
(24, 'Continental'),
(25, 'Oceanic'),
(26, 'Desert'),
(27, 'Tropical'),
(28, 'Arctic'),
(29, 'Steppe'),
(30, 'Humid subtropical'),
(31, 'Highland'),
(32, 'Mediterranean'),
(33, 'Savanna'),
(34, 'Taiga'),
(35, 'Grassland'),
(36, 'Boreal'),
(37, 'Polar'),
(38, 'Alpine'),
(39, 'Subarctic'),
(40, 'Monsoon');

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `password` varchar(24) NOT NULL,
  `mail` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id`, `user_name`, `password`, `mail`) VALUES
(1, 'User1', 'password1', 'user1@example.com'),
(2, 'User2', 'password2', 'user2@example.com'),
(3, 'User3', 'password3', 'user3@example.com'),
(4, 'User4', 'password4', 'user4@example.com'),
(5, 'User5', 'password5', 'user5@example.com'),
(6, 'User6', 'password6', 'user6@example.com'),
(7, 'User7', 'password7', 'user7@example.com'),
(8, 'User8', 'password8', 'user8@example.com'),
(9, 'User9', 'password9', 'user9@example.com'),
(10, 'User10', 'password10', 'user10@example.com'),
(11, 'User11', 'password11', 'user11@example.com'),
(12, 'User12', 'password12', 'user12@example.com'),
(13, 'User13', 'password13', 'user13@example.com'),
(14, 'User14', 'password14', 'user14@example.com'),
(15, 'User15', 'password15', 'user15@example.com'),
(16, 'User16', 'password16', 'user16@example.com'),
(17, 'User17', 'password17', 'user17@example.com'),
(18, 'User18', 'password18', 'user18@example.com'),
(19, 'User19', 'password19', 'user19@example.com'),
(20, 'User20', 'password20', 'user20@example.com'),
(21, 'User1', 'password1', 'user1@example.com'),
(22, 'User2', 'password2', 'user2@example.com'),
(23, 'User3', 'password3', 'user3@example.com'),
(24, 'User4', 'password4', 'user4@example.com'),
(25, 'User5', 'password5', 'user5@example.com'),
(26, 'User6', 'password6', 'user6@example.com'),
(27, 'User7', 'password7', 'user7@example.com'),
(28, 'User8', 'password8', 'user8@example.com'),
(29, 'User9', 'password9', 'user9@example.com'),
(30, 'User10', 'password10', 'user10@example.com'),
(31, 'User11', 'password11', 'user11@example.com'),
(32, 'User12', 'password12', 'user12@example.com'),
(33, 'User13', 'password13', 'user13@example.com'),
(34, 'User14', 'password14', 'user14@example.com'),
(35, 'User15', 'password15', 'user15@example.com'),
(36, 'User16', 'password16', 'user16@example.com'),
(37, 'User17', 'password17', 'user17@example.com'),
(38, 'User18', 'password18', 'user18@example.com'),
(39, 'User19', 'password19', 'user19@example.com'),
(40, 'User20', 'password20', 'user20@example.com'),
(41, 'AyanamiTest1', 'AyanamiTest1', 'AyanamiTest1@gmail.com'),
(42, 'AyanamiTest1', 'AyanamiTest1', 'AyanamiTest1@gmail.com'),
(43, 'AyanamiTest1', 'AyanamiTest1', 'AyanamiTest1@gmail.com'),
(44, 'AyanamiTest1', 'AyanamiTest1', 'AyanamiTest1@gmail.com'),
(45, 'AyanamiTest1', 'AyanamiTest1', 'AyanamiTest1@gmail.com'),
(46, 'AyanamiTest1', 'AyanamiTest1', 'AyanamiTest1@gmail.com'),
(47, 'AyanamiTest1', 'AyanamiTest1', 'AyanamiTest1@gmail.com'),
(48, 'AyanamiTest1', 'AyanamiTest1', 'AyanamiTest1@gmail.com'),
(49, 'AyanamiTest1', 'AyanamiTest1', 'AyanamiTest1@gmail.com'),
(50, 'AyanamiTest1', 'AyanamiTest1', 'AyanamiTest1@gmail.com'),
(51, 'AyanamiTest1', 'AyanamiTest1', 'AyanamiTest1@gmail.com'),
(52, 'ASDFDFdsfdsf', 'dasdasdasdas', 'SDFdsfdsffesd@gmail.com'),
(53, 'ASDFDFdsfdsf', 'dasdasdasdas', 'SDFdsfdsffesd@gmail.com'),
(55, 'Maks123', 'Maks123', 'Maks123'),
(56, 'AyanamiTest_1', 'AyanamiTest1@', 'AyanamiTest1@gmail.com'),
(57, 'Ayanami_Rei1', 'ASDdasd12@', 'asdasf@gmail.com'),
(58, 'ASDdasd_2', 'Afasfasf@fas111', 'dasodi@gmail.com'),
(59, 'fasdAF_21', 'dASDADsadsad@2222', 'dsfds@gmail.com');

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `blueberry`
--
ALTER TABLE `blueberry`
  ADD CONSTRAINT `blueberry_ibfk_1` FOREIGN KEY (`size_blueberry_id`) REFERENCES `size_blueberry` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `blueberry_ibfk_2` FOREIGN KEY (`period_id`) REFERENCES `ripening_period` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `blueberry_ibfk_3` FOREIGN KEY (`taste_id`) REFERENCES `blueberry_taste` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `blueberry_ibfk_4` FOREIGN KEY (`climate_id`) REFERENCES `suitable_climate` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `blueberry_review`
--
ALTER TABLE `blueberry_review`
  ADD CONSTRAINT `blueberry_review_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `blueberry_review_ibfk_2` FOREIGN KEY (`blueberry_id`) REFERENCES `blueberry` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;