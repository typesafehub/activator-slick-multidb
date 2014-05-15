import scala.slick.driver.JdbcProfile

/** The Data Access Layer contains all components and a driver */
class DAL(val driver: JdbcProfile)
      extends UserComponent with PictureComponent with DriverComponent {
  import driver.simple._

  def create(implicit session: Session) =
    (users.ddl ++ pictures.ddl).create
}
