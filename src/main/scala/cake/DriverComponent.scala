import slick.driver.JdbcProfile

/** The slice of the cake which provides the Slick driver */
trait DriverComponent {
  val driver: JdbcProfile
}
