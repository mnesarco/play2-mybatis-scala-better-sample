package persistence

import org.mybatis.scala.config._
import org.mybatis.scala.session.Session
import play.api.Play.current
import play.api.db.DB._

object PersistenceContext {

  //== Define mybatis session configuration ==//
  val conf = 
    Configuration(
      Environment(
        "default", 
        new ManagedTransactionFactory(),
        getDataSource()
      )
  )

  //== Register managed DAOs ==//
  conf ++= ContactStore

  //== Init mybatis context ==//
  val mybatis = conf.createPersistenceContext

  //== Define a few methods to use in your Play code ==//
  
  def withConnection[A] (block: Session => A) : A = mybatis.readOnly(block)

  def withTransaction[A] (block: Session => A) : A = mybatis.transaction(block)

}

