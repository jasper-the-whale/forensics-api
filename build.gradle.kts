import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.5.21"
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "2.2.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("application")
}

application {
    mainClassName = "com.forensics.api.ApplicationKt"
}

group = "me.user"
version = ""

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    //KOTLIN
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    //LOGGING
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.10")
    //JACKSON
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    //SPRING
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    //TESTING
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testImplementation("io.mockk:mockk:1.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    withType<KotlinCompile>() {
        kotlinOptions.jvmTarget = "1.8"
    }
    withType<Test> {
        useJUnitPlatform()
        filter {
            //exclude all tests from a package.
            excludeTestsMatching("com.forensics.api.*")
        }
    }
    distTar {
        enabled = false
    }
    distZip {
        enabled = false
    }
    bootDistZip {
        enabled = false
    }
}
