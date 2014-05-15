/** A Picture contains an ID and a URL pointing to an image file */
case class Picture(url: String, id: Option[Int] = None)

/** PictureComponent provides database definitions for Picture objects */
trait PictureComponent { this: DriverComponent =>
  import driver.simple._

  class Pictures(tag: Tag) extends Table[Picture](tag, "PICTURES") {
    def id = column[Option[Int]]("PIC_ID", O.PrimaryKey, O.AutoInc)
    def url = column[String]("PIC_URL")
    def * = (url, id) <> (Picture.tupled, Picture.unapply)
  }

  val pictures = TableQuery[Pictures]

  private val picturesAutoInc = pictures returning pictures.map(_.id) into {
    case (p, id) => p.copy(id = id)
  }

  def insert(picture: Picture)(implicit session: Session): Picture =
    picturesAutoInc.insert(picture)
}
