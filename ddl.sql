
CREATE TABLE `a_board` (
  `board_id` char(30) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `thumbnail` varchar(50) DEFAULT NULL,
  `created_by` varchar(30) DEFAULT NULL,
  `creation_date` datetime DEFAULT current_timestamp(),
  `modified_by` varchar(30) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`board_id`),
  KEY `a_board_fk` (`created_by`),
  CONSTRAINT `a_board_fk` FOREIGN KEY (`created_by`) REFERENCES `a_user` (`login_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='게시판'

CREATE TABLE `a_role` (
  `ROLE_CD` varchar(30) NOT NULL COMMENT '권한코드',
  `ROLE_NM` varchar(60) DEFAULT NULL COMMENT '권한명',
  `ROLE_SELECT_CD` varchar(30) DEFAULT NULL COMMENT '조회권한',
  `ROLE_DC` varchar(200) DEFAULT NULL COMMENT '권한설명',
  PRIMARY KEY (`ROLE_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `a_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` varchar(30) NOT NULL COMMENT '로그인ID_사번',
  `login_pw` varchar(255) NOT NULL COMMENT '로그인PW',
  `name` varchar(80) DEFAULT NULL COMMENT '사용자명',
  `enable_flag` tinyint(1) DEFAULT 1,
  `attribute_1` varchar(255) DEFAULT NULL,
  `attribute_2` varchar(30) DEFAULT NULL,
  `attribute_3` varchar(30) DEFAULT NULL,
  `attribute_4` varchar(30) DEFAULT NULL,
  `attribute_5` varchar(30) DEFAULT NULL,
  `created_by` varchar(30) DEFAULT NULL COMMENT '생성자',
  `creation_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `modified_by` varchar(30) DEFAULT NULL COMMENT '수정자',
  `modified_date` datetime DEFAULT NULL ON UPDATE current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`id`),
  UNIQUE KEY `A_USER_login_id_uindex` (`login_id`),
  KEY `A_USER_FK1` (`created_by`),
  KEY `A_USER_FK2` (`modified_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자'

CREATE TABLE `refresh_token` (
  `key` varchar(30) NOT NULL,
  `value` varchar(100) NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `u_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `original_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '원본파일명',
  `stored_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '저장파일명',
  `file_type` varchar(30) DEFAULT NULL COMMENT '파일 타입',
  `file_ext` varchar(30) DEFAULT NULL COMMENT '파일 확장자',
  `file_amount` int(11) DEFAULT NULL COMMENT '용량(Byte)',
  `thumbnail` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '썸네일 파일명',
  `attribute_1` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '썸네일_저장파일명',
  `attribute_2` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '썸네일_파일경로',
  `attribute_3` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '비고3',
  `attribute_4` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '비고4',
  `attribute_5` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '비고5',
  `created_by` varchar(30) DEFAULT NULL COMMENT '생성자',
  `creation_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `modified_by` varchar(30) DEFAULT NULL COMMENT '수정자',
  `modified_date` datetime DEFAULT current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`id`),
  UNIQUE KEY `U_FILE_stored_name_uindex` (`stored_name`),
  UNIQUE KEY `U_FILE_stored_thumbnail_uindex` (`thumbnail`),
  KEY `U_FILE_FK2` (`created_by`),
  KEY `U_FILE_FK3` (`modified_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `a_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) DEFAULT NULL COMMENT 'A_USER.ID',
  `role_cd` varchar(30) NOT NULL COMMENT '권한',
  `creation_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `modified_date` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`id`),
  KEY `A_USER_ROLE_FK` (`user_id`),
  CONSTRAINT `A_USER_ROLE_FK` FOREIGN KEY (`user_id`) REFERENCES `a_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자_권한'

CREATE TABLE `board_seq` (
                             `next_not_cached_value` bigint(21) NOT NULL,
                             `minimum_value` bigint(21) NOT NULL,
                             `maximum_value` bigint(21) NOT NULL,
                             `start_value` bigint(21) NOT NULL COMMENT 'start value when sequences is created or value if RESTART is used',
                             `increment` bigint(21) NOT NULL COMMENT 'increment value',
                             `cache_size` bigint(21) unsigned NOT NULL,
                             `cycle_option` tinyint(1) unsigned NOT NULL COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
                             `cycle_count` bigint(21) NOT NULL COMMENT 'How many cycles have been done'
) ENGINE=InnoDB SEQUENCE=1
