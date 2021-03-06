package net.cyphoria.weddingapp.specification.seiten

import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentWebElement
import org.scalatest.matchers.ShouldMatchers
import net.cyphoria.weddingapp.specification.persona.Persona
import org.fluentlenium.core.annotation.Page

/**
 *
 * @author Stefan Penndorf <stefan@cyphoria.net>
 */
class StartSeite extends FluentPage with ShouldMatchers {

  var loginname: FluentWebElement = null

  @Page
  var vipAreaWelcomePage: VipAreaStartSeite = null

  override def getUrl: String = "/"

  override def isAt {
    title() should be ("Steffi und Stefan heiraten!")
    $("h1").getText should equal ("Steffi und Stefan heiraten!")
  }

  def anmeldenAls(persona: Persona) = {
    versucheAnmeldungAls(persona)
    vipAreaWelcomePage.isAt()
    vipAreaWelcomePage
  }

  def versucheAnmeldungAls(persona: Persona) = {
    fill(loginname) `with` persona.email
    fill("#passwort") `with` persona.passwort
    submit("#login")
    this
  }

}
