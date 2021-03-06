package net.cyphoria.weddingapp.specification

import net.cyphoria.weddingapp.specification.infrastructure.{Browser, Schritte}
import cucumber.api.scala.{DE, ScalaDsl}
import org.scalatest.matchers.ShouldMatchers._
import net.cyphoria.weddingapp.specification.persona.Persona._
import net.cyphoria.weddingapp.specification.persona.Persona

/**
 *
 * @author Stefan Penndorf <stefan@cyphoria.net>
 */
class AnmeldeSchritte extends Schritte with ScalaDsl with DE with Browser {

  Angenommen("""^(\w*) hat sich angemeldet$"""){ personaName: String =>
    browser goTo startSeite anmeldenAls Persona.fromName(personaName)
  }

  Wenn("""^Kerstin versucht sich anzumelden$"""){ () =>
    browser goTo startSeite versucheAnmeldungAls Kerstin
  }

  Dann("""^wird Kerstin die Anmeldung verweigert$"""){ () =>
    browser.$(".error").getTexts.toArray.mkString("") should include ("nicht freigeschaltet")
  }

  Dann("""^kann sich Kerstin mit dem neuen Passwort anmelden$"""){ () =>
    browser goTo startSeite anmeldenAls Kerstin.mitPasswort(AnmeldeContext.kerstinsNeuesPasswort)
  }




}

object AnmeldeContext {
  var kerstinsNeuesPasswort: String = "unbekannt"
}