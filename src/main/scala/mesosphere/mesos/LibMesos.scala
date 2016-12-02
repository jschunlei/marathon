package mesosphere.mesos

import mesosphere.marathon.util.SemanticVersion
import org.apache.mesos.MesosNativeLibrary

object LibMesos {

  /**
    * The minimum version of 1.1.0 is currently set to support pods.
    */
  val MesosMasterMinimumVersion = SemanticVersion(1, 1, 0)

  /**
    * The minimum version of 1.1.0 is currently set to support pods.
    */
  val LibMesosMinimumVersion = SemanticVersion(1, 1, 0)

  /**
    * Try to load the libmesos version.
    * @return SemanticVersion if libmesos is found a version could be read.
    */
  lazy val version: SemanticVersion = {
    // This can throw an java.lang.UnsatisfiedLinkError, that can not be handled
    MesosNativeLibrary.load()
    val version = MesosNativeLibrary.version()
    SemanticVersion(version.major.toInt, version.minor.toInt, version.patch.toInt)
  }

  /**
    * Indicates if this version of libmesos is compatible
    */
  def isCompatible: Boolean = version >= LibMesosMinimumVersion

  /**
    * Indicates if the given version of Mesos Master is compatible.
    */
  def masterCompatible(masterVersion: String): Boolean = {
    SemanticVersion(masterVersion).exists(_ >= MesosMasterMinimumVersion)
  }
}
