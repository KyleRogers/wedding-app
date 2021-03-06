package model

import scala.util.Random

/**
 *
 * @author Stefan Penndorf <stefan@cyphoria.net>
 */
case class KlartextPasswort private(passwort: String) {
  override def toString = passwort
}

object KlartextPasswort {
  def generate = {
    val length = 8
    val chars = ('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9') ++ ("-!")
    val randomPasswort = (1 to length).map(
      x => chars(Random.nextInt(chars.length))
    ).mkString("")
    KlartextPasswort(randomPasswort)
  }
}
