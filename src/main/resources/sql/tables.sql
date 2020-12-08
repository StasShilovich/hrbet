CREATE SCHEMA `hrbet` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

CREATE TABLE `hrbet`.`users`
(
    `id`       INT            NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(20)    NOT NULL,
    `surname`  VARCHAR(40)    NOT NULL,
    `password` VARCHAR(60)    NOT NULL,
    `cash`     DECIMAL(10, 2) NOT NULL DEFAULT 0,
    `email`    VARCHAR(50)    NOT NULL,
    `role_id`  INT            NOT NULL DEFAULT 2,
    `deleted`  TINYINT        NULL     DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE `hrbet`.`roles`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(25) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `hrbet`.`bets`
(
    `id`           INT            NOT NULL AUTO_INCREMENT,
    `user_id`      INT            NOT NULL,
    `time`         TIMESTAMP      NOT NULL,
    `race_id`      INT            NOT NULL,
    `cash`         DECIMAL(10, 2) NOT NULL,
    `ratio`        DECIMAL(7, 3)  NOT NULL,
    `type_id`      INT            NOT NULL,
    `bet_horse_id` INT            NOT NULL,
    `is_win`       TINYINT        NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);

CREATE TABLE `hrbet`.`bet_types`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(40) NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE `hrbet`.`horses`
(
    `id`     INT         NOT NULL AUTO_INCREMENT,
    `name`   VARCHAR(50) NOT NULL,
    `jockey` VARCHAR(50) NOT NULL,
    `age`    INT         NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `hrbet`.`permission`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
);

CREATE TABLE `hrbet`.`role_permissions`
(
    `role_id`       INT NOT NULL,
    `permission_id` INT NOT NULL,
    PRIMARY KEY (`role_id`, `permission_id`)
);

CREATE TABLE `hrbet`.`horse_participatings`
(
    `races_id` INT NOT NULL,
    `horse_id` INT NOT NULL,
    PRIMARY KEY (`races_id`, `horse_id`)
);

CREATE TABLE `hrbet`.`races`
(
    `id`           INT         NOT NULL AUTO_INCREMENT,
    `location`     VARCHAR(50) NOT NULL,
    `time`         TIMESTAMP   NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `hrbet`.`ratio`
(
    `race_id`  INT           NOT NULL,
    `horse_id` INT           NOT NULL,
    `type_id`  INT           NOT NULL,
    `ratio`    DECIMAL(7, 3) NOT NULL,
    PRIMARY KEY (`race_id`, `horse_id`, `type_id`)
);


CREATE TABLE `hrbet`.`race_result`
(
    `id`           INT NOT NULL AUTO_INCREMENT,
    `race_id`      INT NOT NULL,
    `first_horse`  INT NOT NULL,
    `second_horse` INT NOT NULL,
    `third_horse`  INT NOT NULL,
    `fourth_horse` INT NOT NULL,
    `fifth_horse`  INT NOT NULL,
    `sixth_horse`  INT NOT NULL,
    PRIMARY KEY (`id`)
);
/**
  ||||||||||||||||||||||||||||||||||||||||||||||||||||||||
 */

# ALTER TABLE `hrbet`.`users`
#     ADD INDEX `fk_users_role_idx` (`role_id` ASC) VISIBLE;
# ;

ALTER TABLE `hrbet`.`users`
    ADD UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE;
;

ALTER TABLE `hrbet`.`users`
    ADD CONSTRAINT `fk_users_role`
        FOREIGN KEY (`role_id`)
            REFERENCES `hrbet`.`roles` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

# ALTER TABLE `hrbet`.`role_permissions`
#     ADD INDEX `fk_rolepermissions_permission_idx` (`permission_id` ASC) VISIBLE;
# ;
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

# ALTER TABLE `hrbet`.`bets`
# ADD INDEX `fk_bets_user_idx` (`user_id` ASC) VISIBLE,
# ADD INDEX `fk_bets_race_idx` (`race_id` ASC) VISIBLE,
# ADD INDEX `fk_bets_type_idx` (`type_id` ASC) VISIBLE,
# ADD INDEX `fk_bets_win_horse_idx` (`bet_horse_id` ASC) VISIBLE;
# ;
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

# ALTER TABLE `hrbet`.`race_result`
#     ADD INDEX `fk_race_result_race_id_idx` (`race_id` ASC) VISIBLE,
#     ADD INDEX `fk_race_result_first_idx` (`first_horse` ASC) VISIBLE,
#     ADD INDEX `fk_race_result_second_idx` (`second_horse` ASC) VISIBLE,
#     ADD INDEX `fk_race_result_third_idx` (`third_horse` ASC) VISIBLE,
#     ADD INDEX `fk_race_result_fourth_idx` (`fourth_horse` ASC) VISIBLE,
#     ADD INDEX `fk_race_result_fifth_idx` (`fifth_horse` ASC) VISIBLE,
#     ADD INDEX `fk_race_result_six_idx` (`sixth_horse` ASC) VISIBLE;
# ;
ALTER TABLE `hrbet`.`race_result`
    ADD CONSTRAINT `fk_race_result_race_id`
        FOREIGN KEY (`race_id`)
            REFERENCES `hrbet`.`races` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_race_result_first`
        FOREIGN KEY (`first_horse`)
            REFERENCES `hrbet`.`horses` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_race_result_second`
        FOREIGN KEY (`second_horse`)
            REFERENCES `hrbet`.`horses` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_race_result_third`
        FOREIGN KEY (`third_horse`)
            REFERENCES `hrbet`.`horses` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_race_result_fourth`
        FOREIGN KEY (`fourth_horse`)
            REFERENCES `hrbet`.`horses` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_race_result_fifth`
        FOREIGN KEY (`fifth_horse`)
            REFERENCES `hrbet`.`horses` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_race_result_six`
        FOREIGN KEY (`sixth_horse`)
            REFERENCES `hrbet`.`horses` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

# ALTER TABLE `hrbet`.`ratio`
#     ADD INDEX `fk_ratio_horse_id_idx` (`horse_id` ASC) VISIBLE,
#     ADD INDEX `fk_ratio_type_id_idx` (`type_id` ASC) VISIBLE;
# ;
ALTER TABLE `hrbet`.`ratio`
    ADD CONSTRAINT `fk_ratio_race_id`
        FOREIGN KEY (`race_id`)
            REFERENCES `hrbet`.`races` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_ratio_horse_id`
        FOREIGN KEY (`horse_id`)
            REFERENCES `hrbet`.`horses` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_ratio_type_id`
        FOREIGN KEY (`type_id`)
            REFERENCES `hrbet`.`bet_types` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

#   ALTER TABLE `hrbet`.`horse_participatings`
#   ADD INDEX `fk_horse_participatings_horse_idx` (`horse_id` ASC) VISIBLE;
#   ;
ALTER TABLE `hrbet`.`horse_participatings`
    ADD CONSTRAINT `fk_horse_participatings_race`
        FOREIGN KEY (`races_id`)
            REFERENCES `hrbet`.`races` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_horse_participatings_horse`
        FOREIGN KEY (`horse_id`)
            REFERENCES `hrbet`.`horses` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

ALTER TABLE `hrbet`.`race_result`
    DROP FOREIGN KEY `fk_race_result_fifth`,
    DROP FOREIGN KEY `fk_race_result_fourth`,
    DROP FOREIGN KEY `fk_race_result_six`;
ALTER TABLE `hrbet`.`race_result`
    CHANGE COLUMN `fourth_horse` `fourth_horse` INT(11) NULL ,
    CHANGE COLUMN `fifth_horse` `fifth_horse` INT(11) NULL ,
    CHANGE COLUMN `sixth_horse` `sixth_horse` INT(11) NULL ;
ALTER TABLE `hrbet`.`race_result`
    ADD CONSTRAINT `fk_race_result_fifth`
        FOREIGN KEY (`fifth_horse`)
            REFERENCES `hrbet`.`horses` (`id`),
    ADD CONSTRAINT `fk_race_result_fourth`
        FOREIGN KEY (`fourth_horse`)
            REFERENCES `hrbet`.`horses` (`id`),
    ADD CONSTRAINT `fk_race_result_six`
        FOREIGN KEY (`sixth_horse`)
            REFERENCES `hrbet`.`horses` (`id`);

/**
  ||||||||||||||||||||||||||||||||||||||||||||||||||||||||
 */

INSERT INTO `hrbet`.`roles` (`name`)
VALUES ('admin');
INSERT INTO `hrbet`.`roles` (`name`)
VALUES ('user');
INSERT INTO `hrbet`.`roles` (`name`)
VALUES ('customer');

INSERT INTO `hrbet`.`permission` (`name`)VALUES ('user_basic');
INSERT INTO `hrbet`.`permission` (`name`)VALUES ('admin_basic');
INSERT INTO `hrbet`.`permission` (`name`)VALUES ('customer_basic');
INSERT INTO `hrbet`.`permission` (`name`)VALUES ('place_bet');
INSERT INTO `hrbet`.`permission` (`name`)VALUES ('place_result');
INSERT INTO `hrbet`.`permission` (`name`)VALUES ('place_ratio');
INSERT INTO `hrbet`.`permission` (`name`)VALUES ('ban_user');


INSERT INTO `hrbet`.`role_permissions` (`role_id`, `permission_id`)
VALUES ('1', '1');
INSERT INTO `hrbet`.`role_permissions` (`role_id`, `permission_id`)
VALUES ('2', '2');
INSERT INTO `hrbet`.`role_permissions` (`role_id`, `permission_id`)
VALUES ('3', '3');
INSERT INTO `hrbet`.`role_permissions` (`role_id`, `permission_id`)
VALUES ('3', '4');



INSERT INTO `hrbet`.`users` (`name`, `surname`, `password`, `cash`, `email`, `role_id`)
VALUES ('Стас', 'Шилович', '$2y$12$CDC.h3J.7RXDC14I1UtOpOwhls3n8NaPo/kl4FNQbAey4rhuj51ba', '300',
        'stas.shilovich@gmail.com', '1');
INSERT INTO `hrbet`.`users` (`name`, `surname`, `password`, `cash`, `email`, `role_id`)
VALUES ('Василий', 'Петров', '$2y$12$CDC.h3J.7RXDC14I1UtOpOwhls3n8NaPo/kl4FNQbAey4rhuj51ba', '300', 'vasya@gmail.com',
        '3');
INSERT INTO `hrbet`.`users` (`name`, `surname`, `password`, `email`)
VALUES ('User', 'Name', '$2y$12$8QTQCmw6VwB0Zcs/xvvOtuPho9MNOZSyiimYLRC9hJE54iKS4CarG', 'user@gmail.com');
INSERT INTO `hrbet`.`users` (`name`, `surname`, `password`, `email`)
VALUES ('Tom', 'Tompson', '$2y$12$CDC.h3J.7RXDC14I1UtOpOwhls3n8NaPo/kl4FNQbAey4rhuj51ba', 'tom@gmail.com');
INSERT INTO `hrbet`.`users` (`name`, `surname`, `password`, `email`)
VALUES ('Son', 'Sonson', '$2y$12$9gfWrQhqASii3UdsHRPIP.MiiSqd7b8ViQ.N1h2rZ1m1Ol4GQUzee', 'son@gmail.com');
INSERT INTO `hrbet`.`users` (`name`, `surname`, `password`, `email`)
VALUES ('Van', 'Vanson', '$2y$12$LfwqxgogM0UtsKbVGQK7muCT7AgoUIwXGmM/UNWlSFPb4nNeJa1y6', 'van@gmail.com');



INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Беверли', '2020-11-08 15:15:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Лингфилд', '2020-10-24 12:45:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Листоуэл', '2020-09-11 17:32:09');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Вулверхэмптон', '2019-06-15 12:20:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Корк', '2019-04-27 13:45:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Пунчестаун', '2018-09-14 16:00:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Бангор-он-Ди', '2018-07-01 15:30:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('TRP Экстрас', '2018-05-08 14:30:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Седжфилд', '2017-09-17 19:00:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Эр', '2017-08-28 15:15:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Каттерик', '2020-11-28 13:15:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Саутуэлл', '2020-11-23 16:45:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Чепстоу', '2020-11-20 12:15:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Дандолк', '2021-02-04 14:30:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Кемптон', '2021-02-07 19:00:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Ноттингем', '2021-03-13 15:15:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Пунчестаун', '2021-04-14 13:15:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Тонтон', '2021-05-23 16:45:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Факенхем', '2021-06-07 12:15:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Ньютон Эббот', '2021-06-08 14:30:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Саутуэлл', '2021-06-15 19:00:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Стратфорд', '2021-06-17 15:15:00');
INSERT INTO `hrbet`.`races` (`location`, `time`)
VALUES ('Челмсфорд', '2021-06-20 13:15:00');


INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('The Butcher Said', '7', 'P J Brennan');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('The Wolf', '6', 'Adrian Heskin');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('Thegallantway', '7', 'Nick Scholfield');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('Brecon Hill', '7', 'F O\'Brien');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('Cool Destination', '7', 'P J Brennan');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('Diomede des Mottes', '7', 'D Skelton');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('Reve', '6', 'M Keighley');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('Snow Leopardess', '8', 'R Johnson');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('Teqany', '6', 'C E Longsdon');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('Midnights\' Gift', '4', 'B S Hughes');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('See The Sea', '6', 'F O\'Brien');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('Ask Himself', '6', 'P J Brennan');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('Beach Break', '6', 'A King');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('Valentino Dancer', '5', 'Tom Cannon');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('Kajaki', '7', 'Theo Gillard');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('Tidal Watch', '6', 'M Scudamore');
INSERT INTO `hrbet`.`horses` (`name`, `age`, `jockey`)
VALUES ('El Borracho', '7', 'R T Dunne');


INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('1', '4');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('1', '5');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('1', '3');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('1', '10');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('1', '7');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('1', '8');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('2', '1');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('2', '3');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('2', '5');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('2', '8');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('2', '15');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('2', '2');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('3', '5');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('3', '7');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('3', '8');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('3', '11');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('3', '1');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('3', '14');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('4', '15');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('4', '17');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('4', '8');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('4', '10');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('4', '1');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('4', '14');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('5', '5');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('5', '7');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('5', '11');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('5', '10');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('5', '12');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('5', '3');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('6', '15');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('6', '17');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('6', '6');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('6', '8');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('6', '12');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('6', '13');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('7', '10');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('7', '17');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('7', '16');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('7', '3');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('7', '2');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('7', '9');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('8', '4');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('8', '17');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('8', '2');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('8', '13');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('8', '12');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('8', '15');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('9', '14');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('9', '1');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('9', '3');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('9', '5');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('9', '10');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('9', '11');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('10', '13');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('10', '1');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('10', '8');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('10', '15');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('10', '14');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('10', '17');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('11', '14');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('11', '11');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('11', '6');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('11', '7');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('11', '2');
INSERT INTO `hrbet`.`horse_participatings` (`races_id`, `horse_id`)
VALUES ('11', '17');



INSERT INTO `hrbet`.`bet_types` (`name`)
VALUES ('win');
INSERT INTO `hrbet`.`bet_types` (`name`)
VALUES ('show');

INSERT INTO `hrbet`.`bets` ( `user_id`, `time`, `race_id`, `cash`, `type_id`, `bet_horse_id`,`ratio`,`is_win`) VALUES ( '1', '2019-10-22 09:49:43', '2', '100.00', '1', '17','2.190','1');
INSERT INTO `hrbet`.`bets` ( `user_id`, `time`, `race_id`, `cash`, `type_id`, `bet_horse_id`,`ratio`,`is_win`) VALUES ( '2', '2020-10-22 09:49:43', '11', '100', '1', '17','2.19','2');
INSERT INTO `hrbet`.`bets` ( `user_id`, `time`, `race_id`, `cash`, `type_id`, `bet_horse_id`,`ratio`,`is_win`) VALUES ( '4', '2019-05-12 18:23:00', '4', '150', '1', '17','2.19','2');
INSERT INTO `hrbet`.`bets` (`user_id`, `time`, `race_id`, `cash`, `type_id`, `bet_horse_id`,`ratio`,`is_win`) VALUES ( '5', '2020-08-22 15:40:00', '3', '100', '1', '1','2.19','2');
INSERT INTO `hrbet`.`bets` (`user_id`, `time`, `race_id`, `cash`, `type_id`, `bet_horse_id`,`ratio`,`is_win`) VALUES ( '4', '2020-10-12 18:23:00', '2', '150', '1', '5','2.19','2');




INSERT INTO `hrbet`.`race_result` (`race_id`, `first_horse`, `second_horse`, `third_horse`, `fourth_horse`, `fifth_horse`, `sixth_horse`) VALUES ('11', '17', '11', '6', '14', '7', '2');
INSERT INTO `hrbet`.`race_result` (`race_id`, `first_horse`, `second_horse`, `third_horse`, `fourth_horse`, `fifth_horse`, `sixth_horse`) VALUES ('2', '5', '8', '15', '3', '2', '1');
INSERT INTO `hrbet`.`race_result` (`race_id`, `first_horse`, `second_horse`, `third_horse`, `fourth_horse`, `fifth_horse`, `sixth_horse`) VALUES ('3', '1', '11', '14', '5', '8', '7');
INSERT INTO `hrbet`.`race_result` (`race_id`, `first_horse`, `second_horse`, `third_horse`, `fourth_horse`, `fifth_horse`, `sixth_horse`) VALUES ('4', '10', '14', '17', '8', '15', '1');

insert into ratio(race_id, horse_id, type_id, ratio) VALUES (2,17,1,2.19);
insert into ratio(race_id, horse_id, type_id, ratio) VALUES (2,5,1,2.19);











