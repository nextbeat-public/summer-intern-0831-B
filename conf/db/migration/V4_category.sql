-- カテゴリ情報
--------------
CREATE TABLE "category_list" (
  "id"         varchar(8)   NOT     NULL PRIMARY KEY,
  "name"       varchar(255) DEFAULT NULL,
  "updated_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
) ENGINE=InnoDB;

INSERT INTO "category_list" VALUES ('01000', '農業', NOW(), NOW());
INSERT INTO "category_list" VALUES ('02000', '林業', NOW(), NOW());
INSERT INTO "category_list" VALUES ('03000', '漁業', NOW(), NOW());
INSERT INTO "category_list" VALUES ('04000', '建設業', NOW(), NOW());
INSERT INTO "category_list" VALUES ('05000', '製造業', NOW(), NOW());
INSERT INTO "category_list" VALUES ('06000', 'IT', NOW(), NOW());

