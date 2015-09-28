import scala.language.higherKinds
import slick.driver.JdbcProfile

/** All database code goes into the DAO (data access object) class which
  * is parameterized by a Slick driver that implements JdbcProfile.
  */
class DAO(val driver: JdbcProfile) {
  // Import the Scala API from the driver
  import driver.api._

  class Props(tag: Tag) extends Table[(String, String)](tag, "PROPS") {
    def key = column[String]("KEY", O.PrimaryKey)
    def value = column[String]("VALUE")
    def * = (key, value)
  }
  val props = TableQuery[Props]

  /** Create the database schema */
  def create: DBIO[Unit] =
    props.schema.create

  /** Insert a key/value pair */
  def insert(k: String, v: String): DBIO[Int] =
    props += (k, v)

  /** Get the value for the given key */
  def get(k: String): DBIO[Option[String]] =
    (for(p <- props if p.key === k) yield p.value).result.headOption

  /** Get the first element for a Query from this DAO */
  def getFirst[M, U, C[_]](q: Query[M, U, C]): DBIO[U] =
    q.result.head
}
