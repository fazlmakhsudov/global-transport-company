-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema epamdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `epamdb` ;

-- -----------------------------------------------------
-- Schema epamdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `epamdb` DEFAULT CHARACTER SET utf8;
USE `epamdb` ;

-- -----------------------------------------------------
-- Table `epamdb`.`cities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `epamdb`.`cities` ;

CREATE TABLE IF NOT EXISTS `epamdb`.`cities` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `epamdb`.`content_types`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `epamdb`.`content_types` ;

CREATE TABLE IF NOT EXISTS `epamdb`.`content_types` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `epamdb`.`delivery_statuses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `epamdb`.`delivery_statuses` ;

CREATE TABLE IF NOT EXISTS `epamdb`.`delivery_statuses` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `epamdb`.`request_statuses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `epamdb`.`request_statuses` ;

CREATE TABLE IF NOT EXISTS `epamdb`.`request_statuses` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `epamdb`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `epamdb`.`roles` ;

CREATE TABLE IF NOT EXISTS `epamdb`.`roles` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `epamdb`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `epamdb`.`users` ;

CREATE TABLE IF NOT EXISTS `epamdb`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role_id` INT(11) NOT NULL DEFAULT '3',
  `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `fk_users_roles_idx` (`role_id` ASC),
  CONSTRAINT `fk_users_roles`
    FOREIGN KEY (`role_id`)
    REFERENCES `epamdb`.`roles` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `epamdb`.`requests`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `epamdb`.`requests` ;

CREATE TABLE IF NOT EXISTS `epamdb`.`requests` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `city_from_id` INT(11) NOT NULL,
  `city_to_id` INT(11) NOT NULL,
  `weight` DOUBLE NOT NULL,
  `length` DOUBLE NOT NULL,
  `width` DOUBLE NOT NULL,
  `height` DOUBLE NOT NULL,
  `delivery_date` DATETIME NOT NULL,
  `user_id` INT(11) NOT NULL,
  `content_type_id` INT(11) NOT NULL,
  `request_status_id` INT(11) NOT NULL,
  `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_requests_users1_idx` (`user_id` ASC),
  INDEX `fk_requests_content_types1_idx` (`content_type_id` ASC),
  INDEX `fk_requests_request_statuses1_idx` (`request_status_id` ASC),
  INDEX `fk_requests_cities1_idx` (`city_from_id` ASC),
  INDEX `fk_requests_cities2_idx` (`city_to_id` ASC),
  CONSTRAINT `fk_requests_cities1`
    FOREIGN KEY (`city_from_id`)
    REFERENCES `epamdb`.`cities` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_requests_cities2`
    FOREIGN KEY (`city_to_id`)
    REFERENCES `epamdb`.`cities` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_requests_content_types1`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `epamdb`.`content_types` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_requests_request_statuses1`
    FOREIGN KEY (`request_status_id`)
    REFERENCES `epamdb`.`request_statuses` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_requests_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `epamdb`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `epamdb`.`deliveries`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `epamdb`.`deliveries` ;

CREATE TABLE IF NOT EXISTS `epamdb`.`deliveries` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `delivery_status_id` INT(11) NOT NULL,
  `request_id` INT(11) NOT NULL,
  `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_deliveries_requests1_idx` (`request_id` ASC),
  INDEX `fk_deliveries_delivery_statuses1_idx` (`delivery_status_id` ASC),
  CONSTRAINT `fk_deliveries_delivery_statuses1`
    FOREIGN KEY (`delivery_status_id`)
    REFERENCES `epamdb`.`delivery_statuses` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_deliveries_requests1`
    FOREIGN KEY (`request_id`)
    REFERENCES `epamdb`.`requests` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `epamdb`.`distances`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `epamdb`.`distances` ;

CREATE TABLE IF NOT EXISTS `epamdb`.`distances` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `from_city_id` INT(11) NOT NULL,
  `to_city_id` INT(11) NOT NULL,
  `distance` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_distances_cities1_idx` (`to_city_id` ASC),
  INDEX `fk_distances_cities2_idx` (`from_city_id` ASC),
  CONSTRAINT `fk_distances_cities1`
    FOREIGN KEY (`to_city_id`)
    REFERENCES `epamdb`.`cities` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_distances_cities2`
    FOREIGN KEY (`from_city_id`)
    REFERENCES `epamdb`.`cities` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `epamdb`.`invoice_statuses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `epamdb`.`invoice_statuses` ;

