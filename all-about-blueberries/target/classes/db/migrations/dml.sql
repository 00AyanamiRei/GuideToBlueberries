START TRANSACTION;

-- Наповнення таблиці `users`
INSERT INTO `users` (`user_name`, `password`, `mail`)
VALUES
    ('User1', 'password1', 'user1@example.com'),
    ('User2', 'password2', 'user2@example.com'),
    ('User3', 'password3', 'user3@example.com'),
    ('User4', 'password4', 'user4@example.com'),
    ('User5', 'password5', 'user5@example.com'),
    ('User6', 'password6', 'user6@example.com'),
    ('User7', 'password7', 'user7@example.com'),
    ('User8', 'password8', 'user8@example.com'),
    ('User9', 'password9', 'user9@example.com'),
    ('User10', 'password10', 'user10@example.com'),
    ('User11', 'password11', 'user11@example.com'),
    ('User12', 'password12', 'user12@example.com'),
    ('User13', 'password13', 'user13@example.com'),
    ('User14', 'password14', 'user14@example.com'),
    ('User15', 'password15', 'user15@example.com'),
    ('User16', 'password16', 'user16@example.com'),
    ('User17', 'password17', 'user17@example.com'),
    ('User18', 'password18', 'user18@example.com'),
    ('User19', 'password19', 'user19@example.com'),
    ('User20', 'password20', 'user20@example.com');

-- Наповнення таблиці `suitable_climate`
INSERT INTO `suitable_climate` (`climate`)
VALUES
    ('Temperate'),
    ('Subtropical'),
    ('Mediterranean'),
    ('Continental'),
    ('Oceanic'),
    ('Desert'),
    ('Tropical'),
    ('Arctic'),
    ('Steppe'),
    ('Humid subtropical'),
    ('Highland'),
    ('Mediterranean'),
    ('Savanna'),
    ('Taiga'),
    ('Grassland'),
    ('Boreal'),
    ('Polar'),
    ('Alpine'),
    ('Subarctic'),
    ('Monsoon');

-- Наповнення таблиці `size_blueberry`
INSERT INTO `size_blueberry` (`size`)
VALUES
    ('Small'),
    ('Medium'),
    ('Large'),
    ('Extra Large'),
    ('Tiny'),
    ('Huge'),
    ('Giant'),
    ('Miniature'),
    ('Regular'),
    ('Big'),
    ('Small'),
    ('Medium'),
    ('Large'),
    ('Extra Large'),
    ('Tiny'),
    ('Huge'),
    ('Giant'),
    ('Miniature'),
    ('Regular'),
    ('Big');

-- Наповнення таблиці `ripening_period`
INSERT INTO `ripening_period` (`deadline`)
VALUES
    ('2023-05-04'),
    ('2023-05-05'),
    ('2023-05-06'),
    ('2023-05-07'),
    ('2023-05-08'),
    ('2023-05-09'),
    ('2023-05-10'),
    ('2023-05-11'),
    ('2023-05-12'),
    ('2023-05-13'),
    ('2023-05-14'),
    ('2023-05-15'),
    ('2023-05-16'),
    ('2023-05-17'),
    ('2023-05-18'),
    ('2023-05-19'),
    ('2023-05-20'),
    ('2023-05-21'),
    ('2023-05-22'),
    ('2023-05-23');

-- Наповнення таблиці `blueberry_taste`
INSERT INTO `blueberry_taste` (`taste`)
VALUES
    ('Sweet'),
    ('Sour'),
    ('Tangy'),
    ('Bitter'),
    ('Juicy'),
    ('Refreshing'),
    ('Aromatic'),
    ('Mild'),
    ('Tart'),
    ('Delicate'),
    ('Rich'),
    ('Flavorful'),
    ('Subtle'),
    ('Citrusy'),
    ('Creamy'),
    ('Exotic'),
    ('Spicy'),
    ('Earthy'),
    ('Herbaceous'),
    ('Nutty');

-- Наповнення таблиці `blueberry`
INSERT INTO `blueberry` (`name`, `size_blueberry_id`, `period_id`, `taste_id`, `climate_id`, `landing_distance`, `pollination`, `description`, `photo`)
VALUES
    ('Blueberry 1', 1, 1, 1, 1, '10 cm', 'Wind', 'Description 1', 'photo1.jpg'),
    ('Blueberry 2', 2, 2, 2, 2, '15 cm', 'Insects', 'Description 2', 'photo2.jpg'),
    ('Blueberry 3', 3, 3, 3, 3, '20 cm', 'Bees', 'Description 3', 'photo3.jpg'),
    ('Blueberry 4', 1, 1, 2, 1, '12 cm', 'Insects', 'Description 4', 'photo4.jpg'),
    ('Blueberry 5', 2, 2, 3, 2, '18 cm', 'Wind', 'Description 5', 'photo5.jpg'),
    ('Blueberry 6', 3, 3, 1, 3, '22 cm', 'Bees', 'Description 6', 'photo6.jpg'),
    ('Blueberry 7', 1, 2, 2, 1, '14 cm', 'Wind', 'Description 7', 'photo7.jpg'),
    ('Blueberry 8', 2, 3, 3, 2, '17 cm', 'Insects', 'Description 8', 'photo8.jpg'),
    ('Blueberry 9', 3, 1, 1, 3, '19 cm', 'Bees', 'Description 9', 'photo9.jpg'),
    ('Blueberry 10', 1, 2, 3, 1, '11 cm', 'Insects', 'Description 10', 'photo10.jpg'),
    ('Blueberry 11', 2, 3, 1, 2, '16 cm', 'Bees', 'Description 11', 'photo11.jpg'),
    ('Blueberry 12', 3, 1, 2, 3, '21 cm', 'Wind', 'Description 12', 'photo12.jpg'),
    ('Blueberry 13', 1, 3, 1, 1, '13 cm', 'Bees', 'Description 13', 'photo13.jpg'),
    ('Blueberry 14', 2, 1, 2, 2, '24 cm', 'Wind', 'Description 14', 'photo14.jpg'),
    ('Blueberry 15', 3, 2, 3, 3, '26 cm', 'Insects', 'Description 15', 'photo15.jpg'),
    ('Blueberry 16', 1, 3, 2, 1, '28 cm', 'Bees', 'Description 16', 'photo16.jpg'),
    ('Blueberry 17', 2, 1, 3, 2, '30 cm', 'Wind', 'Description 17', 'photo17.jpg'),
    ('Blueberry 18', 3, 2, 1, 3, '32 cm', 'Insects', 'Description 18', 'photo18.jpg');

-- Наповнення таблиці `blueberry_review`
INSERT INTO `blueberry_review` (`lochna_id`, `user_id`, `Review`, `Rating`)
VALUES
    (3, 4, 'Review 4', 2),
    (4, 5, 'Review 5', 3),
    (5, 6, 'Review 6', 4),
    (6, 7, 'Review 7', 5),
    (7, 8, 'Review 8', 4),
    (8, 9, 'Review 9', 3),
    (9, 10, 'Review 10', 2),
    (10, 11, 'Review 11', 5),
    (11, 12, 'Review 12', 4),
    (12, 13, 'Review 13', 3),
    (13, 14, 'Review 14', 2),
    (14, 15, 'Review 15', 4),
    (15, 16, 'Review 16', 5),
    (16, 17, 'Review 17', 3),
    (17, 18, 'Review 18', 4),
    (18, 19, 'Review 19', 5),
    (19, 20, 'Review 20', 2),
    (20, 1, 'Review 21', 3),
    (1, 2, 'Review 22', 4);

COMMIT;