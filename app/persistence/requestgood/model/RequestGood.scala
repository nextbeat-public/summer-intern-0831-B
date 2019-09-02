package persistence.requestgood.model

import java.time.LocalDateTime
import persistence.request.model.Request
import persistence.udb.model.User

case class RequestGood(
  id:        Option[LessonJoin.Id],         // 関係ID
  userId:    User.Id,
  requestId:  Request.Id,
  updatedAt: LocalDateTime = LocalDateTime.now,
  createdAt: LocalDateTime = LocalDateTime.now,
)

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object RequestGood{

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long
}