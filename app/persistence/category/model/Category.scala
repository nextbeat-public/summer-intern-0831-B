package persistence.category.model

import java.time.LocalDateTime
import mvc.util.EnumStatus

// 地域情報
//~~~~~~~~~~
case class Category(
  id:           Category.Id,          // 全国地方公共団体コード
  name:         String,                // カテゴリ名
  updatedAt:    LocalDateTime = LocalDateTime.now, // データ更新日
  createdAt:    LocalDateTime = LocalDateTime.now  // データ作成日
)

// コンパニオン
object Category {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = String

}