name := "lift-ng-chat-impress"

organization := "com.joescii"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.4"

resolvers ++= Seq(
  "snapshots"         at "http://oss.sonatype.org/content/repositories/snapshots",
  "staging"           at "https://oss.sonatype.org/service/local/staging/deploy/maven2",
  "releases"          at "http://oss.sonatype.org/content/repositories/releases",
  "CB Central Mirror" at "http://repo.cloudbees.com/content/groups/public"  // Location of Lift SNAPSHOT builds
)

seq(webSettings :_*)

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "2.6-RC1"
  val liftEdition = liftVersion.substring(0,3)
  Seq(
    "net.liftweb"             %% "lift-webkit"                        % liftVersion           % "compile",
    "net.liftmodules"         %% ("lift-jquery-module_"+liftEdition)  % "2.5",
    "net.liftmodules"         %% ("ng-js_"+liftEdition)               % "0.1_1.2.22"          % "compile",
    "net.liftmodules"         %% ("ng_"+liftEdition)                  % "0.5.3"               % "compile",
    "org.eclipse.jetty"       % "jetty-webapp"                        % "8.1.7.v20120910"     % "container,test",
    "org.eclipse.jetty.orbit" % "javax.servlet"                       % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
    "ch.qos.logback"          % "logback-classic"                     % "1.0.6",
    "org.specs2"              %% "specs2"                             % "1.14"                % "test"
  )
}

// Jasmine stuff
seq(jasmineSettings : _*)

appJsDir <+= sourceDirectory { src => src / "main" / "webapp" / "js" }

appJsLibDir <+= sourceDirectory { src => src / "test" / "js" / "3rdlib" }

jasmineTestDir <+= sourceDirectory { src => src /  "test" / "js" }

jasmineConfFile <+= sourceDirectory { src => src / "test" / "js" / "3rdlib" / "test.dependencies.js" }

jasmineRequireJsFile <+= sourceDirectory { src => src / "test" / "js" / "3rdlib" / "require" / "require-2.0.6.js" }

jasmineRequireConfFile <+= sourceDirectory { src => src / "test" / "js" / "3rdlib" / "require.conf.js" }

(Keys.test in Test) <<= (Keys.test in Test) dependsOn (jasmine)
