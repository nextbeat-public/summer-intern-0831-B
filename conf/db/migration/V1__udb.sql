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
 "updated_at"                     TIMESTAMP    NOT      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 "created_at"                     TIMESTAMP    NOT      NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

INSERT INTO "user_request"
 ("user_request_name", "user_request_detail", "user_request_date", "category_id", "location_id", "user_request_good")
VALUES
 ('逆提案サンプル', 'こんな勉強会やりたい', '2019-12-31', '01000', '13000', 0);

CREATE TABLE "request_good_relationship" (
 "id"                             INT          NOT     NULL AUTO_INCREMENT PRIMARY KEY,
 "user_id"                        INT,
 "user_request_id"                INT,
 "updated_at"                     TIMESTAMP    NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 "created_at"                     TIMESTAMP    NOT     NULL DEFAULT CURRENT_TIMESTAMP
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
 "category_id"                    VARCHAR(8)   NOT     NULL,
 "location_id"                    VARCHAR(8)   NOT     NULL,
 "user_request_id"                INT          NOT     NULL,
 "updated_at"                     TIMESTAMP    NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 "created_at"                     TIMESTAMP    NOT     NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

INSERT INTO "teacher_request" (
  "teacher_request_name",
  "teacher_request_detail",
  "teacher_request_date",
  "teacher_request_deadline",
  "teacher_request_maximum_people",
  "teacher_request_minimum_people",
  "teacher_request_scheduled_date",
  "teacher_request_study_fee",
  "category_id",
  "location_id",
  "user_request_id",
) VALUES
 ('提案します！', 'こんな内容でどうでしょう?', '2020-08-30', '2020-07-30', 20, 10, '2020-08-30', 10000, '06000', '13000', 1);

CREATE TABLE "participation_status_relationship" (
 "id"                             INT          NOT     NULL AUTO_INCREMENT PRIMARY KEY,
 "user_id"                        INT,
 "teacher_request_id"             INT,
 "updated_at"                     TIMESTAMP    NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 "created_at"                     TIMESTAMP    NOT     NULL DEFAULT CURRENT_TIMESTAMP,
) ENGINE=InnoDB;

