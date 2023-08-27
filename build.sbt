import org.scalajs.linker.interface.ESVersion
import org.scalajs.linker.interface.OutputPatterns
import org.scalajs.linker.interface.ModuleSplitStyle

lazy val publishSettings = Seq(
  organization := "dev.bluepitaya",
  organizationName := "blue.pitaya",
  organizationHomepage := Some(url("https://bluepitaya.dev")),
  scmInfo :=
    Some(
      ScmInfo(
        url("https://github.com/blue-pitaya/laminar-color"),
        "scm:git@github.com:blue-pitaya/laminar-color.git"
      )
    ),
  developers :=
    List(
      Developer(
        id = "blue.pitaya",
        name = "blue.pitaya",
        email = "blue.pitaya@pm.me",
        url = url("https://bluepitaya.dev")
      )
    ),
  licenses := List(License.MIT),
  homepage := Some(url("https://bluepitaya.dev")),
  description := "Fully customizable color pickers for Laminar library.",
  // Remove all additional repository other than Maven Central from POM
  pomIncludeRepository := { _ =>
    false
  },
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://s01.oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }
)

lazy val baseSettings = Seq(scalaVersion := "2.13.8", version := "1.1")

lazy val root = (project in file("."))
  .settings(baseSettings)
  .settings(publishSettings)
  .settings(
    name := "laminar-color",
    scalacOptions := Seq("-Xlint"),
    libraryDependencies += "dev.bluepitaya" %%% "laminar-dragging" % "1.1",
    libraryDependencies += "com.raquo" %%% "laminar" % "16.0.0",
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.13" % Test
  )
  .enablePlugins(ScalaJSPlugin)

lazy val example = (project in file("example"))
  .dependsOn(root)
  .settings(baseSettings)
  .settings(
    name := "laminar-color-example",
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withOutputPatterns(OutputPatterns.fromJSFile("%s.js"))
        .withESFeatures(_.withESVersion(ESVersion.ES2021))
    },
    scalaJSUseMainModuleInitializer := true,
    Compile / fastLinkJS / scalaJSLinkerOutputDirectory :=
      baseDirectory.value / "ui/sccode/",
    Compile / fullLinkJS / scalaJSLinkerOutputDirectory :=
      baseDirectory.value / "ui/sccode/"
  )
  .enablePlugins(ScalaJSPlugin)
