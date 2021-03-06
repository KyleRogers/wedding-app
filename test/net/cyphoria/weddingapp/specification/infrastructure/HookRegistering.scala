package net.cyphoria.weddingapp.specification.infrastructure


/**
 *
 * @author Stefan Penndorf <stefan@cyphoria.net>
 */
trait HookRegistering {

  def registerGlobalHooks()

  protected def registerGlobal(name: String, value: Any) {
    HookRegistering.registry += name -> value
  }

  protected def unregisterGlobal(name: String) {
    HookRegistering.registry -= name
  }

  def global[T](name: String) = {
    HookRegistering.global[T](name)
  }

}

object HookRegistering {

  var registry: Map[String, Any] = Map()
  var hooksRegistered = false

  def register(that: HookRegistering) {
    if (!hooksRegistered) {
      that.registerGlobalHooks()
      hooksRegistered = true
    }
  }

  def global[T](name: String) = {
    HookRegistering.registry.get(name).get.asInstanceOf[T]
  }

}
