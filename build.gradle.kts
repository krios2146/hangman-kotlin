plugins {
    kotlin("jvm") version "1.9.22"
}

group = "dev.krios2146"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(19)
}