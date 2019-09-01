package model.site.search

import model.component.util.ViewValuePageLayout
import persistence.geo.model.Location

case class SiteViewValueSearch(
  layout:   ViewValuePageLayout,
  locations: Seq[Location],
  category:Seq[Category],
  requests:Seq[Request],
  lesson:Seq[Lesson]
)
