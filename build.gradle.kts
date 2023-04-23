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
  ios()
  macosArm64()
  macosX64()
  linuxArm64()
  linuxX64()
  sourceSets {
    val commonMain by getting { dependencies { implementation(kotlin("stdlib-common")) } }
    val commonTest by getting { dependencies { implementation(kotlin("test")) } }
    val jvmMain by getting
    val jvmTest by getting
    val jsMain by getting
    val jsTest by getting
    val iosMain by getting
    val iosTest by getting
    val macosArm64Main by getting
    val macosArm64Test by getting
    val macosX64Main by getting
    val macosX64Test by getting
    val linuxArm64Main by getting
    val linuxArm64Test by getting
    val linuxX64Main by getting
    val linuxX64Test by getting
    all { languageSettings.optIn("kotlin.contracts.ExperimentalContracts") }
  }
}

// For future benchmarking.
// testImplementation("com.google.guava:guava:31.1-jre")
