plugins {
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
}

group = "ru.pxanych.led-site"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

val springBootVersion: String by project.properties

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-websocket:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-log4j2:${springBootVersion}")
}

configurations.all {
    exclude("org.springframework.boot", "spring-boot-starter-logging")
}