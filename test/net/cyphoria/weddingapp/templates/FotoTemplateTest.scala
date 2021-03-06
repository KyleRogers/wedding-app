package net.cyphoria.weddingapp.templates

import org.specs2.mutable.Specification
import play.api.test.Helpers._
import play.api.test.FakeApplication
import model.{Foto, Fotoalbum}
import anorm.Id

/**
 *
 * @author Stefan Penndorf <stefan@cyphoria.net>
 */
class FotoTemplateTest extends Specification {

  val foto = running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
    views.html.foto(Fotoalbum(TERESA, 22), Foto(Id(21), new Array[Byte](11), 21))
  }

  "Die Fotoansicht" should {

    "den Namen des Albumbesitzers ausweisen" in  {
      foto.body must contain("Fotoalbum von Teresa")
    }

    "ermöglicht das Blättern im Album" in  {
      foto.body must contain("Weiter &gt;&gt;&gt;")
      foto.body must contain("&lt;&lt;&lt; Zur&uuml;ck")
    }


    "nicht den Schriftug getOrElse enthalten (Regressionstest)" in  {
      foto.body must not contain("getOrElse")
    }

    "einen Link zur richtigen Seite der Übersicht enthalten" in  {
      foto.body must contain("href=\"/fotoalbum/Teresa.Merfert?seite=2\"")
    }


  }

}
