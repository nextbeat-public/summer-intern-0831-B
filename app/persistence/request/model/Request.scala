/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.request.model

import java.time.LocalDateTime
import persistence.geo.model.Location
import persistence.category.model.Category

case class Request(
  id:          Option[Request.Id],                 // 施設ID
  name:        String,                             // 施設名
  detail:      String,                             // 住所(詳細)
  date:        LocalDateTime = LocalDateTime.now,  // データ更新日
  numGood:     Int,
  locationId:  Location.Id,                        // 地域ID
  categoryId:  Category.Id,
  updatedAt:   LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt:   LocalDateTime = LocalDateTime.now   // データ作成日
)

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object Request{

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long
}