CREATE TABLE IF NOT EXISTS `epamdb`.`invoice_statuses` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `epamdb`.`invoices`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `epamdb`.`invoices` ;

CREATE TABLE IF NOT EXISTS `epamdb`.`invoices` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `cost` DOUBLE NOT NULL,
  `invoice_status_id` INT(11) NOT NULL,
  `request_id` INT(11) NOT NULL,
  `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_invoices_requests1_idx` (`request_id` ASC),
  INDEX `fk_invoices_invoice_statuses1_idx` (`invoice_status_id` ASC),
  CONSTRAINT `fk_invoices_invoice_statuses1`
    FOREIGN KEY (`invoice_status_id`)
    REFERENCES `epamdb`.`invoice_statuses` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_invoices_requests1`
    FOREIGN KEY (`request_id`)
    REFERENCES `epamdb`.`requests` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `epamdb`.`rates`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `epamdb`.`rates` ;

CREATE TABLE IF NOT EXISTS `epamdb`.`rates` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `max_weight` DOUBLE NOT NULL,
  `max_length` DOUBLE NOT NULL,
  `max_width` DOUBLE NOT NULL,
  `max_height` DOUBLE NOT NULL,
  `max_distance` DOUBLE NOT NULL,
  `cost` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `epamdb`;

DELIMITER $$

USE `epamdb`$$
DROP TRIGGER IF EXISTS `epamdb`.`users_BEFORE_INSERT` $$
USE `epamdb`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `epamdb`.`users_BEFORE_INSERT`
BEFORE INSERT ON `epamdb`.`users`
FOR EACH ROW
BEGIN
SET NEW.password = md5(NEW.password);
END$$


USE `epamdb`$$
DROP TRIGGER IF EXISTS `epamdb`.`requests_BEFORE_INSERT` $$
USE `epamdb`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `epamdb`.`requests_BEFORE_INSERT`
BEFORE INSERT ON `epamdb`.`requests`
FOR EACH ROW
BEGIN
SET NEW.created_date = NOW();
SET NEW.updated_date = NOW();
END$$


USE `epamdb`$$
DROP TRIGGER IF EXISTS `epamdb`.`requests_BEFORE_UPDATE` $$
USE `epamdb`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `epamdb`.`requests_BEFORE_UPDATE`
BEFORE UPDATE ON `epamdb`.`requests`
FOR EACH ROW
BEGIN
SET NEW.updated_date = NOW();
END$$


USE `epamdb`$$
DROP TRIGGER IF EXISTS `epamdb`.`deliveries_BEFORE_INSERT` $$
USE `epamdb`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `epamdb`.`deliveries_BEFORE_INSERT`
BEFORE INSERT ON `epamdb`.`deliveries`
FOR EACH ROW
BEGIN
SET NEW.created_date = NOW();
SET NEW.updated_date = NOW();
END$$


USE `epamdb`$$
DROP TRIGGER IF EXISTS `epamdb`.`deliveries_BEFORE_UPDATE` $$
USE `epamdb`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `epamdb`.`deliveries_BEFORE_UPDATE`
BEFORE UPDATE ON `epamdb`.`deliveries`
FOR EACH ROW
BEGIN
SET NEW.updated_date = NOW();
END$$


USE `epamdb`$$
DROP TRIGGER IF EXISTS `epamdb`.`invoices_BEFORE_INSERT` $$
USE `epamdb`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `epamdb`.`invoices_BEFORE_INSERT`
BEFORE INSERT ON `epamdb`.`invoices`
FOR EACH ROW
BEGIN
SET NEW.created_date = NOW();
SET NEW.updated_date = NOW();
END$$


USE `epamdb`$$
DROP TRIGGER IF EXISTS `epamdb`.`invoices_BEFORE_UPDATE` $$
USE `epamdb`$$
CREATE
DEFINER=`root`@`localhost`
TRIGGER `epamdb`.`invoices_BEFORE_UPDATE`
BEFORE UPDATE ON `epamdb`.`invoices`
FOR EACH ROW
BEGIN
SET NEW.updated_date = NOW();
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

