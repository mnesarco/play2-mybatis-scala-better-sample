package persistence

import org.mybatis.scala.mapping._
import models._

object ContactStore {

  val search = new SelectListBy[String,Contact] {
    def xsql = <xsql>
      SELECT * 
      FROM contact
      WHERE 
        lower(firstname) LIKE lower(#{{value}})
        OR lower(lastname) LIKE lower(#{{value}})
      ORDER BY lastname
    </xsql>
  }

  val findById = new SelectOneBy[Long,Contact] {
    def xsql = <xsql>
      SELECT *
      FROM contact
      WHERE id = #{{id}}
    </xsql>
  }

  val update = new Update[Contact] {
    def xsql = <xsql>
      UPDATE contact 
      SET firstname = #{{firstname}}, lastname = #{{lastname}}, phone = #{{phone}}, address = #{{address}}
      WHERE id = #{{id}}
    </xsql>
  }

  val insert = new Insert[Contact] {
    def xsql = <xsql>
      INSERT INTO contact (firstname, lastname, phone, address)
      VALUES (#{{firstname}}, #{{lastname}}, #{{phone}}, #{{address}})
    </xsql>
  }

  val delete = new Delete[Long] {
    def xsql = <xsql>DELETE FROM contact WHERE id = #{{id}}</xsql>
  }

  def bind = Seq(search, findById, update, insert, delete)

}
