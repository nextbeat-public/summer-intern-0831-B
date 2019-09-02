
package persistence.lessonjoin.model

import java.time.LocalDateTime
import persistence.teacherrequest.model.TeacherRequest
import persistence.udb.model.User

case class LessonJoin(
  id:        Option[LessonJoin.Id],         // 関係ID
  userId:    Option[User.Id]
  lessonId:  Option[TeacherRequest.Id],
)

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object LessonJoin{

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long
}