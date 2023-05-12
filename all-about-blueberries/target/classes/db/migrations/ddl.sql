START TRANSACTION;

CREATE DATABASE IF NOT EXISTS blueberry_data_hub CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Структура таблиці `blueberry`
--

DROP TABLE IF EXISTS `blueberry`;
CREATE TABLE IF NOT EXISTS `blueberry`
    (
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
    )
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Структура таблиці `blueberry_review`
--

DROP TABLE IF EXISTS `blueberry_review`;
CREATE TABLE IF NOT EXISTS `blueberry_review`
    (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `lochna_id` int(11) NOT NULL,
        `user_id` int(11) NOT NULL,
        `Review` varchar(255) NOT NULL,
        `Rating` int(11) NOT NULL,
        PRIMARY KEY (`id`),
        KEY `lochna_id` (`lochna_id`),
        KEY `user_id` (`user_id`)
    )
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Структура таблиці `blueberry_taste`
--

DROP TABLE IF EXISTS `blueberry_taste`;
CREATE TABLE IF NOT EXISTS `blueberry_taste`
    (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `taste` varchar(255) NOT NULL,
        PRIMARY KEY (`id`)
    )
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Структура таблиці `ripening_period`
--

DROP TABLE IF EXISTS `ripening_period`;
CREATE TABLE IF NOT EXISTS `ripening_period`
    (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `deadline` date NOT NULL,
        PRIMARY KEY (`id`)
    )
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Структура таблиці `size_blueberry`
--

DROP TABLE IF EXISTS `size_blueberry`;
CREATE TABLE IF NOT EXISTS `size_blueberry`
    (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `size` int(11) NOT NULL,
        PRIMARY KEY (`id`)
    )
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Структура таблиці `suitable_climate`
--

DROP TABLE IF EXISTS `suitable_climate`;
CREATE TABLE IF NOT EXISTS `suitable_climate`
    (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `climate` varchar(250) NOT NULL,
        PRIMARY KEY (`id`)
    )
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- Структура таблиці `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users`
    (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `user_name` varchar(20) NOT NULL,
        `password` varchar(24) NOT NULL,
        `mail` varchar(255) NOT NULL,
        PRIMARY KEY (`id`)
    )
ENGINE = InnoDB
DEFAULT CHARSET = utf8;

--
-- Обмеження зовнішнього ключа збережених таблиць
--

--
-- Ограничения внешнего ключа таблицы `blueberry`
--
ALTER TABLE `blueberry`
    ADD CONSTRAINT `blueberry_ibfk_1`
        FOREIGN KEY (`size_blueberry_id`) REFERENCES `size_blueberry` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `blueberry_ibfk_2`
        FOREIGN KEY (`period_id`) REFERENCES `ripening_period` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `blueberry_ibfk_3`
        FOREIGN KEY (`taste_id`) REFERENCES `blueberry_taste` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `blueberry_ibfk_4`
        FOREIGN KEY (`climate_id`) REFERENCES `suitable_climate` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Обмеження зовнішнього ключа таблиці `blueberry_review`
--
ALTER TABLE `blueberry_review`
    ADD CONSTRAINT `blueberry_review_ibfk_1`
        FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `blueberry_review_ibfk_2`
        FOREIGN KEY (`lochna_id`) REFERENCES `blueberry` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;