CREATE TABLE `test`.`nm_user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `avatar` VARCHAR(45) NULL,
  `created` TIMESTAMP NULL,
  `updated` TIMESTAMP NULL,
  `usercol` VARCHAR(45) NULL,
  `status` TINYINT(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`));
