import scala.slick.driver.{H2Driver, SQLiteDriver}
import scala.slick.jdbc.JdbcBackend.Database

/** Run Slick code with multiple drivers using the Cake pattern. */
object MultiDBCakeExample extends App {

  def run(dal: DAL, db: Database) {
    import dal.driver.simple._

    println("Running test against " + dal.driver)
    db withSession { implicit session: Session =>
      dal.create

      // Creating our default picture
      val defaultPic = dal.insert(Picture("http://pics/default"))
      println("- Inserted picture: " + defaultPic)

      // Inserting users
      println("- Inserted user: " + dal.insert(User("name1", defaultPic)))
      println("- Inserted user: " + dal.insert(User("name2", Picture("http://pics/2"))))
      println("- Inserted user: " + dal.insert(User("name3", defaultPic)))
      println("- All pictures: " + dal.pictures.list)
      println("- All users: " + dal.users.list)
    }
  }

  try {
    run(new DAL(H2Driver),
      Database.forURL("jdbc:h2:mem:test1", driver = "org.h2.Driver"))
    run(new DAL(SQLiteDriver),
      Database.forURL("jdbc:sqlite::memory:", driver = "org.sqlite.JDBC"))
  } finally Util.unloadDrivers
}
