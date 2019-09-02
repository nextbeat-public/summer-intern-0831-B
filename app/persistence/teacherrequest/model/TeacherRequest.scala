/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.teacherrequest.model

import play.api.data._
import play.api.data.Forms._
import java.time.LocalDateTime
import persistence.geo.model.Location
import persistence.category.model.Category
import persistence.request.model.Request

// 施設情報 (sample)
//~~~~~~~~~~~~~
case class TeacherRequest(
  id:          Option[TeacherRequest.Id],          // 施設ID
  requestId:   Request.Id,
  locationId:  Location.Id,                        // 地域ID
  categoryId:  Category.Id,
  name:        String,
  detail:      String,
  maxPeople:   Int,
  minPeople:   Int,
  fee:         Int,
  createDate:  LocalDateTime = LocalDateTime.now,
  deadlineDate:LocalDateTime,
  scheduleDate:LocalDateTime,
  updatedAt:   LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt:   LocalDateTime = LocalDateTime.now   // データ作成日
)

case class TeacherRequestInput(
  name:        String,
  detail:      String,
  maxPeople:   Int,
  minPeople:   Int,
  fee:         Int,
  createDate:  LocalDateTime = LocalDateTime.now,
  deadlineDate:LocalDateTime,
  scheduleDate:LocalDateTime,
  updatedAt:   LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt:   LocalDateTime = LocalDateTime.now   // データ作成日
) {
  def toTeacherRequest(requestId: Request.Id, locationId: Location.Id, categoryId: Category.Id) =
    TeacherRequest(None, requestId, locationId, categoryId, name, detail, maxPeople, minPeople, fee, createDate, deadlineDate, scheduleDate)
}

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object TeacherRequest {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  // --[ フォーム定義 ]---------------------------------------------------------
  val formForTeacherRequestInput = Form(
    mapping(
      "name" -> nonEmptyText,
      "detail" -> nonEmptyText,
      "max_people" -> number,
      "min_people" -> number,
      "fee" -> number,
      "deadline_date" -> localDateTime("yyyy-mm-dd'T'HH:MM"),
      "schedule_date" -> localDateTime("yyyy-mm-dd'T'HH:MM"),
    )(
      (x1, x2, x3, x4, x5, x6, x7) => TeacherRequestInput(
        name         = x1,
        detail       = x2,
        maxPeople    = x3,
        minPeople    = x4,
        fee          = x5,
        deadlineDate = x6,
        scheduleDate = x7
      )
    )(TeacherRequestInput.unapply(_).map(t =>
      (t._1, t._2, t._3, t._4, t._5, t._7, t._8)
    ))
  )
}
