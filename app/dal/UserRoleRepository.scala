package dal

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import models.UserRole
import models.Role

import scala.concurrent.{ Future, ExecutionContext }

/**
 * A repository for people.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class UserRoleRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import driver.api._

  private class UserRolesTable(tag: Tag) extends Table[UserRole](tag, "userRole") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[Long]("userId")
    def roleName = column[String]("roleName")
    def roleCode = column[String]("roleCode")
    def * = (id, userId, roleName, roleCode) <> ((UserRole.apply _).tupled, UserRole.unapply)
  }

  private class RolesTable(tag: Tag) extends Table[Role](tag, "roles") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def roleName = column[String]("roleName")
    def roleCode = column[String]("roleCode")
    def * = (id, roleName, roleCode) <> ((Role.apply _).tupled, Role.unapply)
  }


  private val tableUserRole = TableQuery[UserRolesTable]
  private val tableRole = TableQuery[RolesTable]


  def createUserRole(userId: Long, roleName: String, roleCode: String): Future[UserRole] = db.run {
    (tableUserRole.map(p => (p.userId, p.roleName, p.roleCode))
      returning tableUserRole.map(_.id)
      into ((nameAge, id) => UserRole(id, nameAge._1, nameAge._2, nameAge._3))
    ) += (userId, roleName, roleCode)
  }

  def listUserRoles(): Future[Seq[UserRole]] = db.run {
    tableUserRole.result
  }

  // to cpy
  def getUserRoleById(id: Long): Future[Seq[UserRole]] = db.run {
    tableUserRole.filter(_.id === id).result
  }

  // delete required
  def deleteUserRole(id: Long): Future[Seq[UserRole]] = db.run {
    val q = tableUserRole.filter(_.id === id)
    val action = q.delete
    val affectedRowsCount: Future[Int] = db.run(action)
    
    tableUserRole.result
  }


  def createRole(roleName: String, roleCode: String): Future[Role] = db.run {
    (tableRole.map(p => (p.roleName, p.roleCode))
      returning tableRole.map(_.id)
      into ((nameAge, id) => Role(id, nameAge._1, nameAge._2))
    ) += (roleName, roleCode)
  }

  def listRoles(): Future[Seq[Role]] = db.run {
    tableRole.result
  }

  // to cpy
  def getRoleById(id: Long): Future[Seq[Role]] = db.run {
    tableRole.filter(_.id === id).result
  }

  // to cpy
  def getRoleByCode(roleCode: String): Future[Seq[Role]] = db.run {
    tableRole.filter(_.roleCode === roleCode).result
  }

  // delete required
  def deleteRole(id: Long): Future[Seq[Role]] = db.run {
    val q = tableRole.filter(_.id === id)
    val action = q.delete
    val affectedRowsCount: Future[Int] = db.run(action)
    
    tableRole.result
  }
   
}
