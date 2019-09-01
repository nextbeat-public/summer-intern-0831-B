-- ユーザ定義
-- ------------
CREATE TABLE "udb_user" (
  "id"                            INT          NOT     NULL AUTO_INCREMENT PRIMARY KEY,
  "name_first"                    VARCHAR(255) NOT     NULL,
  "name_last"                     VARCHAR(255) NOT     NULL,
  "email"                         VARCHAR(255) DEFAULT NULL,
  "pref"                          VARCHAR(8)   DEFAULT NULL,
  "address"                       VARCHAR(255) DEFAULT NULL,
  "updated_at"                    TIMESTAMP    NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"                    TIMESTAMP    NOT     NULL DEFAULT CURRENT_TIMESTAMP,
  "user_detail"                   VARCHAR(255) DEFAULT NULL,
  "user_money"                    INT          DEFAULT 100000000 NOT NULL,
  "category_id"                   VARCHAR(8)   NOT     NULL,
  UNIQUE KEY "udb_user_ukey01" ("email")
) ENGINE=InnoDB;

-- ユーザ・パスワード
-- --------------------
CREATE TABLE "udb_user_password" (
  "id"                            INT          NOT      NULL PRIMARY KEY,
  "password"                      VARCHAR(255) NOT      NULL ,
  "updated_at"                    TIMESTAMP    NOT      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"                    TIMESTAMP    NOT      NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ユーザ・セッション
-- --------------------
CREATE TABLE "udb_user_session" (
  "id"                            INT          NOT      NULL PRIMARY KEY,
  "token"                         VARCHAR(64)  NOT      NULL,
  "exprity"                       TIMESTAMP    NOT      NULL DEFAULT CURRENT_TIMESTAMP,
  "updated_at"                    TIMESTAMP    NOT      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"                    TIMESTAMP    NOT      NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY "udb_user_session_ukey01"("token")
) ENGINE=InnoDB;

CREATE TABLE "user_request" (
 "user_request_id"                INT          NOT     NULL AUTO_INCREMENT PRIMARY KEY,
 "user_request_name"              VARCHAR(255) NOT     NULL,
 "user_request_detail"            VARCHAR(255) DEFAULT NULL,
 "user_request_date"              DATE,
 "category_id"                    VARCHAR(8)   NOT     NULL,
 "location_id"                    VARCHAR(8)   NOT     NULL,
 "user_request_good"              INT,
 "id"                             INT          NOT     NULL,
 "updated_at"                     TIMESTAMP    NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 "created_at"                     TIMESTAMP    NOT     NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE "request_good_relationship" (
 "id"                             INT          NOT     NULL,
 "user_request_id"                INT          NOT     NULL,
 "updated_at"                    TIMESTAMP     NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 "created_at"                    TIMESTAMP     NOT     NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE "teacher_request" (
 "teacher_request_id"             INT          NOT     NULL AUTO_INCREMENT PRIMARY KEY,
 "teacher_request_name"           VARCHAR(255) NOT     NULL,
 "teacher_request_detail"         VARCHAR(255) NOT     NULL,
 "teacher_request_date"           DATE         NOT     NULL,
 "teacher_request_deadline"       DATE         NOT     NULL,
 "teacher_request_maximum_people" INT,
 "teacher_request_minimum_people" INT,
 "teacher_request_scheduled_date" DATE         NOT     NULL,
 "teacher_request_study_fee"      INT          NOT     NULL,
 "teacher_request_address"        VARCHAR(255) NOT     NULL,
 "category_id"                    VARCHAR(8)   NOT     NULL,
 "location_id"                    VARCHAR(8)   NOT     NULL,
 "user_request_id"                INT          NOT     NULL,
 "id"                             INT          NOT     NULL,
 "updated_at"                    TIMESTAMP     NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 "created_at"                    TIMESTAMP     NOT     NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE "participation_status_relationship" (
 "id"                             INT          NOT     NULL,
 "teacher_request_id"             INT          NOT     NULL,
 "updated_at"                    TIMESTAMP     NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 "created_at"                    TIMESTAMP     NOT     NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;





-- ユーザ定義
-- ------------
CREATE TABLE "udb_user" (
  "id"                            INT          NOT     NULL AUTO_INCREMENT PRIMARY KEY,
  "name_first"                    VARCHAR(255) NOT     NULL,
  "name_last"                     VARCHAR(255) NOT     NULL,
  "email"                         VARCHAR(255) DEFAULT NULL,
  "pref"                          VARCHAR(8)   DEFAULT NULL,
  "address"                       VARCHAR(255) DEFAULT NULL,
  "updated_at"                    TIMESTAMP    NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"                    TIMESTAMP    NOT     NULL DEFAULT CURRENT_TIMESTAMP,
  "user_detail"                   VARCHAR(255) DEFAULT NULL,
  "user_money"                    INT          DEFAULT 100000000 NOT NULL,
  "category_id"                   VARCHAR(8)   NOT     NULL,
  UNIQUE KEY "udb_user_ukey01" ("email")
) ENGINE=InnoDB;

-- ユーザ・パスワード
-- --------------------
CREATE TABLE "udb_user_password" (
  "id"                            INT          NOT      NULL PRIMARY KEY,
  "password"                      VARCHAR(255) NOT      NULL ,
  "updated_at"                    TIMESTAMP    NOT      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"                    TIMESTAMP    NOT      NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ユーザ・セッション
-- --------------------
CREATE TABLE "udb_user_session" (
  "id"                            INT          NOT      NULL PRIMARY KEY,
  "token"                         VARCHAR(64)  NOT      NULL,
  "exprity"                       TIMESTAMP    NOT      NULL DEFAULT CURRENT_TIMESTAMP,
  "updated_at"                    TIMESTAMP    NOT      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"                    TIMESTAMP    NOT      NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY "udb_user_session_ukey01"("token")
) ENGINE=InnoDB;

CREATE TABLE "user_request" (
 "user_request_id"                INT          NOT     NULL AUTO_INCREMENT PRIMARY KEY,
 "user_request_name"              VARCHAR(255) NOT     NULL,
 "user_request_detail"            VARCHAR(255) DEFAULT NULL,
 "user_request_date"              DATE,
 "category_id"                    VARCHAR(8)   NOT     NULL,
 "location_id"                    VARCHAR(8)   NOT     NULL,
 "user_request_good"              INT,
 "id"                             INT          NOT     NULL,
 "updated_at"                     TIMESTAMP    NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 "created_at"                     TIMESTAMP    NOT     NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE "request_good_relationship" (
 "id"                             INT          NOT     NULL,
 "user_request_id"                INT          NOT     NULL,
 "updated_at"                    TIMESTAMP     NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 "created_at"                    TIMESTAMP     NOT     NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE "teacher_request" (
 "teacher_request_id"             INT          NOT     NULL AUTO_INCREMENT PRIMARY KEY,
 "teacher_request_name"           VARCHAR(255) NOT     NULL,
 "teacher_request_detail"         VARCHAR(255) NOT     NULL,
 "teacher_request_date"           DATE         NOT     NULL,
 "teacher_request_deadline"       DATE         NOT     NULL,
 "teacher_request_maximum_people" INT,
 "teacher_request_minimum_people" INT,
 "teacher_request_scheduled_date" DATE         NOT     NULL,
 "teacher_request_study_fee"      INT          NOT     NULL,
 "teacher_request_address"        VARCHAR(255) NOT     NULL,
 "category_id"                    VARCHAR(8)   NOT     NULL,
 "location_id"                    VARCHAR(8)   NOT     NULL,
 "user_request_id"                INT          NOT     NULL,
 "id"                             INT          NOT     NULL,
 "updated_at"                    TIMESTAMP     NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 "created_at"                    TIMESTAMP     NOT     NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE "participation_status_relationship" (
 "id"                             INT          NOT     NULL,
 "teacher_request_id"             INT          NOT     NULL,
 "updated_at"                    TIMESTAMP     NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 "created_at"                    TIMESTAMP     NOT     NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;





