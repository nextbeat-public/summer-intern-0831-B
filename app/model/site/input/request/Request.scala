/*
 * This file is part of the MARIAGE services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.input.request

import model.component.util.ViewValuePageLayout
import persistence.geo.model.Location
import persistence.category.model.Category

// 表示: 施設一覧
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValueInputRequest(
  layout:     ViewValuePageLayout,
  locations:  Seq[Location],
  categories: Seq[Category]
)
