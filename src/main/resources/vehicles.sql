CREATE IF NOT EXISTS DATABASE invoices;
USE `invoices` ;

CREATE TABLE IF NOT EXISTS `invoices`.`brand` (
  `id` VARCHAR(45) NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  PRIMARY KEY (`id`));


CREATE TABLE IF NOT EXISTS `invoices`.`engine` (
  `id` VARCHAR(45) NOT NULL,
  `volume` DOUBLE NOT NULL,
  `valves` INT NOT NULL,
  `Brand_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Engine_Brand1_idx` (`Brand_id` ASC) VISIBLE,
  INDEX `fk_Engine_Brand1` (`Brand_id` ASC) VISIBLE,
  CONSTRAINT `fk_Engine_Brand1`
    FOREIGN KEY (`Brand_id`)
    REFERENCES `invoices`.`brand` (`id`)
    ON DELETE cascade
    ON UPDATE cascade);


CREATE TABLE IF NOT EXISTS `invoices`.`invoices` (
  `id` VARCHAR(45) NOT NULL,
  `Created` TIMESTAMP NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  PRIMARY KEY (`id`));


CREATE TABLE IF NOT EXISTS `invoices`.`motos` (
  `id` VARCHAR(45) NOT NULL,
  `Model` VARCHAR(45) NOT NULL,
  `Price` DECIMAL NOT NULL,
  `Created` TIMESTAMP NOT NULL,
  `Landing` INT NOT NULL,
  `Brand_id` VARCHAR(45) NOT NULL,
  `Engine_id` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_Motos_Brand1_idx` (`Brand_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  INDEX `fk_Motos_Brand1` (`Brand_id` ASC) VISIBLE,
  INDEX `motos___engine_fk` (`Engine_id` ASC) VISIBLE,
  CONSTRAINT `fk_Motos_Brand1`
    FOREIGN KEY (`Brand_id`)
    REFERENCES `invoices`.`brand` (`id`),
  CONSTRAINT `motos___engine_fk`
    FOREIGN KEY (`Engine_id`)
    REFERENCES `invoices`.`engine` (`id`)
    ON DELETE cascade
    ON UPDATE cascade);


CREATE TABLE IF NOT EXISTS `invoices`.`trucks` (
  `id` VARCHAR(45) NOT NULL,
  `Model` VARCHAR(45) NOT NULL,
  `Price` DECIMAL NOT NULL,
  `Created` TIMESTAMP NOT NULL,
  `Capacity` INT NOT NULL,
  `Brand_id` VARCHAR(45) NOT NULL,
  `Engine_id` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_Trucks_Brand1_idx` (`Brand_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  INDEX `fk_Trucks_Brand1` (`Brand_id` ASC) VISIBLE,
  INDEX `trucks__Engine_fk` (`Engine_id` ASC) VISIBLE,
  CONSTRAINT `fk_Trucks_Brand1`
    FOREIGN KEY (`Brand_id`)
    REFERENCES `invoices`.`brand` (`id`),
  CONSTRAINT `trucks__Engine_fk`
    FOREIGN KEY (`Engine_id`)
    REFERENCES `invoices`.`engine` (`id`)
    ON DELETE cascade
    ON UPDATE cascade);


CREATE TABLE IF NOT EXISTS `invoices`.`type` (
  `id` VARCHAR(45) NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  PRIMARY KEY (`id`));


CREATE TABLE IF NOT EXISTS `invoices`.`autos` (
  `id` VARCHAR(45) NOT NULL,
  `Model` VARCHAR(45) NOT NULL,
  `Price` DECIMAL NOT NULL,
  `Created` TIMESTAMP NOT NULL,
  `Type_id` VARCHAR(45) NOT NULL,
  `Brand_id` VARCHAR(45) NOT NULL,
  `Engine_id` VARCHAR(45) NULL DEFAULT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_Autos_Brand1_idx` (`Brand_id` ASC) VISIBLE,
  INDEX `fk_Autos_Engine1_idx` (`Engine_id` ASC) VISIBLE,
  INDEX `fk_Autos_Type_idx` (`Type_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  INDEX `fk_Autos_Brand1` (`Brand_id` ASC) VISIBLE,
  INDEX `fk_Autos_Engine1` (`Engine_id` ASC) VISIBLE,
  INDEX `fk_Autos_Type` (`Type_id` ASC) VISIBLE,
  CONSTRAINT `fk_Autos_Brand1`
    FOREIGN KEY (`Brand_id`)
    REFERENCES `invoices`.`brand` (`id`),
  CONSTRAINT `fk_Autos_Engine1`
    FOREIGN KEY (`Engine_id`)
    REFERENCES `invoices`.`engine` (`id`),
  CONSTRAINT `fk_Autos_Type`
    FOREIGN KEY (`Type_id`)
    REFERENCES `invoices`.`type` (`id`));


CREATE TABLE IF NOT EXISTS `invoices`.`invoices_in_invoice` (
  `Invoices_id` VARCHAR(45) NOT NULL,
  `Autos_id` VARCHAR(45) NULL DEFAULT NULL,
  `Trucks_id` VARCHAR(45) NULL DEFAULT NULL,
  `Motos_id` VARCHAR(45) NULL DEFAULT NULL,
  `id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_invoices_in_Invoice_Autos1_idx` (`Autos_id` ASC) VISIBLE,
  INDEX `fk_invoices_in_Invoice_Invoices1_idx` (`Invoices_id` ASC) VISIBLE,
  INDEX `fk_invoices_in_Invoice_Motos1_idx` (`Motos_id` ASC) VISIBLE,
  INDEX `fk_invoices_in_Invoice_Trucks1_idx` (`Trucks_id` ASC) VISIBLE,
  INDEX `fk_invoices_in_Invoice_Autos1` (`Autos_id` ASC) VISIBLE,
  INDEX `fk_invoices_in_Invoice_Invoices1` (`Invoices_id` ASC) VISIBLE,
  INDEX `fk_invoices_in_Invoice_Motos1` (`Motos_id` ASC) VISIBLE,
  INDEX `fk_invoices_in_Invoice_Trucks1` (`Trucks_id` ASC) VISIBLE,
  CONSTRAINT `fk_invoices_in_Invoice_Autos1`
    FOREIGN KEY (`Autos_id`)
    REFERENCES `invoices`.`autos` (`id`),
  CONSTRAINT `fk_invoices_in_Invoice_Invoices1`
    FOREIGN KEY (`Invoices_id`)
    REFERENCES `invoices`.`invoices` (`id`),
  CONSTRAINT `fk_invoices_in_Invoice_Motos1`
    FOREIGN KEY (`Motos_id`)
    REFERENCES `invoices`.`motos` (`id`),
  CONSTRAINT `fk_invoices_in_Invoice_Trucks1`
    FOREIGN KEY (`Trucks_id`)
    REFERENCES `invoices`.`trucks` (`id`));
