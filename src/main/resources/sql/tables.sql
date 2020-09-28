CREATE SCHEMA `hrbet` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;

CREATE TABLE `hrbet`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `surname` VARCHAR(40) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `cash_dollars` BIGINT NOT NULL DEFAULT 0,
  `cash_cents` INT NOT NULL DEFAULT 0,
  `email` VARCHAR(50) NOT NULL,
  `role_id` INT NOT NULL,
  `deleted` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`id`));

CREATE TABLE `hrbet`.`roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `hrbet`.`bets` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` TINYINT NULL DEFAULT 1,
  `user_id` INT NOT NULL,
  `time` TIMESTAMP NOT NULL,
  `race_id` INT NOT NULL,
  `cash_dollars` BIGINT NOT NULL DEFAULT 0,
  `cash_cents` INT NOT NULL DEFAULT 0,
  `type_id` INT NOT NULL,
  `bet_horse_id` INT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `hrbet`.`bet_types` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `hrbet`.`races` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `location` VARCHAR(50) NOT NULL,
  `time` TIMESTAMP NOT NULL,
  `bank_dollars` BIGINT NOT NULL DEFAULT 0,
  `bank_cents` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`));

CREATE TABLE `hrbet`.`horses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `jockey` VARCHAR(50) NOT NULL,
  `age` INT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `hrbet`.`permission` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);

