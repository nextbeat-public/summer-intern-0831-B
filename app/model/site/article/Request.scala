package model.site.article

import model.component.util.ViewValuePageLayout
import persistence.category.model.Category
import persistence.geo.model.Location
import persistence.request.model.Request

case class SiteViewValueRequest(
  layout:     ViewValuePageLayout,
  locations:  Seq[Location],
  categories: Seq[Category],
  request:    Request,
) {

}
