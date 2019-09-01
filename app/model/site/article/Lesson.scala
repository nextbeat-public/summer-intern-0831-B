package model.site.article

import model.component.util.ViewValuePageLayout
import persistence.category.model.Category
import persistence.geo.model.Location
import persistence.teacherrequest.model.TeacherRequest

case class SiteViewValueLesson(
  layout:     ViewValuePageLayout,
  locations:  Seq[Location],
  categories: Seq[Category],
  lesson:     TeacherRequest,
) {

}
