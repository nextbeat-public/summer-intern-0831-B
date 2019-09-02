/*
 * This file is part of the MARIAGE services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.input.request

import java.time.LocalDateTime
import model.component.util.ViewValuePageLayout
import model.site.input.request.SiteViewValueInputRequest.RequestForm
import persistence.geo.model.Location
import persistence.category.model.Category
import play.api.data.Form
import play.api.data.Forms._
import persistence.request.model.Request

// 表示: 施設一覧
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValueInputRequest(
  layout:     ViewValuePageLayout,
  locations:  Seq[Location],
  categories: Seq[Category],
  form:       Form[RequestForm]
)

object SiteViewValueInputRequest {

  case class RequestForm(
    name: String,
    detail: String,
    locationId: Location.Id,
    categoryId: Category.Id,
  ){
    def toRequest =
      Request(None, name, detail, LocalDateTime.now, 0,
        locationId, categoryId, LocalDateTime.now, LocalDateTime.now)
  }

  val formForRequest = Form(
    mapping(
      "name" -> nonEmptyText,
      "detail" -> nonEmptyText,
      "locationId" -> nonEmptyText,
      "categoryId" -> nonEmptyText,
    )(RequestForm.apply)(RequestForm.unapply)
  )
}
