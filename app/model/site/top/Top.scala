/*
 * This file is part of the MARIAGE services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.top

import model.component.util.ViewValuePageLayout
import play.api.data.Form
import play.api.data.Forms._

// 表示: 施設一覧
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValueTop(
  layout:     ViewValuePageLayout,
)
