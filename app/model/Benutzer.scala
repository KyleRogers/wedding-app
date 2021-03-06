package model

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current
import org.mindrot.jbcrypt.BCrypt

/**
 *
 * @author Stefan Penndorf <stefan@cyphoria.net>
 */
case class BenutzerName(vorname: String, nachname: String)
case class EMail(email: String)
object EMail {
  implicit def toStringValue(mail: EMail): String = mail.email
  implicit def fromStringValue(email: String): EMail = EMail(email)
}



case class Benutzer(
    id: Pk[Long] = NotAssigned,
    name: BenutzerName,
    email: EMail,
    passwort: Option[String] = None,
    istAdmin: Boolean = false) {

  def this(vorname: String, nachname: String, email: String) = this(NotAssigned, BenutzerName(vorname,nachname), email)

  def freischalten(): KlartextPasswort = {
    val passwort = KlartextPasswort.generate
    DB.withConnection { implicit connection =>
      SQL(
        """
          update users set
             passwort = {passwort}
          where
             email = {email}
        """
      ).on(
        'email -> email.email,
        'passwort -> BCrypt.hashpw(passwort.passwort, BCrypt.gensalt())
      ).executeUpdate()
    }

    passwort
  }

  def istVIP(): Boolean = {
    passwort.isDefined
  }

  def fotoalbum: Option[Fotoalbum] = Fotoalbum.findeFotoalbumVon(this)


}

object Benutzer {

  def authentifiziere(email: String, passwort: String): Option[Benutzer] = {
    (new PersistenteGästeliste).findeGastMitEMail(email).filter {
      case benutzer@Benutzer(_, _, _, Some(benutzerPasswort), _) => BCrypt.checkpw(passwort, benutzer.passwort.get)
      case _ => false
    }
  }

  val simple = {
      get[Long]("users.id") ~
      get[String]("users.email") ~
      get[String]("users.vorname") ~
      get[String]("users.nachname") ~
      get[Option[String]]("users.passwort") ~
      get[Boolean]("users.istAdmin") map {
      case id~email~vorname~nachname~passwort~istAdmin =>
          Benutzer(Id(id), BenutzerName(vorname, nachname), EMail(email), passwort, istAdmin)
    }
  }
}
