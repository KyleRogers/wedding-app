package model

import play.api.db.DB
import anorm._
import play.api.Play.current

/**
 *
 * @author Stefan Penndorf <stefan@cyphoria.net>
 */
case class HochzeitsGastBewerbung(vorname: String, nachname: String, email: String) {
  def benutzerName = BenutzerName(vorname, nachname)
}

object Bewerber {
  def bewirbtSichMit(bewerbung: HochzeitsGastBewerbung) {
    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into users
          ( email,   vorname,   nachname) values
          ({email}, {vorname}, {nachname})
        """
      ).on(
        'email -> bewerbung.email,
        'vorname -> bewerbung.vorname,
        'nachname -> bewerbung.nachname
      ).executeUpdate()
    }
  }
}