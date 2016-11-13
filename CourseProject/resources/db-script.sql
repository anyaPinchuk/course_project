-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `isAdmin` TINYINT(1) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`film`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`film` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `genre` VARCHAR(100) NULL,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(500) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`filmSession`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`filmSession` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `duration` DOUBLE UNSIGNED NOT NULL,
  `film_id` INT UNSIGNED NOT NULL,
  `date` DATETIME NOT NULL,
  `film_id1` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `film_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `date_UNIQUE` (`date` ASC),
  INDEX `fk_session_film_idx` (`film_id1` ASC),
  CONSTRAINT `fk_session_film`
    FOREIGN KEY (`film_id1`)
    REFERENCES `mydb`.`film` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`place`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`place` (
  `price` DOUBLE UNSIGNED NOT NULL,
  `number` INT UNSIGNED NOT NULL,
  `state` VARCHAR(45) GENERATED ALWAYS AS (Свободно) VIRTUAL,
  `session_id` INT UNSIGNED NOT NULL,
  `session_film_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`number`),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC),
  INDEX `fk_place_session1_idx` (`session_id` ASC, `session_film_id` ASC),
  CONSTRAINT `fk_place_session1`
    FOREIGN KEY (`session_id` , `session_film_id`)
    REFERENCES `mydb`.`filmSession` (`id` , `film_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`ticket` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `loginOfUser` VARCHAR(45) NOT NULL,
  `placeNumber` INT UNSIGNED NOT NULL,
  `session_id` INT UNSIGNED NOT NULL,
  `session_film_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_ticket_session1_idx` (`session_id` ASC, `session_film_id` ASC),
  CONSTRAINT `fk_ticket_session1`
    FOREIGN KEY (`session_id` , `session_film_id`)
    REFERENCES `mydb`.`filmSession` (`id` , `film_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
