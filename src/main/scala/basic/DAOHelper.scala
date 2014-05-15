/** Common functionality that needs to work with types from the DAO
  * but in a DAO-independent way.
  */
class DAOHelper(val dao: DAO) {
  import dao.driver.simple._

  def restrictKey(s: String, q: Query[DAO#Props, _]) =
    q.filter(_.key === s)
}
