package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import persistence.PersistenceContext._
import persistence._
import models._
import play.api.data.format.Formats._

object Contacts extends Controller {

  val searchForm = Form[String] (
    "filter" -> text
  )

  val editForm = Form[Contact] (
    mapping(
      "id" -> optional(of[Long]),
      "firstname" -> nonEmptyText,
      "lastname" -> nonEmptyText,
      "phone" -> nonEmptyText,
      "address" -> nonEmptyText
    )(Contact.apply)(Contact.unapply)
  )

  def home(filter : String = "")  = Action {
    withConnection { implicit s =>
      val contacts = ContactStore search "%" + filter + "%"
      Ok(views.html.contacts.home("Contacts", contacts, searchForm))
    }
  }
 
  def search = Action { implicit req =>
    searchForm.bindFromRequest.fold(
      formWithErrors => Redirect(routes.Contacts.home("")),
      filter => Redirect(routes.Contacts.home(filter))
    )
  }

  def create = Action {
    Ok(views.html.contacts.form("New Contact", editForm))
  }

  def edit(id : Long) = Action {
    withConnection { implicit s =>
      ContactStore.findById(id) match {
        case None => BadRequest(<h1>Contact {id} does not extists!</h1>)
        case Some(contact) => 
          val f = editForm.fill(contact)
          Ok(views.html.contacts.form("Edit Contact", f))
      }
    }
  }

  def delete(id : Long) = Action {
    withTransaction { implicit s =>
      ContactStore delete id
      Redirect(routes.Contacts.home(""))
    }
  }

  def save = Action { implicit req =>
    withConnection { implicit s =>
      editForm.bindFromRequest.fold(
        formWithErrors => BadRequest(views.html.contacts.form("Edit Contact", formWithErrors)),
        contact  => {
          if (contact.id == 0) ContactStore.insert(contact) else ContactStore.update(contact)
          Redirect(routes.Contacts.home(""))
        }
      )
    }
  }
      

}
