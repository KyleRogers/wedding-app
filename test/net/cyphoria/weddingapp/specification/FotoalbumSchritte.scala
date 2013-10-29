package net.cyphoria.weddingapp.specification

import net.cyphoria.weddingapp.specification.infrastructure.{Browser, Schritte}
import cucumber.api.scala.{DE, ScalaDsl}
import net.cyphoria.weddingapp.specification.seiten.{Navigation, VipAreaStartSeite, FotoalbenSeite}
import org.springframework.core.io.ClassPathResource
import java.io.File

/**
 *
 * @author Stefan Penndorf <stefan@cyphoria.net>
 */
class FotoalbumSchritte extends Schritte with ScalaDsl with DE with Browser {

  val bild = new ClassPathResource("images/mara_und_lukas.jpg")
  val bild1 = bild
  val bild2 = new ClassPathResource("images/blumen.png")
  val bild3 = new ClassPathResource("images/mara_und_lukas_mit_kleinem_Fehler.jpg")

  def fotoalben = browser.createPage(classOf[FotoalbenSeite])
  def startseite = browser.createPage(classOf[VipAreaStartSeite])
  def navigation = browser.createPage(classOf[Navigation])

  var albumZip: Option[File] = None

  Angenommen("""^Kerstin ruft die Fotoalben auf$"""){ () =>
    navigation geheZuFotoalbum()
  }

  Angenommen("""^Kerstin hat drei Bilder hochgeladen$"""){ () =>
    navigation geheZuFotoalbum()

    Seq(bild1, bild2, bild3).foreach({cp => fotoalben upload cp; fotoalben go()})
  }

  Angenommen("""^Kerstin ruft ihr Fotoalbum auf$"""){ () =>
    fotoalben oeffneAlbumVon "Kerstin"
  }

  Wenn("""^sie ein Bild hochlädt$"""){ () =>
    fotoalben upload bild
  }

  Wenn("""^sie ein Bild weiter blättert$"""){ () =>
    fotoalben naechstesBild()
  }

  Wenn("""^sie ein Bild zurück blättert$"""){ () =>
    fotoalben vorhergehendesBild()
  }

  Wenn("""^sie ihr Fotoalbum herunterlädt$"""){ () =>
    albumZip = Some(fotoalben ladeAlbumKomplettHerunterMit "Kerstin" )
  }

  Dann("""^wird ein Fotoalbum für sie erstellt$"""){ () =>
    fotoalben oeffneAlbumVon "Kerstin"
  }

  Dann("""^kann sie das erste Foto anschauen$"""){ () =>
    fotoalben zeigtBild bild
  }

  Dann("""^kann sie das zweite Foto anschauen$"""){ () =>
    fotoalben zeigtBild bild2
  }

}
