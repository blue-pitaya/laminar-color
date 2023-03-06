import org.scalajs.linker.interface.ESVersion
import org.scalajs.linker.interface.OutputPatterns
import org.scalajs.linker.interface.ModuleSplitStyle

lazy val baseSettings = Seq(
  organization := "xyz.bluepitaya",
  scalaVersion := "2.13.8",
  version := "1.0"
)

val publishing = Seq(
  // publishing
  publishMavenStyle := true,
  Test / publishArtifact := false,
  pomIncludeRepository := (_ ⇒ false)
)

lazy val root = (project in file("."))
  .settings(baseSettings)
  .settings(
    name := "laminar-color",
    scalacOptions :=
      Seq(
        // "-Xlint"
      ),
    libraryDependencies += "xyz.bluepitaya" %%% "common-utils" % "1.0",
    libraryDependencies += "com.raquo" %%% "laminar" % "15.0.0-M7",
    libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.13" % Test,
    publishing
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
