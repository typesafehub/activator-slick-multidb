/** A User contains a name, picture and ID */
case class User(name: String, picture: Picture, id: Option[Int] = None)

/** UserComponent provides database definitions for User objects */
trait UserComponent { this: DriverComponent with PictureComponent =>
  import driver.simple._

  class Users(tag: Tag) extends Table[(String, Int, Option[Int])](tag, "USERS") {
    def id = column[Option[Int]]("USER_ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("USER_NAME", O.NotNull)
    def pictureId = column[Int]("PIC_ID", O.NotNull)
    def * = (name, pictureId, id)
  }
  val users = TableQuery[Users]

  private val usersAutoInc =
    users.map(u => (u.name, u.pictureId)) returning users.map(_.id)

  def insert(user: User)(implicit session: Session): User = {
    val pic =
      if(user.picture.id.isEmpty) insert(user.picture)
      else user.picture
    val id = usersAutoInc.insert(user.name, pic.id.get)
    user.copy(picture = pic, id = id)
  }
}
