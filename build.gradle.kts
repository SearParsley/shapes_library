plugins {
    kotlin("jvm") version "2.1.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0") // Or the latest stable version
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0") // Or the latest stable version
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
}