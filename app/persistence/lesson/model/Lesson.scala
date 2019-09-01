/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.facility.model

import play.api.data._
import play.api.data.Forms._
import java.time.LocalDateTime
import persistence.geo.model.Location
import persistence.category.model.Category
import persistence.request.model.Request

// 施設情報 (sample)
//~~~~~~~~~~~~~
case class Lesson(
  id:          Option[Lesson.Id],                // 施設ID
  requestId:  Request.Id,
  locationId:  Location.Id,                        // 地域ID
  categoryId:  Category.Id,
  name:        String,                             // 施設名
  detail:      String,                             // 施設名
  address:     String,                             // 住所(詳細)
  maxPeaple:   Int,
  minPeaple:   Int,
  fee:         Int,
  createDate:  LocalDateTime = LocalDateTime.now,
  deadlineDate:LocalDateTime,
  scheduleDate:LocalDateTime,
  updatedAt:   LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt:   LocalDateTime = LocalDateTime.now   // データ作成日
)

// 施設検索
//case class FacilitySearch(
//  locationIdOpt: Option[Location.Id]
//)

case class LessonFromInput (
  id:          Option[Lesson.Id],                // 施設ID
  name:        String,                             // 施設名
  detail:      String,                             // 施設名
  address:     String,                             // 住所(詳細)
  maxPeaple:   Int,
  minPeaple:   Int,
  fee:         Int,
  createDate:  LocalDateTime = LocalDateTime.now,
  deadlineDate:LocalDateTime,
  scheduleDate:LocalDateTime,
  updatedAt:   LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt:   LocalDateTime = LocalDateTime.now   // データ作成日
) {
  def toLesson(locationId: Location.Id, categoryId: Category.Id, requestId: Request.Id) =
    Lesson(id, requestId, locationId, categoryId, name, detail, address, maxPeaple, minPeaple, fee, createDate, deadlineDate, scheduleDate)
}

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object Lesson {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long


  // --[ フォーム定義 ]---------------------------------------------------------
  val formForLesson = Form(
    mapping(
      "name" -> text,
      "detail" -> text,
      "address" -> text,
      "max_peaple" -> number,
      "min_peaple" -> number,
      "fee" -> number,
      "deadline_date" -> LocalDateTime("yyyy-mm-ddTHH:MM"),
      "schedule_date" -> LocalDateTime("yyyy-mm-ddTHH:MM"),
    )(LessonFromInput.apply)(LessonFromInput.unapply)
  )
}
