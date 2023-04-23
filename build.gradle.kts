plugins {
  kotlin("multiplatform") version "1.8.20"
  id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.13.0"
  id("org.jetbrains.dokka") version "1.8.10"
}

repositories { mavenCentral() }

kotlin {
  explicitApi()
  jvm()
  js(IR) {
    browser()
    nodejs()
  }
  sourceSets {
    val commonMain by getting { dependencies { implementation(kotlin("stdlib-common")) } }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
      }
    }
    val jvmMain by getting
    val jvmTest by getting { dependencies { implementation(kotlin("test-junit")) } }
    val jsMain by getting
    val jsTest by getting { dependencies { implementation(kotlin("test-js")) } }
    all { languageSettings.optIn("kotlin.contracts.ExperimentalContracts") }
  }
}

// For future benchmarking.
// testImplementation("com.google.guava:guava:31.1-jre")
