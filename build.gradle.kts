import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  kotlin("multiplatform") version "1.9.21"
  id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.13.2"
  id("org.jetbrains.dokka") version "1.9.10"
  id("com.vanniktech.maven.publish") version "0.25.2"
}

repositories { mavenCentral() }

kotlin {
  explicitApi()

  js(IR) {
    nodejs()
    browser()
  }

  jvm { compilations.configureEach { compilerOptions.options.jvmTarget.set(JvmTarget.JVM_1_8) } }

  iosArm64()
  iosX64()
  iosSimulatorArm64()
  linuxX64()
  macosX64()
  macosArm64()
  mingwX64()

  sourceSets {
    val commonMain by getting { dependencies { implementation(kotlin("stdlib-common")) } }
    val commonTest by getting { dependencies { implementation(kotlin("test")) } }
    all {
      languageSettings.optIn("kotlin.contracts.ExperimentalContracts")
      languageSettings.optIn("kotlin.time.ExperimentalTime")
    }
  }
}

// For future benchmarking.
// testImplementation("com.google.guava:guava:31.1-jre")
