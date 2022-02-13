// auto increment 초기화

ALTER TABLE chatroom_table auto_increment = 1;
SET @COUNT = 0;
UPDATE chatroom_table SET cnum = @COUNT:=@COUNT+1;

// 권한부여
GRANT ALL PRIVILEGES ON `%`.* TO 'root'@'%' IDENTIFIED BY '1234' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* to 'root'@'%' IDENTIFIED BY '1234';
SET PASSWORD FOR 'root'@'localhost' = PASSWORD('1234');

// 스키마 생성
create schema `mychatproj` default character set utf8;

CREATE TABLE `member` (
  `member_no` int NOT NULL AUTO_INCREMENT,
  `member_id` varchar(32) DEFAULT NULL,
  `member_pwd` varchar(50) DEFAULT NULL,
  `member_name` varchar(32) DEFAULT NULL,
  `member_email` varchar(40) DEFAULT NULL,
  `member_phone` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`member_no`)
);

CREATE TABLE `member_profileimg` (
  `member_profileimg_no` int NOT NULL AUTO_INCREMENT,
  `member_no` int DEFAULT NULL,
  `member_profileimg_filename` varchar(200) DEFAULT NULL,
  `member_profileimg_original_filename` varchar(300) DEFAULT NULL,
  `member_profileimg_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`member_profileimg_no`),
  KEY `member_member_no_idx` (`member_no`),
  CONSTRAINT `member_member_no` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `chatroom` (
  `chatroom_no` int NOT NULL AUTO_INCREMENT,
  `chatroom_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`chatroom_no`)
);

CREATE TABLE `chatroom_member` (
  `chatroom_member_no` int NOT NULL AUTO_INCREMENT,
  `chatroom_no` int DEFAULT NULL,
  `member_no` int DEFAULT NULL,
  PRIMARY KEY (`chatroom_member_no`),
  KEY `chatroom_member_member_no_idx` (`member_no`),
  KEY `chatroom_member_chatroom_no_idx` (`chatroom_no`),
  CONSTRAINT `chatroom_member_chatroom_no` FOREIGN KEY (`chatroom_no`) REFERENCES `chatroom` (`chatroom_no`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `chatroom_member_member_no` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`) ON DELETE CASCADE
);

CREATE TABLE `chatlog` (
  `chatlog_no` int NOT NULL AUTO_INCREMENT,
  `member_no` int DEFAULT NULL,
  `chatroom_no` int DEFAULT NULL,
  `chatlog_log` varchar(400) DEFAULT NULL,
  `chatlog_time` varchar(100) DEFAULT NULL,
  `chatlog_division` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`chatlog_no`),
  KEY `chatlog_chatroom_no_idx` (`chatroom_no`),
  CONSTRAINT `chatlog_chatroom_no` FOREIGN KEY (`chatroom_no`) REFERENCES `chatroom` (`chatroom_no`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `chat_filelist` (
  `chat_filelist_no` int NOT NULL AUTO_INCREMENT,
  `chatroom_no` int DEFAULT NULL,
  `member_no` int DEFAULT NULL,
  `chat_filelist_filename` varchar(500) DEFAULT NULL,
  `chat_filelist_original_filename` varchar(300) DEFAULT NULL,
  `chat_filelist_time` varchar(100) DEFAULT NULL,
  `chat_filelist_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`chat_filelist_no`),
  KEY `chat_filelist_member_no` (`member_no`),
  KEY `chat_filelist_chatroom_no_idx` (`chatroom_no`),
  CONSTRAINT `chat_filelist_chatroom_no` FOREIGN KEY (`chatroom_no`) REFERENCES `chatroom` (`chatroom_no`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `chat_filelist_member_no` FOREIGN KEY (`member_no`) REFERENCES `member` (`member_no`) ON DELETE CASCADE ON UPDATE CASCADE
);