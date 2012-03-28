package models

class Contact {
  var id : Long = _
  var firstname : String = _
  var lastname : String = _
  var phone : String = _
  var address : String = _
}

object Contact {

  def apply(id: Option[Long], firstname: String, lastname: String, phone: String, address: String) = {
    val c = new Contact
    c.id = id.getOrElse(0)
    c.firstname = firstname
    c.lastname = lastname
    c.phone = phone
    c.address = address
    c
  }

  def unapply(c : Contact) = Some((Option(c.id), c.firstname, c.lastname, c.phone, c.address))

}
