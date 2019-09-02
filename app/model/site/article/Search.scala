package model.site.article

import model.component.util.ViewValuePageLayout
import persistence.category.model.Category
import persistence.geo.model.Location
import persistence.request.model.Request
import persistence.teacherrequest.model.TeacherRequest
import play.api.data._
import play.api.data.Forms._

case class SiteViewValueSearch(
  layout:     ViewValuePageLayout,
  locations:  Seq[Location],
  categories: Seq[Category],
  requests:   Seq[Request],
  lessons:    Seq[TeacherRequest],
)

// 検索フォーム用value
case class FormValueForSiteSearch(
  locationIdOpt: Option[Location.Id],
  categoryIdOpt: Option[Category.Id]
)

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object FormValueForSiteSearch {

  // --[ フォーム定義 ]---------------------------------------------------------
  val formForSearch = Form(
    mapping(
      "locationId" -> optional(text),
      "categoryId" -> optional(text),
    )(FormValueForSiteSearch.apply)(FormValueForSiteSearch.unapply)
  )
}
