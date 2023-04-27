drop database if exists pmsdb;
drop user if exists `restadmin`@`%`;
create database if not exists pmsdb CHARACTER SET utf8mb4 collate utf8mb4_unicode_ci;
create user if not exists `restadmin`@`%` identified with mysql_native_password by 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `pmsdb`.* TO `restadmin`@`%`;
