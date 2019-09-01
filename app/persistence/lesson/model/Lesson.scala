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

// 施設情報 (sample)
//~~~~~~~~~~~~~
case class Lesson(
  id:          Option[Lesson.Id],                // 施設ID
  locationId:  Location.Id,                        // 地域ID
  categoryId:  Category.Id,
  name:        String,                             // 施設名
  detail:      String,                             // 施設名
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
  name:        String,                             // 施設名
  detail:      String,                             // 施設名
  maxPeaple:   Int,
  minPeaple:   Int,
  fee:         Int,
  createDate:  LocalDateTime = LocalDateTime.now,
  deadlineDate:LocalDateTime,
  scheduleDate:LocalDateTime,
  updatedAt:   LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt:   LocalDateTime = LocalDateTime.now   // データ作成日
) {
  def toLesson(locationId: Location.Id, categoryId: Category.Id) =
    Lesson(None, locationId, categoryId, name, detail, maxPeaple, minPeaple, fee, createDate, deadlineDate, scheduleDate)
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
      "max_peaple" -> number,
      "min_peaple" -> number,
      "fee" -> number,
      "deadline_date" -> localDateTime("yyyy-mm-ddTHH:MM"),
      "schedule_date" -> localDateTime("yyyy-mm-ddTHH:MM"),
    )(
      (x1, x2, x3, x4, x5, x6, x7) => LessonFromInput(
        name         = x1,
        detail       = x2,
        maxPeaple    = x3,
        minPeaple    = x4,
        fee          = x5,
        deadlineDate = x6,
        scheduleDate = x7
      )
    )(LessonFromInput.unapply(_).map(t =>
      (t._1, t._2, t._3, t._4, t._5, t._7, t._8)
    ))
  )
}
