-- -----------------------------------------------------
-- Table `surelei`.`T_MENU_INFO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T_MENU_INFO` (
  `MI_ID` INT UNSIGNED NOT NULL,
  `MI_CODE` VARCHAR(50) NOT NULL,
  `MI_NAME` VARCHAR(50) NOT NULL,
  `MI_LEVEL` INT UNSIGNED NOT NULL,
  `MI_ORDER` INT NOT NULL,
  `MI_TYPE` INT NOT NULL,
  `MI_URL` VARCHAR(200) NULL,
  `MI_IMG_PATH` VARCHAR(200) NULL,
  `MI_ID_PATH` VARCHAR(200) NULL,
  `MI_NAME_PATH` VARCHAR(200) NULL,
  `MI_PARENT_ID` INT UNSIGNED NULL,
  PRIMARY KEY (`MI_ID`),
  UNIQUE INDEX `MI_ID_UNIQUE` (`MI_ID` ASC),
  UNIQUE INDEX `MI_CODE_UNIQUE` (`MI_CODE` ASC),
  INDEX `fk_T_MENU_INFO_T_MENU_INFO_idx` (`MI_PARENT_ID` ASC),
  CONSTRAINT `fk_T_MENU_INFO_T_MENU_INFO`
    FOREIGN KEY (`MI_PARENT_ID`)
    REFERENCES `T_MENU_INFO` (`MI_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `surelei`.`T_ORG_INFO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T_ORG_INFO` (
  `OI_ID` INT UNSIGNED NOT NULL,
  `OI_NAME` VARCHAR(100) NOT NULL,
  `OI_LEVEL` INT NULL,
  `OI_ORDER` INT NULL,
  `OI_DESC` VARCHAR(500) NULL,
  `OI_ID_PATH` VARCHAR(200) NULL,
  `OI_NAME_PATH` VARCHAR(1000) NULL,
  `OI_PARENT_ID` INT UNSIGNED NULL,
  PRIMARY KEY (`OI_ID`),
  UNIQUE INDEX `OI_ID_UNIQUE` (`OI_ID` ASC),
  INDEX `fk_T_ORG_INFO_T_ORG_INFO1_idx` (`OI_PARENT_ID` ASC),
  CONSTRAINT `fk_T_ORG_INFO_T_ORG_INFO1`
    FOREIGN KEY (`OI_PARENT_ID`)
    REFERENCES `T_ORG_INFO` (`OI_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `surelei`.`T_USER_INFO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T_USER_INFO` (
  `UI_ID` INT UNSIGNED NOT NULL,
  `UI_NAME` VARCHAR(50) NOT NULL,
  `UI_PASS` VARCHAR(50) NOT NULL,
  `UI_FIRST_NAME` VARCHAR(50) NULL,
  `UI_LAST_NAME` VARCHAR(50) NULL,
  `OI_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`UI_ID`),
  UNIQUE INDEX `UI_ID_UNIQUE` (`UI_ID` ASC),
  UNIQUE INDEX `UI_NAME_UNIQUE` (`UI_NAME` ASC),
  INDEX `fk_T_USER_INFO_T_ORG_INFO1_idx` (`OI_ID` ASC),
  CONSTRAINT `fk_T_USER_INFO_T_ORG_INFO1`
    FOREIGN KEY (`OI_ID`)
    REFERENCES `T_ORG_INFO` (`OI_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `surelei`.`T_ROLE_INFO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T_ROLE_INFO` (
  `RI_ID` INT UNSIGNED NOT NULL,
  `RI_NAME` VARCHAR(50) NOT NULL,
  `RI_DESC` VARCHAR(500) NULL,
  PRIMARY KEY (`RI_ID`),
  UNIQUE INDEX `RI_ID_UNIQUE` (`RI_ID` ASC),
  UNIQUE INDEX `RI_CODE_UNIQUE` (`RI_NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `surelei`.`T_USER_ROLE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T_USER_ROLE` (
  `UR_ID` INT UNSIGNED NOT NULL,
  `UI_ID` INT UNSIGNED NOT NULL,
  `RI_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`UR_ID`),
  UNIQUE INDEX `UR_ID_UNIQUE` (`UR_ID` ASC),
  INDEX `fk_T_USER_ROLE_T_USER_INFO1_idx` (`UI_ID` ASC),
  INDEX `fk_T_USER_ROLE_T_ROLE_INFO1_idx` (`RI_ID` ASC),
  CONSTRAINT `fk_T_USER_ROLE_T_USER_INFO1`
    FOREIGN KEY (`UI_ID`)
    REFERENCES `T_USER_INFO` (`UI_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_T_USER_ROLE_T_ROLE_INFO1`
    FOREIGN KEY (`RI_ID`)
    REFERENCES `T_ROLE_INFO` (`RI_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `surelei`.`T_RES_INFO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T_RES_INFO` (
  `RSI_ID` INT UNSIGNED NOT NULL,
  `RSI_NAME` VARCHAR(50) NOT NULL,
  `RSI_URL` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`RSI_ID`),
  UNIQUE INDEX `RSI_ID_UNIQUE` (`RSI_ID` ASC),
  UNIQUE INDEX `RSI_NAME_UNIQUE` (`RSI_NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `surelei`.`T_ROLE_RES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T_ROLE_RES` (
  `RR_ID` INT UNSIGNED NOT NULL,
  `RI_ID` INT UNSIGNED NOT NULL,
  `RSI_ID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`RR_ID`),
  UNIQUE INDEX `RR_ID_UNIQUE` (`RR_ID` ASC),
  INDEX `fk_T_ROLE_RES_T_ROLE_INFO1_idx` (`RI_ID` ASC),
  INDEX `fk_T_ROLE_RES_T_RES_INFO1_idx` (`RSI_ID` ASC),
  CONSTRAINT `fk_T_ROLE_RES_T_ROLE_INFO1`
    FOREIGN KEY (`RI_ID`)
    REFERENCES `T_ROLE_INFO` (`RI_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_T_ROLE_RES_T_RES_INFO1`
    FOREIGN KEY (`RSI_ID`)
    REFERENCES `T_RES_INFO` (`RSI_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;