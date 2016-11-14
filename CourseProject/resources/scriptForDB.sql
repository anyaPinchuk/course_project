-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`film`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`film` (
  `film_id` INT(10) UNSIGNED NOT NULL,
  `genre` VARCHAR(100) NULL DEFAULT NULL,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`film_id`),
  UNIQUE INDEX `id_UNIQUE` (`film_id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`film_session`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`film_session` (
  `film_session_id` INT(10) UNSIGNED NOT NULL,
  `duration` DOUBLE UNSIGNED NOT NULL,
  `date` DATETIME NOT NULL,
  `film_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`film_session_id`),
  UNIQUE INDEX `id_UNIQUE` (`film_session_id` ASC),
  UNIQUE INDEX `date_UNIQUE` (`date` ASC),
  INDEX `fk_film_session_film1_idx` (`film_id` ASC),
  CONSTRAINT `fk_film_session_film1`
    FOREIGN KEY (`film_id`)
    REFERENCES `mydb`.`film` (`film_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



-- -----------------------------------------------------
-- Table `mydb`.`place`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`place` (
  `price` DOUBLE UNSIGNED NOT NULL,
  `place_id` INT(10) UNSIGNED NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `film_session_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`place_id`),
  UNIQUE INDEX `number_UNIQUE` (`place_id` ASC),
  INDEX `fk_place_session1_idx` (`film_session_id` ASC),
  CONSTRAINT `fk_place_session1`
    FOREIGN KEY (`film_session_id`)
    REFERENCES `mydb`.`film_session` (`film_session_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ticket` (
  `ticket_id` INT(11) NOT NULL,
  `loginOfUser` VARCHAR(45) NOT NULL,
  `film_session_id` INT(10) UNSIGNED NOT NULL,
  `placeNumber` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ticket_id`),
  UNIQUE INDEX `id_UNIQUE` (`ticket_id` ASC),
  INDEX `fk_ticket_film_session1_idx` (`film_session_id` ASC),
  CONSTRAINT `fk_ticket_film_session1`
    FOREIGN KEY (`film_session_id`)
    REFERENCES `mydb`.`film_session` (`film_session_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `isAdmin` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
