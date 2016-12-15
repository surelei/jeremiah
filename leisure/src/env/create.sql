--mysql
create database leisure DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
grant all privileges on leisure.* to leisure@localhost identified by 'leisure';

create database test DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
grant all privileges on test.* to test@localhost identified by '123456';