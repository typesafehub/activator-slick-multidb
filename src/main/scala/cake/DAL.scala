import slick.driver.JdbcProfile

/** The Data Access Layer contains all components and a driver */
class DAL(val driver: JdbcProfile)
      extends UserComponent with PictureComponent with DriverComponent {
  import driver.api._

  def create =
    (users.schema ++ pictures.schema).create
}
