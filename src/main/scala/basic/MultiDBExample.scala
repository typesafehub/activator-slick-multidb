import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

import slick.dbio.DBIO
import slick.driver.{H2Driver, SQLiteDriver}
import slick.jdbc.JdbcBackend.Database

/** Run SLICK code with multiple drivers. */
object MultiDBExample extends App {

  def run(dao: DAO, db: Database): Future[Unit] = {
    val h = new DAOHelper(dao)
    println("Using driver " + dao.driver)
    db.run(DBIO.seq(
      dao.create,
      dao.insert("foo", "bar"),
      dao.get("foo").map(r => println("- Value for key 'foo': " + r)),
      dao.get("baz").map(r => println("- Value for key 'baz': " + r)),
      h.dao.getFirst(h.restrictKey("foo", dao.props)).map(r => println("- Using the helper: " + r))
    ).withPinnedSession)
  }

  try {
    val f = {
      val h2db = Database.forConfig("h2")
      run(new DAO(H2Driver), h2db).andThen { case _ => h2db.close }
    }.flatMap { _ =>
      val sqlitedb = Database.forConfig("sqlite")
      run(new DAO(SQLiteDriver), sqlitedb).andThen { case _ => sqlitedb.close }
    }

    Await.result(f, Duration.Inf)
  } finally Util.unloadDrivers
}
