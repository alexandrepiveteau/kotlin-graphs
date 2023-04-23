plugins {
  kotlin("jvm") version "1.8.20"
  id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.13.0"
  id("org.jetbrains.dokka") version "1.8.10"
}

repositories { mavenCentral() }

dependencies {
  implementation(kotlin("stdlib"))
  testImplementation(kotlin("test"))
  // For future benchmarking.
  // testImplementation("com.google.guava:guava:31.1-jre")
}

kotlin {
  explicitApi()
  sourceSets.all { languageSettings.optIn("kotlin.contracts.ExperimentalContracts") }
}
