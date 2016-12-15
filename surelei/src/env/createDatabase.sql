--mysql
drop database surelei;
create database surelei DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
grant all privileges on surelei.* to surelei@localhost identified by 'surelei';
