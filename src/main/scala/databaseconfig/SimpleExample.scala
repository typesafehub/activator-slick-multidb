import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

object SimpleExample extends App {

  val dc = DatabaseConfig.forConfig[JdbcProfile]("h2_dc")

  // Import the JdbcProfile API from the configured driver
  import dc.driver.api._

  class Props(tag: Tag) extends Table[(String, String)](tag, "PROPS") {
    def key = column[String]("KEY", O.PrimaryKey)
    def value = column[String]("VALUE")
    def * = (key, value)
  }
  val props = TableQuery[Props]

  def get(key: String): DBIO[Option[String]] =
    props.filter(_.key === key).map(_.value).result.headOption

  try {
    // Initialize the Database
    val db = dc.db
    val f = db.run(DBIO.seq(
      props.schema.create,
      props += ("foo", "bar"),
      get("foo").map(r => println("- Value for key 'foo': " + r)),
      get("baz").map(r => println("- Value for key 'baz': " + r))
    ).withPinnedSession)
    val f2 = f andThen { case _ => db.close }
    Await.result(f2, Duration.Inf)
  } finally Util.unloadDrivers
}