CREATE TABLE `hrbet`.`role_permissions` (
  `role_id` INT NOT NULL,
  `permission_id` INT NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`));

CREATE TABLE `hrbet`.`horse_jockey_ratio` (
  `horse_id` INT NOT NULL,
  `jockey_id` INT NOT NULL,
  PRIMARY KEY (`horse_id`, `jockey_id`));

CREATE TABLE `hrbet`.`race_archive` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `race_id` INT NOT NULL,
  `first_horse` INT NOT NULL,
  `second_horse` INT NOT NULL,
  `third_horse` INT NOT NULL,
  PRIMARY KEY (`id`));


||||||||||||||||||||||||||||||||||||||||||||||

ALTER TABLE `hrbet`.`users`
ADD INDEX `fk_users_role_idx` (`role_id` ASC) VISIBLE;
;
ALTER TABLE `hrbet`.`users`
ADD CONSTRAINT `fk_users_role`
  FOREIGN KEY (`role_id`)
  REFERENCES `hrbet`.`roles` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `hrbet`.`role_permissions`
ADD INDEX `fk_rolepermissions_permission_idx` (`permission_id` ASC) VISIBLE;
;
ALTER TABLE `hrbet`.`role_permissions`
ADD CONSTRAINT `fk_rolepermissions_role`
  FOREIGN KEY (`role_id`)
  REFERENCES `hrbet`.`roles` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_rolepermissions_permission`
  FOREIGN KEY (`permission_id`)
  REFERENCES `hrbet`.`permission` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `hrbet`.`bets`
ADD INDEX `fk_bets_user_idx` (`user_id` ASC) VISIBLE,
ADD INDEX `fk_bets_race_idx` (`race_id` ASC) VISIBLE,
ADD INDEX `fk_bets_type_idx` (`type_id` ASC) VISIBLE,
ADD INDEX `fk_bets_win_horse_idx` (`bet_horse_id` ASC) VISIBLE;
;
ALTER TABLE `hrbet`.`bets`
ADD CONSTRAINT `fk_bets_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `hrbet`.`users` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_bets_race`
  FOREIGN KEY (`race_id`)
  REFERENCES `hrbet`.`races` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_bets_type`
  FOREIGN KEY (`type_id`)
  REFERENCES `hrbet`.`bet_types` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_bets_win_horse`
  FOREIGN KEY (`bet_horse_id`)
  REFERENCES `hrbet`.`horses` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `hrbet`.`race_archive`
ADD INDEX `fk_race_archive_race_idx` (`race_id` ASC) VISIBLE,
ADD INDEX `fk_race_archive_first_horce_idx` (`first_horse` ASC) VISIBLE,
ADD INDEX `fk_race_archive_second_horse_idx` (`second_horse` ASC) VISIBLE,
ADD INDEX `fk_race_archive_third_horse_idx` (`third_horse` ASC) VISIBLE;
;
ALTER TABLE `hrbet`.`race_archive`
ADD CONSTRAINT `fk_race_archive_race`
  FOREIGN KEY (`race_id`)
  REFERENCES `hrbet`.`races` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_race_archive_first_horce`
  FOREIGN KEY (`first_horse`)
  REFERENCES `hrbet`.`horses` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_race_archive_second_horse`
  FOREIGN KEY (`second_horse`)
  REFERENCES `hrbet`.`horses` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_race_archive_third_horse`
  FOREIGN KEY (`third_horse`)
  REFERENCES `hrbet`.`horses` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

||||||||||||||||||||||||||||||||||||||||||||||||||

INSERT INTO `hrbet`.`roles` (`name`) VALUES ('admin');
INSERT INTO `hrbet`.`roles` (`name`) VALUES ('user');
INSERT INTO `hrbet`.`roles` (`name`) VALUES ('customer');

INSERT INTO `hrbet`.`permission` (`name`) VALUES ('all');
INSERT INTO `hrbet`.`permission` (`name`) VALUES ('grant_option');
INSERT INTO `hrbet`.`permission` (`name`) VALUES ('ban_user');
INSERT INTO `hrbet`.`permission` (`name`) VALUES ('add');
INSERT INTO `hrbet`.`permission` (`name`) VALUES ('update');
INSERT INTO `hrbet`.`permission` (`name`) VALUES ('select');
INSERT INTO `hrbet`.`permission` (`name`) VALUES ('bet');

INSERT INTO `hrbet`.`role_permissions` (`role_id`, `permission_id`) VALUES ('1', '1');
INSERT INTO `hrbet`.`role_permissions` (`role_id`, `permission_id`) VALUES ('2', '7');
INSERT INTO `hrbet`.`role_permissions` (`role_id`, `permission_id`) VALUES ('3', '4');
INSERT INTO `hrbet`.`role_permissions` (`role_id`, `permission_id`) VALUES ('3', '5');
INSERT INTO `hrbet`.`role_permissions` (`role_id`, `permission_id`) VALUES ('3', '6');

INSERT INTO `hrbet`.`users` (`name`, `surname`, `password`, `cash_dollars`, `email`, `role_id`, `deleted`) VALUES ('Стас', 'Шилович', '$2y$12$SS9teveGeEnvWVwoI11Li.6euRoUXLVYtPS4Ng6u0jRexVkG8VMb2', '300', 'stas.shilovich@gmail.com', '1', '0');
INSERT INTO `hrbet`.`users` (`name`, `surname`, `password`, `email`, `role_id`) VALUES ('Василий', 'Петров', '$2y$12$fa2U/gh2Af658QYqOqgf2OxkY8eFyx2DxL8rgXk7Q0fQaoLBvTWTO', 'vasia@rambler.ru', '2');
INSERT INTO `hrbet`.`users` (`name`, `surname`, `password`, `cash_dollars`, `cash_cents`, `email`, `role_id`, `deleted`) VALUES ('Лариса', 'Мионова', '$2y$12$QN216iWa8cybUI2kwO1eQeYjY.KjOqn6TXdfLYe3rUwAoSta0vtgm', '100', '50', 'laram@mail.ru', '3', '0');

INSERT INTO `hrbet`.`races` (`location`, `time`, `bank_dollars`) VALUES ('Беверли', '2020-11-08 15:15:00', '5000');
INSERT INTO `hrbet`.`races` (`location`, `time`, `bank_dollars`) VALUES ('Лингфилд', '2020-10-24 12:45:00', '10000');
INSERT INTO `hrbet`.`races` (`location`, `time`, `bank_dollars`) VALUES ('Листоуэл', '2020-09-11 17:32:09', '5000');
INSERT INTO `hrbet`.`races` (`location`, `time`, `bank_dollars`) VALUES ('Вулверхэмптон', '2019-06-15 12:20:00', '2000');
INSERT INTO `hrbet`.`races` (`location`, `time`, `bank_dollars`) VALUES ('Корк', '2019-04-27 13:45:00', '3000');
INSERT INTO `hrbet`.`races` (`location`, `time`, `bank_dollars`) VALUES ('Пунчестаун', '2018-09-14 16:00:00', '2500');
INSERT INTO `hrbet`.`races` (`location`, `time`, `bank_dollars`) VALUES ('Бангор-он-Ди', '2018-07-01 15:30:00', '2000');
INSERT INTO `hrbet`.`races` (`location`, `time`, `bank_dollars`) VALUES ('TRP Экстрас', '2018-05-08 14:30:00', '15000');
INSERT INTO `hrbet`.`races` (`location`, `time`, `bank_dollars`) VALUES ('Седжфилд', '2017-09-17 19:00:00', '5000');
INSERT INTO `hrbet`.`races` (`location`, `time`, `bank_dollars`) VALUES ('Эр', '2017-08-28 15:15:00', '1500');



INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('The Butcher Said', '7');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('The Wolf', '6');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('Thegallantway', '7');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('Brecon Hill', '7');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('Cool Destination', '7');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('Diomede des Mottes', '7');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('Reve', '6');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('Snow Leopardess', '8');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('Teqany', '6');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('Midnights\' Gift', '4');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('See The Sea', '6');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('Ask Himself', '6');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('Beach Break', '6');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('Valentino Dancer', '5');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('Kajaki', '7');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('Tidal Watch', '6');
INSERT INTO `hrbet`.`horses` (`name`, `age`) VALUES ('El Borracho', '7');


INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('1', '4');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('1', '5');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('1', '3');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('1', '10');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('1', '7');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('1', '8');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('1', '14');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('1', '16');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('2', '1');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('2', '3');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('2', '5');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('2', '8');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('2', '15');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('2', '2');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('2', '17');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('2', '4');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('3', '5');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('3', '7');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('3', '8');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('3', '11');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('3', '1');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('3', '14');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('3', '12');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`) VALUES ('3', '10');
UPDATE `hrbet`.`horses` SET `jockey` = 'P J Brennan' WHERE (`id` = '1');
UPDATE `hrbet`.`horses` SET `jockey` = 'Adrian Heskin' WHERE (`id` = '2');
UPDATE `hrbet`.`horses` SET `jockey` = 'Nick Scholfield' WHERE (`id` = '3');
UPDATE `hrbet`.`horses` SET `jockey` = 'F O\'Brien' WHERE (`id` = '4');
UPDATE `hrbet`.`horses` SET `jockey` = 'P J Brennan' WHERE (`id` = '5');
UPDATE `hrbet`.`horses` SET `jockey` = 'D Skelton' WHERE (`id` = '6');
UPDATE `hrbet`.`horses` SET `jockey` = 'M Keighley' WHERE (`id` = '7');
UPDATE `hrbet`.`horses` SET `jockey` = 'R Johnson' WHERE (`id` = '8');
UPDATE `hrbet`.`horses` SET `jockey` = 'C E Longsdon' WHERE (`id` = '9');
UPDATE `hrbet`.`horses` SET `jockey` = 'B S Hughes' WHERE (`id` = '10');
UPDATE `hrbet`.`horses` SET `jockey` = 'F O\'Brien' WHERE (`id` = '11');
UPDATE `hrbet`.`horses` SET `jockey` = 'P J Brennan' WHERE (`id` = '12');
UPDATE `hrbet`.`horses` SET `jockey` = 'A King' WHERE (`id` = '13');
UPDATE `hrbet`.`horses` SET `jockey` = 'Tom Cannon' WHERE (`id` = '14');
UPDATE `hrbet`.`horses` SET `jockey` = 'Theo Gillard' WHERE (`id` = '15');
UPDATE `hrbet`.`horses` SET `jockey` = 'M Scudamore' WHERE (`id` = '16');
UPDATE `hrbet`.`horses` SET `jockey` = 'R T Dunne' WHERE (`id` = '17');


INSERT INTO `hrbet`.`bet_types` (`name`) VALUES ('win');
INSERT INTO `hrbet`.`bet_types` (`name`) VALUES ('show');

INSERT INTO `hrbet`.`bets` (`status`, `user_id`, `time`, `race_id`, `cash_dollars`, `type_id`, `bet_horse_id`) VALUES ('1', '1', '2019-10-22 09:49:43', '2', '100', '1', '17');
